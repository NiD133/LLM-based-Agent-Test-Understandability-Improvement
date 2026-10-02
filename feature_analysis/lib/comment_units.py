"""
Comment UNITS, GumTree's raw verdict on them, and the two comment features.

GumTree (java-jdtc) reports comment edits per AST node, which does not match
what a reader calls "a comment":

  - every `//` line is its own node, so a rewritten three-line comment comes
    out as a mix of update + delete/insert;
  - an edit inside a Javadoc is reported on its TextElement/TagElement nodes,
    never on the Javadoc itself (0 Javadoc update-node actions in 17,190 diffs);
  - a comment inside a deleted or inserted subtree gets no action of its own.

So comments are grouped into UNITS first, and lib/comment_postprocess.py pairs
original and improved units through the code they document. This module holds
the unit definition (shared by the post-processor and by the merge stage) and
turns the post-processor's unit labels into the two per-kind features.

Unit definition
  Javadoc       one `/** ... */`
  BlockComment  one `/* ... */`
  LineComment   a maximal run of `//` comments that each sit on their OWN line
                (only whitespace before `//`) on CONSECUTIVE lines. A blank
                line or a line of code ends the run. A trailing `//` after code
                on the same line is always a unit of its own.

Sources MUST be read with newline="" (read_src): GumTree's offsets count the
\\r of CRLF files, and many original files were written on Windows. `nodes`
keep each GumTree node's span in GumTree's coordinates (UTF-16 code units).
"""

from __future__ import annotations

import json
import re
from collections import Counter
from pathlib import Path

KINDS = ("LineComment", "BlockComment", "Javadoc")
SPAN_RE = re.compile(r"\[(\d+),(\d+)\]\s*$")

# The post-processor's labels (one per unit).
#   original side: UNCHANGED | UPDATED | MOVED | CONVERTED | DELETED (+ with_code)
#   improved side: COUNTERPART | ADDED (+ with_code)
O_LABELS = ("UNCHANGED", "UPDATED", "MOVED", "CONVERTED", "DELETED")
I_LABELS = ("ADDED", "COUNTERPART")

# Reviewer-facing spelling of each kind, as in lib/feature_registry.py.
SPACED = {"LineComment": "Line Comment", "BlockComment": "Block Comment",
          "Javadoc": "Javadoc"}


def read_src(path) -> str:
    return open(path, encoding="utf-8", errors="replace", newline="").read()


# ----------------------------------------------------------------------------
# lexing and units
# ----------------------------------------------------------------------------
def lex_comments(src: str):
    """[(kind, start, end)] in Python string indices. String, char and
    text-block literals are skipped so '//' inside a string is not a comment."""
    out = []
    i, n = 0, len(src)
    while i < n:
        c = src[i]
        if src.startswith('"""', i):
            j = src.find('"""', i + 3)
            while j != -1 and src[j - 1] == "\\":
                j = src.find('"""', j + 1)
            i = n if j == -1 else j + 3
        elif c == '"' or c == "'":
            j = i + 1
            while j < n and src[j] != c:
                if src[j] == "\\":
                    j += 1
                elif src[j] == "\n":
                    break
                j += 1
            i = j + 1
        elif src.startswith("//", i):
            j = src.find("\n", i)
            j = n if j == -1 else j
            e = j - 1 if (j > i and src[j - 1] == "\r") else j
            out.append(("LineComment", i, e))
            i = j
        elif src.startswith("/*", i):
            j = src.find("*/", i + 2)
            e = n if j == -1 else j + 2
            kind = "Javadoc" if (src.startswith("/**", i) and not src.startswith("/**/", i)) else "BlockComment"
            out.append((kind, i, e))
            i = e
        else:
            i += 1
    return out


def utf16_index(src: str):
    """Python index -> UTF-16 offset (GumTree/JDT coordinates)."""
    if all(ord(ch) <= 0xFFFF for ch in src):
        return lambda i: i
    acc, run = [0] * (len(src) + 1), 0
    for k, ch in enumerate(src):
        acc[k] = run
        run += 2 if ord(ch) > 0xFFFF else 1
    acc[len(src)] = run
    return lambda i: acc[i]


