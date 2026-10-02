"""
Claude Code SDK — IMPROVE-AND-REPAIR agent driver (self-contained).

Open this file to see exactly which Claude Code SDK calls we make. Cross-
reference each section against the official documentation:

    https://docs.claude.com/en/api/agent-sdk/python
    https://docs.claude.com/en/docs/claude-code/skills

The file is intentionally NOT split across helper modules — every function
the Claude path needs lives here (skill loading, sandbox build, prompt
assembly, state.json writer, message serialization, async fan-out). This
mirrors what the SDK docs describe in one place and isolates Claude
specifics from the Codex driver in `codex_agent_with_repair.py`.

Architecture (top → bottom):
  1.  Public dataclasses                AgentTarget
  2.  Skill loading (SKILL.md → dict)   resolve_skill_path / load_skill_cards / …
  3.  Prompt assembly                   compose_prompt_with_skill_cards / build_*_prompt
  4.  Sandbox creation                  build_sandbox + compile.sh
  5.  State.json writer                 write_agent_state
  6.  SDK message serialization         serialize_message + helpers
  7.  Compile-log extraction            extract_compile_log
  8.  ★  THE Claude SDK call            run_claude_session_for_one_target
  9.  Async fan-out driver              _run_async / run()
"""
from __future__ import annotations

import asyncio
import math
import json
import logging
import os
import shutil
import subprocess
import tempfile
from dataclasses import dataclass
from importlib.metadata import PackageNotFoundError, version as package_version
from pathlib import Path
from typing import Any, Awaitable, Callable, Optional

import build_executor
import coverage_runner
import llm_refactor

log = logging.getLogger(__name__)

PROJECT_ROOT = Path(__file__).parent.parent
REQUIRED_CLAUDE_AGENT_SDK_VERSION = "0.2.152"


# ═════════════════════════════════════════════════════════════════════════════
# 1.  Public dataclasses
# ═════════════════════════════════════════════════════════════════════════════

@dataclass
class AgentTarget:
    """One test the agent needs to improve. Created by main._iter_improvement_targets."""
    test_category: str       # "manual_suite" | "manual_cases" | "auto_suite" | "auto_cases"
    test_id: str             # file-name prefix in the flat data/ layout
    suite_class: str         # parent suite class — for cases names the testcases/<SuiteClass>/ folder
    java_path: str           # absolute path to the source test file
    is_evosuite: bool
    scaffolding_java: str    # empty string when not evosuite


# ═════════════════════════════════════════════════════════════════════════════
# 2.  Skill loading — read .claude/skills/<name>/SKILL.md, split frontmatter
# ═════════════════════════════════════════════════════════════════════════════
#
# We use BODY-INJECTION rather than the lazy Skill-discovery mechanism: the
# SKILL.md body is concatenated into the user prompt so the model has the
# complete instructions from turn 1. This is intentional for cross-model
# reproducibility (Codex doesn't share Claude's Skill tool). See:
# https://docs.claude.com/en/docs/claude-code/skills

def resolve_skill_path(mode: str, cfg=None) -> Optional[Path]:
    """Return the path to .claude/skills/<dir>/SKILL.md for one mode."""
    mode_key = str(mode).strip().lower().replace("-", "_")
    cfg_section = getattr(cfg, "agent", None)

    if mode_key in {"compile", "compile_check", "coverage", "compile_coverage"}:
        explicit_attr = "compile_check_skill_path"
        skill_dir_name = "compile-check"
    elif mode_key in {"repair", "with_repair"}:
        explicit_attr = "repair_skill_path"
        skill_dir_name = "repair-loop"
    else:
        raise ValueError(f"Unsupported skill mode '{mode}'.")

    explicit = getattr(cfg_section, explicit_attr, None) if cfg_section else None
    if explicit:
        candidate = Path(str(explicit)).expanduser()
        if candidate.is_file():
            return candidate

    default_path = PROJECT_ROOT / ".claude" / "skills" / skill_dir_name / "SKILL.md"
    return default_path if default_path.is_file() else None


def _parse_skill_frontmatter(text: str) -> dict:
    """Extract simple YAML frontmatter keys (name, description) from a SKILL.md."""
    src = (text or "").strip()
    if not src.startswith("---"):
        return {}
    lines = src.splitlines()
    end = None
    for idx in range(1, len(lines)):
        if lines[idx].strip() == "---":
            end = idx
            break
    if end is None:
        return {}
    meta: dict[str, str] = {}
    for line in lines[1:end]:
        if ":" not in line:
            continue
        key, value = line.split(":", 1)
        meta[key.strip()] = value.strip().strip('"').strip("'")
    return meta


def _strip_skill_frontmatter(text: str) -> str:
    """Remove YAML frontmatter before injecting SKILL.md into a prompt."""
    src = (text or "").strip()
    if not src.startswith("---"):
        return src
    lines = src.splitlines()
    if len(lines) < 3:
        return src
    for idx in range(1, len(lines)):
        if lines[idx].strip() == "---":
            return "\n".join(lines[idx + 1:]).strip()
    return src


def load_skill_card(mode: str, cfg=None) -> dict:
    """Return {'mode', 'path', 'name', 'description', 'body'} for one skill."""
    skill_path = resolve_skill_path(mode, cfg)
    raw = ""
    meta: dict[str, str] = {}
    if skill_path and skill_path.is_file():
        try:
            raw = skill_path.read_text(encoding="utf-8").strip()
            meta = _parse_skill_frontmatter(raw)
        except Exception as e:
            log.warning(f"Could not read {mode} skill from {skill_path}: {e}")
    return {
        "mode": mode,
        "path": str(skill_path) if skill_path else "",
        "name": meta.get("name") or "",
        "description": meta.get("description") or "",
        "body": _strip_skill_frontmatter(raw) if raw else "",
    }


def load_skill_cards(modes: list[str], cfg=None) -> list[dict]:
    """Load skill cards in the given order."""
    return [load_skill_card(m, cfg) for m in modes]


# ═════════════════════════════════════════════════════════════════════════════
# 3.  Prompt assembly — sandbox layout + skill bodies + task template + mode
# ═════════════════════════════════════════════════════════════════════════════

# Note: workflow + stopping conditions live in the skill SKILL.md files
# (.claude/skills/compile-check/SKILL.md and repair-loop/SKILL.md).
# We previously also inlined a REPAIR_INSTRUCTIONS block in the user prompt
# which duplicated those files. Deleted on the principle that the Anthropic
# Skill mechanism is the canonical place for workflow + stopping conditions
# (verified via Claude Code docs Q1+Q3 audit).


def build_filled_template(prompt_version: str, test_java_path: str,
                           cut_source_path: Optional[Path]) -> str:
    """Render /prompts/<version>.txt (Definition + Task) and splice the
    ABSOLUTE path of the original test INLINE into the Task sentence, right
    after the backticked test name:

        Task: Improve the understandability of the test `Foo.java` \
(path:- /abs/Foo.java). The refactored code …

    The path is NOT a separate block at the top of the message. The CUT-source
    path lives in the per-CUT system prompt (see build_system_prompt_for_cut);
    only the per-test original-test path is here, so the system prompt stays
    byte-identical across one CUT's tests. EvoSuite scaffolding is intentionally
    not surfaced (the compile step supplies it on the classpath). The template
    file's own wording is left untouched — the path is spliced into its rendered
    output here. (cut_source_path is accepted for signature compatibility but
    the CUT path is no longer emitted in the user message.)"""
    template = llm_refactor.load_prompt_template(prompt_version)
    test_file = Path(test_java_path).name
    task_text = llm_refactor.build_improvement_prompt(
        template,
        test_file=test_file,
        cut_file="",          # CUT path is in the system prompt, not inline here
    )

    # Splice the original-test path in right after the backticked test name.
    original_abs = str(Path(test_java_path).resolve())
    token = f"`{test_file}`"
    if token in task_text:
        task_text = task_text.replace(token, f"{token} (path:- {original_abs})", 1)
    else:
        # Template did not reference the test name — fall back to a trailing note.
        task_text = f"{task_text}\n\n(original test path: {original_abs})"
    return task_text


