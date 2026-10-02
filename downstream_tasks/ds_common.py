#!/usr/bin/env python3
"""
Shared helpers for the RQ4 oracle-generation studies
(oracle_generation_evosuite/maskfirst_50 and improvefirst_49).

Reuses (imports, never modifies) agent_improvement/scripts/agent_with_repair.py
for the Claude Agent SDK session machinery.

Key jobs:
  - build a per-unit sandbox by rsync-copying a KEPT repo (with excludes),
  - drop a sandbox-local .claude/skills/{compile-check,repair-loop} + a
    build.sh whose body we control (single-file javac OR full `mvn test`),
  - run ONE Claude Agent SDK session (run_claude_session) with a caller-supplied
    system prompt, user message, writable-file path and read-allow set,
  - collect trace / token usage / step count / wall-clock.
"""
import os, re, json, time, shutil, subprocess, asyncio, tempfile
from pathlib import Path

PROJECT_ROOT = Path(__file__).resolve().parent.parent
import sys
sys.path.insert(0, str(PROJECT_ROOT / "agent_improvement" / "scripts"))
sys.path.insert(0, str(PROJECT_ROOT / "downstream_tasks"))
import agent_with_repair as AWR          # noqa: E402  (SDK session helpers)
import main as M                         # noqa: E402  (load_config, .env)

import logging
log = logging.getLogger("ds_common")


# ── the ONE build-script name ─────────────────────────────────────────────
# The sandbox's verification script. Named `build.sh` (not `compile.sh`) because
# the CUT-generation flavour runs a full `mvn test-compile`, and the skill that
# drives it is `build-project`. Everything that must agree on this name derives
# it from here, so the sandbox file, the Bash whitelist in decide_tool, the
# trace parser and the copied SKILL.md text can never drift apart.
# NOTE: the UPSTREAM improvement harness (scripts/agent_with_repair.py) still
# ships `compile.sh`; its SKILL.md sources are shared with us, so we rewrite the
# name in the sandbox COPY only (see write_sandbox_skills).
BUILD_SCRIPT = "build.sh"
BUILD_CMDS = (f"bash {BUILD_SCRIPT}", f"sh {BUILD_SCRIPT}", f"./{BUILD_SCRIPT}")


# ── config access ─────────────────────────────────────────────────────────
def load_cfg(config_path: str):
    return M.load_config(config_path)

def ns_get(ns, *path, default=None):
    cur = ns
    for k in path:
        cur = getattr(cur, k, None) if not isinstance(cur, dict) else cur.get(k)
        if cur is None:
            return default
    return cur


def maven_path(cfg) -> str:
    return ns_get(cfg, "tools", "maven_path", default="mvn")


# ── sandbox construction ──────────────────────────────────────────────────
# Agent-runtime / tooling dirs that are irrelevant to the Maven build and can be
# pathologically deep. HippoBuddy commits a real .hippo/.hippo/.hippo/… tree that
# exceeds PATH_MAX and breaks BOTH rsync and shutil — always skip these.
_JUNK_DIRS = [".hippo", "node_modules", ".gradle", ".idea", ".vscode", ".venv"]


def rsync_repo(src: Path, dst: Path, exclude: list[str]):
    """Copy a repo tree with excludes (rsync preferred, shutil fallback). Always
    also skips _JUNK_DIRS. rsync exit 23/24 = partial (vanished/permission) and is
    treated as success; only a hard rsync failure falls back to shutil."""
    dst.mkdir(parents=True, exist_ok=True)
    excludes = list(exclude) + [j for j in _JUNK_DIRS if j not in exclude]
    ex = []
    for e in excludes:
        ex += ["--exclude", e]
    r = subprocess.run(["rsync", "-a", *ex, f"{src}/", f"{dst}/"],
                       capture_output=True, text=True)
    if r.returncode in (0, 23, 24):   # 0=ok, 23/24=partial transfer (still usable)
        return
    # hard rsync failure (e.g. rsync missing) → shutil, skipping junk + not following symlinks
    log.warning(f"[rsync_repo] rsync rc={r.returncode}; shutil fallback for {src.name}")
    def ign(d, names):
        return [n for n in names if n in excludes]
    if dst.exists():
        shutil.rmtree(dst)
    shutil.copytree(src, dst, ignore=ign, symlinks=True)


