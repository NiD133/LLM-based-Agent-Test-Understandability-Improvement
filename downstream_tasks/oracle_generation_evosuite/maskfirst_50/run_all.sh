#!/usr/bin/env bash
# mask-first EvoSuite study, 50 exact-match suites, sonnet-4-6 (2026-09-21).
# stage1 prep (已单独跑) → stage2 improve → stage3 gate → stage4 oracle-gen 3x3.
# A label counts only records WITHOUT quota_exhausted/sdk_error — counting raw
# rows is what let a whole earlier run "finish" on 360 empty quota records.
set -u
cd "$(dirname "$0")" || exit 1
LOG=run.log
WAIT_MIN=30
MAX_ATTEMPTS=24
say() { echo "[$(date '+%F %T')] $*" | tee -a "$LOG"; }

good() {  # $1=json path -> count of records without quota/sdk errors
  python3 - "$1" <<'PY'
import json,sys
from pathlib import Path
p=Path(sys.argv[1])
try:
    rs=json.loads(p.read_text())
    print(sum(1 for r in rs if not (r.get("quota_exhausted") or r.get("sdk_error"))))
except Exception: print(0)
PY
}
need_improve() { python3 -c "
import json
from pathlib import Path
p=json.loads(Path('prep_summary.json').read_text())
print(sum(1 for r in p if r['stake'] and r['gt_junit']=='PASS' and r['floor_junit']=='PASS' and not r['gt_err'] and not r['floor_err']))"; }

# ── stage 2: improvement ────────────────────────────────────────────────
NEED=$(need_improve); say "stage2 improvement: 目标 $NEED 个"
a=0
while :; do
  n=$(good improve/results.json)
  [ "$n" -ge "$NEED" ] && { say "stage2 完成 ($n/$NEED)"; break; }
  a=$((a+1)); [ "$a" -gt "$MAX_ATTEMPTS" ] && { say "stage2 放弃于 $n/$NEED"; exit 1; }
  say "stage2 attempt $a (have $n/$NEED)"
  python3 improve.py --jobs 16 >>"$LOG" 2>&1
  n=$(good improve/results.json)
  [ "$n" -lt "$NEED" ] && { say "stage2 短缺 ($n/$NEED) — 等待 ${WAIT_MIN}m"; sleep $((WAIT_MIN*60)); }
done

# ── stage 3: consistency gate ───────────────────────────────────────────
say "stage3 一致性门槛"
python3 gate.py --jobs 4 >>"$LOG" 2>&1
GN=$(python3 -c "
import json
from pathlib import Path
print(sum(1 for r in json.loads(Path('gate_summary.json').read_text()) if r['pass_gate']))")
say "stage3 通过 $GN 个"
TARGET=$((GN*2))

# ── stage 4: oracle generation 3 settings x 3 runs ──────────────────────
for run in 1 2 3; do
  for s in S1s S2s S3s; do
    a=0
    while :; do
      n=$(good "runs/${s}_run${run}/results.json")
      [ "$n" -ge "$TARGET" ] && { say "$s run$run 完成 ($n/$TARGET)"; break; }
      a=$((a+1)); [ "$a" -gt "$MAX_ATTEMPTS" ] && { say "$s run$run 放弃于 $n/$TARGET"; break; }
      say "$s run$run attempt $a (have $n/$TARGET)"
      rm -rf "runs/${s}_run${run}/sandbox" 2>/dev/null
      python3 oraclegen.py --setting "$s" --run "$run" --jobs 16 --measure-jobs 4 >>"$LOG" 2>&1
      n=$(good "runs/${s}_run${run}/results.json")
      [ "$n" -lt "$TARGET" ] && { say "$s run$run 短缺 ($n/$TARGET) — 等待 ${WAIT_MIN}m"; sleep $((WAIT_MIN*60)); }
    done
  done
done
say "ALL DONE"
