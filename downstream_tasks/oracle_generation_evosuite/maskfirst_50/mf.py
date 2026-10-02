#!/usr/bin/env python3
"""Shared helpers for the mask-first EvoSuite study (50 exact-match suites).

Design in one paragraph: take the ORIGINAL EvoSuite suite, delete EVERY oracle
(no minimum-body rule this time), measure that floor, then let the improver work
on the oracle-free test — so it can never encode an answer it was never shown.
Gate on the floor being unchanged, then ask a fresh agent to put the oracles back
under three visibility settings. Nothing in the project repo is modified.
"""
import json, re, subprocess, shutil, sys, threading
from pathlib import Path

ROOT = Path(__file__).resolve().parents[3]          # repository root
sys.path.insert(0, str(ROOT / "agent_improvement" / "scripts"))
sys.path.insert(0, str(ROOT / "downstream_tasks"))
sys.path.insert(0, str(ROOT / "downstream_tasks" / "oracle_masking"))
import coverage_runner as CR        # noqa: E402
import mutation_measurement as MM   # noqa: E402
import build_executor as BE         # noqa: E402
import ds_common as C               # noqa: E402
import mask_v2 as MV                # noqa: E402

HERE = Path(__file__).resolve().parent
CFG = C.load_cfg(str(ROOT / "agent_improvement" / "config.yaml"))
MVN = C.maven_path(CFG)
JH = getattr(CFG.tools.java_homes, "11")       # JDK_11_HOME from .env (via agent_improvement/config.yaml)
EVO = ROOT / "tools/evosuite-standalone-runtime-1.2.0.jar"
JUNIT = ROOT / "tools/junit/junit-4.13.2.jar"
HAMCREST = ROOT / "tools/junit/hamcrest-core-1.3.jar"
_cp: dict[str, str] = {}
_lock = threading.Lock()


def module_dir(rec) -> Path:
    """`class_path` carries the module prefix for multi-module projects
    (commons-math-legacy/src/main/java/..., itext/src/main/java/...), so the
    module is whatever precedes src/main/java. Getting this wrong makes
    `mvn dependency:build-classpath` run against an aggregator pom and fail."""
    base = ROOT / "local_workplace" / rec["project"]
    i = rec["class_path"].find("src/main/java")
    pre = rec["class_path"][:i].strip("/")
    return (base / pre) if pre else base


def classpath(rec) -> str:
    mod = module_dir(rec); key = str(mod)
    with _lock:
        if key in _cp:
            return _cp[key]
    cache = mod / "target" / "evosuite-cp-test.txt"
    cp = cache.read_text().strip() if cache.exists() and cache.stat().st_size else ""
    if not cp:
        cp = BE.extract_classpath(mod, "maven", MVN, include_test=True)
        try:
            cache.write_text(cp)
        except Exception:
            pass
    with _lock:
        _cp[key] = cp
    return cp


def suite_fqn(rec) -> str:
    return rec["class_fqn"].rsplit(".", 1)[0] + "." + rec["suite"]