_JP_DIR = PROJECT_ROOT / "tools" / "javaparser"
_JP_JAR = _JP_DIR / "javaparser-core-3.27.0.jar"


def _java11plus_home() -> str:
    """JDK (>= 11) for the compiled JavaParser tools: JDK_11_HOME or JDK_21_HOME
    from .env, else "" (java on PATH)."""
    for var in ("JDK_11_HOME", "JDK_21_HOME"):
        cand = os.environ.get(var, "").strip()
        if cand and Path(cand, "bin", "java").exists():
            return cand
    return ""


def _run_jp_tool(classname: str, java_file: Path, timeout=120):
    """Run a compiled JavaParser tool (tools/javaparser/<classname>.class) on one
    .java file. Returns stdout, or None if the JVM/jar/class is unavailable or it
    fails — so every caller can fall back to a text heuristic."""
    jh = _java11plus_home()
    cls = _JP_DIR / f"{classname}.class"
    if not (jh and _JP_JAR.exists() and cls.exists()):
        return None
    try:
        r = subprocess.run([f"{jh}/bin/java", "-cp", f"{_JP_JAR}:{_JP_DIR}", classname, str(java_file)],
                           capture_output=True, text=True, timeout=timeout)
    except Exception:
        return None
    return r.stdout if r.returncode == 0 else None


# oracle detection = a call to assert*/verify* (JUnit assertions + Mockito
# verifications). See tools/javaparser/OracleStripper.java for the AST definition.
_ORACLE_START = re.compile(r'\b(assert[A-Za-z]*|verify[A-Za-z]*)\s*\(')


def count_oracles(src: str) -> int:
    """Count oracle STATEMENTS the same way strip_oracles removes them (AST
    OracleStripper COUNT) so oracles_original matches oracles_removed. Falls back to
    the text heuristic if the JVM/tool is unavailable."""
    with tempfile.NamedTemporaryFile("w", suffix=".java", delete=False) as tf:
        tf.write(src); tmp = Path(tf.name)
    try:
        out = _run_jp_tool("OracleStripper", tmp)
    finally:
        tmp.unlink(missing_ok=True)
    if out is not None:
        for line in out.splitlines():
            if line.startswith("COUNT"):
                return int(line.split()[1])
    return len(_ORACLE_START.findall(src))


def write_sandbox_skills(sandbox: Path, build_sh_body: str,
                         skills=("compile-check", "repair-loop")):
    """Drop a sandbox-local .claude/skills + .agents/skills (the named skills)
    and a build.sh whose body the caller controls. The SKILL.md text is copied
    from the project's real skills so the agent behaves the same; only
    build.sh's CONTENT differs. `skills` selects which skill(s) to install:
      - oracle generation → ("compile-check", "repair-loop")  single-file javac
      - CUT generation    → ("build-project", "repair-loop")  compile-only mvn
    Both flavours drive the SAME `bash build.sh`, so repair-loop is unchanged."""
    for root in (".claude", ".agents"):
        for sk in skills:
            src = PROJECT_ROOT / root / "skills" / sk / "SKILL.md"
            dst = sandbox / root / "skills" / sk / "SKILL.md"
            dst.parent.mkdir(parents=True, exist_ok=True)
            # compile-check / repair-loop are SHARED with the upstream improvement
            # harness, which still ships `compile.sh` — so rewrite the script name
            # in this COPY instead of editing the shared source.
            dst.write_text(
                src.read_text(encoding="utf-8").replace("compile.sh", BUILD_SCRIPT),
                encoding="utf-8")
    cs = sandbox / BUILD_SCRIPT
    cs.write_text(build_sh_body, encoding="utf-8")
    cs.chmod(0o755)


# ── permission callback (write ONE file, read sandbox+allow, bash build.sh) ─
# Sandbox-local build scaffolding: the compile script and the skill cards. They
# carry no information about the class under test, and the agent's own workflow
# lives in them, so they stay readable even under the strictest read condition.
_SCAFFOLD_ROOTS = ("compile.sh", "build.sh", ".claude", ".agents")


