"""
Codex CLI — IMPROVE-AND-REPAIR agent driver (self-contained).

Open this file to see exactly which Codex CLI calls we make. Cross-
reference each section against the official Codex documentation:

    https://github.com/openai/codex
    https://github.com/openai/codex/blob/main/docs/getting-started.md
    https://github.com/openai/codex/blob/main/docs/sandbox.md

The file is intentionally NOT split across helper modules — every function
the Codex path needs lives here (skill loading, working-dir build, prompt
assembly, state.json writer, Codex stdout parsing, async fan-out). This
mirrors what the Codex docs describe in one place and isolates Codex
specifics from the Claude driver in `agent_with_repair.py`.

Architecture (top → bottom):
  1.  Public dataclasses                AgentTarget
  2.  Skill loading                     resolve_skill_path / load_skill_cards / …
  3.  Prompt assembly                   build_agent_improvement_prompt / compose_prompt_with_skill_cards
  4.  Working-dir creation              _build_working_dir + compile.sh
  5.  state.json writer                 _write_codex_state
  6.  Codex CLI invocation              _run_codex_exec  (★ the actual subprocess call)
  7.  Codex stdout parsers              parse_codex_exec_events / render_codex_trace / …
  8.  Per-target driver                 _improve_one_target
  9.  Async fan-out                     _run_async / run()
"""
from __future__ import annotations

import asyncio
import json
import logging
import os
import shutil
import subprocess
from dataclasses import dataclass
from pathlib import Path
from typing import Any, Awaitable, Callable, Optional

import coverage_runner
import llm_refactor

log = logging.getLogger(__name__)

PROJECT_ROOT = Path(__file__).parent.parent


# ═════════════════════════════════════════════════════════════════════════════
# 1.  Public dataclasses
# ═════════════════════════════════════════════════════════════════════════════

@dataclass
class AgentTarget:
    """One test the agent needs to improve. Created by main._iter_improvement_targets."""
    test_category: str       # "manual_suite" | "manual_cases" | "auto_suite" | "auto_cases"
    test_id: str             # file-name prefix in the flat data/ layout
    suite_class: str         # parent suite class — names the testcases/<SuiteClass>/ folder
    java_path: str           # absolute path to the source test file
    is_evosuite: bool
    scaffolding_java: str    # empty string when not evosuite


# ═════════════════════════════════════════════════════════════════════════════
# 2.  Skill loading — read .agents/skills/<name>/SKILL.md, split frontmatter
# ═════════════════════════════════════════════════════════════════════════════
#
# Codex doesn't have a native "Skill" tool, so we use BODY-INJECTION:
# the SKILL.md body is concatenated into the user prompt at session start.
# This mirrors the Claude side for cross-model reproducibility.

def resolve_skill_path(mode: str, cfg=None) -> Optional[Path]:
    """Return the path to .agents/skills/<dir>/SKILL.md for one mode."""
    mode_key = str(mode).strip().lower().replace("-", "_")
    cfg_section = getattr(cfg, "codex_agent", None)

    if mode_key in {"compile", "compile_check", "coverage", "compile_coverage"}:
        explicit_attr = "compile_check_skill_path"
        skill_dir_names = ["compile-check", "compile-coverage-check"]
    elif mode_key in {"repair", "with_repair"}:
        explicit_attr = "repair_skill_path"
        skill_dir_names = ["repair-loop"]
    else:
        raise ValueError(f"Unsupported skill mode '{mode}'.")

    explicit = getattr(cfg_section, explicit_attr, None) if cfg_section else None
    if explicit:
        candidate = Path(str(explicit)).expanduser()
        if candidate.is_file():
            return candidate

    for skill_dir_name in skill_dir_names:
        default_path = PROJECT_ROOT / ".agents" / "skills" / skill_dir_name / "SKILL.md"
        if default_path.is_file():
            return default_path
    return None


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
# 3.  Prompt assembly — task template + skill bodies + mode instructions
# ═════════════════════════════════════════════════════════════════════════════

