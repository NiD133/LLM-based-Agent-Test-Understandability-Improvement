#!/usr/bin/env python3
"""Stability check — run the main study's improvement again, `runs` times.

    python3 stability_check/run_stability.py            # run (settings: stability_check/config.yaml)
    python3 stability_check/run_stability.py --status   # progress + quota used, runs nothing
    python3 stability_check/run_stability.py --dry-run  # show what would run, runs nothing

`runs: N` in config.yaml = do N runs now. Runs are numbered run1, run2, … in
<output_dir>/runs/. An unfinished run (interrupted, quota limit) is completed
first and counts as one of the N. Nothing is ever done twice: finished sessions
are skipped, measured ones are not re-measured.

One run, for every case in `data` × every model:
  1. agents   scripts/main.py Step 4 — one process for the Claude models and
              one for the Codex models, in parallel. Spends quota; a usage
              report is printed afterwards.
  2. measure  scripts/main.py Step 5 — compile + JaCoCo + PIT + exact match.

The agents are NOT reimplemented here: scripts/agent_with_repair.py and
scripts/codex_agent_with_repair.py run exactly as in the main study. Four env
hooks in main.py redirect their output into <output_dir>/run<k>/<model>/…, the
same layout as data/improved/, so the main-study data is never touched and
analyze.py can read the runs directly.

Safety checks before running (abort unless --force):
  • inputs  — the hashes stored in the data file (original test, CUT source,
              scaffolding) must still match the files on disk;
  • freeze  — the agent procedure (prompt, skills, drivers, agent config,
              SDK/CLI versions) must be the one recorded on the first run.
Sessions ruined by a quota limit are moved to _quarantine/ and regenerated.

This file also contains the helpers that used to live in stability_common.py
(config + paths, the sample file, the main.py inputs and env hooks, session
state, quota usage, input and procedure fingerprints). The analysis is a
separate step: python3 stability_check/analyse_stability_results.py
"""
import argparse
import hashlib
import json
import os
import re
import shutil
import subprocess
import sys
import threading
import time
import yaml
from collections import defaultdict
from datetime import datetime
from pathlib import Path

# ═══════════════════════════ helpers (formerly stability_common.py) ═══════════════════════════
PROJECT_ROOT = Path(__file__).resolve().parent.parent
STAB_DIR = Path(__file__).resolve().parent
STAB_CONFIG = STAB_DIR / "config.yaml"
AGENT_DIR = PROJECT_ROOT / "agent_improvement"      # the main pipeline (scripts/, config.yaml, data/)
MAIN_CONFIG = AGENT_DIR / "config.yaml"
DATA = AGENT_DIR / "data"
ORIGINAL_ROOT = DATA / "original"

SRC_TO_CATEGORY = {"manual": "manual_cases", "auto": "auto_cases"}

# The improvement prompt is fixed in code (scripts/llm_refactor.PROMPT_VERSION).
import sys as _sys
_sys.path.insert(0, str(PROJECT_ROOT / "agent_improvement" / "scripts"))
from llm_refactor import PROMPT_VERSION  # noqa: E402


# ── config + paths ────────────────────────────────────────────────────────

def resolve(p) -> Path:
    """`~` expands; relative paths resolve against the project root."""
    q = Path(str(p)).expanduser()
    return q if q.is_absolute() else (PROJECT_ROOT / q)


def rel(p) -> str:
    try:
        return str(Path(p).relative_to(PROJECT_ROOT))
    except ValueError:
        return str(p)


def load_stability_config(path: Path | None = None) -> dict:
    with open(path or STAB_CONFIG) as f:
        scfg = yaml.safe_load(f)
    for key in ("data", "runs", "models", "output_dir"):
        if key not in scfg:
            raise SystemExit(f"stability_check/config.yaml: missing `{key}`")
    if not isinstance(scfg["runs"], int) or scfg["runs"] < 1:
        raise SystemExit("stability_check/config.yaml: `runs` must be a whole number ≥ 1")
    return scfg


# Everything the experiment writes lives under output_dir.
_LAYOUT = {
    "runs": ".",                 # run<k>/<model>/<project>/<src>/testcases/<Suite>/<case>/
    "inputs": "_inputs",         # generated subjects/manifest/allowlist/config per subprocess
    "usage": "usage",            # usage_log.csv + per-run JSON
    "quarantine": "_quarantine",  # sessions ruined by a quota limit, moved aside
    "report": "report",
    "freeze": "freeze.json",     # procedure fingerprint recorded on the first run
}


def path(scfg: dict, key: str) -> Path:
    return resolve(scfg["output_dir"]) / _LAYOUT[key]


def run_dir(scfg: dict, k: int) -> Path:
    return path(scfg, "runs") / f"run{k}"


def existing_runs(scfg: dict) -> list[int]:
    root = path(scfg, "runs")
    if not root.exists():
        return []
    return sorted(int(m.group(1)) for d in root.iterdir()
                  if d.is_dir() and (m := re.fullmatch(r"run(\d+)", d.name)))


# ── data ──────────────────────────────────────────────────────────────────