def _is_scaffolding(target: Path, sandbox: Path) -> bool:
    try:
        parts = target.relative_to(sandbox).parts
    except ValueError:
        return False
    return bool(parts) and parts[0] in _SCAFFOLD_ROOTS


def decide_tool(tool_name, tool_input, *, sandbox: Path, writable_abs: Path,
                read_allow, deny_read=(), strict_read_allow=False) -> tuple:
    """THE single scope rule for a tool call → (allowed: bool, reason: str).

    Shared by BOTH enforcement paths so they can never drift:
      - the PreToolUse hook (authoritative: the SDK fires it for EVERY tool call),
      - the can_use_tool callback (only fires when the CLI would have prompted, so
        with `allowed_tools` + permission_mode="acceptEdits" it is nearly never
        called — this was the hole that let the hidden CUT be read).

    Rules: Write/Edit only `writable_abs`; Bash only the build script; sub-agent
    spawning is refused outright (it runs its own unscoped tool loop).

    Reads have TWO modes, and picking the wrong one silently voids an experiment:
      strict_read_allow=False (BLACKLIST, the default) — anything under the sandbox
        is readable except a deny_read path, or a directory containing one so that
        a dir-level Grep cannot leak it. Use for a condition that hides specific
        files but otherwise lets the agent browse the project.
      strict_read_allow=True (WHITELIST) — ONLY the paths in read_allow, plus the
        build scaffolding, are readable. Required by any condition of the form
        "the agent may read only X": under the blacklist the sandbox-prefix rule
        admits every file in the repo, so read_allow is decorative there and the
        restriction never takes effect.
    """
    sb = Path(sandbox).resolve()
    wp = Path(writable_abs).resolve()
    allow = {Path(p).resolve() for p in read_allow if p}
    deny = {Path(p).resolve() for p in deny_read if p}
    ti = tool_input or {}

    def denied(tr: Path) -> bool:
        for d in deny:
            if tr == d:
                return True
            try:
                if d.is_relative_to(tr):   # tr is a dir that contains a denied file
                    return True
            except Exception:
                pass
        return False

    if tool_name in ("Agent", "Task"):
        return False, "Sub-agents are not permitted in this experiment."

    if tool_name in ("Write", "Edit", "MultiEdit"):
        t = ti.get("file_path") or ti.get("path")
        try:
            tr = Path(t).resolve()
        except Exception:
            return False, f"{tool_name}: bad path {t!r}"
        if tr != wp:
            return False, f"{tool_name} is scoped to {wp.name} only. Refused: {t}"
        return True, ""

    if tool_name == "Bash":
        cmd = str(ti.get("command", "")).strip()
        if cmd in BUILD_CMDS:
            return True, ""
        return False, f"Bash is restricted to exactly `bash {BUILD_SCRIPT}`."

    if tool_name in ("Read", "Glob", "Grep"):
        # Glob/Grep may carry only a `pattern`; with no path they range over the
        # whole sandbox, so fall back to the sandbox root — which `denied()` then
        # refuses whenever any hidden file lives under it.
        raw = ti.get("file_path") or ti.get("path") or ti.get("pattern")
        if raw and not str(raw).startswith("/"):
            cand = sb / str(raw).split("*", 1)[0].split("?", 1)[0]
        else:
            cand = Path(raw) if raw else sb
        try:
            tr = cand.resolve()
        except Exception:
            tr = sb
        if deny and denied(tr):
            return False, "Refused: that path is hidden in this experimental condition."
        if strict_read_allow:
            if tr in allow or _is_scaffolding(tr, sb):
                return True, ""
            return False, ("Refused: in this experimental condition you may read only "
                           "the file(s) named in your task.")
        if str(tr).startswith(str(sb)) or tr in allow:
            return True, ""
        return False, f"{tool_name} is scoped to the sandbox + provided inputs. Refused: {raw}"

    return True, ""


