#!/usr/bin/env bash
# improve-FIRST EvoSuite study, 49 exact-match suites, sonnet-4-6 (2026-09-21).
# Mirror of ../maskfirst_50. There: mask -> improve. Here the
# improvement already happened in RQ1, so there is no improve stage and no gate:
# stage1 prep (mask both arms, measure) -> stage2 oracle-gen 3 settings x 3 runs.
#
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

# ── stage 1: prep must already have produced usable.json ────────────────
if [ ! -f usable.json ]; then
  say "usable.json 不存在 — 先跑 python3 prep.py --jobs 4"
  exit 1
fi
NS=$(python3 -c "import json;print(len(json.load(open('usable.json'))))")
TARGET=$((NS*2))
say "stage2 oracle generation: $NS 个 suite x 2 臂 = $TARGET 个 session / label"

# ── stage 2: oracle generation 3 settings x 3 runs ──────────────────────
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