# ── EvoSuite guard-rails (Option B: lives in the USER message, not the system
#    prompt). Empirically (35-mismatch root-cause analysis on the 06-04 run),
#    EVERY real coverage change came from the agent "cleaning up" EvoSuite
#    harness elements it mistook for dead code: deleting `import org.evosuite.*`,
#    adding/removing `@RunWith(EvoRunner.class)`, even deleting a whole @Test
#    method. A generic "preserve runtime behaviour" instruction did NOT prevent
#    this — the agent doesn't realise these elements affect execution — so the
#    forbidden elements are named explicitly.
#
#    Why the USER message and not the system prompt: we want ONE agent applied
#    uniformly to a CUT's manual AND auto tests. Keeping these rules out of the
#    system prompt makes the system_prompt BYTE-IDENTICAL for manual and auto
#    sessions of the same CUT (one prompt-cache prefix, one "agent"); the
#    EvoSuite-specific rule becomes part of the per-test TASK instead — appended
#    to the first user message only when the target carries scaffolding.
EVOSUITE_TASK_CONSTRAINTS = """

────────────────────────────────────────────────────────────────────────
IMPORTANT — this is an EvoSuite-generated test. An EvoSuite companion file \
`<…>_scaffolding.java` sits in your cwd as a Read-only helper. You MUST NOT:
- remove, add, or modify any `import org.evosuite.*` line (even if it looks \
unused);
- add, remove, or change `@RunWith(...)`, `@EvoRunnerParameters`, or any \
runner annotation;
- change the `extends ..._scaffolding` clause;
- touch any EvoSuite mock/runtime setup (EvoAssertions, mock helpers, \
system-property setup)."""


def build_system_prompt_for_cut(*, cut_class_simple: str = "",
                                  has_cut_reference: bool = True,
                                  cut_path: str = "",
                                  original_test_path: str = "",
                                  scaffolding_path: str = "") -> dict:
    """Build the per-CUT system_prompt (preset+append form).

    The append text names the CUT-source path only — this is stable for a given
    class, so the system_prompt is BYTE-IDENTICAL across all of one CUT's tests
    → a per-CUT Anthropic prompt-cache prefix. What does NOT go here (because it
    varies per test, which would fragment the cache):
      • the original-test path → lives in the per-test USER message
        (see build_filled_template);
      • EvoSuite scaffolding → no longer surfaced to the agent at all.

    original_test_path / scaffolding_path / cut_class_simple / has_cut_reference
    are accepted for caller compatibility but unused — edit
    prompts/improve_system_prompt.txt to change behaviour, no code change needed.
    """
    template = (PROJECT_ROOT / "prompts" / "improve_system_prompt.txt").read_text(
        encoding="utf-8")
    append_text = template.replace("{cut_path}", cut_path or "(not provided)")
    return {
        "type": "preset",
        "preset": "claude_code",
        "append": append_text,
    }


# Note: the old `compose_prompt_with_skill_cards` (eager body-injection)
# function was removed. With system_prompt={"type":"preset","preset":
# "claude_code", ...} + setting_sources=["project"], the SDK automatically
# injects every SKILL.md's metadata (level-1 of the Anthropic 3-tier model)
# into the system context. The model invokes the Skill tool to fetch full
# bodies on demand — no need to dump them into the user prompt.


# ═════════════════════════════════════════════════════════════════════════════
# 4.  Sandbox creation — test + CUT reference + scaffolding + compile.sh
# ═════════════════════════════════════════════════════════════════════════════

def javac_path_from_cfg(cfg) -> str:
    """Resolve javac the same way main._build_cp_and_compile_fn does."""
    java_home = getattr(cfg.tools, "java_home_compile", "")
    if java_home:
        return str(Path(java_home) / "bin" / "javac")
    java_path = getattr(cfg.tools, "java_path", "") or "java"
    return java_path.replace("/java", "/javac") if "/java" in java_path else "javac"


def project_dir_from_classpath(classpath: str) -> Optional[Path]:
    """Derive project root from a classpath that includes target/{,test-}classes."""
    for entry in (classpath or "").split(":"):
        entry = entry.strip()
        if not entry:
            continue
        p = Path(entry)
        if p.name in ("classes", "test-classes") and p.parent.name == "target":
            return p.parent.parent
    return None


def build_sandbox_classpath(target: AgentTarget, project_classpath: str,
                             compiled_dir: str, cfg) -> str:
    """Build the classpath the sandbox's compile.sh will pass to javac."""
    if target.is_evosuite:
        return coverage_runner.build_evosuite_classpath(
            project_classpath, compiled_dir, cfg
        )
    return coverage_runner.build_manual_classpath(
        project_classpath, compiled_dir, cfg
    )


def build_sandbox(*, target: AgentTarget, cut_source_path: Optional[Path],
                   classpath: str, javac_path: str,
                   sandbox: Optional[Path] = None,
                   classes_out: Optional[Path] = None,
                   release_version: Optional[int] = None,
                   ) -> tuple[Path, Path, Path]:
    """Set up the agent's WORKING DIRECTORY (= the per-test improved output
    folder). Does NOT copy the CUT / original test / scaffolding — those are
    referenced by ABSOLUTE path (read-only). Only compile.sh + the .claude
    skill symlink are written here; the agent WRITES its improved test as a new
    file (same basename as the original) into this dir.
    Returns (working_dir, output_test_path, classes_out_dir)."""
    if sandbox is None:
        sandbox = Path(tempfile.mkdtemp(prefix="agent_repair_"))
    sandbox.mkdir(parents=True, exist_ok=True)
    if classes_out is None:
        classes_out = sandbox / "test-classes"
    classes_out.mkdir(parents=True, exist_ok=True)

    # The agent WRITES its improved test here (same basename as the original so
    # the public class name matches → compiles in its place). NOT pre-seeded.
    output_test = sandbox / Path(target.java_path).name

    # compile.sh compiles the OUTPUT file. EvoSuite tests need the scaffolding
    # on the compile path — referenced by ABSOLUTE path (not copied in).
    extra_files: list[str] = []
    if target.scaffolding_java:
        scaf_src = Path(target.scaffolding_java)
        if scaf_src.exists():
            extra_files.append(str(scaf_src.resolve()))

    # Lazy-load skills: expose .claude/ from the project root to the SDK by
    # symlinking it into the working dir (SDK discovers skills relative to cwd).
    project_claude_dir = PROJECT_ROOT / ".claude"
    sandbox_claude_dir = sandbox / ".claude"
    if project_claude_dir.exists() and not sandbox_claude_dir.exists():
        try:
            sandbox_claude_dir.symlink_to(project_claude_dir, target_is_directory=True)
        except Exception as e:
            log.warning(f"  could not symlink .claude into working dir: {e}")

    # compile.sh — the one-line interface the agent uses to verify its output.
    release_flag = f"--release {int(release_version)} " if release_version else ""
    files_arg = " ".join([f'"{output_test.name}"'] + [f'"{f}"' for f in extra_files])
    compile_sh = sandbox / "compile.sh"
    compile_sh.write_text(
        "#!/usr/bin/env bash\n"
        "# Compile-check the improved test in this working directory.\n"
        "set -u\n"
        f"JAVAC={json.dumps(javac_path)}\n"
        f"CP={json.dumps(classpath)}\n"
        f"OUT={json.dumps(str(classes_out))}\n"
        f'"$JAVAC" {release_flag}-encoding UTF-8 -cp "$CP" -d "$OUT" {files_arg}\n'
        'rc=$?\n'
        'if [ "$rc" -eq 0 ]; then\n'
        '  echo "COMPILE_OK"\n'
        'else\n'
        '  echo "COMPILE_FAIL (exit=$rc)"\n'
        'fi\n'
        'exit $rc\n',
        encoding="utf-8",
    )
    compile_sh.chmod(0o755)

    return sandbox, output_test, classes_out


# ═════════════════════════════════════════════════════════════════════════════
# 5.  state.json writer (consumed by main.run_compile_improvement_outputs)
# ═════════════════════════════════════════════════════════════════════════════

def append_session_jsonl(path: Path, msg_dict: dict) -> None:
    """Append one serialized message as a JSONL line. Creates the file if needed."""
    path.parent.mkdir(parents=True, exist_ok=True)
    with path.open("a", encoding="utf-8") as f:
        f.write(json.dumps(msg_dict, ensure_ascii=False) + "\n")


