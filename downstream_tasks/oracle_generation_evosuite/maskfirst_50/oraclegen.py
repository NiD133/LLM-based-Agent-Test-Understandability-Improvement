#!/usr/bin/env python3
"""Stage 4 — put the oracles back, under three visibility settings, 3 runs each.

Two arms, both with zero oracles and both gated to the same floor:
  original  the masked EvoSuite suite as-is  (test00, test01, ... )
  improved  the same suite after understandability improvement
Because masking happened BEFORE the improvement, the improved arm cannot carry an
answer the improver was never shown. Both arms share ONE ground truth (the
original suite's killed-mutant set), so retention is directly comparable.

S1s  the project is readable, minus the project's own manual test for this CUT
S2s  only the CUT source + the masked suite
S3s  only the masked suite
No `// please add oracles here` markers: the agent finds the places itself.
"""
import argparse, json, re, shutil, sys, threading, time
from concurrent.futures import ThreadPoolExecutor, as_completed
from pathlib import Path
import mf
from mf import ROOT, C, JH, EVO, JUNIT, HAMCREST
sys.path.insert(0, str(ROOT / "downstream_tasks"))

HERE = Path(__file__).resolve().parent
_P = ROOT / "downstream_tasks" / "prompts"
USR = (_P / "downstream_user_message_unmarked.txt").read_text(encoding="utf-8")
SETTINGS = {
    "S1s": {"prompt": "downstream_system_prompt_s1_open", "cut_readable": True,
            "strict_read": False, "full_project": True},
    "S2s": {"prompt": "downstream_system_prompt_s2_cut_only", "cut_readable": True,
            "strict_read": True, "full_project": False},
    "S3s": {"prompt": "downstream_system_prompt_s3_test_only", "cut_readable": False,
            "strict_read": True, "full_project": False},
}
SYS = {k: (_P / (v["prompt"] + ".txt")).read_text(encoding="utf-8") for k, v in SETTINGS.items()}
_GATE = None
_LOCK = threading.Lock()
_PRINT = threading.Lock()


def say(m):
    with _PRINT:
        print(m, flush=True)


def arm_src(suite, arm):
    return (HERE / "suites" / suite / "masked" / f"{suite}.java") if arm == "original" \
        else (HERE / "improved" / suite / f"{suite}.java")


def manual_tests(rec):
    """The project's own developer test for this CUT — an answer key in S1s."""
    cut = Path(rec["class_path"]).stem
    base = ROOT / "local_workplace" / rec["project"]
    out = []
    for pat in (f"src/test/**/{cut}Test.java", f"src/test/**/Test{cut}.java"):
        out += [p for p in base.rglob(pat)]
    return out


def build_sh(rec, sandbox, suite_abs, scaf_abs):
    import shlex
    q = shlex.quote
    cp = ":".join([mf.classpath(rec), str(JUNIT), str(HAMCREST), str(EVO)])
    return ("#!/usr/bin/env bash\nset -u\n"
            f'JAVAC={q(JH + "/bin/javac")}\nOUT={q(str(sandbox / "test-classes"))}\n'
            'mkdir -p "$OUT"\n'
            f'"$JAVAC" -encoding UTF-8 -nowarn -cp {q(cp)} -d "$OUT" '
            f'{q(str(suite_abs))} {q(str(scaf_abs))}\nrc=$?\n'
            'if [ "$rc" -eq 0 ]; then echo "COMPILE_OK"; else echo "COMPILE_FAIL (exit=$rc)"; fi\n'
            'exit $rc\n')