def make_pretooluse_hook(*, sandbox: Path, writable_abs: Path, read_allow, deny_read=(),
                         strict_read_allow=False):
    """PreToolUse hook enforcing `decide_tool`. Unlike can_use_tool this fires for
    EVERY tool call regardless of allowed_tools / permission_mode (SDK docs:
    "To observe or gate every tool call regardless of permission rules, use a
    PreToolUse hook"), including calls made from inside a sub-agent."""
    async def hook(inp, tool_use_id, ctx):
        ok, reason = decide_tool(inp.get("tool_name"), inp.get("tool_input"),
                                 sandbox=sandbox, writable_abs=writable_abs,
                                 read_allow=read_allow, deny_read=deny_read,
                                 strict_read_allow=strict_read_allow)
        if ok:
            return {}
        return {"hookSpecificOutput": {"hookEventName": "PreToolUse",
                                       "permissionDecision": "deny",
                                       "permissionDecisionReason": reason}}
    return hook


def make_permission_callback(*, sandbox: Path, writable_abs: Path, sdk,
                             read_allow: list[Path], deny_read=(),
                             strict_read_allow=False):
    """can_use_tool handler. Delegates to `decide_tool` so it can never disagree
    with the PreToolUse hook. NOTE this is only a SECOND line of defence: the SDK
    invokes it solely for calls that would otherwise prompt, so with
    `allowed_tools` + permission_mode="acceptEdits" it is almost never reached.
    The hook is what actually enforces the scope."""
    async def cb(tool_name, tool_input, _ctx=None):
        ok, reason = decide_tool(tool_name, tool_input, sandbox=sandbox,
                                 writable_abs=writable_abs, read_allow=read_allow,
                                 deny_read=deny_read, strict_read_allow=strict_read_allow)
        return sdk.PermissionResultAllow() if ok else sdk.PermissionResultDeny(message=reason)
    return cb


def compile_stats_from_messages(serialized) -> dict:
    """Count build.sh invocations and their COMPILE_OK/COMPILE_FAIL outcomes.

    The build-project / compile-check skill is the agent's ONLY feedback channel, so
    how many times it had to run it — and whether the FIRST run already passed — is a
    direct measure of how much repair the given test forced.

    Reconstructed from the session messages: a Bash ToolUseBlock whose command is
    build.sh, paired by tool_use_id with its ToolResultBlock. Block type names are
    matched loosely ("ToolUseBlock" / "tool_use") so a change in the SDK's
    serialisation does not silently zero this metric.
    """
    def btype(b):
        return str(b.get("type", "")).lower().replace("block", "").replace("_", "")

    pending, order, result = {}, [], {}
    for msg in serialized:
        content = msg.get("content")
        if not isinstance(content, list):
            continue
        for b in content:
            if not isinstance(b, dict):
                continue
            t = btype(b)
            if t == "tooluse" and b.get("name") == "Bash":
                cmd = str((b.get("input") or {}).get("command", "")).strip()
                if cmd in BUILD_CMDS:
                    pending[b.get("id")] = True
                    order.append(b.get("id"))
            elif t == "toolresult" and b.get("tool_use_id") in pending:
                body = b.get("content")
                if isinstance(body, list):
                    body = " ".join(str(x.get("text", "")) if isinstance(x, dict) else str(x) for x in body)
                body = str(body or "")
                result[b.get("tool_use_id")] = ("OK" if "COMPILE_OK" in body
                                                else "FAIL" if "COMPILE_FAIL" in body else "?")
    outcomes = [result.get(i, "?") for i in order]
    return {"compile_runs": len(order),
            "compile_fails": sum(1 for o in outcomes if o == "FAIL"),
            "first_compile_ok": (outcomes[0] == "OK") if outcomes else None,
            "compile_outcomes": outcomes}