def write_agent_state(out_dir: Path, *,
                       file_prefix: str = "",
                       initial_prompt: str,
                       final_assistant_text: str,
                       final_code: str,
                       final_java_path: str,
                       serialized_messages: list[dict],
                       target_meta: dict,
                       model_key: str,
                       prompt_version: str,
                       extra: Optional[dict] = None,
                       request_metadata: Optional[dict] = None) -> dict:
    """Build the canonical state dict and write state.json + supporting files.

    Returns the state dict. Status is always PENDING — the compile is owned
    by the separate `compile_improvement_outputs` step in main.py.

    With `file_prefix` set, every per-test file is written as
    `<file_prefix>_<base>` so multiple tests can share a flat folder."""
    state = {
        "status": "PENDING",
        "attempts_used": 1,
        "model": model_key,
        "prompt_version": prompt_version,
        "messages": [
            {"role": "user",      "content": initial_prompt},
            {"role": "assistant", "content": final_assistant_text},
        ],
        "last_code": final_code,
        "last_error": "",
        "last_java_path": str(Path(final_java_path).resolve()) if final_java_path else "",
        "initial_prompt": initial_prompt,
        "improvement_mode": (extra or {}).get("improvement_mode", "agent"),
        "file_prefix": file_prefix,
    }
    state.update(target_meta or {})
    if extra:
        state.update(extra)

    out_dir.mkdir(parents=True, exist_ok=True)

    def _p(base: str) -> Path:
        return out_dir / (f"{file_prefix}_{base}" if file_prefix else base)

    # Original test → <out_dir>/original/<ClassName>.java (preserves the
    # original filename so both files keep matching Java class declarations
    # and can be compiled / diffed independently).
    original_java = str((target_meta or {}).get("original_java", "") or "")
    if original_java and Path(original_java).exists():
        original_subdir = out_dir / "original"
        original_subdir.mkdir(parents=True, exist_ok=True)
        shutil.copy2(original_java, original_subdir / Path(original_java).name)
    _p("prompt.txt").write_text(initial_prompt, encoding="utf-8")
    llm_refactor.save_state(state, _p("state.json"))

    # Persist the full session as JSONL (one msg per line) for analysis
    session_path = _p("session.jsonl")
    if session_path.exists():
        session_path.unlink()
    if serialized_messages:
        for m in serialized_messages:
            append_session_jsonl(session_path, m)
    else:
        append_session_jsonl(session_path, {
            "type": "SyntheticUserPrompt", "role": "user",
            "content": initial_prompt,
        })
        append_session_jsonl(session_path, {
            "type": "SyntheticAssistantMessage", "role": "assistant",
            "content": final_assistant_text,
        })

    trace_meta = {
        "model": model_key,
        "prompt_version": prompt_version,
        "experiment_mode": (extra or {}).get("improvement_mode", "agent"),
        "test_id": (target_meta or {}).get("test_id", "") or file_prefix,
    }
    _p("trace.txt").write_text(
        render_agent_session_trace(serialized_messages, meta=trace_meta),
        encoding="utf-8",
    )

    return state


# ═════════════════════════════════════════════════════════════════════════════
# 6.  Claude Code SDK message serialization (SDK objects → plain dicts)
# ═════════════════════════════════════════════════════════════════════════════
#
# The SDK yields AssistantMessage / UserMessage / SystemMessage / ResultMessage
# objects. Each carries .content blocks (TextBlock / ToolUseBlock / ToolResult-
# Block). We convert them to JSON-serializable dicts so the full transcript
# can be persisted to session.jsonl. Schema reference:
# https://docs.claude.com/en/api/agent-sdk/python (search "AssistantMessage")

def serialize_message(msg: Any) -> dict:
    """Convert one SDK message object into a plain dict suitable for JSONL."""
    cls = type(msg).__name__
    out: dict[str, Any] = {"type": cls}

    for attr in ("session_id", "subtype", "result", "model"):
        v = getattr(msg, attr, None)
        if v is not None:
            out[attr] = v if isinstance(v, (str, int, float, bool)) else str(v)

    content = getattr(msg, "content", None)
    if content is not None:
        out["content"] = _serialize_content(content)

    # ResultMessage often carries usage / cost / num_turns metadata
    for attr in ("num_turns", "duration_ms", "duration_api_ms",
                 "total_cost_usd", "is_error", "stop_reason",
                 "api_error_status"):
        v = getattr(msg, attr, None)
        if v is not None:
            out[attr] = v

    # Dict-typed observability fields (ResultMessage has these in SDK ≥ 0.2)
    for attr in ("usage", "model_usage"):
        v = getattr(msg, attr, None)
        if v is not None:
            try:
                json.dumps(v)
                out[attr] = v
            except (TypeError, ValueError):
                out[attr] = str(v)

    # RateLimitEvent (SDK ≥ 0.2.x): flatten rate_limit_info for audit/log
    rli = getattr(msg, "rate_limit_info", None)
    if rli is not None:
        out["rate_limit_info"] = {
            "status": getattr(rli, "status", None),
            "rate_limit_type": getattr(rli, "rate_limit_type", None),
            "utilization": getattr(rli, "utilization", None),
            "resets_at": getattr(rli, "resets_at", None),
            "overage_status": getattr(rli, "overage_status", None),
        }

    if "content" not in out and "result" not in out:
        out["repr"] = repr(msg)
    return out


def _serialize_content(content: Any) -> Any:
    if isinstance(content, str):
        return content
    if isinstance(content, list):
        return [_serialize_block(b) for b in content]
    return str(content)


def _serialize_block(block: Any) -> dict:
    """Convert one content block (TextBlock / ToolUseBlock / ToolResultBlock)
    into a plain dict by reading useful attributes by name."""
    cls = type(block).__name__
    out: dict[str, Any] = {"type": cls}
    for attr in ("text", "thinking", "signature", "name", "input", "tool_use_id",
                 "is_error", "content", "id"):
        v = getattr(block, attr, None)
        if v is None:
            continue
        if isinstance(v, (str, int, float, bool)):
            out[attr] = v
        elif isinstance(v, (list, dict)):
            try:
                json.dumps(v)
                out[attr] = v
            except (TypeError, ValueError):
                out[attr] = str(v)
        else:
            out[attr] = str(v)
    return out


def extract_thinking_usage(serialized_messages: list[dict]) -> dict:
    """Summarize persisted Claude ``ThinkingBlock`` content.

    SDK 0.2.136+ / Claude Code 2.1.228+ can expose the provider's reasoning
    breakdown at ``usage.output_tokens_details.thinking_tokens``.  Preserve that
    value as the primary metric while retaining a deterministic estimate from
    the visible ThinkingBlock text for auditing older runs and summarized
    thinking responses.
    """
    parts: list[str] = []
    block_count = 0
    for message in serialized_messages:
        if message.get("type") != "AssistantMessage":
            continue
        for block in message.get("content", []) or []:
            if block.get("type") != "ThinkingBlock":
                continue
            block_count += 1
            value = block.get("thinking")
            if isinstance(value, str) and value:
                parts.append(value)

    text = "\n\n".join(parts)
    utf8_bytes = len(text.encode("utf-8"))
    provider_tokens = None
    total_output_tokens = None
    for message in reversed(serialized_messages):
        if message.get("type") != "ResultMessage":
            continue
        usage = message.get("usage") or {}
        details = usage.get("output_tokens_details") or {}
        candidate = details.get("thinking_tokens")
        if isinstance(candidate, (int, float)) and not isinstance(candidate, bool):
            provider_tokens = int(candidate)
        total = usage.get("output_tokens")
        if isinstance(total, (int, float)) and not isinstance(total, bool):
            total_output_tokens = int(total)
        break
    non_thinking = None
    if provider_tokens is not None and total_output_tokens is not None:
        non_thinking = max(0, total_output_tokens - provider_tokens)
    return {
        "provider_breakdown_available": provider_tokens is not None,
        "provider_reported_thinking_tokens": provider_tokens,
        "provider_field": "usage.output_tokens_details.thinking_tokens",
        "total_output_tokens_including_thinking": total_output_tokens,
        "non_thinking_output_tokens_approx": non_thinking,
        "visible_thinking_tokens_estimated": math.ceil(utf8_bytes / 4) if utf8_bytes else 0,
        "visible_estimation_method": "ceil(UTF-8 bytes / 4); not provider usage tokens",
        "blocks": block_count,
        "characters": len(text),
        "utf8_bytes": utf8_bytes,
        "whitespace_words": len(text.split()),
        "text": text,
    }


def extract_assistant_text(serialized_messages: list[dict]) -> str:
    """Concatenate every TextBlock from every AssistantMessage in order."""
    parts: list[str] = []
    for m in serialized_messages:
        if m.get("type") != "AssistantMessage":
            continue
        content = m.get("content", [])
        if isinstance(content, str):
            parts.append(content); continue
        for b in content or []:
            if b.get("type") == "TextBlock" and "text" in b:
                parts.append(b["text"])
    return "".join(parts)