def line_of(src: str, pos: int) -> int:
    return src.count("\n", 0, pos) + 1


def norm_text(kind: str, raw: str) -> str:
    """Comment text without markers / leading '*' / whitespace runs."""
    if kind == "LineComment":
        parts = [ln.strip()[2:] if ln.strip().startswith("//") else ln for ln in raw.splitlines()]
        t = " ".join(parts)
    else:
        t = raw[3:] if kind == "Javadoc" else raw[2:]
        t = t[:-2] if t.endswith("*/") else t
        t = "\n".join(re.sub(r"^\s*\*\s?", "", ln) for ln in t.splitlines())
    return re.sub(r"\s+", " ", t).strip()


def comment_units(src: str, prefix: str) -> list[dict]:
    """Group raw comments into units; ids are f"{prefix}{n}" in source order."""
    to16 = utf16_index(src)
    raw = lex_comments(src)
    enriched = []
    for kind, s, e in raw:
        ls = src.rfind("\n", 0, s) + 1
        own_line = src[ls:s].strip() == ""
        enriched.append({"kind": kind, "s": s, "e": e, "own_line": own_line,
                         "l1": line_of(src, s), "l2": line_of(src, e)})
    units: list[dict] = []
    for c in enriched:
        prev = units[-1] if units else None
        if (prev is not None and c["kind"] == "LineComment" and prev["kind"] == "LineComment"
                and c["own_line"] and prev["own_line"]
                and c["l1"] == prev["lines"][1] + 1
                and src[prev["end"]:c["s"]].strip() == ""):
            prev["end"] = c["e"]
            prev["lines"][1] = c["l2"]
            prev["nodes"].append([to16(c["s"]), to16(c["e"])])
            continue
        units.append({"kind": c["kind"], "start": c["s"], "end": c["e"],
                      "lines": [c["l1"], c["l2"]], "own_line": c["own_line"],
                      "nodes": [[to16(c["s"]), to16(c["e"])]]})
    for n, u in enumerate(units, 1):
        u["id"] = f"{prefix}{n}"
        u["text"] = src[u["start"]:u["end"]]
        u["norm"] = norm_text(u["kind"], u["text"])
    return units


# ----------------------------------------------------------------------------
# GumTree's raw verdicts
# ----------------------------------------------------------------------------
def parse_tree(tree: str):
    m = SPAN_RE.search(tree)
    kind = tree.split(":")[0].split(" [")[0].strip()
    return kind, ((int(m.group(1)), int(m.group(2))) if m else None)


def gumtree_node_verdicts(diff: dict):
    """GumTree's own verdict per comment NODE, keyed by UTF-16 span.
    Returns (o_verdict, i_verdict, o2i_match)."""
    o_v, i_v, o2i = {}, {}, {}
    for m in diff.get("matches", []) or []:
        sk, ss = parse_tree(m["src"])
        dk, ds = parse_tree(m["dest"])
        if sk in KINDS and ss and ds:
            o2i[ss] = (dk, ds)
    for a in diff.get("actions", []) or []:
        k, sp = parse_tree(a["tree"])
        if k not in KINDS or not sp:
            continue
        act = a["action"]
        if act.startswith("delete"):
            o_v[sp] = "Deleted"
        elif act.startswith("insert"):
            i_v[sp] = "Added"
        elif act.startswith("update"):
            o_v[sp] = "Updated"
        elif act.startswith("move"):
            o_v.setdefault(sp, "Moved")
    return o_v, i_v, o2i