def compile_and_run(rec, suite_java: Path, work: Path, timeout=900) -> dict:
    """javac the suite + scaffolding, then JUnitCore under the EvoSuite agent."""
    shutil.rmtree(work, ignore_errors=True); work.mkdir(parents=True, exist_ok=True)
    full = ":".join([classpath(rec), str(JUNIT), str(HAMCREST), str(EVO)])
    r = subprocess.run([f"{JH}/bin/javac", "-encoding", "UTF-8", "-nowarn", "-cp", full,
                        "-d", str(work), str(suite_java), rec["scaffolding"]],
                       capture_output=True, text=True)
    if r.returncode:
        shutil.rmtree(work, ignore_errors=True)
        return {"status": "COMPILE_FAIL", "tail": r.stderr[-400:]}
    try:
        rr = subprocess.run([f"{JH}/bin/java", "-Djava.awt.headless=true",
                             "-Dapple.awt.UIElement=true", f"-javaagent:{EVO}",
                             "-Djdk.attach.allowAttachSelf=true",
                             "-cp", ":".join([str(work), full]),
                             "org.junit.runner.JUnitCore", suite_fqn(rec)],
                            capture_output=True, text=True, timeout=timeout)
    except subprocess.TimeoutExpired:
        shutil.rmtree(work, ignore_errors=True)
        return {"status": "TIMEOUT"}
    shutil.rmtree(work, ignore_errors=True)
    o = (rr.stdout or "") + (rr.stderr or "")
    m = re.search(r"^OK \((\d+) test", o, re.M)
    if m:
        return {"status": "PASS", "total": int(m.group(1)), "failures": 0}
    m = re.search(r"Tests run:\s*(\d+),\s*Failures:\s*(\d+)", o)
    if m:
        return {"status": "FAIL", "total": int(m.group(1)), "failures": int(m.group(2)),
                "which": re.findall(r"^\d+\)\s+(\w+)", o, re.M)[:8]}
    return {"status": "RUN_ERR", "tail": o[-400:]}


def measure(rec, suite_java: Path, work: Path, keep=False) -> dict:
    """JaCoCo + PIT for one suite, via the main-study EvoSuite path."""
    work.mkdir(parents=True, exist_ok=True)
    mod = module_dir(rec)
    r = CR.measure(test_java_path=str(suite_java), target_class_fqn=rec["class_fqn"],
                   project_classpath=classpath(rec),
                   sut_classes_dir=str(mod / "target" / "classes"),
                   work_dir=str(work), cfg=CFG, project_dir=str(mod),
                   is_evosuite=True, scaffolding_java=rec["scaffolding"],
                   mvn_module="") or {"compile_success": False}
    if r.get("compile_success") and MM.enabled(CFG):
        try:
            m = MM.measure(test_java_path=str(suite_java), target_class_fqn=rec["class_fqn"],
                           project_classpath=classpath(rec),
                           sut_classes_dir=str(mod / "target" / "classes"),
                           work_dir=str(work), cfg=CFG, is_evosuite=True,
                           scaffolding_java=rec["scaffolding"])
            if m:
                r.update(m)
        except Exception as e:
            r["mutation_error"] = f"{type(e).__name__}: {e}"
    killed = r.pop("killed_mutants", None)
    r.pop("killed_mutants_preview", None)
    if killed is not None:
        r["killed_keys"] = sorted({
            ":".join([k.get("mutated_class", ""), k.get("mutated_method", ""),
                      k.get("method_description", ""), str(k.get("line_number", "")),
                      k.get("mutator", ""), ",".join(k.get("indexes") or [])])
            for k in killed})
    if not keep:
        shutil.rmtree(work, ignore_errors=True)
    return r


def mask_all_oracles(src: str):
    """Delete EVERY oracle in the suite — no MIN_LOC rule, no markers.
    Returns (masked_source, info). Methods that end up empty are kept as-is:
    an empty body is exactly the hardest, most honest starting point."""
    um = MV.unitmap(src)
    pos = [k for k, t in enumerate(um["tests"])
           if any(mi == t["idx"] for _, _, mi in um["oracles"])]
    m = MV.apply_mask(src, um, pos)
    masked = "\n".join(l for l in m["masked"].split("\n") if l.strip() != MV.MARKER.strip())
    L = src.split("\n")
    empty = sum(1 for k in pos if MV.loc_after(L, um, um["tests"][k]) == 0)
    return masked, {"n_tests": len(um["tests"]), "n_tests_masked": len(pos),
                    "n_oracles_removed": m["n_removed"], "removed": m["removed"],
                    "n_methods_emptied": empty, "shared_helpers": m["shared_helpers"]}


def _abs_paths(recs):
    for r in recs:
        for k in ("original", "scaffolding", "improved"):
            if r.get(k) and not Path(r[k]).is_absolute():
                r[k] = str(ROOT / r[k])
    return recs


def load50():
    return _abs_paths(json.loads((HERE / "selected50.json").read_text()))