def detect_api_error(serialized_messages: list[dict]) -> Optional[str]:
    """Detect an upstream API-level error that prevented the agent from
    actually doing work. Returns the first error string seen, or None.

    The SDK does NOT raise on these — it surfaces them as an
    AssistantMessage whose text starts with "API Error: ..." (e.g. a 400
    from a thinking-config mismatch) and a ResultMessage with
    is_error=True and total_cost_usd=0. Without this check the pipeline
    treats the original (untouched) sandbox file as the agent's output
    and silently stamps COMPILE_SUCCESS."""
    for m in serialized_messages:
        if m.get("type") == "AssistantMessage":
            content = m.get("content", [])
            blocks = content if isinstance(content, list) else []
            for b in blocks:
                txt = b.get("text") if isinstance(b, dict) else None
                if isinstance(txt, str) and txt.lstrip().startswith("API Error"):
                    # Trim to a single line for the state.json
                    return txt.strip().split("\n", 1)[0]
        if m.get("type") == "ResultMessage":
            result_text = str(m.get("result", "") or "")
            if (m.get("is_error") and not m.get("total_cost_usd")
                    and result_text.lstrip().startswith("API Error")):
                return result_text.strip().split("\n", 1)[0]
    return None


def extract_rate_limit_events(serialized_messages: list[dict]) -> list[dict]:
    """Pull every RateLimitEvent (SDK ≥ 0.2.x) from a serialized session.

    Each entry shows status + utilization + reset time + type. Useful for
    smoke-test review: if you see `status=allowed_warning` or `rejected`,
    lower `agent.concurrency` or wait for the reset window.
    """
    events: list[dict] = []
    for m in serialized_messages:
        if m.get("type") == "RateLimitEvent":
            rli = m.get("rate_limit_info") or {}
            events.append({
                "status": rli.get("status"),
                "rate_limit_type": rli.get("rate_limit_type"),
                "utilization": rli.get("utilization"),
                "resets_at": rli.get("resets_at"),
                "overage_status": rli.get("overage_status"),
                "session_id": m.get("session_id"),
            })
    return events


def extract_token_usage(serialized_messages: list[dict]) -> dict:
    """Pull token usage from the final ResultMessage.

    SDK fields:
      • usage       — Anthropic API usage dict (input/output/cache tokens)
      • model_usage — per-model breakdown when fallback_model kicks in
    Returns {} if no ResultMessage with usage info is present.
    """
    for m in reversed(serialized_messages):
        if m.get("type") == "ResultMessage":
            usage = m.get("usage") or {}
            model_usage = m.get("model_usage") or {}
            if usage or model_usage:
                return {
                    "usage": usage,
                    "model_usage": model_usage,
                    "total_cost_usd": m.get("total_cost_usd"),
                    "num_turns": m.get("num_turns"),
                    "duration_ms": m.get("duration_ms"),
                    "duration_api_ms": m.get("duration_api_ms"),
                }
    return {}


def log_rate_limit_event(model_key: str, target_id: str, event: dict) -> None:
    """Print a WARNING when a rate-limit transition is meaningful."""
    status = event.get("status")
    if status in ("allowed_warning", "rejected"):
        log.warning(
            f"  [{model_key}] {target_id}: rate limit {status} "
            f"(type={event.get('rate_limit_type')}, "
            f"util={event.get('utilization')}, "
            f"resets_at={event.get('resets_at')})"
        )


def extract_runtime_model(serialized_messages: list[dict]) -> str:
    """Return the last concrete model id observed in serialized messages."""
    models: list[str] = []
    for m in serialized_messages:
        model = str(m.get("model", "") or "").strip()
        if model:
            models.append(model)
    return models[-1] if models else ""


def extract_requested_model(serialized_messages: list[dict]) -> str:
    """Best-effort extraction of the configured/requested model from init."""
    import re
    for m in serialized_messages:
        if m.get("type") != "SystemMessage":
            continue
        text = str(m.get("repr", "") or "")
        if not text:
            continue
        match = re.search(r"['\"]model['\"]:\s*['\"]([^'\"]+)['\"]", text)
        if match:
            return match.group(1).strip()
    return ""


def render_agent_session_trace(serialized_messages: list[dict],
                                meta: Optional[dict] = None) -> str:
    """Render session.jsonl into a human-readable transcript.

    [Turn N] counts MODEL-INVOCATION ROUNDS, not raw AssistantMessage stream
    fragments. The SDK streams one model response as several AssistantMessage
    objects (separate objects for the thinking block, the text block and each
    tool_use block); numbering per object inflated the count (e.g. 24 turns for
    an 11-call session) and made it incomparable to ``max_turns`` / ``num_turns``.

    Here one turn == a maximal run of consecutive AssistantMessage objects
    (SystemMessage token-events are ignored; a UserMessage with tool results
    closes the round). All fragments of one response share one [Turn N] header,
    so max [Turn N] == number of model calls. AssistantMessage TextBlocks become
    [THINKING], ToolUseBlocks become [TOOL] with their inputs, and UserMessage
    ToolResultBlocks become [TOOL RESULT] under the round that issued them. The
    final ResultMessage becomes the SESSION END summary line.

    NOTE: keep the turn-counting logic in sync with
    scripts/rerender_traces.py, which re-renders already-written trace.txt."""
    TRUNCATE = 3000
    SEP = "═" * 68

    lines: list[str] = []
    header_parts = []
    if meta:
        for key in ("model", "experiment_mode", "prompt_version", "test_id", "test_key"):
            val = meta.get(key, "")
            if val:
                header_parts.append(str(val))
    lines.append("SESSION: " + " | ".join(header_parts) if header_parts else "SESSION")
    lines.append(SEP)
    lines.append("")

    tool_id_to_name: dict[str, str] = {}
    for m in serialized_messages:
        if m.get("type") != "AssistantMessage":
            continue
        for b in m.get("content", []) or []:
            if b.get("type") == "ToolUseBlock" and b.get("id") and b.get("name"):
                tool_id_to_name[b["id"]] = b["name"]

    turn = 0
    in_round = False  # True while inside a run of consecutive AssistantMessages
    for m in serialized_messages:
        msg_type = m.get("type", "")
        if msg_type == "AssistantMessage":
            if not in_round:
                # Start of a new model-invocation round.
                turn += 1
                if lines and lines[-1] != "":
                    lines.append("")
                lines.append(f"[Turn {turn}] ASSISTANT")
                in_round = True
            for b in m.get("content", []) or []:
                btype = b.get("type", "")
                if btype == "ThinkingBlock":
                    thinking = (b.get("thinking") or "").strip()
                    if thinking:
                        lines.append(f"  [THINKING] {thinking}")
                elif btype == "TextBlock":
                    text = (b.get("text") or "").strip()
                    if text:
                        lines.append(f"  [ASSISTANT] {text}")
                elif btype == "ToolUseBlock":
                    name = b.get("name", "?")
                    inp = b.get("input") or {}
                    lines.append(f"  [TOOL] {name}")
                    if isinstance(inp, dict):
                        for k, v in inp.items():
                            v_str = str(v)
                            if len(v_str) > TRUNCATE:
                                v_str = v_str[:TRUNCATE] + f"\n  ... (truncated, {len(v_str)} chars total)"
                            for idx2, vline in enumerate(v_str.splitlines()):
                                prefix = f"    {k}: " if idx2 == 0 else "    " + " " * (len(k) + 2)
                                lines.append(prefix + vline)
                    else:
                        lines.append(f"    {inp}")
        elif msg_type == "UserMessage":
            # Tool results close the current round; they belong to round `turn`.
            in_round = False
            for b in m.get("content", []) or []:
                if b.get("type") != "ToolResultBlock":
                    continue
                tool_id = b.get("tool_use_id", "")
                tool_name = tool_id_to_name.get(tool_id, tool_id or "?")
                raw_content = b.get("content", "")
                if isinstance(raw_content, list):
                    text = " ".join(
                        str(item.get("text", "")) for item in raw_content
                        if isinstance(item, dict)
                    )
                else:
                    text = str(raw_content or "")
                if len(text) > TRUNCATE:
                    text = text[:TRUNCATE] + f"\n  ... (truncated, {len(text)} chars total)"
                if lines and lines[-1] != "":
                    lines.append("")
                lines.append(f"[Turn {turn}] TOOL RESULT ({tool_name})")
                for tline in text.splitlines():
                    lines.append(f"  {tline}")
        elif msg_type == "SystemMessage":
            # Token-counting / init events stream *within* a model response;
            # they must not break a round nor bump the turn counter.
            continue
        elif msg_type == "ResultMessage":
            num_turns = m.get("num_turns", "?")
            cost = m.get("total_cost_usd")
            is_error = m.get("is_error", False)
            result = m.get("result", "")
            if lines and lines[-1] != "":
                lines.append("")
            lines.append(SEP)
            cost_str = f"${cost:.4f}" if isinstance(cost, (int, float)) else str(cost or "?")
            error_note = " (max_turns reached — agent finished via tool calls, not an execution failure)" if is_error else ""
            lines.append(
                f"SESSION END: model_turns={turn} | num_turns={num_turns} | cost={cost_str}"
                f" | is_error={is_error}{error_note}"
                + (f" | result={result}" if result else "")
            )
            lines.append("")
    return "\n".join(lines)