def load_targets(scfg: dict) -> list[dict]:
    p = resolve(scfg["data"])
    if not p.exists():
        raise SystemExit(f"data file not found: {rel(p)}")
    blob = json.loads(p.read_text(encoding="utf-8"))
    rows = blob["targets"] if isinstance(blob, dict) else blob
    if not rows:
        raise SystemExit(f"{rel(p)} contains no test cases")
    return rows


def data_name(scfg: dict) -> str:
    return resolve(scfg["data"]).stem


# ── models ────────────────────────────────────────────────────────────────

def _main_model_entries() -> dict[str, dict]:
    """{model id: its entry in the main config.yaml (id + output_dir)}, for both backends."""
    with open(MAIN_CONFIG) as f:
        cfg = yaml.safe_load(f)
    out = {}
    for section, backend in (("agent", "claude"), ("codex_agent", "codex")):
        for e in ((cfg.get(section) or {}).get("models") or []):
            out[e["id"]] = {**e, "backend": backend}
    return out


def model_label(model: str, backend: str, prompt: str = "") -> str:
    """The pipeline's output-folder name for a model: its `output_dir` in the
    main config.yaml (opus-4.8, sonnet-4.6, gpt-5.5)."""
    e = _main_model_entries().get(model)
    if not e or not e.get("output_dir"):
        raise SystemExit(f"{model}: not listed with an output_dir under agent:/codex_agent: in config.yaml")
    return e["output_dir"]


def model_entries(scfg: dict) -> list[dict]:
    """Every configured model as {model, backend, label, alias}; label = alias = output_dir."""
    out = []
    for backend in ("claude", "codex"):
        for m in (scfg["models"].get(backend) or []):
            lb = model_label(m, backend)
            out.append({"model": m, "backend": backend, "label": lb, "alias": lb})
    if not out:
        raise SystemExit("stability_check/config.yaml: `models` is empty")
    return out


# ── per-subprocess inputs for scripts/main.py ─────────────────────────────

def _group_by_repo(rows: list[dict]) -> list[dict]:
    """The per-repo {repo, class_paths, test_paths} shape of dataset.json. A
    class appears once even when several of its cases are selected."""
    order, by_repo = [], {}
    for r in rows:
        entry = by_repo.setdefault(r["repo"], {"repo": r["repo"],
                                               "class_paths": [], "test_paths": []})
        if r["repo"] not in order:
            order.append(r["repo"])
        if r["class_path"] not in entry["class_paths"]:
            entry["class_paths"].append(r["class_path"])
            entry["test_paths"].append(r["test_path"])
    return [by_repo[k] for k in order]


_STEP_FLAGS_OFF = [
    "clone_and_build", "generate_evosuite", "measure_baseline",
    "split_and_measure_cases", "generate_improvement_prompts",
    "generate_summary",
]
_NESTED_OFF = ["filter_out"]


def _force_all_false(node) -> None:
    if isinstance(node, dict):
        for k, v in node.items():
            if isinstance(v, bool):
                node[k] = False
            else:
                _force_all_false(v)


def patched_main_config(scfg: dict, entries: list[dict], phase: str) -> dict:
    """The main config.yaml, patched for one subprocess. Only these keys change
    — everything else (skills, max_turns, thinking/effort, permission mode,
    concurrency, JDKs, PIT) is inherited as-is:

      agent.models / codex_agent.models   (single `model:` keys dropped)
      codex_agent.reasoning_effort        (dropped: the main-study GPT-5.5 run
                                           predates that flag)
      pipeline_control.*                  (agents → Step 4 only,
                                           measure → Step 5 only)
    """
    with open(MAIN_CONFIG) as f:
        cfg = yaml.safe_load(f)

    claude = {e["model"] for e in entries if e["backend"] == "claude"}
    codex = {e["model"] for e in entries if e["backend"] == "codex"}

    # keep the main config's model entries (id + output_dir), restricted to the selection
    agent = cfg.setdefault("agent", {})
    agent.pop("model", None)
    agent["models"] = [e for e in (agent.get("models") or []) if e["id"] in claude]
    cx = cfg.setdefault("codex_agent", {})
    cx.pop("model", None)
    cx["models"] = [e for e in (cx.get("models") or []) if e["id"] in codex]
    cx.pop("reasoning_effort", None)

    pc = cfg.setdefault("pipeline_control", {})
    for k in _STEP_FLAGS_OFF:
        pc[k] = False
    for k in _NESTED_OFF:
        if isinstance(pc.get(k), dict):
            pc[k]["enabled"] = False
        else:
            pc[k] = False
    pc["run_claude_code"] = phase == "agents" and bool(claude)
    pc["run_codex"] = phase == "agents" and bool(codex)
    pc["compile_improvement_outputs"] = phase == "measure"

    ds_pc = (cfg.get("downstream") or {}).get("pipeline_control")
    if ds_pc is not None:
        _force_all_false(ds_pc)
    return cfg


