#!/usr/bin/env python3
"""Stage 2 — understandability improvement ON THE ORACLE-FREE TEST.

The improver sees the CUT and a test whose oracles are all gone, so anything it
writes (a name, a comment, a named constant) can only come from the visible call
sequence and the CUT — never from an oracle it was shown. That is the whole point
of doing this after masking rather than before.

System prompt: the main-study one, verbatim.
User message:  the main-study one plus ONE rule — do not add oracles. The rules
that froze renaming in the earlier round (keep the same @Test count / keep every
statement / keep every import) are deliberately NOT here.
"""
import argparse, json, re, shutil, time, threading
from concurrent.futures import ThreadPoolExecutor, as_completed
from pathlib import Path
import mf
from mf import ROOT, C, JH, EVO, JUNIT, HAMCREST
import sys
sys.path.insert(0, str(ROOT / "downstream_tasks"))

HERE = Path(__file__).resolve().parent
SYS_PROMPT = (ROOT / "agent_improvement" / "prompts" / "improve_system_prompt.txt").read_text(encoding="utf-8")
USER_MSG = """Definition: Understandability refers to how easily developers can comprehend and maintain the test code.

Definition: A test oracle is an assertion or verification statement that checks the expected behavior of the class under test, such as JUnit assertions (`assertEquals`, `assertTrue`, `assertNull`, `assertThrows`) or Mockito verifications (`verify`, `verifyNoMoreInteractions`).

Task: Improve the understandability of the test `{test_file}`. The refactored code must be syntactically correct and compilable against the provided CUT signatures. Do not alter the runtime behaviour of the original test: the same methods must be called with the same arguments.

Rule: Do not add any test oracles.
"""
_PRINT = threading.Lock()
_LOCK = threading.Lock()


def say(m):
    with _PRINT:
        print(m, flush=True)


def build_sh(rec, sandbox: Path, suite_abs: Path, scaf_abs: Path) -> str:
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


def one(rec, cfg, model, max_turns, tools, keep_sandbox):
    s = rec["suite"]
    masked = HERE / "suites" / s / "masked" / f"{s}.java"
    art = HERE / "improve"
    sandbox = art / "sandbox" / s
    out_dir = art / "results" / s
    if sandbox.exists():
        shutil.rmtree(sandbox)
    (sandbox / "_spec").mkdir(parents=True, exist_ok=True)
    out_dir.mkdir(parents=True, exist_ok=True)
    suite_abs = sandbox / "_spec" / f"{s}.java"
    shutil.copy2(masked, suite_abs)
    scaf_abs = sandbox / "_spec" / Path(rec["scaffolding"]).name
    shutil.copy2(rec["scaffolding"], scaf_abs)
    cut_abs = sandbox / "cut" / Path(rec["class_path"]).name
    cut_abs.parent.mkdir(exist_ok=True)
    cut_src = mf.module_dir(rec) / rec["class_path"].split("src/main/java", 1)[0].strip("/") if False else None
    real_cut = ROOT / "local_workplace" / rec["project"] / rec["class_path"]
    shutil.copy2(real_cut, cut_abs)
    C.write_sandbox_skills(sandbox, build_sh(rec, sandbox, suite_abs, scaf_abs))

    sys_append = SYS_PROMPT.replace("{cut_path}", str(cut_abs.resolve()))
    left = re.findall(r"\{[a-z_]+\}", sys_append)
    if left:
        raise RuntimeError(f"unsubstituted {left} in system prompt")
    usr = USER_MSG.replace("{test_file}", str(suite_abs.resolve()))

    t0 = time.time()
    say(f"[{s}] improve start · {rec['project']} · LOC {rec['loc']}")
    proc = C.run_claude_session(
        cfg=cfg, sandbox=sandbox,
        writable_abs=suite_abs, read_allow=[suite_abs, cut_abs], system_append=sys_append,
        user_message=usr, model=model, max_turns=max_turns, out_dir=out_dir,
        allowed_tools=list(tools), deny_read=[], strict_read_allow=True)
    wall = round(time.time() - t0, 1)

    improved = HERE / "improved" / s
    improved.mkdir(parents=True, exist_ok=True)
    shutil.copy2(suite_abs, improved / f"{s}.java")
    shutil.copy2(masked, out_dir / "masked.java")
    shutil.copy2(suite_abs, out_dir / "improved.java")
    mtxt = masked.read_text(errors="ignore"); itxt = (improved / f"{s}.java").read_text(errors="ignore")
    T = re.compile(r"@Test[^\n]*\n(?:\s*@[\w.]+(?:\([^)]*\))?\s*\n)*\s*(?:public\s+)?void\s+(\w+)\s*\(")
    nm, ni = T.findall(mtxt), T.findall(itxt)
    rec_out = {
        "suite": s, "project": rec["project"], "model": model,
        "loc_masked": len(mtxt.split("\n")), "loc_improved": len(itxt.split("\n")),
        "n_tests_masked": len(nm), "n_tests_improved": len(ni),
        "tests_renamed": sum(1 for a, b in zip(nm, ni) if a != b) if len(nm) == len(ni) else None,
        "oracles_in_masked": C.count_oracles(mtxt), "oracles_in_improved": C.count_oracles(itxt),
        "wall_clock_s": wall, "sdk_error": proc.get("sdk_error"),
        "quota_exhausted": proc.get("quota_exhausted"), "tokens": proc.get("tokens"),
        **C.effort_fields(proc, max_turns, "MEASURED"),
    }
    co = rec_out.get("compile_outcomes") or []
    rec_out["first_compile_ok"] = (co[0] == "OK") if co else None
    rec_out["compile_fails"] = sum(1 for x in co if x != "OK")
    rec_out["compile_runs"] = len(co)
    if not keep_sandbox:
        shutil.rmtree(sandbox, ignore_errors=True)
    say(f"[{s}] improve done · LOC {rec_out['loc_masked']}→{rec_out['loc_improved']} · "
        f"renamed {rec_out['tests_renamed']}/{rec_out['n_tests_masked']} · "
        f"oracles {rec_out['oracles_in_masked']}→{rec_out['oracles_in_improved']} · "
        f"reasoning {rec_out['reasoning_tokens']} · {wall}s")
    return rec_out