def build_agent_improvement_prompt(test_java_path: str, cut_source: str,
                                     prompt_version: str,
                                     working_dir: str = "") -> str:
    """Render the Codex prompt from the same content Claude Code receives.

    Codex does not expose the Claude SDK's system_prompt parameter through the
    CLI path we use, so we prepend the Claude system-prompt append text to the
    initial user prompt. The Claude-controlled system/user text remains sourced
    from the same template files; the only Codex-specific addition is the cwd
    metadata above them.
    """
    template = llm_refactor.load_prompt_template(prompt_version)
    test_file = Path(test_java_path).name
    task_text = llm_refactor.build_improvement_prompt(
        template,
        test_file=test_file,
        cut_file=str(Path(cut_source).resolve()) if cut_source else "",
    )
    original_abs = str(Path(test_java_path).resolve())
    token = f"`{test_file}`"
    if token in task_text:
        task_text = task_text.replace(token, f"{token} (path:- {original_abs})", 1)
    else:
        task_text = f"{task_text}\n\n(original test path: {original_abs})"

    cut_abs = str(Path(cut_source).resolve()) if cut_source else "(not provided)"
    system_append = (PROJECT_ROOT / "prompts" / "improve_system_prompt.txt").read_text(
        encoding="utf-8"
    ).replace("{cut_path}", cut_abs)

    lines = []
    if working_dir:
        lines += [
            "Codex execution metadata:",
            f"- cwd: {Path(working_dir).resolve()}",
            "- `compile.sh` is already in cwd; run it from cwd exactly as the compile-check skill says.",
            "",
        ]
    lines += [
        system_append,
        "",
        "---",
        "",
        task_text,
    ]
    return "\n".join(lines)


# ═════════════════════════════════════════════════════════════════════════════
# 4.  Working-dir creation — output folder + compile.sh
# ═════════════════════════════════════════════════════════════════════════════

def _javac_path_from_cfg(cfg) -> str:
    java_home = getattr(cfg.tools, "java_home_compile", "")
    if java_home:
        return str(Path(java_home) / "bin" / "javac")
    java_path = getattr(cfg.tools, "java_path", "") or "java"
    return java_path.replace("/java", "/javac") if "/java" in java_path else "javac"


def _build_classpath(target: AgentTarget, project_classpath: str,
                      compiled_dir: str, cfg) -> str:
    if target.is_evosuite:
        return coverage_runner.build_evosuite_classpath(
            project_classpath, compiled_dir, cfg
        )
    return coverage_runner.build_manual_classpath(
        project_classpath, compiled_dir, cfg
    )


