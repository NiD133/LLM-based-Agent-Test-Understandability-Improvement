#!/usr/bin/env python3
"""v2 masking rules (2026-09-13).

1. ORACLE = framework assertion / verification statement only (javatools/UnitMap).
   Calls to local helpers — including helpers named assertXxx — are NOT oracles and
   are never deleted.
2. A test method is masked when, after removing its own oracles, its body keeps
   >= MIN_LOC code lines; otherwise its oracles stay.
3. A test case whose text is IDENTICAL in original and improved (whitespace
   ignored, comments included) is never masked in either arm: it carries no
   understandability difference.
4. Masking a test method also masks the oracles inside every helper it reaches
   (transitively); the call sites stay. Every method that loses at least one oracle
   — test or helper — gets `// please add oracles here` above its first annotation /
   declaration line.
"""
from __future__ import annotations
import re, subprocess, tempfile
from pathlib import Path

HERE = Path(__file__).resolve().parent
ROOT = HERE.parents[1]
# JDK 21 for the JavaParser helper: JDK_21_HOME in .env at the repository root
# (the same variable agent_improvement/config.yaml tools.java_homes["21"] reads).
import os as _os
from dotenv import load_dotenv as _load_dotenv
_load_dotenv(ROOT / ".env")
JAVA = str(Path(_os.environ["JDK_21_HOME"]) / "bin" / "java") if _os.environ.get("JDK_21_HOME") else "java"
CP = f"{ROOT / 'tools/javaparser/javaparser-core-3.27.0.jar'}:{HERE / 'javatools'}"
MARKER = "// please add oracles here"
_NOISE = {"{", "}", "});", ");", "};", "})", ")"}


def unitmap(src: str) -> dict:
    with tempfile.NamedTemporaryFile("w", suffix=".java", delete=False) as tf:
        tf.write(src); p = tf.name
    try:
        out = subprocess.run([JAVA, "-cp", CP, "UnitMap", p], capture_output=True, text=True, timeout=120)
    finally:
        Path(p).unlink(missing_ok=True)
    if out.returncode != 0:
        raise RuntimeError(out.stderr[-500:])
    methods, oracles, calls = [], [], {}
    for ln in out.stdout.splitlines():
        f = ln.split(" ")
        if f[0] == "METHOD":
            methods.append({"idx": int(f[1]), "kind": f[2], "first": int(f[3]), "body_open": int(f[4]),
                            "body_end": int(f[5]), "name": f[6]})
        elif f[0] == "ORACLE":
            oracles.append((int(f[1]), int(f[2]), int(f[3])))
        elif f[0] == "CALL":
            calls.setdefault(int(f[1]), set()).add(f[2])
    tests = sorted((m for m in methods if m["kind"] == "TEST"), key=lambda m: m["first"])
    return {"methods": methods, "tests": tests, "oracles": oracles, "calls": calls}


def code_lines(lines) -> int:
    n = 0
    for ln in lines:
        s = ln.strip()
        if not s or s in _NOISE or s.startswith(("//", "/*", "*", "*/")):
            continue
        n += 1
    return n


def loc_after(src_lines, um, m) -> int:
    drop = {i for b, e, mi in um["oracles"] if mi == m["idx"] for i in range(b, e + 1)}
    return code_lines(src_lines[i - 1] for i in range(m["body_open"] + 1, m["body_end"]) if i not in drop)


def reachable_helpers(um, start_idx: set) -> set:
    by_name = {}
    for m in um["methods"]:
        by_name.setdefault(m["name"], []).append(m)
    seen, stack = set(), list(start_idx)
    while stack:
        i = stack.pop()
        for name in um["calls"].get(i, ()):
            for h in by_name.get(name, []):
                if h["kind"] == "HELPER" and h["idx"] not in seen:
                    seen.add(h["idx"]); stack.append(h["idx"])
    return seen


def apply_mask(src: str, um: dict, test_positions: list[int], extra_oracles=None) -> dict:
    """Mask the test methods at the given positions (in test order). `extra_oracles`
    (list of (begin,end)) forces additional oracle statements out - used by the
    mutation-guided C category."""
    lines = src.split("\n")
    sel = {um["tests"][k]["idx"] for k in test_positions}
    helpers = reachable_helpers(um, sel)
    scope = sel | helpers
    strip = [(b, e, mi) for b, e, mi in um["oracles"] if mi in scope]
    if extra_oracles:
        have = {(b, e) for b, e, _ in strip}
        strip += [(b, e, mi) for b, e, mi in um["oracles"] if (b, e) in set(map(tuple, extra_oracles)) and (b, e) not in have]
    drop = {i for b, e, _ in strip for i in range(b, e + 1)}
    marked = {}
    for b, e, mi in strip:
        marked[mi] = marked.get(mi, 0) + 1
    by_idx = {m["idx"]: m for m in um["methods"]}
    marker_at = {}
    for mi in marked:
        m = by_idx[mi]
        indent = re.match(r"\s*", lines[m["first"] - 1]).group(0)
        marker_at[m["first"]] = indent + MARKER
    out = []
    for i, ln in enumerate(lines, 1):
        if i in marker_at:
            out.append(marker_at[i])
        if i not in drop:
            out.append(ln)
    kept_tests = {t["idx"] for t in um["tests"]} - sel
    # helpers that LOSE oracles here but are also reached from an unmasked test:
    # masking them removes checks from that unmasked test too
    shared = sorted(by_idx[h]["name"] for h in helpers if h in marked and h in reachable_helpers(um, kept_tests))
    return {"masked": "\n".join(out), "n_removed": len(strip),
            "removed": [[b, e] for b, e, _ in strip],
            "marked": [{"name": by_idx[mi]["name"], "kind": by_idx[mi]["kind"], "n_removed": n}
                       for mi, n in sorted(marked.items(), key=lambda x: by_idx[x[0]]["first"])],
            "shared_helpers": shared}