def one(rec, arm, setting, run_n, cfg, art, model, max_turns, tools, keep):
    s = rec["suite"]; cf = SETTINGS[setting]
    uid = f"{rec['project']}__{s}__{arm}__{setting}"
    sandbox = art / "sandbox" / uid
    out_dir = art / "results" / uid
    if sandbox.exists():
        shutil.rmtree(sandbox)
    (sandbox / "_spec").mkdir(parents=True, exist_ok=True)
    (out_dir / "tests").mkdir(parents=True, exist_ok=True)
    base = ROOT / "local_workplace" / rec["project"]
    if cf["full_project"]:
        C.rsync_repo(base, sandbox, exclude=[".git", "target"])
        for p in manual_tests(rec):
            t = sandbox / p.relative_to(base)
            if t.exists():
                t.unlink()
    src = arm_src(s, arm)
    suite_abs = sandbox / "_spec" / f"{s}.java"; shutil.copy2(src, suite_abs)
    scaf_abs = sandbox / "_spec" / Path(rec["scaffolding"]).name
    shutil.copy2(rec["scaffolding"], scaf_abs)
    shutil.copy2(src, out_dir / "tests" / "masked.java")
    real_cut = base / rec["class_path"]
    if cf["cut_readable"] and not cf["full_project"]:
        cut_abs = sandbox / "cut" / Path(rec["class_path"]).name
        cut_abs.parent.mkdir(exist_ok=True); shutil.copy2(real_cut, cut_abs)
    else:
        cut_abs = sandbox / rec["class_path"] if cf["full_project"] else None
    C.write_sandbox_skills(sandbox, build_sh(rec, sandbox, suite_abs, scaf_abs))

    sys_append = SYS[setting].replace("{test_file}", str(suite_abs.resolve()))
    if cf["cut_readable"]:
        sys_append = sys_append.replace("{cut_path}", str(cut_abs.resolve()))
    left = re.findall(r"\{[a-z_]+\}", sys_append)
    if left:
        raise RuntimeError(f"unsubstituted {left} in {setting}")
    usr = USR.replace("{test_file}", str(suite_abs.resolve()))

    t0 = time.time()
    proc = C.run_claude_session(
        cfg=cfg, sandbox=sandbox,
        writable_abs=suite_abs, read_allow=[suite_abs] + ([cut_abs] if cf["cut_readable"] else []),
        system_append=sys_append, user_message=usr, model=model, max_turns=max_turns,
        out_dir=out_dir, allowed_tools=list(tools), deny_read=[],
        strict_read_allow=cf["strict_read"])
    wall = round(time.time() - t0, 1)

    filled = suite_abs.read_text(errors="ignore")
    (out_dir / "tests" / "filled.java").write_text(filled, encoding="utf-8")
    gt = json.loads((HERE / "suites" / s / "gt.json").read_text())
    fl = json.loads((HERE / "suites" / s / "floor.json").read_text())
    gk = set(gt.get("killed_keys") or []); fk = set(fl.get("killed_keys") or [])
    with _GATE:
        tr = mf.compile_and_run(rec, suite_abs, HERE / "_work" / f"{uid}__r{run_n}__junit")
        mm = mf.measure(rec, suite_abs, HERE / "_work" / f"{uid}__r{run_n}")
    kk = set(mm.get("killed_keys") or []); stake = gk - fk
    out = {
        "unit_id": uid, "suite": s, "project": rec["project"], "arm": arm,
        "setting": setting, "run": run_n, "model": model, "loc_original": rec["loc"],
        "oracles_in_masked": C.count_oracles(src.read_text(errors="ignore")),
        "oracles_in_filled": C.count_oracles(filled),
        "abstained": len(re.findall(r"//\s*ABSTAINED", filled)),
        "rebuilt_compile_success": bool(mm.get("compile_success")),
        "rebuilt_line_pct": mm.get("line_pct"), "rebuilt_branch_pct": mm.get("branch_pct"),
        "rebuilt_mutation_pct": mm.get("mutation_score_pct"), "rebuilt_n_killed": len(kk),
        "mutation_error": mm.get("mutation_error"),
        "gt_mutation_pct": gt.get("mutation_score_pct"), "floor_mutation_pct": fl.get("mutation_score_pct"),
        "stake_mutants": len(stake), "stake_recovered": len(kk & stake),
        "fault_detection_retention": round(len(kk & gk) / len(gk), 4) if gk else None,
        "filled_test_status": tr["status"], "filled_tests_run": tr.get("total"),
        "filled_test_failures": tr.get("failures"), "wall_clock_s": wall,
        "sdk_error": proc.get("sdk_error"), "quota_exhausted": proc.get("quota_exhausted"),
        "tokens": proc.get("tokens"),
        **C.effort_fields(proc, max_turns, "MEASURED" if mm.get("compile_success") else "COMPILE_FAIL"),
    }
    co = out.get("compile_outcomes") or []
    out["first_compile_ok"] = (co[0] == "OK") if co else None
    out["compile_fails"] = sum(1 for x in co if x != "OK")
    out["compile_runs"] = len(co)
    if not keep:
        shutil.rmtree(sandbox, ignore_errors=True)
    say(f"[{uid}] run{run_n} · mut {out['rebuilt_mutation_pct']} "
        f"(floor {out['floor_mutation_pct']} → gt {out['gt_mutation_pct']}) · "
        f"stake {out['stake_recovered']}/{out['stake_mutants']} · reasoning {out['reasoning_tokens']} · {wall}s")
    return out