# ═════════════════════════════════════════════════════════════════════════════
# 7.  Compile-log extraction — pull every `bash compile.sh` call + ToolResult
# ═════════════════════════════════════════════════════════════════════════════

def extract_compile_log(serialized: list[dict]) -> list[dict]:
    """Recover every (bash compile.sh, matching ToolResult) pair so we can
    audit how many compile-check rounds the agent went through."""
    tool_uses: dict[str, dict] = {}
    for m in serialized:
        if m.get("type") != "AssistantMessage":
            continue
        for b in m.get("content", []) or []:
            if b.get("type") != "ToolUseBlock" or b.get("name") != "Bash":
                continue
            inp = b.get("input")
            cmd = inp.get("command", "") if isinstance(inp, dict) else str(inp or "")
            if "compile.sh" not in cmd:
                continue
            tool_uses[b.get("id", "")] = {"command": cmd, "id": b.get("id", "")}

    compile_log: list[dict] = []
    seen: set[str] = set()
    for m in serialized:
        if m.get("type") != "UserMessage":
            continue
        for b in m.get("content", []) or []:
            if b.get("type") != "ToolResultBlock":
                continue
            tu_id = b.get("tool_use_id", "")
            if tu_id not in tool_uses or tu_id in seen:
                continue
            seen.add(tu_id)
            content = b.get("content", "")
            if isinstance(content, list):
                content = "".join(
                    (c.get("text", "") if isinstance(c, dict) else str(c))
                    for c in content
                )
            compile_log.append({
                "tool_use_id": tu_id,
                "command": tool_uses[tu_id]["command"],
                "is_error": bool(b.get("is_error", False)),
                "output": str(content),
            })
    return compile_log


# ═════════════════════════════════════════════════════════════════════════════
# 8.  ★  THE Claude Code SDK call — one session for one target
# ═════════════════════════════════════════════════════════════════════════════
#
# This is the heart of the file. Compare against:
#   https://docs.claude.com/en/api/agent-sdk/python
# The Claude SDK exposes two interfaces:
#   • ClaudeAgentOptions  (configure tools, permission_mode, max_turns,
#                          system_prompt, cwd, model)
#   • ClaudeSDKClient     (async context manager; .query() then
#                          .receive_response() yields messages)

def _load_sdk():
    """Import the study-pinned claude_agent_sdk on demand."""
    try:
        import claude_agent_sdk  # noqa: F401
    except ImportError as e:
        raise ImportError(
            "claude-agent-sdk is not installed. Run "
            "`pip install -r requirements.txt` (see the root README, "
            "\"Before you start\")."
        ) from e
    try:
        installed = package_version("claude-agent-sdk")
    except PackageNotFoundError as e:
        raise RuntimeError("Could not determine claude-agent-sdk version") from e
    if installed != REQUIRED_CLAUDE_AGENT_SDK_VERSION:
        raise RuntimeError(
            f"Claude runs require claude-agent-sdk=={REQUIRED_CLAUDE_AGENT_SDK_VERSION}; "
            f"this interpreter has {installed}. Install the pinned version with "
            "`pip install -r requirements.txt`."
        )
    return claude_agent_sdk


def claude_sdk_runtime_info(sdk) -> dict:
    """Return auditable Python-SDK and bundled-Claude-Code versions."""
    try:
        sdk_version = package_version("claude-agent-sdk")
    except PackageNotFoundError:
        sdk_version = "unknown"
    cli_path = Path(sdk.__file__).resolve().parent / "_bundled" / "claude"
    cli_version = "unknown"
    try:
        completed = subprocess.run(
            [str(cli_path), "--version"], capture_output=True, text=True,
            timeout=15, check=False,
        )
        cli_version = (completed.stdout or completed.stderr).strip()
    except Exception:
        pass
    return {
        "python_sdk_version": sdk_version,
        "required_python_sdk_version": REQUIRED_CLAUDE_AGENT_SDK_VERSION,
        "bundled_claude_code_version": cli_version,
        "bundled_claude_code_path": str(cli_path),
    }


# ─────────────────────────────────────────────────────────────────────────────
# Auth strategy: OAuth (subscription) first, API-key fallback on usage limit
# ─────────────────────────────────────────────────────────────────────────────
#
# Both credentials live in .env (loaded by main.py into os.environ):
#   CLAUDE_CODE_OAUTH_TOKEN  → Claude Pro/Max subscription billing
#   ANTHROPIC_API_KEY        → pay-per-token API billing
#
# Auth precedence in the claude CLI is: API key > OAuth token. So to make a
# session actually USE the subscription, we must BLANK the API key for that
# attempt (set it to "" via options.env, which overrides the inherited
# os.environ value). For the API-key attempt we do the reverse.

def build_auth_attempts() -> list[dict]:
    """Return an ordered list of auth attempts. Each entry:
        {"label": "oauth"|"api_key", "env": {<env overrides for options.env>}}

    Order: OAuth (subscription) first if a token is present, then API key.
    If only one credential exists, returns just that one (no fallback) —
    so behaviour is identical to before when only ANTHROPIC_API_KEY is set.
    """
    oauth = os.environ.get("CLAUDE_CODE_OAUTH_TOKEN", "").strip()
    api_key = os.environ.get("ANTHROPIC_API_KEY", "").strip()

    attempts: list[dict] = []
    if oauth:
        # Subscription attempt: provide OAuth token, BLANK the API key so it
        # doesn't take precedence over OAuth.
        attempts.append({
            "label": "oauth",
            "env": {
                "CLAUDE_CODE_OAUTH_TOKEN": oauth,
                "ANTHROPIC_API_KEY": "",
            },
        })
    if api_key:
        # API-key attempt: provide API key, BLANK the OAuth token.
        attempts.append({
            "label": "api_key",
            "env": {
                "ANTHROPIC_API_KEY": api_key,
                "CLAUDE_CODE_OAUTH_TOKEN": "",
            },
        })
    if not attempts:
        # Neither set — let the CLI use whatever ambient auth it can find.
        attempts.append({"label": "ambient", "env": {}})
    return attempts


# Signals that indicate the current credential hit a usage / rate limit and we
# should fall back to the next auth attempt. Case-insensitive substring match.
# NOTE: the exact OAuth/subscription limit wording is UNVERIFIED — tune this
# list after the first real subscription run (capture the actual error text).
_USAGE_LIMIT_SIGNALS = (
    "usage limit",
    "rate limit",
    "reached your specified api usage",
    "quota",
    "429",
    "insufficient",
    "credit",
    "overloaded_error",          # transient, also worth falling back
)


def is_usage_limit_error(err_text: str) -> bool:
    """True if an error string looks like a usage/rate-limit that warrants
    falling back to the other credential."""
    if not err_text:
        return False
    low = err_text.lower()
    return any(sig in low for sig in _USAGE_LIMIT_SIGNALS)


def make_sandbox_permission_callback(*, sandbox: Path, test_file_name: str, sdk,
                                     read_allow_paths: tuple = ()):
    """Build a SDK `can_use_tool` callback that enforces path scoping at runtime.

    `allowed_tools` only restricts WHICH tools the agent may call, not WHICH
    paths/commands. This callback runs before EVERY tool call and denies any
    out-of-scope use — so even with permission_mode="acceptEdits" the agent
    cannot write/read/run outside the intended scope.

    Rules:
      Edit / Write     : ONLY the output test file (<sandbox>/<test_file_name>)
      Read / Glob / Grep: the working dir, OR one of the whitelisted absolute
                          input paths (CUT source / original test / scaffolding,
                          passed in via read_allow_paths)
      Bash             : only `bash compile.sh` (no other shell commands)
      Skill            : always allowed (this is how the SDK loads SKILL.md
                          in lazy mode)
      everything else  : allowed
    """
    sandbox_resolved = sandbox.resolve()
    allowed_test_path = (sandbox_resolved / test_file_name).resolve()
    # Absolute read-only inputs the agent may Read even though they live OUTSIDE
    # the working dir (CUT source, original test, scaffolding).
    extra_read_paths = set()
    for p in read_allow_paths:
        if not p:
            continue
        try:
            extra_read_paths.add(Path(p).resolve())
        except (ValueError, OSError):
            pass

    def _is_inside_sandbox(p: str) -> bool:
        try:
            return Path(p).resolve().is_relative_to(sandbox_resolved)
        except (ValueError, OSError):
            return False

    async def callback(tool_name, tool_input, _ctx):
        allow = sdk.PermissionResultAllow
        deny  = sdk.PermissionResultDeny

        # Edit / Write: only the designated test file
        if tool_name in ("Edit", "Write"):
            target = str(tool_input.get("file_path", ""))
            if not target:
                return deny(message=f"{tool_name} requires file_path")
            try:
                target_resolved = Path(target).resolve()
            except (ValueError, OSError):
                return deny(message=f"{tool_name}: invalid file_path {target!r}")
            if target_resolved != allowed_test_path:
                return deny(
                    message=(
                        f"{tool_name} is scoped to `{test_file_name}` only. "
                        f"Refused: {target}"
                    ))
            return allow()

        # Bash: only `bash compile.sh` (the compile-check skill)
        if tool_name == "Bash":
            cmd = str(tool_input.get("command", "")).strip()
            if cmd not in ("bash compile.sh", "sh compile.sh", "./compile.sh"):
                return deny(message=(
                    f"Bash is scoped to `bash compile.sh` only. Refused: {cmd!r}"
                ))
            return allow()

        # Read / Glob / Grep: the working dir + the whitelisted absolute inputs
        if tool_name in ("Read", "Glob", "Grep"):
            # Resolve whichever field this tool uses.
            path = (tool_input.get("file_path")
                    or tool_input.get("path")
                    or str(sandbox_resolved))
            try:
                resolved = Path(path).resolve()
            except (ValueError, OSError):
                resolved = None
            if not _is_inside_sandbox(path) and resolved not in extra_read_paths:
                return deny(message=(
                    f"{tool_name} is scoped to the working dir + the provided "
                    f"input files. Refused: {path}"
                ))
            return allow()

        # Skill (lazy load) — always allow, this is the SDK's own mechanism
        if tool_name == "Skill" or "skill" in tool_name.lower():
            return allow()

        # Default: allow but log for audit visibility. The set of tools that
        # may reach this branch is bounded by `allowed_tools` in options.
        return allow()

    return callback