# javac ("X.java:12: error: cannot find symbol") and maven ("X.java:[12,5] cannot find
# symbol") diagnostics → coarse buckets. cannot_find_symbol (a method/field that does not
# exist, e.g. a guessed result.isOk()) and method_or_ctor_mismatch mean the agent misread
# the code it was calling; the assertion-library bucket (assertThat without AssertJ, a
# non-existent assertGreater) and missing_type (an unimported List) are not about the
# CUT; syntax means it wrote malformed Java.
_ASSERTION_SYMBOL = re.compile(r"symbol:\s+\w+\s+(assert|verify|fail\b|Assertions|Mockito|assumeT)")
_TYPE_SYMBOL = re.compile(r"symbol:\s+class\s")
_COMPILE_ERROR_KINDS = (          # matched against the diagnostic's header line
    ("cannot_find_symbol", re.compile(r"cannot find symbol|does not exist")),
    ("method_or_ctor_mismatch", re.compile(r"cannot be applied|no suitable (method|constructor)"
                                           r"|constructor .* in class .* cannot")),
    ("incompatible_types", re.compile(r"incompatible types|cannot be converted|bad operand"
                                      r"|cannot be dereferenced")),
    ("unreported_exception", re.compile(r"unreported exception")),
    ("syntax", re.compile(r"expected|illegal start|not a statement|unclosed|reached end of file"
                          r"|orphaned|class, interface")),
)
# one diagnostic = its header line plus the indented "symbol:/location:" lines under it
_COMPILE_DIAG = re.compile(r"\.java:(?:\d+: error:|\[\d+,\d+\])\s*(.+?)(?=\S+\.java:(?:\d+: error:|\[\d+,\d+\])|\Z)", re.S)
_THINK_DELTA = re.compile(r"estimated_tokens_delta'?\"?\s*:\s*(\d+)")
_EDIT_TOOLS = ("Edit", "Write", "MultiEdit")


def session_effort_metrics(serialized) -> dict:
    """Effort metrics reconstructed from the ordered session messages.

    - api_calls: model round-trips. Blocks of one API response are streamed as
      separate AssistantMessages that repeat the same usage, so a change of the
      usage triple marks a new call. (num_turns counts tool uses instead: one
      response with 20 Edits is ~20 turns but 1 API call.)
    - reasoning_tokens_estimated*: sum of the CLI's streamed thinking_tokens
      deltas. The session TOTAL is exact in usage.output_tokens_details on
      SDK >= 0.2.136 (see AWR.extract_thinking_usage); the estimate is kept
      because it is the only way to split reasoning by phase, and it matched
      the exact count within 1% when checked.
    - repair_*: from the first COMPILE_FAIL result up to and including the first
      successful compile; None when the first compile passed or never recovered.
    - refused_access_attempts: tool calls the permission hook refused (reading
      outside the allowed files, non-build Bash). other_tool_errors: any other
      failed tool call, e.g. an Edit whose old_string was not found.
    """
    def btype(b):
        return str(b.get("type", "")).lower().replace("block", "").replace("_", "")

    by_name, tool_kind, outcome = {}, {}, {}
    api_calls = tool_calls = before_edit = edits = refused = other_err = 0
    think = think_before = think_repair = 0
    seen_edit, prev_usage = False, None
    compiles = []                          # tool_use ids in order
    error_kinds = {k: 0 for k in ("assertion_library_symbol", "missing_type",
                                  *(k for k, _ in _COMPILE_ERROR_KINDS), "other")}
    repair = {"active": False, "done": False, "tools": 0, "api": 0}
    result_subtype = None

    for msg in serialized:
        mtype = str(msg.get("type", ""))
        if msg.get("subtype") == "thinking_tokens":
            m = _THINK_DELTA.search(str(msg.get("repr", "")) + json.dumps(msg.get("data") or {}))
            d = int(m.group(1)) if m else 0
            think += d
            if not seen_edit: think_before += d
            if repair["active"]: think_repair += d
            continue
        if mtype.lower() in ("resultmessage", "result"):
            result_subtype = msg.get("subtype")
        if mtype == "AssistantMessage":
            u = msg.get("usage") or {}
            key = (u.get("input_tokens"), u.get("cache_read_input_tokens"), u.get("cache_creation_input_tokens"))
            if key != prev_usage:
                api_calls += 1
                if repair["active"]: repair["api"] += 1
            prev_usage = key
        content = msg.get("content")
        if not isinstance(content, list):
            continue
        for b in content:
            if not isinstance(b, dict):
                continue
            t = btype(b)
            if t == "tooluse":
                name = b.get("name", "?")
                tool_calls += 1
                by_name[name] = by_name.get(name, 0) + 1
                if repair["active"]: repair["tools"] += 1
                cmd = str((b.get("input") or {}).get("command", "")).strip()
                if name == "Bash" and cmd in BUILD_CMDS:
                    tool_kind[b.get("id")] = "compile"; compiles.append(b.get("id"))
                elif name in _EDIT_TOOLS:
                    tool_kind[b.get("id")] = "edit"; edits += 1; seen_edit = True
                if not seen_edit: before_edit += 1
            elif t == "toolresult":
                tid = b.get("tool_use_id")
                body = b.get("content")
                if isinstance(body, list):
                    body = " ".join(str(x.get("text", "")) if isinstance(x, dict) else str(x) for x in body)
                body = str(body or "")
                if tool_kind.get(tid) == "compile":
                    ok = "COMPILE_OK" in body
                    outcome[tid] = "OK" if ok else "FAIL" if "COMPILE_FAIL" in body else "?"
                    if outcome[tid] == "FAIL":
                        for diag in _COMPILE_DIAG.findall(body):
                            header = diag.split("\n", 1)[0]
                            if "cannot find symbol" in header and _ASSERTION_SYMBOL.search(diag):
                                kind = "assertion_library_symbol"
                            elif "cannot find symbol" in header and _TYPE_SYMBOL.search(diag):
                                kind = "missing_type"
                            else:
                                kind = next((k for k, rx in _COMPILE_ERROR_KINDS if rx.search(header)), "other")
                            error_kinds[kind] += 1
                        if not repair["active"] and not repair["done"] and tid == compiles[0]:
                            repair["active"] = True
                    elif ok and repair["active"]:
                        repair["active"], repair["done"] = False, True
                elif b.get("is_error"):
                    if body.startswith("Refused") or "restricted" in body or "not allowed" in body:
                        refused += 1
                    else:
                        other_err += 1

    outcomes = [outcome.get(i, "?") for i in compiles]
    first_ok_at = next((k for k, o in enumerate(outcomes, 1) if o == "OK"), None)
    first_failed = bool(outcomes) and outcomes[0] == "FAIL"
    recovered = first_failed and first_ok_at is not None
    return {
        "api_calls": api_calls,
        "tool_calls": tool_calls, "tool_calls_by_name": by_name,
        "tool_calls_before_first_edit": before_edit, "edit_calls": edits,
        "reasoning_tokens_estimated": think,
        "reasoning_tokens_estimated_before_first_edit": think_before,
        "reasoning_tokens_estimated_repair": think_repair if recovered else None,
        "compile_outcomes": outcomes,
        "reached_compile_ok": first_ok_at is not None,
        "compile_attempts_to_ok": first_ok_at,
        "repair_tool_calls": repair["tools"] if recovered else None,
        "repair_api_calls": repair["api"] if recovered else None,
        "compile_error_types": error_kinds,
        "refused_access_attempts": refused, "other_tool_errors": other_err,
        "result_subtype": result_subtype,
        "hit_max_turns": result_subtype == "error_max_turns",
    }