def write_inputs(scfg: dict, rows: list[dict], *, run_index: int, phase: str,
                 entries: list[dict], tag: str) -> dict:
    """subjects / manifest / allowlist / config for ONE main.py subprocess,
    in _inputs/run<k>_<tag>/ so parallel subprocesses never share a file."""
    d = path(scfg, "inputs") / f"run{run_index}_{tag}"
    d.mkdir(parents=True, exist_ok=True)
    grouped = _group_by_repo(rows)

    subjects_p = d / "subjects.json"
    subjects_p.write_text(json.dumps(grouped, indent=2), encoding="utf-8")
    manifest_p = d / "manifest.json"
    manifest_p.write_text(json.dumps(
        {"preserve_manual_edits": True, "projects": grouped}, indent=2),
        encoding="utf-8")
    allowlist_p = d / "allowlist.json"
    allowlist_p.write_text(json.dumps({"targets": [
        [r["project_id"], r["test_category"], r["suite_class"], r["test_id"]]
        for r in rows]}, indent=2), encoding="utf-8")

    cfg = patched_main_config(scfg, entries, phase)
    config_p = d / "config.yaml"
    header = (
        "# GENERATED by stability_check/stability_common.py — do not edit.\n"
        f"# Source: {MAIN_CONFIG} (patched)   phase: {phase}   run: {run_index}\n"
        f"# generated: {datetime.now().isoformat(timespec='seconds')}   "
        f"models: {[e['model'] for e in entries]}   cases: {len(rows)}\n"
    )
    config_p.write_text(header + yaml.safe_dump(cfg, sort_keys=False,
                                                allow_unicode=True), encoding="utf-8")
    return {"subjects": subjects_p, "manifest": manifest_p,
            "allowlist": allowlist_p, "config": config_p}


def subprocess_env(scfg: dict, run_index: int, inputs: dict) -> dict:
    """The three main.py hooks (no-ops when unset; see agent_improvement/scripts/main.py)."""
    env = dict(os.environ)
    env["STABILITY_IMPROVED_ROOT"] = str(run_dir(scfg, run_index))
    env["STABILITY_MANIFEST"] = str(inputs["manifest"])
    env["STABILITY_TARGET_ALLOWLIST"] = str(inputs["allowlist"])
    return env


# ── session state ─────────────────────────────────────────────────────────

# Phrases a provider uses when a subscription / API quota is exhausted.
# Matched only against sessions that did NOT succeed, so ordinary test code
# that happens to mention "quota" can never trigger it.
LIMIT_SIGNALS = (
    "hit your session limit", "hit your usage limit", "hit your limit",
    "session limit", "usage limit", "rate limit", "rate_limit_exceeded",
    "insufficient_quota", "quota exceeded", "exceeded your current quota",
    "too many requests", "try again at", "limit · resets", "limit resets",
)


def _tail(p: Path, n: int = 6000) -> str:
    try:
        return p.read_text(encoding="utf-8", errors="replace")[-n:]
    except Exception:
        return ""


def classify_session(leaf: Path) -> dict:
    """One (run, model, case) cell.

    state:
      pending    no state.json yet
      retry      LLM_ERROR / <2 messages — the driver regenerates it by itself
      stuck      failed WITH a quota-limit signal and ≥2 messages — the
                 driver's resume check would skip it forever, so
                 run_stability.py moves it to _quarantine/ and regenerates it
      generated  the agent finished (any compile status)
    measured: Step 5 wrote a usable metrics.json for it.
    """
    sp = leaf / "state.json"
    out = {"state": "pending", "status": None, "limit_hit": False,
           "measured": False, "compile_status": None, "exact": None}
    if not sp.exists():
        return out
    try:
        st = json.loads(sp.read_text(encoding="utf-8"))
    except Exception:
        out["state"] = "retry"
        return out
    status = st.get("status")
    out["status"] = status
    n_msgs = len(st.get("messages") or [])

    if status != "COMPILE_SUCCESS":
        text = " ".join(str(st.get(k) or "") for k in
                        ("last_error", "last_code", "codex_last_message"))
        text += _tail(leaf / "codex_exec_stderr.txt")
        # Codex CLI reports a quota limit as an `error` / `turn.failed` event
        # on stdout, with stderr empty.
        text += _tail(leaf / "codex_exec_stdout.jsonl", 3000)
        for jf in leaf.glob("*.java"):
            text += _tail(jf, 2000)
        low = text.lower()
        out["limit_hit"] = any(sig in low for sig in LIMIT_SIGNALS)

    if status == "LLM_ERROR" or n_msgs < 2:
        out["state"] = "retry"
    elif out["limit_hit"]:
        out["state"] = "stuck"
    else:
        out["state"] = "generated"

    mp = leaf / "metrics.json"
    if mp.exists() and out["state"] == "generated":
        try:
            m = json.loads(mp.read_text(encoding="utf-8"))
            cs = m.get("compile_status")
            imp = m.get("improved") or {}
            out["compile_status"] = cs
            # A metrics.json left over from an earlier FAILED attempt (e.g.
            # LLM_ERROR on a quota limit) must not count once the session has
            # been regenerated: Step 5 always writes the state's own status.
            out["measured"] = cs == status and (
                cs != "COMPILE_SUCCESS" or imp.get("line_pct") is not None)
            if cs == "COMPILE_SUCCESS":
                out["exact"] = bool(((m.get("comparison") or {}).get("verdict") or {})
                                    .get("is_exact_match"))
        except Exception:
            pass
    return out


