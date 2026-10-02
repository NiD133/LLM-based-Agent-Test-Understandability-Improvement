"""
Shared helpers for the agent-driven improvement pipeline.

After the direct-API LLM path was retired in favor of Claude Code SDK +
Codex CLI agents, this module is reduced to the small set of helpers the
agent pipeline still needs:

  Prompt assembly:
    - load_prompt_template(prompt_version)
    - build_improvement_prompt(template, test_code, cut_signature, cut_full)
    - get_cut_signature(source_file)          # bodies → /* ... */
    - get_cut_full(source_file)

  Java extraction / on-disk writing:
    - extract_java_code(llm_response)
    - _write_java_by_class_name(code, out_dir)

  State persistence (the canonical state.json schema):
    - save_state(state, path)
    - load_state(path)
    - _messages_to_readable(messages)
    - append_conversation_log(path, entry)
    - recompile_existing(state, compile_fn, classpath, compiled_dir, work_dir)

state.json schema:
  {
    "status": "PENDING | COMPILE_SUCCESS | COMPILE_FAIL | MAX_ATTEMPTS | LLM_ERROR",
    "attempts_used": int,
    "messages": [{"role": "user"|"assistant", "content": str}, ...],
    "last_code": str,
    "last_error": str,
    ... (metadata)
  }
"""
from __future__ import annotations

import json
import re
from pathlib import Path


PROJECT_ROOT = Path(__file__).parent.parent

# The study uses ONE user-message template for the improvement task. Its file
# is prompts/<PROMPT_VERSION>.txt; the system prompt is always
# prompts/improve_system_prompt.txt. Every improved test records this name in
# its state.json ("prompt_version") and in metrics.json.
PROMPT_VERSION = "understandability_improvement_constraints"
PROMPT_VERSIONS = [PROMPT_VERSION]


# ══════════════════════════════════════════════════════════════════════════════
# Prompt helpers
# ══════════════════════════════════════════════════════════════════════════════

def load_prompt_template(prompt_version: str) -> str:
    """Load a prompt template from /prompts/<version>.txt."""
    prompt_file = PROJECT_ROOT / "prompts" / f"{prompt_version}.txt"
    if not prompt_file.exists():
        raise FileNotFoundError(f"Prompt file not found: {prompt_file}")
    return prompt_file.read_text(encoding="utf-8")


def build_improvement_prompt(
    template: str,
    *,
    test_file: str = "",
    cut_file: str = "",
    # Legacy by-value placeholders — kept for backward compatibility with
    # callers that still inline source. The current Anthropic-aligned path
    # is by-reference (file paths), so prefer test_file / cut_file.
    test_code: str = "",
    cut_signature: str = "",
    cut_full: str = "",
) -> str:
    """Fill placeholders in the improvement prompt template.

    By-reference (preferred, current templates):
      {test_file}      — sandbox-relative path to the test
      {cut_file}       — sandbox-relative path to the class under test

    By-value (legacy, no longer in templates):
      {test_code}      — the test source being refactored
      {cut_signature}  — CUT source with method bodies stripped
      {cut_full}       — full CUT source code (unmodified)
    """
    return (template
            .replace("{test_file}", test_file)
            .replace("{cut_file}", cut_file)
            .replace("{test_code}", test_code)
            .replace("{cut_signature}", cut_signature)
            .replace("{cut_full}", cut_full))


# ══════════════════════════════════════════════════════════════════════════════
# Java code extraction
# ══════════════════════════════════════════════════════════════════════════════