# ── flat effort columns for the results files ─────────────────────────────

def effort_fields(proc, max_turns, rebuilt_status):
    """Flat effort columns for the results file (the nested originals stay in
    state.json). Reasoning tokens are the provider's exact count when the SDK
    reports it; the per-phase splits can only come from the streamed estimate."""
    tok = proc.get("tokens") or {}
    think = proc.get("thinking_tokens") or {}
    eff = proc.get("effort") or {}
    rt = proc.get("sdk_runtime") or {}
    out_tokens = think.get("total_output_tokens_including_thinking")
    if out_tokens is None:
        out_tokens = (tok.get("usage") or {}).get("output_tokens")
    exact = think.get("provider_reported_thinking_tokens")
    api_ms = tok.get("duration_api_ms")
    return {
        "max_turns": max_turns,
        "hit_max_turns": eff.get("hit_max_turns"), "result_subtype": eff.get("result_subtype"),
        # did the filled test RUN (MEASURED) — pass/fail comes from _surefire_counts
        "filled_test_ran": {"MEASURED": True, "TEST_RUN_FAIL": False}.get(rebuilt_status),
        "reasoning_tokens": exact,
        "reasoning_tokens_source": "provider_exact" if exact is not None else "sdk_estimate",
        "reasoning_tokens_estimated": eff.get("reasoning_tokens_estimated"),
        "reasoning_tokens_before_first_edit_est": eff.get("reasoning_tokens_estimated_before_first_edit"),
        "reasoning_tokens_repair_est": eff.get("reasoning_tokens_estimated_repair"),
        "output_tokens": out_tokens,
        "visible_output_tokens": (out_tokens - exact) if (out_tokens is not None and exact is not None) else None,
        "num_turns": tok.get("num_turns"), "api_calls": eff.get("api_calls"),
        "tool_calls": eff.get("tool_calls"), "tool_calls_by_name": eff.get("tool_calls_by_name"),
        "tool_calls_before_first_edit": eff.get("tool_calls_before_first_edit"),
        "edit_calls": eff.get("edit_calls"),
        "api_time_s": round(api_ms / 1000, 1) if api_ms is not None else None,
        "cost_usd": tok.get("total_cost_usd"),
        "compile_outcomes": eff.get("compile_outcomes"),
        "reached_compile_ok": eff.get("reached_compile_ok"),
        "compile_attempts_to_ok": eff.get("compile_attempts_to_ok"),
        "repair_tool_calls": eff.get("repair_tool_calls"), "repair_api_calls": eff.get("repair_api_calls"),
        "compile_error_types": eff.get("compile_error_types"),
        "refused_access_attempts": eff.get("refused_access_attempts"),
        "other_tool_errors": eff.get("other_tool_errors"),
        "sdk_version": rt.get("python_sdk_version"), "claude_code_version": rt.get("bundled_claude_code_version"),
    }