def scan_run(scfg: dict, k: int, rows: list[dict], entries: list[dict]) -> dict:
    """{label: {leaf_rel: classify_session(...)}} for one run."""
    root = run_dir(scfg, k)
    return {e["label"]: {r["leaf_rel"]: classify_session(root / e["label"] / r["leaf_rel"])
                         for r in rows}
            for e in entries}


def run_complete(scan: dict) -> bool:
    return all(c["state"] == "generated" and c["measured"]
               for cells in scan.values() for c in cells.values())


# ── usage (how much quota a session consumed) ─────────────────────────────

USAGE_KEYS = ("input_tokens", "cache_creation_input_tokens", "cache_read_input_tokens",
              "output_tokens", "reasoning_output_tokens", "cost_usd",
              "duration_s", "num_turns")


def session_usage(leaf: Path, backend: str) -> dict | None:
    """Tokens (+ API-equivalent cost for Claude) of one finished session."""
    u = {k: 0 for k in USAGE_KEYS}
    if backend == "claude":
        try:
            st = json.loads((leaf / "state.json").read_text(encoding="utf-8"))
        except Exception:
            return None
        ro = st.get("runtime_observability") or {}
        tu = ro.get("token_usage") or {}
        if not tu:
            return None
        usage = tu.get("usage") or {}
        for k in ("input_tokens", "cache_creation_input_tokens",
                  "cache_read_input_tokens", "output_tokens"):
            u[k] = int(usage.get(k) or 0)
        th = ro.get("thinking_tokens") or {}
        u["reasoning_output_tokens"] = int(th.get("provider_reported_thinking_tokens") or 0)
        u["cost_usd"] = float(tu.get("total_cost_usd") or 0.0)
        u["duration_s"] = round((tu.get("duration_ms") or 0) / 1000, 1)
        u["num_turns"] = int(tu.get("num_turns") or 0)
        u["auth"] = ro.get("auth_used")
        # Subscription window as the SDK reported it (utilization is often
        # null until the window nears its cap; status escalates allowed →
        # allowed_warning → rejected).
        u["rate_limit"] = [
            {"type": e.get("rate_limit_type"), "status": e.get("status"),
             "utilization": e.get("utilization"), "resets_at": e.get("resets_at")}
            for e in (ro.get("rate_limit_events") or [])]
        return u
    # Codex CLI: sum the `turn.completed` usage events of the JSONL stream.
    p = leaf / "codex_exec_stdout.jsonl"
    if not p.exists():
        return None
    seen = False
    for line in p.read_text(encoding="utf-8", errors="replace").splitlines():
        if '"turn.completed"' not in line:
            continue
        try:
            ev = json.loads(line)
        except Exception:
            continue
        us = ev.get("usage") or {}
        seen = True
        u["num_turns"] += 1
        # OpenAI's input_tokens INCLUDES the cached part; split it so the
        # columns mean the same as Claude's (fresh input vs cache read).
        cached = int(us.get("cached_input_tokens") or 0)
        u["input_tokens"] += int(us.get("input_tokens") or 0) - cached
        u["cache_read_input_tokens"] += cached
        u["output_tokens"] += int(us.get("output_tokens") or 0)
        u["reasoning_output_tokens"] += int(us.get("reasoning_output_tokens") or 0)
    if not seen:
        return None
    u["cost_usd"] = None              # Codex CLI reports tokens only
    return u


# ── input + procedure fingerprints ────────────────────────────────────────

def sha1_file(p: Path) -> str | None:
    try:
        return hashlib.sha1(p.read_bytes()).hexdigest()
    except Exception:
        return None


def input_fingerprint(row: dict) -> dict:
    """Hashes of everything one case's agent reads: original split, CUT
    source, EvoSuite scaffolding (auto only)."""
    fp = {"original_sha1": sha1_file(PROJECT_ROOT / row["original_split"]),
          "cut_sha1": sha1_file(PROJECT_ROOT / "local_workplace" / row["project_id"]
                                / row["class_path"])}
    if row.get("scaffolding"):
        fp["scaffolding_sha1"] = sha1_file(PROJECT_ROOT / row["scaffolding"])
    return fp


def verify_inputs(rows: list[dict]) -> list[str]:
    bad = []
    for r in rows:
        for k, v in input_fingerprint(r).items():
            if r.get(k) and v != r[k]:
                bad.append(f"{r['leaf_rel']}: {k} changed "
                           f"({r[k][:10]} → {(v or 'MISSING')[:10]})")
    return bad


def _hash_tree(paths: list[Path]) -> dict:
    out = {}
    for p in paths:
        if p.is_dir():
            for f in sorted(x for x in p.rglob("*") if x.is_file()
                            and ".DS_Store" not in x.name):
                out[rel(f)] = sha1_file(f)
        else:
            out[rel(p)] = sha1_file(p) if p.exists() else None
    return out