async def run_claude_session_for_one_target(*,
        target: AgentTarget,
        out_dir: Path,
        file_prefix: str,
        cut_source_path: Optional[Path],
        project_classpath: str,
        javac_path: str,
        model_key: str,
        prompt_version: str,
        cfg,
        project_id: str,
        class_name: str,
        # ── Reuse hooks (used by the downstream oracle-fill driver) ──────────
        # When set, override what this session does WITHOUT changing the
        # telemetry/persistence (state.json + session.jsonl + token usage +
        # tools) — so downstream records EXACTLY like improvement.
        agent_cfg=None,                       # which config section (default cfg.agent)
        system_prompt_block: Optional[dict] = None,   # default = build_system_prompt_for_cut
        append_evosuite_constraints: bool = False,    # TEMP off: don't distinguish EvoSuite vs manual for now (flip to True to restore)
        initial_prompt_override: Optional[str] = None,  # downstream: full turn-1 user message (skips build_filled_template)
        ) -> str:
    """Run ONE Claude Code SDK session against ONE target.

    Steps:
      1. Idempotent skip if state.json already complete
      2. Sanity check: project/target/classes must exist (built)
      3. Detect Java release from pom.xml
      4. Build sandbox (test + CUT ref + scaffolding + compile.sh)
      5. Build initial prompt:
           sandbox-layout note + skill bodies + task template + REPAIR_INSTRUCTIONS
      6. Call ClaudeSDKClient with allowed_tools, max_turns, model
      7. Read agent's final edit from sandbox
      8. Persist state.json + session.jsonl + trace.txt + compile_log.jsonl
      9. Clean up sandbox (unless cfg.agent.keep_sandbox=true)
    """
    # ── 1. Idempotent skip ────────────────────────────────────────────────────
    state_path = out_dir / (f"{file_prefix}_state.json" if file_prefix else "state.json")
    if state_path.exists():
        try:
            existing = llm_refactor.load_state(state_path)
            if len(existing.get("messages", [])) >= 2:
                log.info(f"  [{model_key}] {target.test_category}/{target.test_id}: "
                         "existing state.json — skipping agent call")
                return existing.get("status", "PENDING")
        except Exception:
            pass

    sdk = _load_sdk()
    sdk_runtime = claude_sdk_runtime_info(sdk)
    agent_cfg = agent_cfg if agent_cfg is not None else cfg.agent
    permission_mode = str(getattr(agent_cfg, "permission_mode", "acceptEdits"))
    max_turns = int(getattr(agent_cfg, "max_turns_with_repair", 10))
    allowed_tools = list(getattr(agent_cfg, "allowed_tools_with_repair",
                                  ["Read", "Write", "Edit", "Bash", "Glob", "Grep"]))

    # ── 2. Sanity check — SUT must have been compiled ───────────────────────
    project_dir = project_dir_from_classpath(project_classpath)
    if project_dir is None:
        log.warning(f"  [{model_key}] could not derive project_dir from classpath")
    else:
        target_classes_dir = project_dir / "target" / "classes"
        if not target_classes_dir.exists() or not any(target_classes_dir.iterdir()):
            raise RuntimeError(
                f"Project not built: {target_classes_dir} is missing or empty. "
                f"Set pipeline_control.clone_and_build=true or run `mvn compile "
                f"test-compile` inside {project_dir} first."
            )

    # ── 3. Detect Java release ───────────────────────────────────────────────
    release_version = (
        build_executor.detect_java_release(project_dir) if project_dir else None
    )
    if release_version:
        log.info(f"  [{model_key}] {target.test_category}/{target.test_id}: "
                 f"detected Java release {release_version} from pom.xml")

    # ── 4. Build the working directory (= the per-test improved output dir) ───
    # cwd IS the output folder: the agent writes its new test here, compile.sh
    # lives here, javac output goes to test-classes/ here. No temp sandbox, no
    # copies — the CUT / original test / scaffolding are read by ABSOLUTE path.
    # Unique per test → concurrent sessions never collide.
    sandbox = Path(out_dir)
    sandbox.mkdir(parents=True, exist_ok=True)
    classes_out = sandbox / "test-classes"
    classes_out.mkdir(parents=True, exist_ok=True)
    classpath = build_sandbox_classpath(target, project_classpath,
                                        str(classes_out), cfg)
    sandbox, sandbox_test, _classes_out = build_sandbox(
        target=target,
        cut_source_path=cut_source_path,
        classpath=classpath,
        javac_path=javac_path,
        sandbox=sandbox,
        classes_out=classes_out,
        release_version=release_version,
    )

    # ── 5. Build initial prompt + per-CUT system_prompt ──────────────────────
    cut_class_simple = (
        cut_source_path.stem if cut_source_path and cut_source_path.exists()
        else class_name.split(".")[-1]
    )
    # Skill cards loaded only for state.json record-keeping (audit). The
    # actual skill loading is done by the SDK from .claude/skills/ via
    # setting_sources=["project"] — not by us in the user prompt.
    skill_cards = load_skill_cards(["compile_check", "repair"], cfg)

    # User message (turn 1) is now MINIMAL — just the rendered task template
    # (Definition + Task with both file paths). Sandbox layout + tool
    # permissions live in the per-CUT system_prompt below.
    if initial_prompt_override is not None:
        initial_prompt = initial_prompt_override
    else:
        initial_prompt = build_filled_template(
            prompt_version=prompt_version,
            test_java_path=target.java_path,
            cut_source_path=cut_source_path,
        )
    # Option B: EvoSuite guard-rails ride in the USER message (only for auto
    # targets), NOT the system prompt — so the system prompt stays byte-
    # identical for this CUT's manual AND auto sessions (one agent / one cache
    # prefix). The text is a module constant, so it is identical across all of
    # the CUT's auto sessions.
    if target.scaffolding_java and append_evosuite_constraints:
        initial_prompt = initial_prompt + EVOSUITE_TASK_CONSTRAINTS

    # ── 6. Call the Claude Code SDK ──────────────────────────────────────────
    #
    # DeepSeek-* models route through Claude Code's settings.json — pass
    # model=None so the CLI uses its configured default. For Anthropic
    # models pass the exact name so we get a deterministic snapshot.
    sdk_model_arg = None if str(model_key).lower().startswith("deepseek-") else model_key

    # Per Anthropic SDK audit Q4: text-only path-scoping in the prompt is
    # the LEAST reliable enforcement mode. We back it up with a runtime
    # can_use_tool callback that denies tool calls outside the sandbox or
    # against unintended file paths.
    permission_callback = make_sandbox_permission_callback(
        sandbox=sandbox, test_file_name=sandbox_test.name, sdk=sdk,
        read_allow_paths=(
            str(cut_source_path) if cut_source_path else "",
            target.java_path,
            # 方案1: scaffolding is intentionally NOT readable by the agent — it
            # is not surfaced in the prompt, and compile.sh supplies it on the
            # classpath behind the scenes.
        ))

    # ── system_prompt: PER-CUT PRESET + APPEND (official pattern) ──────────
    # NOT a plain string. SDK source subprocess_cli.py:171-182 shows:
    #   • string  → --system-prompt (REPLACES the Claude Code default)
    #   • preset+append → --append-system-prompt (PRESERVES default + adds)
    # The default includes auto-injected cwd info, skill metadata (3-tier
    # level 1), env info — which we want.
    #
    # Per-CUT: all sessions for the same CUT share an IDENTICAL append text
    # (depends only on cut_class_simple + reference/scaffolding presence).
    # Different sessions for the same CUT → identical prefix → Anthropic
    # prompt cache hits (within 5-min default TTL).
    # NOTE: system prompt no longer depends on has_scaffolding (Option B) — it
    # is byte-identical for this CUT's manual and auto sessions. The EvoSuite
    # guard-rails for auto targets were appended to initial_prompt above.
    if system_prompt_block is None:
        system_prompt_block = build_system_prompt_for_cut(
            cut_class_simple=cut_class_simple,
            has_cut_reference=bool(cut_source_path and cut_source_path.exists()),
            cut_path=(str(Path(cut_source_path).resolve())
                      if cut_source_path and cut_source_path.exists() else ""),
            original_test_path=str(Path(target.java_path).resolve()),
            scaffolding_path=(str(Path(target.scaffolding_java).resolve())
                              if target.scaffolding_java
                              and Path(target.scaffolding_java).exists() else ""),
        )

    # ── Optional: extended thinking + effort (required by some models) ─────
    # claude-opus-4-8 only accepts thinking={"type":"adaptive"} (NOT the
    # older "enabled" form). claude-opus-4-7 works with either default or
    # adaptive. We read both from cfg.agent so the user controls them
    # without touching code:
    #
    #   agent:
    #     thinking:
    #       type: "adaptive"      # or "disabled" or {type: "enabled", budget_tokens: N}
    #     effort: "medium"        # one of low/medium/high/xhigh/max
    #
    # Pass only when set, so leaving them unset = SDK/CLI default (works for
    # opus-4-7).
    thinking_cfg = getattr(agent_cfg, "thinking", None)
    effort_cfg   = getattr(agent_cfg, "effort",   None)
    # Convert SimpleNamespace (yaml-parsed) → plain dict for SDK
    if thinking_cfg is not None and not isinstance(thinking_cfg, dict):
        thinking_cfg = {
            k: getattr(thinking_cfg, k)
            for k in ("type", "budget_tokens", "display")
            if hasattr(thinking_cfg, k) and getattr(thinking_cfg, k) is not None
        }
    sdk_kwargs = dict(
        # tool surface + permissions
        allowed_tools=allowed_tools,
        permission_mode=permission_mode,
        max_turns=max_turns,
        can_use_tool=permission_callback,
        # workspace
        cwd=str(sandbox),
        # skills="all" (SDK ≥ 0.2.x) is the canonical way to enable Skills.
        # Per the SDK docstring: "This is the single place to turn skills on;
        # you do not need to add 'Skill' to allowed_tools or set
        # setting_sources yourself — the SDK does both when this is set."
        # We KEEP setting_sources=["project"] explicitly to document that the
        # skill discovery roots at sandbox/.claude/ (symlinked to
        # PROJECT_ROOT/.claude in build_sandbox).
        skills="all",
        setting_sources=["project"],
        # model + system prompt
        model=sdk_model_arg,
        system_prompt=system_prompt_block,
    )
    if thinking_cfg is not None:
        sdk_kwargs["thinking"] = thinking_cfg
    if effort_cfg is not None:
        sdk_kwargs["effort"] = effort_cfg

    target_id_for_log = f"{target.test_category}/{target.test_id}"

    # ── ONE auth attempt = one full SDK session ─────────────────────────────
    async def _run_one_session(auth_env: dict):
        """Run a single ClaudeSDKClient session with the given auth env
        overrides. Returns (serialized, sdk_exception, context_usage)."""
        kwargs = dict(sdk_kwargs)
        kwargs["env"] = auth_env            # per-session auth (OAuth or API key)
        opts = sdk.ClaudeAgentOptions(**kwargs)
        ser: list[dict] = []
        exc: Optional[str] = None
        ctx_usage: dict = {}
        try:
            async with sdk.ClaudeSDKClient(options=opts) as client:
                await client.query(initial_prompt)
                async for msg in client.receive_response():
                    s = serialize_message(msg)
                    ser.append(s)
                    if s.get("type") == "RateLimitEvent":
                        log_rate_limit_event(
                            model_key, target_id_for_log,
                            s.get("rate_limit_info") or {})
                try:
                    ctx_usage = await client.get_context_usage() or {}
                except Exception as e:
                    log.debug(f"  [{model_key}] could not fetch context usage: {e}")
                    ctx_usage = {"error": str(e)}
        except Exception as e:
            log.error(f"  [{model_key}] agent session failed: {e}")
            exc = str(e)
        return ser, exc, ctx_usage

    # ── Auth fallback loop: OAuth (subscription) first, API key on limit ─────
    auth_attempts = build_auth_attempts()
    serialized: list[dict] = []
    sdk_exception: Optional[str] = None
    context_usage_snapshot: dict = {}
    api_error: Optional[str] = None
    auth_used = None

    for idx, attempt in enumerate(auth_attempts):
        label = attempt["label"]
        is_last = (idx == len(auth_attempts) - 1)
        log.info(f"  [{model_key}] {target_id_for_log}: starting agent session "
                 f"(auth={label}, sandbox={sandbox})…")
        serialized, sdk_exception, context_usage_snapshot = \
            await _run_one_session(attempt["env"])
        # Detect upstream API error surfaced as an AssistantMessage (not raised)
        api_error = detect_api_error(serialized) if not sdk_exception else None
        this_error = sdk_exception or api_error
        auth_used = label

        if not this_error:
            break  # success with this credential

        # Did we hit a usage / rate limit? If so AND there's another auth
        # attempt left, fall back to it (per user: OAuth-first, API-key on limit).
        if is_usage_limit_error(this_error) and not is_last:
            log.warning(
                f"  [{model_key}] {target_id_for_log}: auth={label} hit a "
                f"usage/rate limit — falling back to "
                f"{auth_attempts[idx+1]['label']}. (signal: {this_error[:80]})")
            continue
        # Non-limit error, or no more credentials to try → stop here.
        break

    fatal_error = sdk_exception or api_error

    if fatal_error:
        out_dir.mkdir(parents=True, exist_ok=True)
        err_state = {
            "status": "LLM_ERROR",
            "attempts_used": 0,
            "model": model_key,
            "prompt_version": prompt_version,
            "messages": [{"role": "user", "content": initial_prompt}],
            "last_code": "",
            "last_error": fatal_error,
            "last_java_path": "",
            "initial_prompt": initial_prompt,
            "improvement_mode": "agent_improve_and_repair",
            "project_id": project_id,
            "target_class": class_name,
            "test_category": target.test_category,
            "test_id": target.test_id,
            "suite_class": target.suite_class,
            "is_evosuite": target.is_evosuite,
            "scaffolding_java": target.scaffolding_java or None,
            "original_java": target.java_path,
            "agent_sandbox": str(sandbox),
            "skill_names": [c.get("name", "") for c in skill_cards if c.get("name")],
            "skill_injection_mode": "lazy",
            "system_prompt_form": "preset+append",
            "file_prefix": file_prefix,
        }
        llm_refactor.save_state(err_state, state_path)
        # Persist whatever the SDK did send back so analyze_session can see it
        if serialized:
            session_path = out_dir / (f"{file_prefix}_session.jsonl"
                                       if file_prefix else "session.jsonl")
            if session_path.exists():
                session_path.unlink()
            for m in serialized:
                append_session_jsonl(session_path, m)
        if api_error:
            log.error(f"  [{model_key}] upstream API error: {api_error}")
        return "LLM_ERROR"

    # ── 7. Read agent's final edit ───────────────────────────────────────────
    final_code = ""
    if sandbox_test.exists():
        try:
            final_code = sandbox_test.read_text(encoding="utf-8")
        except Exception as e:
            log.warning(f"  could not read agent's final test file: {e}")

    final_assistant_text = extract_assistant_text(serialized)
    requested_runtime_model = extract_requested_model(serialized)
    actual_runtime_model = extract_runtime_model(serialized)

    java_path = ""
    if final_code:
        # Fallback class name = the suite class so the improved file lands at
        # <out_dir>/<SuiteClass>.java even if the agent returned non-Java text.
        # Replaces the old hard-coded "FinalTest" fallback in llm_refactor.
        fallback = target.suite_class or class_name or "ImprovedTest"
        java_path = llm_refactor._write_java_by_class_name(
            final_code, str(out_dir), fallback_class_name=fallback)

    compile_log = extract_compile_log(serialized)

    # ── Runtime observability (Proposals A + C) ──────────────────────────────
    # A: every RateLimitEvent (status transitions) the SDK emitted
    # C: token usage from ResultMessage + per-category context breakdown
    #    captured via client.get_context_usage() above
    rate_limit_events = extract_rate_limit_events(serialized)
    token_usage = extract_token_usage(serialized)
    thinking_usage = extract_thinking_usage(serialized)
    thinking_text = thinking_usage.pop("text", "")
    runtime_observability = {
        "sdk_runtime": sdk_runtime,
        "rate_limit_events": rate_limit_events,
        "rate_limit_hit": any(
            e.get("status") == "rejected" for e in rate_limit_events),
        "rate_limit_warned": any(
            e.get("status") == "allowed_warning" for e in rate_limit_events),
        "token_usage": token_usage,
        "thinking_tokens": thinking_usage,
        "context_usage": context_usage_snapshot,
        # Which credential actually produced this session (oauth=subscription,
        # api_key=pay-per-token). Lets you audit how much ran on each.
        "auth_used": auth_used,
        "auth_attempts_available": [a["label"] for a in auth_attempts],
    }

    # ── 8. Persist state.json + supporting files ─────────────────────────────
    state = write_agent_state(
        out_dir=out_dir,
        file_prefix=file_prefix,
        initial_prompt=initial_prompt,
        final_assistant_text=final_assistant_text,
        final_code=final_code,
        final_java_path=java_path,
        serialized_messages=serialized,
        target_meta={
            "project_id": project_id,
            "target_class": class_name,
            "test_category": target.test_category,
            "test_id": target.test_id,
            "suite_class": target.suite_class,
            "is_evosuite": target.is_evosuite,
            "scaffolding_java": target.scaffolding_java or None,
            "original_java": target.java_path,
        },
        model_key=model_key,
        prompt_version=prompt_version,
        extra={
            "improvement_mode": "agent_improve_and_repair",
            "agent_sandbox": str(sandbox),
            "agent_compile_attempts": len(compile_log),
            "skill_names": [c.get("name", "") for c in skill_cards if c.get("name")],
            "skill_paths": [c.get("path", "") for c in skill_cards if c.get("path")],
            "provider_model": model_key,
            "requested_provider_model": requested_runtime_model or model_key,
            "actual_provider_model": actual_runtime_model or "",
            "runtime_observability": runtime_observability,
        },
        request_metadata={
            "provider": "claude",
            "mode": "run_agent_improve_and_repair",
            "model": model_key,
            "allowed_tools": allowed_tools,
            "permission_mode": permission_mode,
            "max_turns": max_turns,
            "skill_names": [c.get("name", "") for c in skill_cards if c.get("name")],
            # SDK-native skill loading (3-tier lazy model). Eager
            # body-injection was removed — there's only one path now.
            "skill_injection_mode": "lazy",
            "skills_option": "all",       # ClaudeAgentOptions(skills="all")
            "system_prompt_form": "preset+append",
            "setting_sources": ["project"],
            "can_use_tool_callback": "make_sandbox_permission_callback",
            "sdk_version_min": "0.2.0",   # skills="all" requires SDK ≥ 0.2
            # Extended thinking config (None if not set in config.yaml)
            "thinking": thinking_cfg,
            "effort":   effort_cfg,
        },
    )
    thinking_path = out_dir / (f"{file_prefix}_thinking.txt"
                               if file_prefix else "thinking.txt")
    thinking_path.write_text(thinking_text, encoding="utf-8")

    # Note: the per-target compile_log.jsonl writer was removed — the same
    # information is preserved inside session.jsonl (every Bash compile.sh
    # tool_use / tool_result pair is captured by serialize_message), and the
    # turn count is kept on state.json as agent_compile_attempts.

    # ── 9. Clean up scratch (NOT the whole dir — it IS the output folder) ─────
    # cwd is the per-test output dir, so we must NOT rmtree it. Remove only the
    # agent's scratch artifacts (compile.sh, test-classes/, .claude symlink) and
    # keep the improved .java + state/session files. keep_sandbox leaves all.
    keep_sandbox = bool(getattr(agent_cfg, "keep_sandbox", False))
    if not keep_sandbox:
        for scratch in (sandbox / "compile.sh", sandbox / "test-classes",
                        sandbox / ".claude"):
            try:
                if scratch.is_symlink() or scratch.is_file():
                    scratch.unlink()
                elif scratch.is_dir():
                    shutil.rmtree(scratch)
            except Exception as e:
                log.warning(f"  could not clean scratch {scratch}: {e}")

    log.info(f"    → status: {state['status']} "
             f"(agent_compile_attempts={len(compile_log)}, "
             f"sandbox={sandbox if keep_sandbox else 'cleaned'})")
    return state["status"]