def extract_java_code(llm_response: str) -> str:
    """Extract Java code from an LLM response.

    Preference order:
      1. the last fenced ```java block
      2. the last fenced block of any type
      3. the last bare Java class/package declaration in the response
      4. the raw stripped text
    """
    fence_pattern = re.compile(r'```(?P<lang>[A-Za-z0-9_+-]*)\s*\n(?P<body>.*?)```',
                               re.DOTALL)
    fence_matches = list(fence_pattern.finditer(llm_response))
    if fence_matches:
        java_fences = [
            m for m in fence_matches
            if str(m.group("lang") or "").strip().lower() == "java"
        ]
        chosen = java_fences[-1] if java_fences else fence_matches[-1]
        return chosen.group("body").strip()

    class_matches = list(re.finditer(
        r'((?:package\s+[\w.]+\s*;\s*)?\s*(?:import\s+[^;]+;\s*)*'
        r'\s*(?:(?:@\w+[^\n]*\n\s*)*)'
        r'\s*(?:public\s+(?:abstract\s+)?class\s+\w+|class\s+\w+))',
        llm_response,
        re.DOTALL,
    ))
    if class_matches:
        return llm_response[class_matches[-1].start():].strip()
    return llm_response.strip()


def _looks_like_java_source(text: str) -> bool:
    src = (text or "").strip()
    if not src:
        return False
    return any(
        marker in src
        for marker in ("public class ", "@Test", "import org.junit", "package ")
    )


def _recover_java_from_codex_stdout(work_dir: str) -> str:
    """Best-effort recovery of edited Java from Codex command-execution events.

    Codex often verifies its edit with `sed -n ... <file>` before finishing.
    When the final on-disk sandbox file is unexpectedly corrupted, the stdout
    event log still contains the correct readback.
    """
    stdout_path = Path(work_dir) / "codex_exec_stdout.jsonl"
    if not stdout_path.exists():
        return ""
    best = ""
    try:
        for line in stdout_path.read_text(encoding="utf-8", errors="replace").splitlines():
            line = line.strip()
            if not line:
                continue
            event = json.loads(line)
            item = event.get("item") or {}
            if item.get("type") != "command_execution":
                continue
            if item.get("status") != "completed":
                continue
            output = str(item.get("aggregated_output", ""))
            if _looks_like_java_source(output):
                best = output
    except Exception:
        return ""
    return best


# ══════════════════════════════════════════════════════════════════════════════
# State persistence
# ══════════════════════════════════════════════════════════════════════════════

def save_state(state: dict, path) -> None:
    """Dump state dict to path as indented JSON."""
    p = Path(path)
    p.parent.mkdir(parents=True, exist_ok=True)
    p.write_text(json.dumps(state, indent=2, ensure_ascii=False), encoding="utf-8")


def load_state(path) -> dict:
    """Load state dict from JSON file."""
    return json.loads(Path(path).read_text(encoding="utf-8"))


# ══════════════════════════════════════════════════════════════════════════════
# Compile rerun (no LLM call) — used by run_compile_improvement_outputs
# ══════════════════════════════════════════════════════════════════════════════