def codex_cli_version() -> str | None:
    cli = os.environ.get("CODEX_CLI_PATH")
    if not cli:
        envf = PROJECT_ROOT / ".env"   # repository root
        if envf.exists():
            for line in envf.read_text(encoding="utf-8").splitlines():
                if line.strip().startswith("CODEX_CLI_PATH="):
                    cli = line.split("=", 1)[1].strip().strip('"').strip("'")
    for cand in [cli, "/Applications/Codex.app/Contents/Resources/codex"]:
        if cand and Path(cand).expanduser().is_file():
            try:
                r = subprocess.run([str(Path(cand).expanduser()), "--version"],
                                   capture_output=True, text=True, timeout=20)
                return (r.stdout or r.stderr).strip()
            except Exception:
                return None
    return None


def package_version(name: str) -> str | None:
    try:
        from importlib.metadata import version
        return version(name)
    except Exception:
        return None


def _config_hash(section: dict) -> str:
    # Which models run is the experiment's choice, not the procedure.
    s = {k: v for k, v in (section or {}).items() if k not in ("model", "models")}
    return hashlib.sha1(json.dumps(s, sort_keys=True, default=str).encode()).hexdigest()


def procedure_fingerprint(scfg: dict) -> dict:
    """What must stay identical across runs.

    agent     — a change ABORTS the next run: prompt + system prompt, the
                skills both backends discover, the agent drivers, the patched
                agent/codex config, SDK + CLI versions.
    pipeline  — a change only WARNS: main.py and the measurement runners."""
    cfg = patched_main_config(scfg, model_entries(scfg), "agents")
    agent = _hash_tree([
        AGENT_DIR / "prompts" / f"{PROMPT_VERSION}.txt",
        AGENT_DIR / "prompts" / "improve_system_prompt.txt",
        AGENT_DIR / ".claude" / "skills",
        AGENT_DIR / ".agents" / "skills",
        AGENT_DIR / "scripts" / "agent_with_repair.py",
        AGENT_DIR / "scripts" / "codex_agent_with_repair.py",
        AGENT_DIR / "scripts" / "llm_refactor.py",
    ])
    agent["config:agent"] = _config_hash(cfg.get("agent"))
    agent["config:codex_agent"] = _config_hash(cfg.get("codex_agent"))
    agent["version:claude-agent-sdk"] = package_version("claude-agent-sdk")
    agent["version:codex-cli"] = codex_cli_version()
    pipeline = _hash_tree([
        AGENT_DIR / "scripts" / "main.py",
        AGENT_DIR / "scripts" / "coverage_runner.py",
        AGENT_DIR / "scripts" / "mutation_measurement.py",
    ])
    pipeline["config:mutation_testing"] = _config_hash(cfg.get("mutation_testing"))
    return {"agent": agent, "pipeline": pipeline}


def compare_fingerprints(old: dict, new: dict) -> dict:
    diff = {}
    for part in ("agent", "pipeline"):
        a, b = old.get(part) or {}, new.get(part) or {}
        diff[part] = sorted(k for k in set(a) | set(b) if a.get(k) != b.get(k))
    return diff


MAIN_PY = AGENT_DIR / "scripts" / "main.py"


# ═══════════════════════════ the run driver ═══════════════════════════
# ── formatting ────────────────────────────────────────────────────────────

def human(n) -> str:
    if n is None:
        return "—"
    n = float(n)
    for unit, div in (("B", 1e9), ("M", 1e6), ("k", 1e3)):
        if abs(n) >= div:
            return f"{n / div:.1f}{unit}"
    return f"{n:.0f}"


def hms(seconds: float) -> str:
    h, rem = divmod(int(seconds), 3600)
    m, s = divmod(rem, 60)
    return f"{h}h{m:02d}m" if h else f"{m}m{s:02d}s"


def table(header: list[str], rows: list[list]) -> str:
    cells = [header] + [[str(c) for c in r] for r in rows]
    w = [max(len(r[i]) for r in cells) for i in range(len(header))]
    line = lambda r: "  ".join(c.rjust(w[i]) if i else c.ljust(w[i])  # noqa: E731
                               for i, c in enumerate(r))
    return "\n".join([line(header), "  ".join("─" * x for x in w)]
                     + [line(r) for r in cells[1:]])


# ── checks ────────────────────────────────────────────────────────────────

def preflight(entries: list[dict]) -> None:
    backends = {e["backend"] for e in entries}
    if "claude" in backends:
        src = (AGENT_DIR / "scripts" / "agent_with_repair.py").read_text(encoding="utf-8")
        m = re.search(r'REQUIRED_CLAUDE_AGENT_SDK_VERSION\s*=\s*"([^"]+)"', src)
        have = package_version("claude-agent-sdk")
        if m and have != m.group(1):
            raise SystemExit(
                f"claude-agent-sdk {have} in {sys.executable}, but the driver requires "
                f"{m.group(1)} — every Claude session would fail. Run with "
                "`python3 stability_check/run_stability.py`.")
    if "codex" in backends and not codex_cli_version():
        raise SystemExit("Codex CLI not found (CODEX_CLI_PATH in .env, or "
                         "/Applications/Codex.app) — Codex sessions would fail.")