# ═════════════════════════════════════════════════════════════════════════════
# 9.  Async fan-out — one task per (prompt_version × model × target)
# ═════════════════════════════════════════════════════════════════════════════

async def _run_bounded(sem: asyncio.Semaphore,
                        coro_factory: Callable[[], Awaitable[Any]]) -> Any:
    """Acquire `sem`, run the coroutine returned by `coro_factory()`, release."""
    async with sem:
        return await coro_factory()


async def _run_async(*, targets: list[AgentTarget],
                       models: list[str],
                       cut_source_path: Optional[Path],
                       classpath: str,
                       cfg,
                       project_id: str,
                       class_name: str,
                       improvement_dir_fn) -> None:
    agent_cfg = getattr(cfg, "agent", None)
    if agent_cfg is None:
        raise RuntimeError(
            "config.yaml is missing an `agent:` section — required for Claude runs."
        )
    concurrency = int(getattr(agent_cfg, "concurrency", 5))
    javac_path = javac_path_from_cfg(cfg)

    sem = asyncio.Semaphore(concurrency)
    prompt_versions = list(llm_refactor.PROMPT_VERSIONS)

    coros = []
    for prompt_version in prompt_versions:
        for model_key in models:
            for t in targets:
                out_dir, file_prefix = improvement_dir_fn(
                    project_id, class_name, prompt_version, model_key, t,
                )

                def make_factory(tt=t, mk=model_key, od=out_dir,
                                  pv=prompt_version, fp=file_prefix):
                    async def _go():
                        return await run_claude_session_for_one_target(
                            target=tt,
                            out_dir=od,
                            file_prefix=fp,
                            cut_source_path=cut_source_path,
                            project_classpath=classpath,
                            javac_path=javac_path,
                            model_key=mk,
                            prompt_version=pv,
                            cfg=cfg,
                            project_id=project_id,
                            class_name=class_name,
                        )
                    return _go

                coros.append(_run_bounded(sem, make_factory()))

    if not coros:
        log.warning("[claude_session] no targets to run")
        return
    log.info(f"[claude_session] launching {len(coros)} agent task(s) "
             f"(concurrency={concurrency})")
    results = await asyncio.gather(*coros, return_exceptions=True)
    for idx, r in enumerate(results, start=1):
        if isinstance(r, Exception):
            log.error(f"[claude_session] task #{idx} raised "
                      f"{type(r).__name__}: {r}")
    n_ok  = sum(1 for r in results if r in ("PENDING",))
    n_err = sum(1 for r in results if r == "LLM_ERROR" or isinstance(r, Exception))
    log.info(f"[claude_session] done — pending(ok)={n_ok}, errors={n_err} "
             f"/ total={len(results)}")


# ═════════════════════════════════════════════════════════════════════════════
# 10.  Public sync entry point — called by main.run_claude_code_step
# ═════════════════════════════════════════════════════════════════════════════

def run(*, targets: list,
        models: list[str],
        cut_source_path: Optional[Path],
        classpath: str,
        cfg,
        project_id: str,
        class_name: str,
        improvement_dir_fn) -> None:
    """The IMPROVE-AND-REPAIR Claude Code SDK agent run.

    Called by main.run_claude_code_step. Targets come from main.ImprovementTarget;
    we map them to local AgentTarget so this file stays independent.
    """
    agent_targets = [
        AgentTarget(
            test_category=t.test_category,
            test_id=t.test_id,
            suite_class=t.suite_class,
            java_path=t.java_path,
            is_evosuite=t.is_evosuite,
            scaffolding_java=t.scaffolding_java,
        )
        for t in targets
    ]
    asyncio.run(_run_async(
        targets=agent_targets,
        models=models,
        cut_source_path=cut_source_path,
        classpath=classpath,
        cfg=cfg,
        project_id=project_id,
        class_name=class_name,
        improvement_dir_fn=improvement_dir_fn,
    ))
