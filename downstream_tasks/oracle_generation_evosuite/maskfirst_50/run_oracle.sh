#!/usr/bin/env bash
# stage4 only: oracle generation, 3 settings x 3 runs (stages 1-3 already done).
set -u
cd "$(dirname "$0")" || exit 1
LOG=run.log; WAIT_MIN=30; MAX_ATTEMPTS=24
say() { echo "[$(date '+%F %T')] $*" | tee -a "$LOG"; }
good() { python3 - "$1" <<'PY'
import json,sys
from pathlib import Path
try:
    rs=json.loads(Path(sys.argv[1]).read_text())
    print(sum(1 for r in rs if not (r.get("quota_exhausted") or r.get("sdk_error"))))
except Exception: print(0)
PY
}
TARGET=$(python3 -c "
import json
from pathlib import Path
print(2*sum(1 for r in json.loads(Path('gate_summary.json').read_text()) if r['pass_gate']))")
say "stage4 目标每个 label $TARGET 条"
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
