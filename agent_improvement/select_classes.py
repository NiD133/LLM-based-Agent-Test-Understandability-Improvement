#!/usr/bin/env python3
"""
select_classes.py -- Step 1 of the study: candidate class selection.

Input   agent_improvement/data/subjects_14.json
            [{"project": "commons-cli", "repo": "apache/commons-cli"}, ...]
        `project` is the checkout folder under <repository root>/local_workplace/; `repo` is the
        GitHub owner/name, used only to clone when the checkout is missing.

Filter  a production class (under src/main/java) is kept when ALL hold:
            physical LOC   > 50   non-blank lines after stripping comments
            McCabe CC      > 5    file level: 1 + if/for/while/case/catch/&&/||/?:
            branches       >= 1   if / switch / case / ?:
            manual test    exists FooTest.java, TestFoo.java, FooTests.java, ...
        Metrics are regex-based on comment- and literal-stripped source.

Output  data/classes_selected.json, in the shape of data/classes_140.json,
        plus loc / cc / branches per class.

Usage
    python3 select_classes.py
    python3 select_classes.py --subjects data/subjects_14.json --output data/classes_selected.json
"""

from __future__ import annotations

import argparse
import json
import re
import subprocess
import sys
from dataclasses import dataclass
from pathlib import Path
from typing import Iterable, Optional

HERE = Path(__file__).resolve().parent

MIN_LOC = 50       # physical LOC must be  > MIN_LOC
MIN_CC = 5         # cyclomatic complexity > MIN_CC
MIN_BRANCHES = 1   # branches             >= MIN_BRANCHES


# --------------------------------------------------------------------------- #
# metrics
# --------------------------------------------------------------------------- #

_BLOCK_COMMENT = re.compile(r"/\*.*?\*/", re.DOTALL)
_LINE_COMMENT = re.compile(r"//[^\n]*")
_TEXT_BLOCK = re.compile(r'"""(?:\\.|[^\\])*?"""', re.DOTALL)
_STRING = re.compile(r'"(?:\\.|[^"\\\n])*"')
_CHAR = re.compile(r"'(?:\\.|[^'\\\n])'")

_DECISION_PATTERNS = [re.compile(p) for p in
                      (r"\bif\b", r"\bfor\b", r"\bwhile\b", r"\bcase\b", r"\bcatch\b",
                       r"&&", r"\|\|", r"\?")]
_BRANCH_PATTERNS = [re.compile(p) for p in (r"\bif\b", r"\bswitch\b", r"\bcase\b", r"\?")]


def strip_comments(source: str) -> str:
    return _LINE_COMMENT.sub("", _BLOCK_COMMENT.sub("", source))


def strip_comments_and_literals(source: str) -> str:
    code = strip_comments(source)
    code = _TEXT_BLOCK.sub('""', code)
    code = _STRING.sub('""', code)
    return _CHAR.sub("''", code)


@dataclass
class ClassMetrics:
    physical_loc: int   # non-blank, comment-stripped lines
    complexity: int     # McCabe cyclomatic complexity, file level
    branches: int       # if / switch / case / ternary count


def compute_metrics(source: str) -> ClassMetrics:
    code = strip_comments_and_literals(source)
    physical_loc = sum(1 for ln in strip_comments(source).splitlines() if ln.strip())
    decisions = sum(len(p.findall(code)) for p in _DECISION_PATTERNS)
    branches = sum(len(p.findall(code)) for p in _BRANCH_PATTERNS)
    return ClassMetrics(physical_loc=physical_loc, complexity=decisions + 1, branches=branches)


def passes_filter(m: ClassMetrics) -> bool:
    return m.physical_loc > MIN_LOC and m.complexity > MIN_CC and m.branches >= MIN_BRANCHES


# --------------------------------------------------------------------------- #
# source and test discovery
# --------------------------------------------------------------------------- #

_SKIP_DIRS = {".git", "target", "build", "out", "node_modules"}


def is_test_path(path: Path) -> bool:
    parts = [p.lower() for p in path.parts]
    return "test" in parts


def iter_source_files(root: Path) -> Iterable[Path]:
    """Production Java files under a src/main/java root."""
    for path in root.rglob("*.java"):
        if any(part in _SKIP_DIRS for part in path.parts) or is_test_path(path):
            continue
        rel = path.relative_to(root).as_posix()
        if "src/main/java" not in rel and "/main/java/" not in rel:
            continue
        if path.name in ("package-info.java", "module-info.java"):
            continue
        yield path