def recompile_existing(
    state: dict,
    compile_fn,                 # callable: (java_file, cp, out_dir) -> (bool, stderr)
    classpath: str,
    compiled_dir: str,
    work_dir: str,
) -> dict:
    """Recompile the LLM output already stored in `state` WITHOUT calling the LLM.

    PREFERS the on-disk file at ``state["last_java_path"]`` — that's the
    canonical source of truth for both code paths:
      • agent-with-repair (modern): the post-session save-back already wrote
        the agent's edited Java there.
      • direct-API (legacy): the first compile attempt wrote it there.

    Only falls back to re-extracting Java from ``state["messages"]`` when the
    on-disk file is missing or is clearly NOT Java (this is a recovery path
    for crashes that lost the .java file). The old behaviour — always
    re-extracting from natural-language assistant text — corrupted state for
    agent runs because the agent's text is a summary like "I refactored into
    `@Test` methods…" which `extract_java_code`+`_looks_like_java_source`
    happily mistook for Java (the substring `@Test` is enough to trigger a
    false positive).

    Updates `status`, `last_error`, `last_code`, and `last_java_path` in
    place. Does NOT touch `messages` or `attempts_used`.

    Raises ValueError if the state contains no usable prior response.
    """
    # ── PRIORITY 1: trust the on-disk file ──────────────────────────────────
    existing_java_path = state.get("last_java_path") or ""
    if existing_java_path:
        try:
            disk_path = Path(existing_java_path)
            if disk_path.exists():
                disk_code = disk_path.read_text(encoding="utf-8")
                if _looks_like_java_source(disk_code):
                    # File on disk is real Java — just recompile it.
                    state["last_code"] = disk_code
                    ok, stderr = compile_fn(
                        str(disk_path), classpath, compiled_dir)
                    state["last_error"] = stderr or ""
                    state["status"] = ("COMPILE_SUCCESS" if ok
                                       else "COMPILE_FAIL")
                    return state
        except Exception:
            # Fall through to legacy extraction below.
            pass

    # ── PRIORITY 2: legacy extraction (only when disk path is missing/junk)
    messages = state.get("messages") or []
    response_text = ""
    if len(messages) >= 2 and messages[1].get("role") == "assistant":
        response_text = messages[1].get("content", "")
    extracted_from_response = extract_java_code(response_text) if response_text else ""
    fallback_code = state.get("last_code", "") or ""

    if _looks_like_java_source(extracted_from_response):
        extracted = extracted_from_response
    elif _looks_like_java_source(fallback_code):
        extracted = fallback_code
    elif extracted_from_response:
        extracted = extracted_from_response
    elif fallback_code:
        extracted = fallback_code
    else:
        extracted = _recover_java_from_codex_stdout(work_dir)
        if not extracted:
            raise ValueError(
                "recompile_existing: state has neither messages[1] nor last_code"
            )
    state["last_code"] = extracted

    java_path = _write_java_by_class_name(extracted, work_dir)
    state["last_java_path"] = str(Path(java_path).resolve())

    ok, stderr = compile_fn(java_path, classpath, compiled_dir)
    state["last_error"] = stderr or ""
    state["status"] = "COMPILE_SUCCESS" if ok else "COMPILE_FAIL"
    return state


# ══════════════════════════════════════════════════════════════════════════════
# Internal helpers
# ══════════════════════════════════════════════════════════════════════════════

def _strip_comments_for_scan(code: str) -> str:
    """Remove // line comments and /* */ block comments so a class-name scan
    doesn't accidentally match prose like 'the helper class that captures' or
    'the class names describe ...' inside a Javadoc. String literals are left
    in place (a `class X` inside a string literal is extremely unlikely in a
    test header)."""
    # block comments (incl. /** Javadoc */), non-greedy, across lines
    no_block = re.sub(r'/\*.*?\*/', '', code, flags=re.DOTALL)
    # line comments
    no_line = re.sub(r'//[^\n]*', '', no_block)
    return no_line


def _write_java_by_class_name(code: str, out_dir: str,
                              fallback_class_name: str = "FinalTest") -> str:
    """Write `code` to <out_dir>/<ClassName>.java.

    Accepts both `public class Foo` and the package-private `class Foo`
    (common in JUnit 5 tests). The fallback name should be passed by the
    caller — relying on a literal "FinalTest" silently masks LLM errors
    that returned non-Java content.

    BUGFIX: we scan a COMMENT-STRIPPED copy of the code. Previously the regex
    matched the first `class <word>` anywhere, which could hit Javadoc prose
    such as '... the helper class that captures ...' → filename `that.java`,
    or '... the class names describe ...' → `names.java`, making the public
    class / filename mismatch and javac fail. Stripping comments first means
    the first real `class` token is the declaration. We also PREFER a
    `public ... class` match (the top-level public test class), falling back
    to any `class` only if there is no public one."""
    scan = _strip_comments_for_scan(code)
    # Prefer the public top-level class (test suites are public).
    m = re.search(r'public\s+(?:final\s+|abstract\s+)*class\s+(\w+)', scan)
    if not m:
        # Package-private top-level class (common for JUnit 5).
        m = re.search(r'(?:^|\n)\s*(?:final\s+|abstract\s+)*class\s+(\w+)', scan)
    if not m:
        # Last resort: any class token in the stripped code.
        m = re.search(r'class\s+(\w+)', scan)
    class_name = m.group(1) if m else fallback_class_name
    dest = Path(out_dir) / f"{class_name}.java"
    dest.parent.mkdir(parents=True, exist_ok=True)
    dest.write_text(code, encoding="utf-8")
    return str(dest)