def check_inputs(rows: list[dict], force: bool) -> None:
    bad = verify_inputs(rows)
    if not bad:
        return
    msg = "inputs changed since the sample was drawn:\n  " + "\n  ".join(bad[:20])
    if not force:
        raise SystemExit(msg + "\nRuns would no longer see the same test. "
                               "Restore the files, or pass --force.")
    print("[inputs] WARNING — " + msg + "\n[inputs] continuing (--force)")


def check_freeze(scfg: dict, force: bool, dry_run: bool) -> None:
    fp_path = path(scfg, "freeze")
    now = procedure_fingerprint(scfg)
    if not fp_path.exists():
        if not dry_run:
            fp_path.parent.mkdir(parents=True, exist_ok=True)
            fp_path.write_text(json.dumps({
                "created": datetime.now().isoformat(timespec="seconds"),
                "fingerprint": now, "history": []}, indent=2), encoding="utf-8")
            print(f"[freeze] agent procedure recorded → {rel(fp_path)}")
        return
    blob = json.loads(fp_path.read_text(encoding="utf-8"))
    diff = compare_fingerprints(blob["fingerprint"], now)
    if diff["pipeline"]:
        print("[freeze] note — pipeline code changed since the first run "
              "(measurement only):\n    " + "\n    ".join(diff["pipeline"]))
    if not diff["agent"]:
        return
    msg = ("[freeze] the AGENT PROCEDURE changed since the first run:\n    "
           + "\n    ".join(diff["agent"]))
    if not force:
        raise SystemExit(msg + "\nNew runs would not be comparable with the earlier "
                               "ones. Revert the change, or pass --force.")
    print(msg + "\n[freeze] continuing (--force) — change recorded in freeze.json")
    if not dry_run:
        blob["history"].append({"replaced": datetime.now().isoformat(timespec="seconds"),
                                "changed": diff["agent"], "fingerprint": blob["fingerprint"]})
        blob["fingerprint"] = now
        fp_path.write_text(json.dumps(blob, indent=2), encoding="utf-8")


def requeue_stuck(scfg: dict, rows: list[dict], entries: list[dict], dry_run: bool) -> None:
    """Sessions whose output is a quota-limit message: the driver would skip
    them forever, so move them aside and let this invocation regenerate them."""
    q = path(scfg, "quarantine") / datetime.now().strftime("%Y%m%d_%H%M%S")
    n = 0
    for k in existing_runs(scfg):
        for label, cells in scan_run(scfg, k, rows, entries).items():
            for leaf_rel, c in cells.items():
                if c["state"] != "stuck":
                    continue
                n += 1
                if not dry_run:
                    dst = q / f"run{k}" / label / leaf_rel
                    dst.parent.mkdir(parents=True, exist_ok=True)
                    shutil.move(str(run_dir(scfg, k) / label / leaf_rel), str(dst))
    if n:
        print(f"[quota] {n} session(s) had failed on a quota limit — "
              f"{'would be' if dry_run else 'moved to ' + rel(q) + ','} regenerated")


# ── status + usage ────────────────────────────────────────────────────────

def status_table(scfg: dict, rows: list[dict], entries: list[dict], runs: list[int]) -> str:
    out = []
    for k in runs:
        scan = scan_run(scfg, k, rows, entries)
        line = [f"run{k}"]
        for e in entries:
            c = defaultdict(int)
            for x in scan[e["label"]].values():
                c[x["state"]] += 1
                c["measured"] += bool(x["measured"])
                c["exact"] += bool(x["exact"])
                c["retry_limit"] += x["state"] == "retry" and x["limit_hit"]
            txt = (f"generated {c['generated']}/{len(rows)} · measured {c['measured']} "
                   f"· exact {c['exact']}")
            if c["retry"]:
                txt += f" · failed {c['retry']}"
                if c["retry_limit"]:
                    txt += f" (quota limit {c['retry_limit']}, retried next time)"
            if c["stuck"]:
                txt += f" · quota-limit {c['stuck']}"
            line.append(txt)
        out.append(line)
    return table(["run"] + [e["alias"] for e in entries], out)


def collect_usage(scfg: dict, runs: list[int], rows: list[dict], entries: list[dict],
                  since: float | None = None) -> dict:
    """{label: totals} over sessions in scope; `since` = written after it."""
    out = {}
    for e in entries:
        agg = defaultdict(float)
        rl_last, sessions = None, []
        for k in runs:
            for r in rows:
                leaf = run_dir(scfg, k) / e["label"] / r["leaf_rel"]
                sp = leaf / "state.json"
                if not sp.exists() or (since and sp.stat().st_mtime < since):
                    continue
                cell = classify_session(leaf)
                agg["n"] += 1
                agg["failed"] += cell["state"] in ("retry", "stuck")
                u = session_usage(leaf, e["backend"])
                if u:
                    agg["with_usage"] += 1
                    for key in USAGE_KEYS:
                        if u.get(key) is not None:
                            agg[key] += u[key]
                    if u.get("rate_limit"):
                        rl_last = u["rate_limit"][-1]
                sessions.append({"run": k, "case": r["leaf_rel"], "state": cell["state"],
                                 "usage": u})
        out[e["label"]] = {**agg, "rate_limit_last": rl_last, "sessions": sessions}
    return out