def main():
    ap = argparse.ArgumentParser()
    ap.add_argument("--setting", required=True, choices=list(SETTINGS))
    ap.add_argument("--run", type=int, required=True)
    ap.add_argument("--jobs", type=int, default=16)
    ap.add_argument("--measure-jobs", type=int, default=4)
    ap.add_argument("--model", default="claude-sonnet-4-6")
    ap.add_argument("--max-turns", type=int, default=80)
    ap.add_argument("--only"); ap.add_argument("--keep-sandboxes", action="store_true")
    a = ap.parse_args()
    global _GATE
    _GATE = threading.Semaphore(a.measure_jobs)
    cfg = C.load_cfg(str(ROOT / "agent_improvement" / "config.yaml"))
    gate = {r["suite"] for r in json.loads((HERE / "gate_summary.json").read_text()) if r["pass_gate"]}
    recs = [r for r in mf.load50() if r["suite"] in gate]
    if a.only:
        recs = [r for r in recs if r["suite"] in a.only.split(",")]
    art = HERE / "runs" / f"{a.setting}_run{a.run}"
    art.mkdir(parents=True, exist_ok=True)
    rp = art / "results.json"
    results = json.loads(rp.read_text()) if rp.exists() else []
    bad = [r for r in results if r.get("quota_exhausted") or r.get("sdk_error")]
    if bad:
        say(f"[run] resume: 丢弃 {len(bad)} 条限额/SDK 错误记录")
        results = [r for r in results if not (r.get("quota_exhausted") or r.get("sdk_error"))]
    have = {r["unit_id"] for r in results}
    tasks = [(r, arm) for r in recs for arm in ("original", "improved")
             if f"{r['project']}__{r['suite']}__{arm}__{a.setting}" not in have]
    say(f"[run] {a.setting} run{a.run} · {len(recs)} 个 suite · 待跑 {len(tasks)} 个 session · 并发 {a.jobs}")
    tools = ["Read", "Edit", "Write", "Bash", "Glob", "Grep"]
    with ThreadPoolExecutor(a.jobs) as ex:
        futs = {ex.submit(one, r, arm, a.setting, a.run, cfg, art, a.model, a.max_turns,
                          tools, a.keep_sandboxes): (r["suite"], arm) for r, arm in tasks}
        for f in as_completed(futs):
            s, arm = futs[f]
            try: out = f.result()
            except Exception as e:
                say(f"  !! {s}/{arm} FAILED {type(e).__name__}: {e}"); continue
            with _LOCK:
                results.append(out)
                rp.write_text(json.dumps(results, indent=1, ensure_ascii=False))
    say(f"[run] {a.setting} run{a.run} 完成 {len(results)} 条 → {rp}")


if __name__ == "__main__":
    main()