# ── quota exhaustion is NOT an exception ───────────────────────────────────
# When the subscription's session limit is reached the SDK still returns a
# ResultMessage with subtype="success"; the refusal text sits in `result` and
# only `is_error` is set. Nothing raises, so `sdk_error` stays None and the run
# is recorded as a normal failure — an agent that "chose" to write nothing.
# Left undetected this silently fills a results file with fabricated zeros: a
# whole batch reads as a 63% compile-failure rate that is really a billing event.
_LIMIT_MARKERS = ("session limit", "usage limit", "rate limit",
                  "exceeded your", "quota")


def detect_session_limit(serialized) -> str | None:
    """Return the limit message if this session was cut short by quota."""
    for msg in serialized:
        if str(msg.get("type", "")).lower() not in ("resultmessage", "result"):
            continue
        text = str(msg.get("result") or "")
        low = text.lower()
        if msg.get("is_error") and any(m in low for m in _LIMIT_MARKERS):
            return text[:200]
    return None


# ── one Claude Agent SDK session ───────────────────────────────────────────
def run_claude_session(*, cfg, sandbox: Path, writable_abs: Path, read_allow: list[Path],
                       system_append: str, user_message: str, model: str,
                       max_turns: int, out_dir: Path,
                       allowed_tools=None, deny_read=(), strict_read_allow=False) -> dict:
    """Run ONE Claude session (haiku etc.) in `sandbox`. Writes trace.txt /
    state.json / session.jsonl into out_dir. Returns process metrics.
    `allowed_tools` = the per-task tool vocabulary (defaults to the full set);
    `deny_read` = paths the agent must not read even inside the sandbox."""
    sdk = AWR._load_sdk()
    sdk_runtime = AWR.claude_sdk_runtime_info(sdk)
    agent_cfg = getattr(cfg, "downstream", None)
    agent_cfg = getattr(agent_cfg, "agent", None)
    thinking = None; effort = None
    tk = getattr(agent_cfg, "thinking", None)
    if tk is not None:
        thinking = {k: getattr(tk, k) for k in ("type", "budget_tokens", "display")
                    if getattr(tk, k, None) is not None}
    if getattr(agent_cfg, "effort", None) is not None:
        effort = agent_cfg.effort
    perm_cb = make_permission_callback(sandbox=sandbox, writable_abs=writable_abs,
                                       sdk=sdk, read_allow=read_allow, deny_read=deny_read,
                                       strict_read_allow=strict_read_allow)
    # PreToolUse hook = the REAL enforcement. can_use_tool alone silently failed:
    # the SDK skips it for anything `allowed_tools`/permission_mode already permits,
    # so hidden-CUT reads, out-of-scope writes and non-build.sh Bash all went
    # through. The hook fires for every tool call, sub-agent calls included.
    pre_hook = make_pretooluse_hook(sandbox=sandbox, writable_abs=writable_abs,
                                    read_allow=read_allow, deny_read=deny_read,
                                    strict_read_allow=strict_read_allow)
    kwargs = dict(
        allowed_tools=list(allowed_tools or ["Read", "Write", "Edit", "Bash", "Glob", "Grep"]),
        # Strip sub-agent spawning from the model's context entirely: a sub-agent
        # runs its own tool loop and burns turns/tokens on unrelated exploration.
        disallowed_tools=["Agent", "Task"],
        hooks={"PreToolUse": [sdk.HookMatcher(matcher=None, hooks=[pre_hook])]},
        permission_mode="acceptEdits", max_turns=max_turns, can_use_tool=perm_cb,
        cwd=str(sandbox), skills="all", setting_sources=["project"], model=model,
        system_prompt={"type": "preset", "preset": "claude_code", "append": system_append},
    )
    if thinking: kwargs["thinking"] = thinking
    if effort: kwargs["effort"] = effort
    auth = AWR.build_auth_attempts()[0]
    kwargs["env"] = auth.get("env", {})

    serialized: list[dict] = []
    t0 = time.time()
    async def _go():
        opts = sdk.ClaudeAgentOptions(**kwargs)
        async with sdk.ClaudeSDKClient(options=opts) as client:
            await client.query(user_message)
            async for msg in client.receive_response():
                serialized.append(AWR.serialize_message(msg))
    err = None
    try:
        asyncio.run(_go())
    except Exception as e:
        err = f"{type(e).__name__}: {str(e)[:200]}"
    wall = round(time.time() - t0, 1)

    out_dir.mkdir(parents=True, exist_ok=True)
    try:
        (out_dir / "trace.txt").write_text(AWR.render_agent_session_trace(serialized, {"model": model}), encoding="utf-8")
    except Exception:
        (out_dir / "trace.txt").write_text("(trace render failed)\n")
    (out_dir / "session.jsonl").write_text("\n".join(json.dumps(s) for s in serialized), encoding="utf-8")
    tokens = {}
    try: tokens = AWR.extract_token_usage(serialized)
    except Exception: pass
    try:
        thinking = AWR.extract_thinking_usage(serialized)
    except Exception:
        thinking = {
            "provider_breakdown_available": False,
            "provider_reported_thinking_tokens": None,
            "provider_field": "usage.output_tokens_details.thinking_tokens",
            "total_output_tokens_including_thinking": None,
            "non_thinking_output_tokens_approx": None,
            "visible_thinking_tokens_estimated": None,
            "visible_estimation_method": "extraction failed",
            "blocks": None, "characters": None, "utf8_bytes": None,
            "whitespace_words": None, "text": "",
        }
    thinking_text = thinking.pop("text", "")
    (out_dir / "thinking.txt").write_text(thinking_text, encoding="utf-8")
    steps = sum(1 for s in serialized if s.get("type") in ("AssistantMessage", "assistant"))
    cs = {}
    try: cs = compile_stats_from_messages(serialized)
    except Exception: pass
    effort = {}
    try: effort = session_effort_metrics(serialized)
    except Exception as e: effort = {"error": f"{type(e).__name__}: {e}"}
    limit_msg = detect_session_limit(serialized)
    if limit_msg and not err:
        err = f"SessionLimit: {limit_msg}"
    metrics = {"wall_clock_s": wall, "sdk_runtime": sdk_runtime, "tokens": tokens,
               "thinking_tokens": thinking, "steps": steps, "effort": effort,
               "max_turns": max_turns,
               "compile_runs": cs.get("compile_runs"), "compile_fails": cs.get("compile_fails"),
               "first_compile_ok": cs.get("first_compile_ok"),
               "sdk_error": err, "quota_exhausted": bool(limit_msg),
               "auth": auth.get("label"), "backend": "claude"}
    (out_dir / "state.json").write_text(json.dumps(metrics, indent=2), encoding="utf-8")
    return metrics