def fresh_tokens(u: dict) -> float:
    return (u.get("input_tokens", 0) + u.get("cache_creation_input_tokens", 0)
            + u.get("output_tokens", 0))


def usage_report(usage: dict, entries: list[dict], n_cases: int) -> str:
    rows = []
    for e in entries:
        u = usage[e["label"]]
        wu = u.get("with_usage") or 0
        cost = u.get("cost_usd") if e["backend"] == "claude" else None
        per_tok = fresh_tokens(u) / wu if wu else None
        per_cost = cost / wu if cost is not None and wu else None
        rows.append([
            e["alias"], int(u.get("n", 0)), int(u.get("failed", 0)),
            human(u.get("input_tokens", 0) + u.get("cache_creation_input_tokens", 0)),
            human(u.get("cache_read_input_tokens", 0)), human(u.get("output_tokens", 0)),
            f"${cost:,.2f}" if cost is not None else "—",
            human(per_tok) if per_tok else "—",
            (f"{human(per_tok * n_cases)} tok" if per_tok else "—")
            + (f" · ${per_cost * n_cases:,.0f}" if per_cost else ""),
        ])
    txt = table(["model", "sessions", "failed", "input", "cache read", "output",
                 "cost (API price)", "tokens/session", f"≈ one run of {n_cases} cases"],
                rows)
    notes = []
    for e in entries:
        rl = usage[e["label"]].get("rate_limit_last")
        if rl:
            reset = (datetime.fromtimestamp(rl["resets_at"]).strftime("%a %H:%M")
                     if rl.get("resets_at") else "?")
            util = rl.get("utilization")
            notes.append(f"  {e['alias']}: {rl.get('type')} window {rl.get('status')}"
                         f"{f', utilization {util}' if util is not None else ''}, "
                         f"resets {reset}")
    if notes:
        txt += "\nClaude subscription window (last reported by the SDK):\n" + "\n".join(notes)
    txt += ("\n(tokens = fresh input + cache writes + output. Claude cost is the API "
            "price of those tokens, not your subscription %; Codex reports tokens only.)")
    return txt


def log_usage(scfg: dict, k: int, t0: float, usage: dict, entries: list[dict]) -> Path:
    d = path(scfg, "usage")
    d.mkdir(parents=True, exist_ok=True)
    stamp = datetime.fromtimestamp(t0).strftime("%Y%m%d_%H%M%S")
    p = d / f"{stamp}_run{k}.json"
    p.write_text(json.dumps({"run": k, "data": scfg["data"], "started": stamp,
                             "wall_seconds": int(time.time() - t0), "usage": usage},
                            indent=2, default=str), encoding="utf-8")
    csv = d / "usage_log.csv"
    new = not csv.exists()
    with open(csv, "a", encoding="utf-8") as f:
        if new:
            f.write("started,run,data,model,sessions,failed,input_tokens,cache_write_tokens,"
                    "cache_read_tokens,output_tokens,reasoning_tokens,cost_usd,wall_seconds\n")
        for e in entries:
            u = usage[e["label"]]
            f.write(",".join(str(x) for x in [
                stamp, k, data_name(scfg), e["alias"], int(u.get("n", 0)),
                int(u.get("failed", 0)), int(u.get("input_tokens", 0)),
                int(u.get("cache_creation_input_tokens", 0)),
                int(u.get("cache_read_input_tokens", 0)), int(u.get("output_tokens", 0)),
                int(u.get("reasoning_output_tokens", 0)),
                round(u["cost_usd"], 4) if u.get("cost_usd") else "",
                int(time.time() - t0)]) + "\n")
    return p


# ── subprocesses ──────────────────────────────────────────────────────────

def launch(scfg: dict, k: int, rows: list[dict], entries: list[dict], phase: str,
           tag: str, dry_run: bool):
    print(f"  [{tag}] {len(rows)} case(s) × {[e['alias'] for e in entries]}")
    if dry_run:
        return None
    inputs = write_inputs(scfg, rows, run_index=k, phase=phase, entries=entries, tag=tag)
    env = subprocess_env(scfg, k, inputs)
    cmd = [sys.executable, str(MAIN_PY), "--config", str(inputs["config"]),
           "--subjects", str(inputs["subjects"])]
    log_dir = run_dir(scfg, k) / "logs"
    log_dir.mkdir(parents=True, exist_ok=True)
    log_path = log_dir / f"{datetime.now():%Y%m%d_%H%M%S}_{tag}.log"
    proc = subprocess.Popen(cmd, cwd=str(PROJECT_ROOT), env=env, stdout=subprocess.PIPE,
                            stderr=subprocess.STDOUT, text=True, bufsize=1)

    def pump():
        with open(log_path, "w", encoding="utf-8") as log:
            for line in proc.stdout:
                log.write(line)
                log.flush()
                sys.stdout.write(f"[run{k}:{tag}] {line}")

    t = threading.Thread(target=pump, daemon=True)
    t.start()
    return proc, t


def wait_all(handles: list) -> list[int]:
    try:
        rcs = [h[0].wait() for h in handles]
        for h in handles:
            h[1].join()
        return rcs
    except KeyboardInterrupt:
        for h in handles:
            h[0].terminate()
        raise