def _build_working_dir(*, target: AgentTarget,
                       classpath: str,
                       javac_path: str,
                       work_dir: Path,
                       classes_out: Optional[Path] = None,
                       ) -> tuple[Path, Path]:
    """Prepare the final per-test output directory for a Codex session.

    Mirrors the Claude driver: the agent works directly in out_dir and writes a
    new test file there. The original test/CUT stay at read-only absolute paths.
    A project ``.agents`` directory is linked into the working directory so
    Codex can discover the same project-local Skills in its native, lazy-load
    mechanism that the Claude driver uses via ``sandbox/.claude``.
    """
    work_dir.mkdir(parents=True, exist_ok=True)
    if classes_out is None:
        classes_out = work_dir / "test-classes"
    classes_out.mkdir(parents=True, exist_ok=True)

    # Native Codex Skills discovery starts at the current working directory
    # (the ``--cd work_dir`` passed to ``codex exec``).  Make the canonical
    # project Skills visible at that root without injecting their bodies into
    # the user prompt.  Codex can therefore select and read a SKILL.md only
    # when its description matches the task, matching Claude's implicit,
    # lazy-loading protocol.
    project_agents_dir = PROJECT_ROOT / ".agents"
    work_agents_dir = work_dir / ".agents"
    if project_agents_dir.exists() and not work_agents_dir.exists():
        try:
            work_agents_dir.symlink_to(project_agents_dir, target_is_directory=True)
        except Exception as e:
            log.warning(f"  could not symlink .agents into Codex working dir: {e}")

    src = Path(target.java_path)
    output_test = work_dir / src.name

    extra_files: list[str] = []
    if target.scaffolding_java:
        scaffolding_src = Path(target.scaffolding_java)
        if scaffolding_src.exists():
            extra_files.append(str(scaffolding_src.resolve()))

    files_arg = " ".join([f'"{output_test.name}"'] + [f'"{f}"' for f in extra_files])
    compile_sh = work_dir / "compile.sh"
    compile_sh.write_text(
        "#!/usr/bin/env bash\n"
        "set -u\n"
        f"JAVAC={json.dumps(javac_path)}\n"
        f"CP={json.dumps(classpath)}\n"
        f"OUT={json.dumps(str(classes_out))}\n"
        f'"$JAVAC" -encoding UTF-8 -cp "$CP" -d "$OUT" {files_arg}\n'
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
    return work_dir, output_test


# ═════════════════════════════════════════════════════════════════════════════
# 5.  state.json writer (consumed by main.run_compile_improvement_outputs)
# ═════════════════════════════════════════════════════════════════════════════

def _append_session_jsonl(path: Path, msg_dict: dict) -> None:
    """Append one serialized message as a JSONL line. Creates the file if needed."""
    path.parent.mkdir(parents=True, exist_ok=True)
    with path.open("a", encoding="utf-8") as f:
        f.write(json.dumps(msg_dict, ensure_ascii=False) + "\n")


def _write_codex_state(out_dir: Path, *,
                        file_prefix: str = "",
                        initial_prompt: str,
                        final_assistant_text: str,
                        final_code: str,
                        final_java_path: str,
                        target_meta: dict,
                        model_label: str,
                        prompt_version: str,
                        extra: Optional[dict] = None) -> dict:
    """Build the canonical state dict + supporting files. Status = PENDING.

    With `file_prefix` set, files are written as `<file_prefix>_<base>` so
    multiple tests can share a flat folder under data/improved/."""
    state = {
        "status": "PENDING",
        "attempts_used": 1,
        "model": model_label,
        "prompt_version": prompt_version,
        "messages": [
            {"role": "user",      "content": initial_prompt},
            {"role": "assistant", "content": final_assistant_text},
        ],
        "last_code": final_code,
        "last_error": "",
        "last_java_path": str(Path(final_java_path).resolve()) if final_java_path else "",
        "initial_prompt": initial_prompt,
        "improvement_mode": (extra or {}).get("improvement_mode", "codex_agent"),
        "file_prefix": file_prefix,
    }
    state.update(target_meta or {})
    if extra:
        state.update(extra)

    out_dir.mkdir(parents=True, exist_ok=True)

    def _p(base: str) -> Path:
        return out_dir / (f"{file_prefix}_{base}" if file_prefix else base)

    # Original test → <out_dir>/original/<ClassName>.java (preserves real
    # filename so the original Java compiles independently). See the Claude
    # agent for the same convention.
    original_java = str((target_meta or {}).get("original_java", "") or "")
    if original_java and Path(original_java).exists():
        original_subdir = out_dir / "original"
        original_subdir.mkdir(parents=True, exist_ok=True)
        shutil.copy2(original_java, original_subdir / Path(original_java).name)
    _p("prompt.txt").write_text(initial_prompt, encoding="utf-8")
    llm_refactor.save_state(state, _p("state.json"))

    # Synthetic single-turn session.jsonl so downstream analysis is uniform
    session_path = _p("session.jsonl")
    if session_path.exists():
        session_path.unlink()
    _append_session_jsonl(session_path, {
        "type": "SyntheticUserPrompt", "role": "user",
        "content": initial_prompt,
    })
    _append_session_jsonl(session_path, {
        "type": "SyntheticAssistantMessage", "role": "assistant",
        "content": final_assistant_text,
    })

    return state


# ═════════════════════════════════════════════════════════════════════════════
# 6.  ★  THE Codex CLI invocation
# ═════════════════════════════════════════════════════════════════════════════
#
# We shell out to the official `codex exec` subcommand. Reference:
#   https://github.com/openai/codex/blob/main/docs/getting-started.md#codex-exec
#
# Key flags we pass:
#   --skip-git-repo-check       output dir isn't a git repo; bypass the check
#   --cd <work_dir>             run inside the per-target output directory
#   --sandbox <mode>            workspace-write | read-only
#   --model <id>                e.g. gpt-5.4
#   --json                      one JSON event per line on stdout
#   --output-last-message <p>   final assistant text dumped to file
#   -                           read the user prompt from stdin

def resolve_codex_cli(cfg=None) -> Optional[str]:
    """Locate the Codex CLI across app + shell + Applications/."""
    explicit = (getattr(getattr(cfg, "codex_agent", None), "cli_path", None)
                or os.environ.get("CODEX_CLI_PATH"))
    if explicit:
        candidate = Path(str(explicit)).expanduser()
        if candidate.is_file():
            return str(candidate)

    found = shutil.which("codex")
    if found:
        return found

    candidates = [
        Path("/Applications/Codex.app/Contents/Resources/codex"),
        Path.home() / "Applications/Codex.app/Contents/Resources/codex",
    ]
    for candidate in candidates:
        if candidate.is_file():
            return str(candidate)
    return None


async def _run_codex_exec(*, codex_cli: str, work_dir: Path, prompt: str,
                            model_id: str, sandbox_mode: str,
                            reasoning_effort: Optional[str] = None,
                            ) -> tuple[int, str, str, str]:
    """Invoke `codex exec` once. Returns (returncode, stdout, stderr, last_message)."""
    last_message_path = work_dir / "codex_last_message.txt"
    cmd = [
        codex_cli, "exec",
        "--skip-git-repo-check",
        "--cd", str(work_dir),
        "--sandbox", sandbox_mode,
        "--model", model_id,
    ]
    # Reasoning effort → codex config override (-c model_reasoning_effort=<v>).
    # Only added when set, so unset config leaves codex's own default in place.
    if reasoning_effort:
        cmd += ["-c", f"model_reasoning_effort={reasoning_effort}"]
    cmd += [
        "--json",
        "--output-last-message", str(last_message_path),
        "-",
    ]

    def _run():
        return subprocess.run(
            cmd, input=prompt, text=True, capture_output=True, check=False,
        )

    proc = await asyncio.to_thread(_run)
    last_message = ""
    if last_message_path.exists():
        last_message = last_message_path.read_text(encoding="utf-8")
    return proc.returncode, proc.stdout, proc.stderr, last_message


# ═════════════════════════════════════════════════════════════════════════════
# 7.  Codex stdout parsers (JSONL event stream → readable trace)
# ═════════════════════════════════════════════════════════════════════════════

def parse_codex_exec_events(raw_jsonl: str) -> list[dict]:
    """Best-effort parse of `codex exec --json` output."""
    events: list[dict] = []
    for line in (raw_jsonl or "").splitlines():
        line = line.strip()
        if not line:
            continue
        try:
            events.append(json.loads(line))
        except Exception:
            continue
    return events


def extract_codex_agent_messages(raw_jsonl: str) -> list[str]:
    """Return every `agent_message` text emitted by Codex."""
    messages: list[str] = []
    for event in parse_codex_exec_events(raw_jsonl):
        item = event.get("item") or {}
        if item.get("type") == "agent_message":
            text = str(item.get("text", "")).strip()
            if text:
                messages.append(text)
    return messages


def is_probable_java_source(text: str) -> bool:
    """Heuristic guard for distinguishing Java source from status prose."""
    src = (text or "").strip()
    if not src:
        return False
    return any(
        marker in src
        for marker in ("public class ", "@Test", "import org.junit", "package ")
    )


def extract_codex_file_readback(raw_jsonl: str, file_name: str) -> str:
    """Recover the latest Java file content from Codex command outputs.

    Codex often verifies its edit with `sed -n ... <file>` before finishing.
    When the final on-disk output file is unexpectedly corrupted, the stdout
    event log still contains the correct readback.
    """
    best = ""
    for event in parse_codex_exec_events(raw_jsonl):
        item = event.get("item") or {}
        if item.get("type") != "command_execution":
            continue
        if item.get("status") != "completed":
            continue
        command = str(item.get("command", ""))
        if file_name not in command:
            continue
        output = str(item.get("aggregated_output", ""))
        if is_probable_java_source(output):
            best = output
    return best


def analyze_codex_skill_usage(raw_jsonl: str, skill_cards: Optional[list[dict]] = None) -> dict:
    """Return structured evidence of skill loading and compile/repair activity.

    `skill_applied` historically meant "the driver found skill files". For the
    Codex native-discovery experiment we also need to know whether the running
    agent actually read a SKILL.md file, so this parser derives that from the
    JSONL command events.
    """
    skill_cards = skill_cards or []
    expected_paths = {
        str(c.get("path", "")): str(c.get("name", "") or c.get("mode", ""))
        for c in skill_cards
        if c.get("path")
    }
    expected_names = {str(c.get("name", "")) for c in skill_cards if c.get("name")}

    loaded_names: set[str] = set()
    loaded_paths: set[str] = set()
    compile_sh_runs = 0
    compile_sh_failures = 0
    compile_sh_successes = 0

    for event in parse_codex_exec_events(raw_jsonl):
        item = event.get("item") or {}
        if item.get("type") != "command_execution":
            continue

        command = str(item.get("command", ""))
        output = str(item.get("aggregated_output", ""))
        searchable = f"{command}\n{output}"

        for path, name in expected_paths.items():
            if path and path in searchable:
                loaded_paths.add(path)
                if name:
                    loaded_names.add(name)

        # Backward-compatible aliases: early runs used both directory names in
        # different workspaces.
        if ".agents/skills/compile-check/SKILL.md" in searchable \
                or ".agents/skills/compile-coverage-check/SKILL.md" in searchable:
            loaded_names.add("compile-check")
        if ".agents/skills/repair-loop/SKILL.md" in searchable:
            loaded_names.add("repair-loop")

        if "bash compile.sh" not in command or event.get("type") != "item.completed":
            continue
        compile_sh_runs += 1
        status = str(item.get("status", ""))
        exit_code = item.get("exit_code")
        if "COMPILE_FAIL" in output or status == "failed" \
                or exit_code not in (0, None):
            compile_sh_failures += 1
        if "COMPILE_OK" in output:
            compile_sh_successes += 1

    compile_loaded = bool(
        {"compile-check", "compile-coverage-check"} & loaded_names
    )
    repair_loaded = "repair-loop" in loaded_names
    return {
        "skill_configured": bool(skill_cards),
        "skill_used": bool(loaded_names),
        "skill_loaded_names": sorted(loaded_names),
        "skill_loaded_paths": sorted(loaded_paths),
        "skill_load_evidence": {
            "compile_check_loaded": compile_loaded,
            "repair_loop_loaded": repair_loaded,
            "expected_skill_names": sorted(expected_names),
            "expected_skill_paths": sorted(expected_paths),
        },
        "compile_check_invoked": compile_sh_runs > 0,
        "repair_loop_invoked": repair_loaded or compile_sh_failures > 0,
        "compile_sh_runs": compile_sh_runs,
        "compile_sh_failures_during_agent": compile_sh_failures,
        "compile_sh_successes_during_agent": compile_sh_successes,
    }


def render_codex_trace(raw_jsonl: str) -> str:
    """Render Codex exec events into a compact human-readable trace."""
    lines: list[str] = []
    for idx, event in enumerate(parse_codex_exec_events(raw_jsonl), start=1):
        item = event.get("item") or {}
        event_type = event.get("type", "?")
        if item.get("type") == "agent_message":
            lines.append(f"[{idx}] AGENT")
            lines.append(str(item.get("text", "")))
            lines.append("")
            continue
        if item.get("type") == "command_execution":
            lines.append(f"[{idx}] COMMAND {item.get('status', '')}".strip())
            lines.append(str(item.get("command", "")))
            output = str(item.get("aggregated_output", ""))
            if output:
                lines.append("--- output ---")
                lines.append(output)
            lines.append("")
            continue
        if item.get("type") == "file_change":
            lines.append(f"[{idx}] FILE_CHANGE {item.get('status', '')}".strip())
            for change in item.get("changes", []) or []:
                lines.append(f"{change.get('kind', 'change')}: {change.get('path', '')}")
            lines.append("")
            continue
        message = event.get("message")
        if message:
            lines.append(f"[{idx}] {event_type.upper()}")
            lines.append(str(message))
            lines.append("")
    return "\n".join(lines).strip() + ("\n" if lines else "")


# ═════════════════════════════════════════════════════════════════════════════
# 8.  Per-target driver — one Codex session for one target
# ═════════════════════════════════════════════════════════════════════════════

def _output_label(model_id: str) -> str:
    """Tag Codex model outputs with a "codex-" prefix to distinguish from Claude."""
    return f"codex-{model_id}"


async def _improve_one_target(*,
        target: AgentTarget,
        out_dir: Path,
        file_prefix: str,
        cut_source_path: Optional[Path],
        project_classpath: str,
        javac_path: str,
        codex_cli: str,
        model_id: str,
        prompt_version: str,
        cfg,
        project_id: str,
        class_name: str,
        ) -> str:
    """Run ONE Codex CLI session against ONE target.

    Steps:
      1. Idempotent skip if state.json already complete
      2. Prepare output working directory (compile.sh + test-classes/)
      3. Build initial prompt:
           skill bodies (inlined) + task template
      4. Subprocess-call the Codex CLI
      5. Persist Codex stdout/stderr/trace next to state.json
      6. Read agent's final edit from the output directory (or recover from stdout)
      7. Write state.json + supporting files
      8. Clean up scratch files (unless cfg.codex_agent.keep_sandbox=true)
    """
    # ── 1. Idempotent skip ────────────────────────────────────────────────────
    state_path = out_dir / (f"{file_prefix}_state.json" if file_prefix else "state.json")
    if state_path.exists():
        try:
            existing = llm_refactor.load_state(state_path)
            if len(existing.get("messages", [])) >= 2:
                log.info(f"  [{_output_label(model_id)}] "
                         f"{target.test_category}/{target.test_id}: "
                         "existing state.json present — skipping agent call")
                return existing.get("status", "PENDING")
        except Exception:
            pass

    codex_cfg = cfg.codex_agent
    sandbox_mode = str(getattr(codex_cfg, "sandbox_mode", "workspace-write"))
    max_turns = int(getattr(codex_cfg, "max_turns_with_repair", 5))

    work_dir = None
    skill_cards: list[dict] = []
    try:
        # ── 2. Prepare output working directory ──────────────────────────────
        work_dir = Path(out_dir)
        classes_out = work_dir / "test-classes"
        classes_out.mkdir(parents=True, exist_ok=True)
        classpath = _build_classpath(target, project_classpath,
                                      str(classes_out), cfg)

        work_dir, output_test = _build_working_dir(
            target=target,
            classpath=classpath,
            javac_path=javac_path,
            work_dir=work_dir,
            classes_out=classes_out,
        )

        # ── 3. Build initial prompt ──────────────────────────────────────────
        initial_prompt = build_agent_improvement_prompt(
            test_java_path=target.java_path,
            cut_source=str(cut_source_path) if cut_source_path else "",
            prompt_version=prompt_version,
            working_dir=str(work_dir.resolve()),
        )
        # Option B parity with the Claude driver (agent_with_repair): auto
        # (EvoSuite) targets get the EvoSuite guard-rails appended to the TASK
        # message — keeping the rest of the prompt identical for manual & auto.
        # Imported from agent_with_repair so the constraint text has a single
        # source of truth.
        if target.scaffolding_java:
            import agent_with_repair
            initial_prompt = initial_prompt + agent_with_repair.EVOSUITE_TASK_CONSTRAINTS
        # For the preliminary Codex parity experiment, do NOT eager-inject the
        # SKILL.md bodies. Let Codex's native .agents/skills discovery decide
        # whether it can find and invoke compile-check / repair-loop from cwd.
        skill_cards = load_skill_cards(["compile_check", "repair"], cfg)
        full_prompt = initial_prompt

        log.info(f"  [{_output_label(model_id)}] "
                 f"{target.test_category}/{target.test_id}: "
                 "calling Codex repair agent…")

        # ── 4. Subprocess-call Codex CLI ─────────────────────────────────────
        rc, stdout, stderr, last_message = await _run_codex_exec(
            codex_cli=codex_cli,
            work_dir=work_dir,
            prompt=full_prompt,
            model_id=model_id,
            sandbox_mode=sandbox_mode,
            reasoning_effort=(str(getattr(codex_cfg, "reasoning_effort", "")).strip() or None),
        )

        # ── 5. Persist Codex stdout/stderr/trace ─────────────────────────────
        out_dir.mkdir(parents=True, exist_ok=True)

        def _p(base: str) -> Path:
            return out_dir / (f"{file_prefix}_{base}" if file_prefix else base)

        _p("codex_exec_stdout.jsonl").write_text(stdout, encoding="utf-8")
        _p("codex_exec_stderr.txt").write_text(stderr, encoding="utf-8")
        trace_text = render_codex_trace(stdout)
        if trace_text.strip():
            _p("trace.txt").write_text(trace_text, encoding="utf-8")
        agent_messages = extract_codex_agent_messages(stdout)
        if agent_messages:
            _p("codex_agent_messages.txt").write_text(
                "\n\n".join(agent_messages), encoding="utf-8",
            )
        skill_usage = analyze_codex_skill_usage(stdout, skill_cards)

        # ── 6. Read agent's final edit (with fallback to stdout readback) ────
        final_code = output_test.read_text(encoding="utf-8") \
                     if output_test.exists() else ""
        recovered_code = ""
        if not is_probable_java_source(final_code):
            recovered_code = extract_codex_file_readback(stdout, output_test.name)
            if is_probable_java_source(recovered_code):
                final_code = recovered_code
        java_path = ""
        if final_code.strip():
            fallback = target.suite_class or class_name or "ImprovedTest"
            java_path = llm_refactor._write_java_by_class_name(
                final_code, str(out_dir), fallback_class_name=fallback)

        model_label = _output_label(model_id)
        has_useful_output = bool(final_code.strip()) or bool(last_message.strip())

        # ── 7. Write state.json ──────────────────────────────────────────────
        if rc != 0 and not has_useful_output:
            err_state = {
                "status": "LLM_ERROR",
                "attempts_used": 0,
                "model": model_label,
                "provider_model": model_id,
                "prompt_version": prompt_version,
                "messages": [{"role": "user", "content": full_prompt}],
                "last_code": "",
                "last_error": stderr or f"codex exec exited with code {rc}",
                "last_java_path": "",
                "initial_prompt": full_prompt,
                "improvement_mode": "codex_agent_improve_and_repair",
                "project_id": project_id,
                "target_class": class_name,
                "test_category": target.test_category,
                "test_id": target.test_id,
                "suite_class": target.suite_class,
                "is_evosuite": target.is_evosuite,
                "scaffolding_java": target.scaffolding_java or None,
                "original_java": target.java_path,
                "codex_exit_code": rc,
                "skill_names": [c.get("name", "") for c in skill_cards if c.get("name")],
                "skill_paths": [c.get("path", "") for c in skill_cards if c.get("path")],
                "skill_applied": bool(skill_cards),
                **skill_usage,
                "file_prefix": file_prefix,
            }
            llm_refactor.save_state(err_state, state_path)
            return "LLM_ERROR"

        state = _write_codex_state(
            out_dir=out_dir,
            file_prefix=file_prefix,
            initial_prompt=full_prompt,
            final_assistant_text="\n\n".join(agent_messages) if agent_messages else last_message,
            final_code=final_code,
            final_java_path=java_path,
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
            model_label=model_label,
            prompt_version=prompt_version,
            extra={
                "improvement_mode": "codex_agent_improve_and_repair",
                "provider_model": model_id,
                "codex_exit_code": rc,
                "skill_names": [c.get("name", "") for c in skill_cards if c.get("name")],
                "skill_paths": [c.get("path", "") for c in skill_cards if c.get("path")],
                "codex_last_message": last_message,
                "codex_recovered_source_from_events": bool(recovered_code),
                "skill_applied": bool(skill_cards),
                "skill_injection_mode": "native_discovery",
                **skill_usage,
            },
        )

        if state.get("last_java_path"):
            print(f"[codex_agent_with_repair] Generated: {state['last_java_path']}")
        log.info(f"    → status: {state['status']} (compile pending)")
        return state["status"]

    except Exception as e:
        log.error(f"  [{_output_label(model_id)}] "
                  f"{target.test_category}/{target.test_id}: "
                  f"Codex repair agent call failed: {e}")
        out_dir.mkdir(parents=True, exist_ok=True)
        err_state = {
            "status": "LLM_ERROR",
            "attempts_used": 0,
            "model": _output_label(model_id),
            "provider_model": model_id,
            "prompt_version": prompt_version,
            "messages": [],
            "last_code": "",
            "last_error": str(e),
            "last_java_path": "",
            "initial_prompt": "",
            "improvement_mode": "codex_agent_improve_and_repair",
            "project_id": project_id,
            "target_class": class_name,
            "test_category": target.test_category,
            "test_id": target.test_id,
            "suite_class": target.suite_class,
            "is_evosuite": target.is_evosuite,
            "scaffolding_java": target.scaffolding_java or None,
            "original_java": target.java_path,
            "skill_names": [c.get("name", "") for c in skill_cards if c.get("name")],
            "skill_paths": [c.get("path", "") for c in skill_cards if c.get("path")],
            "skill_applied": bool(skill_cards),
            "file_prefix": file_prefix,
        }
        llm_refactor.save_state(err_state, state_path)
        return "LLM_ERROR"
    finally:
        # ── 8. Clean up scratch ──────────────────────────────────────────────
        keep_sandbox = bool(getattr(codex_cfg, "keep_sandbox", False))
        if work_dir and work_dir.exists() and not keep_sandbox:
            for scratch in (
                work_dir / "compile.sh",
                work_dir / "test-classes",
                work_dir / "codex_last_message.txt",
                work_dir / ".agents",
            ):
                try:
                    if scratch.is_symlink() or scratch.is_file():
                        scratch.unlink()
                    elif scratch.is_dir():
                        shutil.rmtree(scratch)
                except Exception as e:
                    log.warning(f"  could not clean scratch {scratch}: {e}")


# ═════════════════════════════════════════════════════════════════════════════
# 9.  Async fan-out + public sync entry point
# ═════════════════════════════════════════════════════════════════════════════

async def _run_bounded(sem: asyncio.Semaphore,
                        coro_factory: Callable[[], Awaitable[Any]]) -> Any:
    """Acquire `sem`, run the coroutine, release."""
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
    codex_cfg = getattr(cfg, "codex_agent", None)
    if codex_cfg is None:
        raise RuntimeError(
            "config.yaml is missing a `codex_agent:` section — required for Codex runs."
        )

    concurrency = int(getattr(codex_cfg, "concurrency", 2))
    codex_cli = resolve_codex_cli(cfg)
    if not codex_cli:
        raise RuntimeError(
            "Codex CLI executable not found. Install Codex, add `codex` to PATH, "
            "or set `codex_agent.cli_path` in config.yaml."
        )
    javac_path = _javac_path_from_cfg(cfg)
    sem = asyncio.Semaphore(concurrency)
    prompt_versions = list(llm_refactor.PROMPT_VERSIONS)

    coros = []
    for prompt_version in prompt_versions:
        for model_id in models:
            for t in targets:
                out_dir, file_prefix = improvement_dir_fn(
                    project_id, class_name, prompt_version,
                    _output_label(model_id), t,
                )

                def make_factory(tt=t, mid=model_id, od=out_dir,
                                  pv=prompt_version, fp=file_prefix):
                    async def _go():
                        return await _improve_one_target(
                            target=tt,
                            out_dir=od,
                            file_prefix=fp,
                            cut_source_path=cut_source_path,
                            project_classpath=classpath,
                            javac_path=javac_path,
                            codex_cli=codex_cli,
                            model_id=mid,
                            prompt_version=pv,
                            cfg=cfg,
                            project_id=project_id,
                            class_name=class_name,
                        )
                    return _go

                coros.append(_run_bounded(sem, make_factory()))

    if not coros:
        log.warning("[codex_agent_with_repair] no targets to run")
        return

    log.info(f"[codex_agent_with_repair] launching {len(coros)} agent task(s) "
             f"with concurrency={concurrency}")
    results = await asyncio.gather(*coros, return_exceptions=True)
    for idx, r in enumerate(results, start=1):
        if isinstance(r, Exception):
            log.error(f"[codex_agent_with_repair] task #{idx} raised "
                      f"{type(r).__name__}: {r}")
    n_ok = sum(1 for r in results if r in ("PENDING",))
    n_err = sum(1 for r in results if r == "LLM_ERROR" or isinstance(r, Exception))
    log.info(f"[codex_agent_with_repair] done — pending(ok)={n_ok}, "
             f"errors={n_err} / total={len(results)}")


def run(*, targets: list,
        models: list[str],
        cut_source_path: Optional[Path],
        classpath: str,
        cfg,
        project_id: str,
        class_name: str,
        improvement_dir_fn) -> None:
    """The IMPROVE-AND-REPAIR Codex CLI agent run.

    Called by main.run_codex_step. Targets come from main.ImprovementTarget;
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