def build_test(relative_test: str, original, improved, diff_path) -> dict:
    """Units of both sides + GumTree's raw verdict per node (`gt_nodes`) and
    the improved units its node matches point to (`gt_partners`). The
    post-processor uses the raw verdicts only as a tie-breaker."""
    osrc, isrc = read_src(original), read_src(improved)
    diff = json.loads(Path(diff_path).read_text(encoding="utf-8"))
    ou, iu = comment_units(osrc, "o"), comment_units(isrc, "i")
    o_v, i_v, o2i = gumtree_node_verdicts(diff)
    i_node2unit = {tuple(n): u["id"] for u in iu for n in u["nodes"]}
    for u in ou:
        nv = []
        partners = []
        for n in u["nodes"]:
            v = o_v.get(tuple(n))
            m = o2i.get(tuple(n))
            if v is None:
                v = "Unchanged" if m else "Unknown"
            nv.append(v)
            if m and tuple(m[1]) in i_node2unit:
                partners.append(i_node2unit[tuple(m[1])])
        u["gt_nodes"] = nv
        u["gt_partners"] = sorted(set(partners), key=lambda x: int(x[1:]))
    matched_i = {tuple(m[1]) for m in o2i.values()}
    for u in iu:
        u["gt_nodes"] = [i_v.get(tuple(n)) or ("Matched" if tuple(n) in matched_i else "Unknown")
                         for n in u["nodes"]]
    jd_internal = Counter()
    for a in diff.get("actions", []) or []:
        k, sp = parse_tree(a["tree"])
        if k in ("TextElement", "TagElement", "MemberRef", "MethodRef", "MethodRefParameter"):
            jd_internal[a["action"]] += 1
    return {"relative_test": relative_test, "original": str(original), "improved": str(improved),
            "diff": str(diff_path), "o_units": ou, "i_units": iu, "jd_internal": dict(jd_internal)}


# ----------------------------------------------------------------------------
# labels -> features
# ----------------------------------------------------------------------------
# TWO features per comment kind, split by DIRECTION:
#
#   "<Kind> Added/Updated"  the improved test holds a comment of that kind the
#                           LLM wrote or rewrote: a new comment (ADDED, also on
#                           new code), an existing one with changed text
#                           (UPDATED), one moved elsewhere (MOVED), or one
#                           converted FROM another kind (CONVERTED counts for
#                           the NEW kind — its content was not lost).
#   "<Kind> Deleted"        an original comment of that kind was removed while
#                           the code it documents is still present.
#
# A comment removed TOGETHER with its code (with_code) is not a comment edit:
# the per-case files carry their suite's fields and helper methods, and the
# LLMs remove the unused ones — in the manual test cases that alone was a
# third of the old "Line Comment Deleted". UNCHANGED produces nothing.
ADDED_UPDATED, DELETED = "Added/Updated", "Deleted"
COMMENT_FEATURES = [f"{SPACED[k]} {d}" for k in ("LineComment", "Javadoc", "BlockComment")
                    for d in (ADDED_UPDATED, DELETED)]


def comment_entries(record: dict) -> list[dict]:
    """One summary entry per unit that contributes to a feature.

    `record` is what the comments stage writes per test: {"units": {"o": [..],
    "i": [..]}, "labels": {"o": {..}, "i": {..}}}."""
    units = record.get("units") or {}
    labels = record.get("labels") or {}
    o_units = {u["id"]: u for u in units.get("o", [])}
    i_units = {u["id"]: u for u in units.get("i", [])}
    out = []

    def entry(kind, direction, side, u, label, note, partners=()):
        return {
            "type": f"{SPACED[kind]} {direction}",
            "description": f"{label} {u['id']}"
                           + (f" -> {', '.join(partners)}" if partners else "")
                           + f": {u.get('norm', '')[:160]}",
            "location": {"source": "gumtree", "side": side, "line": u["lines"][0],
                         "unit": u["id"], "label": label, "rule": note},
        }

    for uid, lab in (labels.get("o") or {}).items():
        u, L = o_units.get(uid), lab.get("label")
        if u is None:
            continue
        partners = lab.get("partners") or []
        if L in ("UPDATED", "MOVED"):
            out.append(entry(u["kind"], ADDED_UPDATED, "original", u, L, lab.get("note"), partners))
        elif L == "CONVERTED":
            for kind in sorted({i_units[p]["kind"] for p in partners if p in i_units}):
                out.append(entry(kind, ADDED_UPDATED, "original", u, L, lab.get("note"), partners))
        elif L == "DELETED" and not lab.get("with_code"):
            out.append(entry(u["kind"], DELETED, "original", u, L, lab.get("note")))
    for uid, lab in (labels.get("i") or {}).items():
        v = i_units.get(uid)
        if v is not None and lab.get("label") == "ADDED":
            out.append(entry(v["kind"], ADDED_UPDATED, "improved", v, "ADDED", lab.get("note")))
    return out