def build_test_index(root: Path) -> dict[str, list[Path]]:
    index: dict[str, list[Path]] = {}
    for path in root.rglob("*.java"):
        if is_test_path(path):
            index.setdefault(path.name, []).append(path)
    return index


def test_name_candidates(class_name: str) -> list[str]:
    return [f"{class_name}Test.java", f"Test{class_name}.java", f"{class_name}Tests.java",
            f"{class_name}TestCase.java", f"{class_name}IT.java"]


def find_test(class_path: Path, root: Path, test_index: dict[str, list[Path]]) -> Optional[Path]:
    """The manual test of a class: same package under src/test/java first,
    then any test-tree file with a conventional name."""
    candidates = test_name_candidates(class_path.stem)
    rel = class_path.relative_to(root).as_posix()
    if "src/main/java" in rel:
        test_dir = root / rel.rsplit("/", 1)[0].replace("src/main/java", "src/test/java")
        for cand in candidates:
            if (test_dir / cand).exists():
                return test_dir / cand
    for cand in candidates:
        if test_index.get(cand):
            return test_index[cand][0]
    return None


# --------------------------------------------------------------------------- #
# selection
# --------------------------------------------------------------------------- #

def to_fqn(class_path: str) -> str:
    i = class_path.find("src/main/java/")
    rel = class_path[i + len("src/main/java/"):] if i >= 0 else class_path
    return rel[:-5].replace("/", ".") if rel.endswith(".java") else rel.replace("/", ".")


def resolve_checkout(subject: dict, workdir: Path) -> Optional[Path]:
    target = workdir / subject["project"]
    if target.is_dir():
        return target
    url = f"https://github.com/{subject['repo']}.git"
    print(f"  cloning {url} -> {target}", flush=True)
    r = subprocess.run(["git", "clone", "--quiet", url, str(target)], capture_output=True, text=True)
    if r.returncode != 0:
        print(f"  !! clone failed: {r.stderr.strip()}", file=sys.stderr)
        return None
    return target


def select_classes(checkout: Path) -> list[dict]:
    test_index = build_test_index(checkout)
    kept = []
    for src in sorted(iter_source_files(checkout)):
        try:
            source = src.read_text(encoding="utf-8", errors="replace")
        except OSError:
            continue
        m = compute_metrics(source)
        if not passes_filter(m):
            continue
        test = find_test(src, checkout, test_index)
        if test is None:
            continue
        cp = src.relative_to(checkout).as_posix()
        kept.append({
            "class_fqn": to_fqn(cp),
            "class_path": cp,
            "manual_suite": test.stem,
            "manual_test_path": test.relative_to(checkout).as_posix(),
            "auto_suite": f"{src.stem}_ESTest",
            "loc": m.physical_loc,
            "cc": m.complexity,
            "branches": m.branches,
        })
    return kept


def main(argv: Optional[list[str]] = None) -> int:
    ap = argparse.ArgumentParser(description=__doc__,
                                 formatter_class=argparse.RawDescriptionHelpFormatter)
    ap.add_argument("--subjects", default=str(HERE / "data" / "subjects_14.json"))
    ap.add_argument("--workdir", default=str(HERE.parent / "local_workplace"),
                    help="folder holding one checkout per project (repository root/local_workplace)")
    ap.add_argument("--output", default=str(HERE / "data" / "classes_selected.json"))
    args = ap.parse_args(argv)

    subjects = json.loads(Path(args.subjects).read_text(encoding="utf-8"))
    workdir = Path(args.workdir)
    workdir.mkdir(parents=True, exist_ok=True)

    print(f"{'project':20} {'selected':>8}")
    print("-" * 29)
    projects, total = [], 0
    for s in subjects:
        checkout = resolve_checkout(s, workdir)
        if checkout is None:
            continue
        kept = select_classes(checkout)
        total += len(kept)
        print(f"{s['project']:20} {len(kept):>8}", flush=True)
        projects.append({"project": s["project"], "repo": s["repo"],
                         "n_classes": len(kept), "classes": kept})

    result = {
        "_meta": {
            "source": "select_classes.py",
            "filter": (f"physical LOC > {MIN_LOC} AND McCabe CC > {MIN_CC} "
                       f"AND branches >= {MIN_BRANCHES} AND manual test exists"),
            "n_projects": len(projects),
            "n_classes": total,
        },
        "projects": projects,
    }
    out = Path(args.output)
    out.parent.mkdir(parents=True, exist_ok=True)
    out.write_text(json.dumps(result, indent=2, ensure_ascii=False) + "\n", encoding="utf-8")
    print("-" * 29)
    print(f"selected {total} classes across {len(projects)} projects -> {out}")
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