def do_run(scfg: dict, k: int, rows: list[dict], entries: list[dict], dry_run: bool) -> bool:
    """One run. Returns False if sessions failed (then no further run starts)."""
    print("\n" + "═" * 78)
    print(f"  RUN {k}  ·  {len(rows)} cases × {[e['alias'] for e in entries]}  "
          f"→ {rel(run_dir(scfg, k))}")
    print("═" * 78)

    # 1. agents — Claude and Codex processes in parallel
    before = scan_run(scfg, k, rows, entries)
    t0 = time.time()
    handles = []
    for backend in ("claude", "codex"):
        group = [e for e in entries if e["backend"] == backend]
        todo = [r for r in rows if any(before[e["label"]][r["leaf_rel"]]["state"]
                                       != "generated" for e in group)]
        if group and todo:
            h = launch(scfg, k, todo, group, "agents", f"agents_{backend}", dry_run)
            if h:
                handles.append(h)
        elif group:
            print(f"  [agents_{backend}] already generated")
    if handles:
        rcs = wait_all(handles)
        if any(rcs):
            print(f"  [agents] a process exited with {rcs} — see "
                  f"{rel(run_dir(scfg, k) / 'logs')}")
        usage = collect_usage(scfg, [k], rows, entries, since=t0)
        print(f"\n── quota used by run{k} ({hms(time.time() - t0)}) ──")
        print(usage_report(usage, entries, len(rows)))
        print(f"usage log → {rel(log_usage(scfg, k, t0, usage, entries))}")

    # 2. measure — every generated, not yet measured session (all models)
    if dry_run:
        print("  [measure] after the agents: compile + coverage + mutation + exact match")
        return True
    scan = scan_run(scfg, k, rows, entries)
    todo = [r for r in rows if any(scan[e["label"]][r["leaf_rel"]]["state"] == "generated"
                                   and not scan[e["label"]][r["leaf_rel"]]["measured"]
                                   for e in entries)]
    if todo:
        rc = wait_all([launch(scfg, k, todo, entries, "measure", "measure", dry_run)])[0]
        if rc:
            print(f"  [measure] exited with {rc} — see {rel(run_dir(scfg, k) / 'logs')}")

    print(f"\n── run{k} ──")
    print(status_table(scfg, rows, entries, [k]))
    scan = scan_run(scfg, k, rows, entries)
    n_bad = sum(c["state"] != "generated" or not c["measured"]
                for cells in scan.values() for c in cells.values())
    if n_bad:
        print(f"\n⚠️  run{k} is not complete ({n_bad} session(s) failed or unmeasured — "
              "usually a quota limit). Run the script again later: it finishes run"
              f"{k} first.")
        return False
    return True


# ── main ──────────────────────────────────────────────────────────────────

def plan_runs(scfg: dict, rows: list[dict], entries: list[dict], n: int) -> list[int]:
    existing = existing_runs(scfg)
    plan = [k for k in existing
            if not run_complete(scan_run(scfg, k, rows, entries))][:n]
    nxt = max(existing, default=0) + 1
    while len(plan) < n:
        if nxt not in plan:
            plan.append(nxt)
        nxt += 1
    return plan


def main():
    ap = argparse.ArgumentParser(description=__doc__,
                                 formatter_class=argparse.RawDescriptionHelpFormatter)
    ap.add_argument("--status", action="store_true", help="show progress + quota used")
    ap.add_argument("--dry-run", action="store_true", help="show the plan, run nothing")
    ap.add_argument("--force", action="store_true",
                    help="run even if inputs or the agent procedure changed")
    ap.add_argument("--config", default=str(STAB_CONFIG), help=argparse.SUPPRESS)
    args = ap.parse_args()

    scfg = load_stability_config(Path(args.config))
    rows = load_targets(scfg)
    entries = model_entries(scfg)
    existing = existing_runs(scfg)

    if args.status:
        print(f"data {rel(resolve(scfg['data']))} · {len(rows)} cases · "
              f"output {scfg['output_dir']}\n")
        if not existing:
            print("no runs yet")
            return 0
        print(status_table(scfg, rows, entries, existing))
        print("\n── quota used so far (all runs, this data) ──")
        print(usage_report(collect_usage(scfg, existing, rows, entries), entries, len(rows)))
        return 0

    preflight(entries)
    check_inputs(rows, args.force)
    check_freeze(scfg, args.force, args.dry_run)
    requeue_stuck(scfg, rows, entries, args.dry_run)

    plan = plan_runs(scfg, rows, entries, scfg["runs"])
    resumed = [k for k in plan if k in existing]
    print(f"plan: {len(plan)} run(s) → {', '.join(f'run{k}' for k in plan)}"
          + (f"  (finishing unfinished {', '.join(f'run{k}' for k in resumed)})"
             if resumed else "")
          + f"  ·  {len(rows)} cases × {[e['alias'] for e in entries]}")
    for k in plan:
        if not do_run(scfg, k, rows, entries, args.dry_run):
            return 1
    if not args.dry_run:
        print("\ndone. progress: python3 stability_check/run_stability.py --status")
    return 0


if __name__ == "__main__":
    raise SystemExit(main())