def main():
    ap = argparse.ArgumentParser()
    ap.add_argument("--jobs", type=int, default=16)
    ap.add_argument("--model", default="claude-sonnet-4-6")
    ap.add_argument("--max-turns", type=int, default=10)
    ap.add_argument("--only")
    ap.add_argument("--keep-sandboxes", action="store_true")
    a = ap.parse_args()
    cfg = C.load_cfg(str(ROOT / "agent_improvement" / "config.yaml"))
    recs = mf.load50()
    prep = {r["suite"]: r for r in json.loads((HERE / "prep_summary.json").read_text())}
    usable = [r for r in recs
              if prep.get(r["suite"], {}).get("stake")
              and prep[r["suite"]]["gt_junit"] == "PASS" and prep[r["suite"]]["floor_junit"] == "PASS"
              and not prep[r["suite"]]["gt_err"] and not prep[r["suite"]]["floor_err"]]
    if a.only:
        usable = [r for r in usable if r["suite"] in a.only.split(",")]
    rp = HERE / "improve" / "results.json"
    rp.parent.mkdir(parents=True, exist_ok=True)
    have = json.loads(rp.read_text()) if rp.exists() else []
    have = [h for h in have if not (h.get("quota_exhausted") or h.get("sdk_error"))]
    done = {h["suite"] for h in have}
    todo = [r for r in usable if r["suite"] not in done]
    say(f"[improve] {len(usable)} 个可用 · 待跑 {len(todo)} · 并发 {a.jobs} · {a.model} max_turns={a.max_turns}")
    tools = ["Read", "Write", "Edit", "Bash", "Glob", "Grep"]
    with ThreadPoolExecutor(a.jobs) as ex:
        futs = {ex.submit(one, r, cfg, a.model, a.max_turns, tools, a.keep_sandboxes): r for r in todo}
        for f in as_completed(futs):
            r = futs[f]
            try:
                out = f.result()
            except Exception as e:
                say(f"  !! {r['suite']} FAILED {type(e).__name__}: {e}"); continue
            with _LOCK:
                have.append(out)
                rp.write_text(json.dumps(have, indent=1, ensure_ascii=False))
    say(f"[improve] 完成 {len(have)}/{len(usable)} → {rp}")


if __name__ == "__main__":
    main()
