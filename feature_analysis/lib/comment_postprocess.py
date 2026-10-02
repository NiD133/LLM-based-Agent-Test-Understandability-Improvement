"""
(4) Comment post-processor: GumTree java-jdtc comment edits -> comment-UNIT labels.

GumTree's own comment verdicts are per AST node and systematically wrong for
what a reader calls a comment (see lib/comment_units.py). This module keeps
GumTree's CODE-node matches as the backbone and re-pairs the comments at UNIT
level through their anchors -- the declaration or statement each one
documents -- so GumTree's per-node comment verdicts are only a tie-breaker.

Labels, one per unit:
  original  UNCHANGED | UPDATED | MOVED | CONVERTED | DELETED (+ with_code)
  improved  COUNTERPART | ADDED (+ with_code)
lib/comment_units.comment_entries turns them into the two features per kind.

Validated before it was adopted: on 92 stratified test pairs (886 comment
units) labelled blind against a written codebook, the per-test comment feature
set matched the labels for 96% of the pairs vs 64% for the raw GumTree
verbs; an independently written GumTree-free classifier agreed with it on
96-99.5% of all units across the 17,190 pairs.

Needs the full java-jdtc ASTs of both sides, which the stock `gumtree parse`
client cannot write in bulk (with `-o dir` it opens the directory as a file);
lib/java/BatchParse.java parses many files in ONE JVM instead. The labeller
itself is standard library only.
"""
from __future__ import annotations

import bisect
import json
import os
import re
import subprocess
import sys
from collections import Counter
from difflib import SequenceMatcher
from pathlib import Path

from .comment_units import build_test, read_src

JAVA_SRC = Path(__file__).resolve().parent / "java" / "BatchParse.java"

# ----------------------------------------------------------------------------
# thresholds (each explained in the comment next to it)
# ----------------------------------------------------------------------------
MOVE_RATIO = 0.85          # char-level similarity for "same or nearly the same text"
MOVE_MIN_TOKENS = 1        # a MOVED comment must carry at least one word
MOVE_SHORT = 3             # texts with fewer words may only move within the corresponding scope
CONV_CONTAIN = 0.6         # share of O-words found in a different-kind I-unit
CONV_MIN_TOKENS = 3        # ... and O must have at least this many words
MERGE_CONTAIN = 0.6        # share of words for merge / split partners
MERGE_MIN_TOKENS = 3       # ... and the merged/split part must have this many words
CARRY = 0.7                # MOVED with edits: share of the smaller comment's words found in the other
CARRY_GT = 0.5             # ... when GumTree itself matched the two comments
CARRY_FLOOR = 0.3          # ... and share of the larger comment's words
CARRY_MIN_COMMON = 3       # ... and at least this many shared words
WIDE_GAP = 2               # image interval wider than this = weak slot
SOFT_VOTE = 0.5            # share of mapped descendants that must land in the target
DESC_KEEP = 0.25           # share of descendants mapped -> code "still exists"
DECL_ALIGN = 0.7           # token similarity to pair two declarations GumTree left unmatched
FIELD_KEEP = 0.3           # renamed field pairs sharing less than this (type + initializer) are unpaired
METHOD_REPAIR_MIN = 0.45   # renamed methods: minimum body token Dice to (re)pair them
METHOD_REPAIR_MARGIN = 0.1 # ... and how much better than GumTree's pair it must be
METHOD_GT_BONUS = 0.1      # ... GumTree's own pair gets this bonus in the greedy order
STMT_ALIGN = 0.4           # ... two statements / other nodes
TEXT_SURVIVE = 0.7         # token Dice with some I statement -> the O statement still exists
TEXT_ANCHOR = 0.85         # token Dice of two anchor statements -> same statement (GumTree-independent)
TEXT_ANCHOR_SAME = 0.6     # ... enough when the two comments are identical

COMMENT = frozenset(("LineComment", "BlockComment", "Javadoc"))
DECL = frozenset(("MethodDeclaration", "FieldDeclaration", "TypeDeclaration", "EnumDeclaration",
                  "EnumConstantDeclaration", "AnnotationTypeDeclaration",
                  "AnnotationTypeMemberDeclaration", "Initializer", "RecordDeclaration",
                  "PackageDeclaration"))
MEMBER_DECL = DECL - {"PackageDeclaration"}
SCOPE = MEMBER_DECL | {"LambdaExpression"}
SPAN_RE = re.compile(r"\[(\d+),(\d+)\]\s*$")
WORD_RE = re.compile(r"[A-Za-z0-9_]+")


# ============================================================================
# trees
# ============================================================================
class Tree:
    """GumTree tree as preorder arrays.  Node 0 is the CompilationUnit."""

    def __init__(self, root, line_starts):
        T, L, P, E, PAR, CH = [], [], [], [], [], []
        stack = [(root, -1)]
        while stack:
            n, par = stack.pop()
            idx = len(T)
            T.append(n[0]); L.append(n[1] or ""); P.append(n[2]); E.append(n[2] + n[3])
            PAR.append(par); CH.append([])
            if par >= 0:
                CH[par].append(idx)
            for c in reversed(n[4]):
                stack.append((c, idx))
        size = [1] * len(T)
        for i in range(len(T) - 1, 0, -1):
            size[PAR[i]] += size[i]
        key = {}
        for i in range(len(T)):
            key.setdefault((T[i], P[i], E[i]), []).append(i)
        self.type, self.label, self.pos, self.end = T, L, P, E
        self.parent, self.children, self.size, self.key = PAR, CH, size, key
        self.line_starts = line_starts
        self._code = {}

    def __len__(self):
        return len(self.type)

    def find(self, typ, s, e, label=None):
        c = self.key.get((typ, s, e))
        if not c:
            return None
        if len(c) > 1 and label is not None:
            for x in c:
                if self.label[x] == label:
                    return x
        return c[0]

    def inside(self, a, b):
        """b is a or a descendant of a"""
        return a <= b < a + self.size[a]

    def ancestors(self, x):
        p = self.parent[x]
        while p >= 0:
            yield p
            p = self.parent[p]

    def code_children(self, x):
        r = self._code.get(x)
        if r is None:
            r = [c for c in self.children[x] if self.type[c] not in COMMENT]
            self._code[x] = r
        return r

    def line(self, off):
        return bisect.bisect_right(self.line_starts, off)

    def up_to(self, x, types):
        """x itself or its nearest ancestor whose type is in `types`"""
        while x is not None and x >= 0:
            if self.type[x] in types:
                return x
            x = self.parent[x]
        return None

    def smallest_container(self, s, e):
        x = 0
        while True:
            nxt = None
            for c in self.children[x]:
                if self.pos[c] <= s and self.end[c] >= e and self.type[c] not in COMMENT:
                    nxt = c
                    break
            if nxt is None:
                return x
            x = nxt

    def name_of(self, x):
        for c in self.children[x]:
            if self.type[c] == "SimpleName":
                return self.label[c]
        return None


def line_starts16(src: str):
    """UTF-16 offsets of line starts (src read with newline='')."""
    if all(ord(ch) <= 0xFFFF for ch in src):
        starts = [0]
        k = src.find("\n")
        while k != -1:
            starts.append(k + 1)
            k = src.find("\n", k + 1)
        return starts
    starts, off = [0], 0
    for ch in src:
        off += 2 if ord(ch) > 0xFFFF else 1
        if ch == "\n":
            starts.append(off)
    return starts


def parse_tree_str(tree: str):
    """'Type: label [s,e]' -> (type, label, s, e)"""
    m = SPAN_RE.search(tree)
    head = tree[:m.start()].rstrip() if m else tree
    if ": " in head:
        typ, lab = head.split(": ", 1)
    else:
        typ, lab = head, None
    typ = typ.split(" [")[0].strip()
    return typ, lab, (int(m.group(1)) if m else None), (int(m.group(2)) if m else None)


# ============================================================================
# ASTs (GumTree java-jdtc via the BatchParse helper)
# ============================================================================
def parse_asts(jobs, java_home: str, gumtree_jar, work_dir, nproc: int = 8) -> int:
    """Parse every (source, out_json) pair in `jobs` into compact JSON trees
    ([type, label, pos, length, children]) with GumTree's java-jdtc generator
    -- the generator the diff used, so every span in the diff resolves.
    `nproc` JVMs run side by side. Returns the number of files that failed."""
    if not jobs:
        return 0
    work_dir = Path(work_dir)
    work_dir.mkdir(parents=True, exist_ok=True)
    java_bin = Path(java_home) / "bin" if java_home else None
    javac = str(java_bin / "javac") if java_bin else "javac"
    java = str(java_bin / "java") if java_bin else "java"
    cls = work_dir / "classes"
    cls.mkdir(exist_ok=True)
    subprocess.run([javac, "-proc:none", "-cp", str(gumtree_jar), "-d", str(cls), str(JAVA_SRC)],
                   check=True)
    procs = []
    for k in range(max(1, nproc)):
        chunk = jobs[k::max(1, nproc)]
        if not chunk:
            continue
        lf = work_dir / f"parse_list_{k}.txt"
        lf.write_text("".join(f"{s}\t{o}\n" for s, o in chunk), encoding="utf-8")
        procs.append(subprocess.Popen([java, "-Xss8m", "-cp", f"{gumtree_jar}{os.pathsep}{cls}",
                                       "BatchParse", str(lf)], stderr=subprocess.PIPE, text=True))
    failed = 0
    for p in procs:
        _, err = p.communicate()
        failed += sum(1 for line in err.splitlines() if line.startswith("FAIL\t"))
        if p.returncode != 0:
            print(err[-2000:], file=sys.stderr)
            failed += 1
    return failed


def load_tree(path, src):
    return Tree(json.loads(Path(path).read_text()), line_starts16(src))


# ============================================================================
# node mapping: GumTree matches + recovery
# ============================================================================
ALIGN_TYPES = frozenset(("CompilationUnit", "TypeDeclaration", "EnumDeclaration", "RecordDeclaration",
                         "AnnotationTypeDeclaration", "AnonymousClassDeclaration", "MethodDeclaration",
                         "Initializer", "Block", "TryStatement", "CatchClause", "IfStatement", "ForStatement",
                         "EnhancedForStatement", "WhileStatement", "DoStatement", "SwitchStatement",
                         "SynchronizedStatement", "LabeledStatement", "LambdaExpression", "SwitchCase",
                         "ExpressionStatement", "VariableDeclarationStatement", "MethodInvocation",
                         "METHOD_INVOCATION_ARGUMENTS", "ClassInstanceCreation", "VariableDeclarationFragment",
                         "FieldDeclaration", "ReturnStatement", "ThrowStatement", "TryStatement"))


TEST_ANNOTATIONS = frozenset(("Test", "ParameterizedTest", "RetryingTest", "RepeatedTest", "TestFactory",
                              "TestTemplate"))
LITERALS = frozenset(("StringLiteral", "NumberLiteral", "CharacterLiteral", "TextBlock"))
STRUCTURAL = frozenset(("Block", "SimpleName", "SimpleType", "PrimitiveType", "ParameterizedType",
                        "ArrayType", "QualifiedType", "TYPE_DECLARATION_KIND", "CatchClause",
                        "METHOD_INVOCATION_ARGUMENTS", "METHOD_INVOCATION_RECEIVER", "AnonymousClassDeclaration",
                        "ArrayInitializer", "SingleVariableDeclaration", "Modifier", "TypeParameter"))


class Mapping:
    """O->I node mapping.
    f/b    : strict one-to-one.  GumTree's matches, repaired so that member
             declarations with the same name/arity on both sides correspond
             (GumTree sometimes cross-matches similar methods), plus
             declarations recovered by name and a content-aware alignment of
             still-unmatched children inside corresponding containers.
    soft_f : many-to-one "restructured into" links for unmatched O code nodes
             whose mapped descendants mostly land inside one I sibling."""

    def __init__(self, O: Tree, I: Tree, diff: dict):
        self.O, self.I = O, I
        f, b = {}, {}
        for m in diff.get("matches", []) or []:
            st, sl, ss, se = parse_tree_str(m["src"])
            dt, dl, ds, de = parse_tree_str(m["dest"])
            if ss is None or ds is None:
                continue
            a = O.find(st, ss, se, sl)
            c = I.find(dt, ds, de, dl)
            if a is not None and c is not None:
                f[a] = c
                b[c] = a
        self.gt_f = dict(f)
        self.f, self.b = f, b
        self.recovered = set()
        self.soft_f, self.soft_b = {}, {}
        self.rename = {}
        for a, c in f.items():
            if O.type[a] == "SimpleName" and O.label[a] != I.label[c]:
                self.rename[O.label[a]] = I.label[c]
        self._tok_cache = {}
        self._idf_w = None
        self._idf_default = 1.0
        self._calls_cache = {}
        self._stmt_index = None
        self._text_cache = {}
        self._decl_repair()
        self._drop_crossing()
        if 0 not in f and 0 not in b:
            f[0] = 0
            b[0] = 0
        if f.get(0) == 0:
            self._align(0, 0)
        for x in sorted(self.remapped):
            if x in f:
                self._align(x, f[x])
        self._match_literals()
        self._recover_soft()

    def _match_literals(self):
        """identical, distinctive literals left unmatched on both sides (e.g.
        test data moved from one big call into many small ones)"""
        O, I, f, b = self.O, self.I, self.f, self.b
        def cands(T, m):
            out = {}
            for x in range(len(T)):
                if x in m or T.type[x] not in LITERALS:
                    continue
                lab = T.label[x]
                if len(lab) < 4 or (T.type[x] == "NumberLiteral" and len(lab) < 5):
                    continue
                out.setdefault(lab, []).append(x)
            return out
        cO, cI = cands(O, f), cands(I, b)
        for lab, xs in cO.items():
            ys = cI.get(lab)
            if ys and len(xs) == 1 and len(ys) == 1:
                f[xs[0]] = ys[0]
                b[ys[0]] = xs[0]
                self.recovered.add(xs[0])

    # ---- member declarations: same name/arity on both sides -------------------
    def _decl_key(self, T, x, arity=True):
        t = T.type[x]
        if t == "MethodDeclaration":
            if not arity:
                return (t, T.name_of(x))
            nparams = sum(1 for c in T.children[x] if T.type[c] == "SingleVariableDeclaration")
            return (t, T.name_of(x), nparams)
        if t == "FieldDeclaration":
            names = []
            for c in T.children[x]:
                if T.type[c] == "VariableDeclarationFragment":
                    names.append(T.name_of(c))
            return (t, tuple(names))
        if t == "Initializer":
            return (t, any(T.type[c] == "Modifier" and T.label[c] == "static" for c in T.children[x]))
        return (t, T.name_of(x))

    def _scope_key(self, T, x):
        """names of the enclosing member declarations (so that e.g. two inner
        classes' close() methods are not confused)"""
        out = []
        for a in T.ancestors(x):
            if T.type[a] in MEMBER_DECL:
                out.append(self._decl_key(T, a, False))
        return tuple(out)

    def _unmatch_foreign_fields(self):
        """GumTree pairs `private static final A X = ...` with `private static
        final B Y = ...` on structure alone.  A pair with different names AND
        (almost) nothing else in common is two different constants."""
        O, I, f, b = self.O, self.I, self.f, self.b
        def raw(T, x):
            c = Counter()
            for d in range(x + 1, x + T.size[x]):
                if T.label[d] and T.type[d] not in ("Modifier",) and T.type[d] not in COMMENT \
                        and not (T.type[d] == "SimpleName" and T.type[T.parent[d]] == "VariableDeclarationFragment"):
                    c[T.label[d]] += 1
            return c
        for x in range(len(O)):
            if O.type[x] != "FieldDeclaration" or x not in f:
                continue
            y = f[x]
            if I.type[y] != "FieldDeclaration":
                continue
            if set(self._decl_key(O, x)[1]) & set(self._decl_key(I, y)[1]):
                continue
            a, c = raw(O, x), raw(I, y)
            n, m = sum(a.values()), sum(c.values())
            d = 2.0 * sum((a & c).values()) / (n + m) if n + m else 0.0
            if d < FIELD_KEEP:
                for z in range(x, x + O.size[x]):
                    t = f.get(z)
                    if t is not None and I.inside(y, t):
                        del f[z]
                        b.pop(t, None)

    def _decl_repair(self):
        """member declarations whose name (or, for overloads, name+arity) is
        unique on both sides are paired by name, overriding GumTree"""
        O, I, f, b = self.O, self.I, self.f, self.b
        self.remapped = set()
        self._unmatch_foreign_fields()
        for arity in (False, True):
            kO = {x: (self._decl_key(O, x, arity), self._scope_key(O, x))
                  for x in range(len(O)) if O.type[x] in MEMBER_DECL}
            kI = {y: (self._decl_key(I, y, arity), self._scope_key(I, y))
                  for y in range(len(I)) if I.type[y] in MEMBER_DECL}
            cO, cI = Counter(kO.values()), Counter(kI.values())
            byI = {k: y for y, k in kI.items() if cI[k] == 1}
            for x, k in kO.items():            # preorder: outer declarations first
                if cO[k] != 1 or k not in byI or k[0][1] is None or k[0][1] == ():
                    continue
                y = byI[k]
                if f.get(x) == y:
                    continue
                if x in self.remapped or y in b and b[y] in self.remapped:
                    continue
                if x in f:
                    b.pop(f.pop(x), None)
                if y in b:
                    f.pop(b.pop(y), None)
                f[x] = y
                b[y] = x
                self.remapped.add(x)
                self.recovered.add(x)
        self._repair_renamed_methods()
        # declarations left unmatched: same name among the unmatched ones
        uO, uI = {}, {}
        for x in range(len(O)):
            if O.type[x] in MEMBER_DECL and x not in f:
                uO.setdefault(self._decl_key(O, x, False), []).append(x)
        for y in range(len(I)):
            if I.type[y] in MEMBER_DECL and y not in b:
                uI.setdefault(self._decl_key(I, y, False), []).append(y)
        for k, xs in uO.items():
            ys = uI.get(k, [])
            if len(xs) == 1 and len(ys) == 1 and k[1] not in (None, ()):
                f[xs[0]] = ys[0]
                b[ys[0]] = xs[0]
                self.remapped.add(xs[0])
                self.recovered.add(xs[0])
        # the one remaining test method on each side (renamed and rewritten
        # beyond GumTree's reach)
        tO = [x for x in range(len(O)) if O.type[x] == "MethodDeclaration" and x not in f and self._is_test(O, x)]
        tI = [y for y in range(len(I)) if I.type[y] == "MethodDeclaration" and self._is_test(I, y)
              and (y not in b or not self._is_test(O, b[y]))]
        if len(tO) == 1 and len(tI) == 1:
            x, y = tO[0], tI[0]
            if y in b:
                f.pop(b.pop(y), None)      # was paired with a helper (inlined into the test)
            f[x] = y
            b[y] = x
            self.remapped.add(x)
            self.recovered.add(x)

    def _repair_renamed_methods(self):
        """Renamed methods (e.g. EvoSuite's test07 -> a descriptive name,
        often also reordered): GumTree sometimes pairs look-alike tests
        wrongly.  Re-pair methods that no name identity locks, greedily by
        body token similarity, keeping GumTree's pair unless another one is
        clearly more similar."""
        O, I, f, b = self.O, self.I, self.f, self.b
        oname = Counter(O.name_of(x) for x in range(len(O)) if O.type[x] == "MethodDeclaration")
        iname = Counter(I.name_of(y) for y in range(len(I)) if I.type[y] == "MethodDeclaration")
        xs = [x for x in range(len(O)) if O.type[x] == "MethodDeclaration" and not iname[O.name_of(x)]]
        ys = [y for y in range(len(I)) if I.type[y] == "MethodDeclaration" and not oname[I.name_of(y)]]
        if len(xs) < 2 or len(ys) < 2 or len(xs) * len(ys) > 40000:
            return
        cls = {}
        for x in xs:
            cls[x] = f.get(O.parent[x])
        sim = {}
        for x in xs:
            for y in ys:
                if cls[x] is not None and I.parent[y] != cls[x]:
                    continue
                sim[(x, y)] = self.dice(x, y)
        pairs = sorted(sim.items(), key=lambda kv: -(kv[1] + (METHOD_GT_BONUS if f.get(kv[0][0]) == kv[0][1] else 0)))
        used_x, used_y, new = set(), set(), {}
        for (x, y), d in pairs:
            if x in used_x or y in used_y or d < METHOD_REPAIR_MIN:
                continue
            used_x.add(x)
            used_y.add(y)
            new[x] = y
        for x, y in new.items():
            old = f.get(x)
            if old == y:
                continue
            d_old = sim.get((x, old), 0.0) if old is not None else 0.0
            if sim[(x, y)] < d_old + METHOD_REPAIR_MARGIN:
                continue
            if x in f:
                b.pop(f.pop(x), None)
            if y in b:
                f.pop(b.pop(y), None)
            f[x] = y
            b[y] = x
            self.remapped.add(x)
            self.recovered.add(x)

    @staticmethod
    def _is_test(T, x):
        for c in T.children[x]:
            if T.type[c] in ("MarkerAnnotation", "NormalAnnotation", "SingleMemberAnnotation"):
                for g in T.children[c]:
                    if T.type[g] in ("SimpleName", "QualifiedName") and T.label[g].split(".")[-1] in TEST_ANNOTATIONS:
                        return True
        return False

    def _drop_crossing(self):
        """drop matches of nodes whose enclosing member declarations are mapped
        to OTHER declarations (GumTree cross-matches between two methods that
        both still exist)"""
        O, I, f, b = self.O, self.I, self.f, self.b
        encO = self._enclosing_decls(O)
        encI = self._enclosing_decls(I)
        drop = []
        for a, c in f.items():
            if O.type[a] in MEMBER_DECL:
                continue
            X, Y = encO[a], encI[c]
            if X is None or Y is None:
                continue
            fx = f.get(X)
            if fx == Y:
                continue
            if O.parent[a] == X or I.parent[c] == Y:
                drop.append(a)      # a body / name / parameter of non-corresponding declarations
                continue
            bx = b.get(Y)
            if fx is None:
                # X has no counterpart: keep its code's matches only if that
                # code was inlined where X was called (or moved to new code)
                if bx is not None and bx != X and not self._calls(bx, X):
                    drop.append(a)
                continue
            if bx is not None and bx != X:
                drop.append(a)      # both declarations have other counterparts
        for a in drop:
            b.pop(f.pop(a), None)

    def _calls(self, caller, callee):
        """does O declaration `caller` mention the name of O declaration `callee`"""
        O = self.O
        key = (caller, callee)
        r = self._calls_cache.get(key)
        if r is None:
            name = O.name_of(callee) if O.type[callee] != "FieldDeclaration" else None
            if O.type[callee] == "FieldDeclaration":
                names = {O.name_of(c) for c in O.children[callee] if O.type[c] == "VariableDeclarationFragment"}
            else:
                names = {name}
            r = any(O.type[d] == "SimpleName" and O.label[d] in names
                    for d in range(caller + 1, caller + O.size[caller]))
            self._calls_cache[key] = r
        return r

    @staticmethod
    def _enclosing_decls(T):
        enc = [None] * len(T)
        for x in range(1, len(T)):
            p = T.parent[x]
            enc[x] = p if T.type[p] in MEMBER_DECL else enc[p]
        return enc

    # ---- content-aware alignment of unmatched children -----------------------
    def _tokens(self, T, x, side):
        key = (side, x)
        r = self._tok_cache.get(key)
        if r is None:
            r = Counter()
            for d in range(x, x + T.size[x]):
                lab = T.label[d]
                if lab and T.type[d] not in COMMENT:
                    if side == "o":
                        lab = self.rename.get(lab, lab)
                    r[lab] += 1
            self._tok_cache[key] = r
        return r

    def _idf(self):
        """inverse document frequency of tokens over the statements of both
        files: 'final', 'assertEquals', 'expected' ... weigh little"""
        if self._idf_w is None:
            import math
            df, n = Counter(), 0
            for T, side in ((self.O, "o"), (self.I, "i")):
                for x in range(1, len(T)):
                    if T.type[T.parent[x]] in ("Block", "ArrayInitializer", "METHOD_INVOCATION_ARGUMENTS") \
                            and T.type[x] not in COMMENT:
                        n += 1
                        df.update(self._tokens(T, x, side).keys())
            n = max(n, 2)
            self._idf_w = {t: math.log(1.0 + n / d) for t, d in df.items()}
            self._idf_default = math.log(1.0 + n)
        return self._idf_w

    def _sim(self, a, c):
        """IDF-weighted token Dice of two nodes"""
        O, I = self.O, self.I
        ta, tc = self._tokens(O, a, "o"), self._tokens(I, c, "i")
        same = O.type[a] == I.type[c]
        if not ta and not tc:
            return 0.5 if same else 0.0
        if not ta or not tc:
            return 0.0
        w = self._idf()
        dflt = self._idf_default
        wa = sum(k * w.get(t, dflt) for t, k in ta.items())
        wc = sum(k * w.get(t, dflt) for t, k in tc.items())
        common = sum(k * w.get(t, dflt) for t, k in (ta & tc).items())
        return 2.0 * common / (wa + wc)

    def _align(self, x, y, depth=0):
        """align the unmatched code children of corresponding x (O) and y (I)
        between their already-mapped siblings (monotone, max total similarity),
        then recurse into corresponding containers."""
        if depth > 60:
            return
        O, I, f, b = self.O, self.I, self.f, self.b
        A = [c for c in O.children[x] if O.type[c] not in COMMENT and O.type[c] != "Javadoc"]
        B = [c for c in I.children[y] if I.type[c] not in COMMENT]
        if A and B:
            posB = {c: j for j, c in enumerate(B)}
            anchors = [(i, posB[f[a]]) for i, a in enumerate(A) if a in f and f[a] in posB]
            # longest increasing chain of anchors
            anchors = _lis(anchors)
            bounds = [(-1, -1)] + anchors + [(len(A), len(B))]
            for (i0, j0), (i1, j1) in zip(bounds, bounds[1:]):
                segA = [a for a in A[i0 + 1:i1] if a not in f]
                segB = [c for c in B[j0 + 1:j1] if c not in b]
                if not segA or not segB:
                    continue
                # structural parts first: a type that occurs once on each side
                tA = Counter(O.type[a] for a in segA)
                tB = Counter(I.type[c] for c in segB)
                for a in segA:
                    ty = O.type[a]
                    if ty in STRUCTURAL and tA[ty] == 1 and tB[ty] == 1:
                        c = next(c for c in segB if I.type[c] == ty)
                        f[a] = c
                        b[c] = a
                        self.recovered.add(a)
                segA = [a for a in segA if a not in f]
                segB = [c for c in segB if c not in b]
                if segA and segB:
                    for a, c in self._dp(segA, segB):
                        f[a] = c
                        b[c] = a
                        self.recovered.add(a)
        # recurse into corresponding children
        for a in A:
            c = f.get(a)
            if c is not None and I.parent[c] == y and O.type[a] == I.type[c] and O.type[a] in ALIGN_TYPES:
                self._align(a, c, depth + 1)

    def _dp(self, A, B, tau=STMT_ALIGN):
        n, m = len(A), len(B)
        if n * m > 40000:
            return []
        W = [[0.0] * m for _ in range(n)]
        O, I = self.O, self.I
        for i in range(n):
            for j in range(m):
                same = O.type[A[i]] == I.type[B[j]]
                if O.type[A[i]] in MEMBER_DECL or I.type[B[j]] in MEMBER_DECL:
                    if not same:
                        continue
                    s = self._sim(A[i], B[j])
                    W[i][j] = s + 0.1 if s >= DECL_ALIGN else 0.0
                else:
                    s = self._sim(A[i], B[j])
                    # the same node type is a tie-breaker, never a reason to pair
                    W[i][j] = s + (0.1 if same else 0.0) if s >= tau else 0.0
        D = [[0.0] * (m + 1) for _ in range(n + 1)]
        for i in range(n - 1, -1, -1):
            for j in range(m - 1, -1, -1):
                best = max(D[i + 1][j], D[i][j + 1])
                if W[i][j] > 0:
                    best = max(best, W[i][j] + D[i + 1][j + 1])
                D[i][j] = best
        out, i, j = [], 0, 0
        while i < n and j < m:
            if W[i][j] > 0 and abs(D[i][j] - (W[i][j] + D[i + 1][j + 1])) < 1e-9:
                out.append((A[i], B[j]))
                i += 1
                j += 1
            elif D[i + 1][j] >= D[i][j + 1]:
                i += 1
            else:
                j += 1
        return out

    # ---- restructured code: soft links by descendant votes ------------------
    def _recover_soft(self):
        O, I, f = self.O, self.I, self.f
        for x in range(1, len(O)):
            if x in f or O.type[x] in COMMENT or O.type[x] in MEMBER_DECL:
                continue
            px = O.parent[x]
            py = f.get(px, self.soft_f.get(px))
            if py is None:
                continue
            votes = Counter()
            total = 0
            for d in range(x + 1, x + O.size[x]):
                t = f.get(d)
                if t is None:
                    continue
                total += 1
                c = t
                while c >= 0 and I.parent[c] != py:
                    c = I.parent[c]
                if c >= 0 and I.type[c] not in COMMENT:
                    votes[c] += 1
            if not votes:
                continue
            c, n = votes.most_common(1)[0]
            ok = n >= 2 and n >= SOFT_VOTE * total
            if ok:
                self.soft_f[x] = c
                self.soft_b.setdefault(c, x)

    # ---- queries ------------------------------------------------------------
    def fwd(self, x):
        if x is None:
            return None
        y = self.f.get(x)
        return y if y is not None else self.soft_f.get(x)

    def bwd(self, y):
        if y is None:
            return None
        x = self.b.get(y)
        return x if x is not None else self.soft_b.get(y)

    def o_survives(self, x):
        """O code node x still exists in I (matched, restructured, or a good
        part of its content mapped).  Declarations must be matched themselves."""
        if x in self.f or x in self.soft_f:
            return True
        O = self.O
        if O.type[x] in MEMBER_DECL:
            return False
        n = O.size[x]
        if n <= 1:
            return False
        mapped = sum(1 for d in range(x + 1, x + n) if d in self.f)
        return mapped >= max(2, DESC_KEEP * (n - 1))

    def _index(self):
        if self._stmt_index is None:
            I = self.I
            idx = []
            for y in range(1, len(I)):
                if I.type[I.parent[y]] in ("Block", "SwitchStatement", "ArrayInitializer",
                                           "METHOD_INVOCATION_ARGUMENTS") and I.type[y] not in COMMENT:
                    ty = self._tokens(I, y, "i")
                    idx.append((y, sum(ty.values()), ty))
            self._stmt_index = idx
        return self._stmt_index

    def text_match(self, x):
        """(best I statement, Dice, unique?) for O node x by token Dice"""
        r = self._text_cache.get(x)
        if r is not None:
            return r
        tx = self._tokens(self.O, x, "o")
        n = sum(tx.values())
        best, bd, second = None, 0.0, 0.0
        if n >= 3:
            for y, m, ty in self._index():
                if m == 0 or 2 * min(n, m) < TEXT_SURVIVE * (n + m):
                    continue
                d = 2.0 * sum((tx & ty).values()) / (n + m)
                if d > bd:
                    best, bd, second = y, d, bd
                elif d > second:
                    second = d
        r = (best, bd, bd - second > 0.05)
        self._text_cache[x] = r
        return r

    def dice(self, x, y):
        """token Dice of O node x and I node y (O names renamed as GumTree saw)"""
        tx, ty = self._tokens(self.O, x, "o"), self._tokens(self.I, y, "i")
        n, m = sum(tx.values()), sum(ty.values())
        if n == 0 or m == 0:
            return 0.0
        return 2.0 * sum((tx & ty).values()) / (n + m)

    def o_text_survives(self, x):
        """an I statement / element with (nearly) the same tokens exists
        anywhere in I (code moved or restructured beyond GumTree's reach)"""
        return self.text_match(x)[1] >= TEXT_SURVIVE

    def text_anchor(self, x):
        """the unique I statement textually matching O statement x"""
        y, d, uniq = self.text_match(x)
        if y is not None and d >= TEXT_SURVIVE and uniq and y not in self.b:
            return y
        return None

    def i_existed(self, y):
        """I code node y already existed in O"""
        if y in self.b or y in self.soft_b:
            return True
        I = self.I
        if I.type[y] in MEMBER_DECL:
            return False
        n = I.size[y]
        if n <= 1:
            return False
        mapped = sum(1 for d in range(y + 1, y + n) if d in self.b)
        return mapped >= max(2, DESC_KEEP * (n - 1))


def _lis(pairs):
    """longest chain of pairs increasing in both coordinates (pairs sorted by i)"""
    if not pairs:
        return []
    n = len(pairs)
    best = [1] * n
    prev = [-1] * n
    for k in range(n):
        for j in range(k):
            if pairs[j][1] < pairs[k][1] and best[j] + 1 > best[k]:
                best[k] = best[j] + 1
                prev[k] = j
    k = max(range(n), key=lambda z: best[z])
    out = []
    while k != -1:
        out.append(pairs[k])
        k = prev[k]
    return out[::-1]


# ============================================================================
# units and their anchors
# ============================================================================
URL_RE = re.compile(r"(?:https?|ftp)://[^\s)>\]}\"']+")


def words(s):
    """lower-cased words; a URL counts as ONE word (two different links to
    the same site must not look like shared text)"""
    out = []
    pos = 0
    for m in URL_RE.finditer(s or ""):
        out.extend(w.lower() for w in WORD_RE.findall(s[pos:m.start()]))
        out.append(m.group(0).rstrip(".,;:").lower())
        pos = m.end()
    out.extend(w.lower() for w in WORD_RE.findall((s or "")[pos:]))
    return out


def looks_like_code(norm: str) -> bool:
    s = norm.strip()
    if not s:
        return False
    if s.endswith(";") or s.endswith("{") or s.endswith("}") or s.startswith("@"):
        return True
    if re.match(r"^(assert\w*|fail|verify\w*|return|if|for|while|try|new|final)\b.*[(;]", s):
        return True
    return False


def is_separator(norm: str) -> bool:
    return not WORD_RE.search(norm or "")


class U:
    __slots__ = ("id", "kind", "norm", "own_line", "start", "end", "nodes", "C", "P", "N", "gap", "section",
                 "anchor", "doc_target", "scope", "gt", "gt_partners", "words", "raw", "lines", "side")

    def __repr__(self):
        return f"<{self.id} {self.kind} {self.anchor}>"


def attach_units(T: Tree, units, side):
    out = []
    for u in units:
        a = U()
        a.id, a.kind, a.norm, a.own_line = u["id"], u["kind"], u["norm"], u["own_line"]
        a.lines, a.side = u["lines"], side
        a.raw = u["text"]
        a.gt = u.get("gt_nodes") or []
        a.gt_partners = u.get("gt_partners") or []
        a.words = words(u["norm"])
        a.start, a.end = u["nodes"][0][0], u["nodes"][-1][1]
        a.nodes = [T.find(u["kind"], s, e) for s, e in u["nodes"]]
        x0 = a.nodes[0]
        C = T.parent[x0] if x0 is not None else T.smallest_container(a.start, a.end)
        a.C = C
        code = T.code_children(C)
        gap = 0
        while gap < len(code) and T.end[code[gap]] <= a.start:
            gap += 1
        a.gap = gap
        a.P = code[gap - 1] if gap > 0 else None
        a.N = code[gap] if gap < len(code) else None
        kids = T.children[C]
        jd_attached = (a.kind == "Javadoc" and T.type[C] in DECL and x0 is not None and kids and kids[0] == x0)
        if jd_attached:
            a.anchor = ("decl", C)
        elif a.own_line:
            a.anchor = ("node", a.N) if a.N is not None else ("end", C)
        else:
            if a.P is not None and T.line(max(T.end[a.P] - 1, T.pos[a.P])) == T.line(a.start):
                a.anchor = ("node", a.P)
            else:
                a.anchor = ("start", C)
        dt = None
        if jd_attached:
            dt = C
        elif a.anchor[0] == "node" and T.type[a.anchor[1]] in MEMBER_DECL:
            dt = a.anchor[1]
        a.doc_target = dt
        a.scope = T.up_to(C if not jd_attached else T.parent[C], SCOPE)
        out.append(a)
    # section of an own-line comment: the code siblings it heads, up to the
    # next comment of the same container
    by_c = {}
    for a in out:
        by_c.setdefault(a.C, []).append(a)
    for C, lst in by_c.items():
        code = T.code_children(C)
        lst.sort(key=lambda z: z.start)
        for k, a in enumerate(lst):
            if a.anchor[0] != "node" or not a.own_line or T.type[C] in CLASS_BODIES:
                a.section = []
                continue
            g_end = len(code)
            for b in lst[k + 1:]:
                if b.gap > a.gap:
                    g_end = b.gap
                    break
            a.section = code[a.gap:g_end]
    return out


# ============================================================================
# correspondence of places
# ============================================================================
CLASS_BODIES = frozenset(("TypeDeclaration", "AnonymousClassDeclaration", "EnumDeclaration", "CompilationUnit",
                          "RecordDeclaration", "AnnotationTypeDeclaration"))
STMT_PARENTS = frozenset(("Block", "SwitchStatement", "TypeDeclaration", "AnonymousClassDeclaration",
                          "EnumDeclaration", "CompilationUnit", "RecordDeclaration", "SwitchCase"))


class Places:
    """Where does an O-unit's place land in I?"""

    def __init__(self, M: Mapping):
        self.M, self.O, self.I = M, M.O, M.I
        self._img = {}
        self._amap = {}
        self._region = {}

    # ---- anchors -------------------------------------------------------------
    def stmt_level(self, y):
        """I node y climbed to statement / member level"""
        I = self.I
        c = y
        while c > 0 and I.type[I.parent[c]] not in STMT_PARENTS:
            c = I.parent[c]
        return c if c > 0 and I.type[c] not in COMMENT else None

    def amap(self, x):
        """I counterpart of an O anchor node: its mapping, else the I item
        (statement / array element / member) that received most of its
        mapped content"""
        if x is None:
            return None
        y = self.M.fwd(x)
        if y is not None:
            return y
        r = self._amap.get(x, -1)
        if r != -1:
            return r
        O, I, f = self.O, self.I, self.M.f
        votes, total, lit = Counter(), 0, None
        for d in range(x + 1, x + O.size[x]):
            t = f.get(d)
            if t is None:
                continue
            total += 1
            if O.type[d] in LITERALS and len(O.label[d]) >= 4:
                lit = t
            c = self.stmt_level(t)
            if c is not None:
                votes[c] += 1
        r = None
        if votes:
            c, n = votes.most_common(1)[0]
            if (n >= 2 and n >= 0.5 * total) or (total == 1 and lit is not None):
                r = c
        self._amap[x] = r
        return r

    def same_gap(self, u, v):
        """u and v sit between the same two (corresponding) code siblings"""
        def same(xo, yi):
            if xo is None:
                return yi is None
            y = self.M.fwd(xo)
            if y is None:
                y = self.amap(xo)
            return y is not None and y == yi
        return same(u.P, v.P) and same(u.N, v.N)

    def same_scope(self, u, v):
        """v lies in the counterpart of u's enclosing method / member"""
        if u.scope is None or v.scope is None:
            return False
        s2 = self.M.fwd(u.scope)
        return s2 is not None and (s2 == v.scope or self.I.inside(s2, v.C))

    def local(self, x, u):
        """image of O node x, provided it stayed inside the counterpart of u's
        method (code moved to another method is no anchor for a comment that
        stayed behind)"""
        y = self.M.fwd(x)
        if y is None:
            y = self.amap(x)
        if y is None:
            return None
        s2 = self.M.fwd(u.scope) if u.scope is not None else None
        if s2 is not None and not self.I.inside(s2, y):
            return None
        return y

    def section_anchor(self, u):
        """for an own-line comment whose statement has no (local) counterpart:
        the image of the first statement of its section that has one"""
        if u.anchor[0] != "node" or not u.own_line:
            return None
        X = u.anchor[1]
        if self.local(X, u) is not None:
            return None
        for k in u.section[1:]:
            y = self.local(k, u)
            if y is not None:
                return y
        return None

    def image_interval(self, u):
        """I-gap interval [lo, hi] that O-gap u.gap of u.C projects onto, in
        container fwd(u.C); None if the container has no counterpart."""
        C2 = self.M.fwd(u.C)
        if C2 is None:
            return None
        key = (u.C, u.gap)
        r = self._img.get(key)
        if r is not None:
            return r
        r = (C2,) + self._interval(u.C, C2, u.gap, u.gap)
        self._img[key] = r
        return r

    def _interval(self, C, C2, g_before, g_after):
        """image [lo, hi] of the O-gap between code child g_before-1 and code
        child g_after of C, inside C2"""
        O, I = self.O, self.I
        kO, kI = O.code_children(C), I.code_children(C2)
        pos = {y: j for j, y in enumerate(kI)}
        lo, hi = 0, len(kI)
        for j in range(g_before - 1, -1, -1):
            y = self.amap(kO[j])
            if y in pos:
                # a predecessor that was only restructured into y may have
                # absorbed what followed it: y's own slot stays in range
                lo = pos[y] + (1 if self.M.f.get(kO[j]) == y else 0)
                break
        for j in range(g_after, len(kO)):
            y = self.amap(kO[j])
            if y in pos:
                hi = pos[y]
                break
        if lo > hi:
            lo, hi = hi, lo
        return lo, hi

    def region(self, u):
        """for a unit whose container has no counterpart (it sat inside code
        that was removed or restructured): the I interval that replaced the
        outermost unmatched ancestor, in the nearest corresponding ancestor.
        Returns (A2, lo, hi) or None."""
        key = u.C
        if key in self._region:
            return self._region[key]
        O, M = self.O, self.M
        child, A = u.C, O.parent[u.C]
        if O.type[child] in MEMBER_DECL:
            A = -1                  # the declaration itself is gone: no region
        while A >= 0 and M.fwd(A) is None:
            if O.type[A] in MEMBER_DECL:
                A = -1              # never look beyond a removed declaration
                break
            child, A = A, O.parent[A]
        r = None
        if A >= 0 and O.type[A] not in TYPE_DECL and O.type[A] != "CompilationUnit":
            A2 = M.fwd(A)
            kO = O.code_children(A)
            if child in kO:
                g = kO.index(child)
                y = self.amap(child)
                kI = self.I.code_children(A2)
                if y is not None and y in kI:
                    j = kI.index(y)
                    r = (A2, j, j + 1)
                else:
                    lo, hi = self._interval(A, A2, g, g + 1)
                    # the removed code may have been merged into a neighbour
                    r = (A2, max(lo - 1, 0), min(hi + 1, len(kI)))
        self._region[key] = r
        return r

    def level(self, u, v):
        """How well does v's place correspond to u's?  Returns (L, width):
          3    same anchor: the declaration / statement u documents (or the
               statement its restructured content went into) is the one v
               documents; also a comment that travelled with its code
          2.9  v's statement is textually (near-)identical to u's statement
          2.5  first surviving statement of u's section, or u's statement
               found by text where GumTree lost it
          2    same gap (between the same two corresponding statements), or
               u's statement is gone and v documents the new code in its slot
          1.9  u's statement is there, v sits above new code inserted just
               before it (e.g. an extracted local)
          1.5  u's whole block is gone; v lies in the code that replaced it
          1    same corresponding method / member, other place
          0    elsewhere
        width = size of the I interval for levels 2 / 1.9 / 1.5."""
        M, I = self.M, self.I
        ku, xu = u.anchor
        kv, xv = v.anchor
        if ku == "decl":
            D2 = M.fwd(xu)
            if D2 is not None and ((kv == "decl" and xv == D2) or (kv == "node" and xv == D2)):
                return 3, 0
        elif ku == "node":
            X = M.fwd(xu)
            if X is not None:
                if kv in ("node", "decl") and xv == X:
                    return 3, 0
                if kv == "start" and u.P is None and I.code_children(xv)[:1] == [X]:
                    return 3, 0
            # the anchor statement reappears verbatim at v (GumTree may have
            # paired it with a look-alike elsewhere, or lost it when it moved)
            if kv == "node" and self.O.type[xu] not in MEMBER_DECL:
                y = self.stmt_level(xv) or xv
                if (self.same_scope(u, v) or M.bwd(v.scope) is None) and M.dice(xu, y) >= TEXT_ANCHOR:
                    return 2.9, 0
            else:
                if self.same_scope(u, v):
                    X = self.amap(xu)       # statement-level image of restructured code
                    if X is not None and kv in ("node", "decl") and self.stmt_level(xv) == X:
                        return 3, 0
                if self.amap(xu) is None and kv == "node":
                    Z = M.text_anchor(xu)   # the same statement, moved where GumTree lost it
                    if Z is not None and (xv == Z or self.stmt_level(xv) == Z):
                        return 2.5, 0
            if self.same_scope(u, v) and kv == "node" and v.own_line:
                Y = self.section_anchor(u)  # first surviving statement of u's section
                if Y is not None and xv == Y:
                    return 2.5, 0
        elif ku == "end":
            if kv == "end" and xv == M.fwd(xu):
                return 3, 0
        elif ku == "start":
            C2 = M.fwd(xu)
            if C2 is not None:
                if kv == "start" and xv == C2:
                    return 3, 0
                if v.C == C2 and v.P is None and v.own_line:
                    return 3, 0
        class_level = self.O.type[u.C] in CLASS_BODIES
        if ku != "decl" and kv != "decl" and not class_level:
            r = self.image_interval(u)
            if r is not None:
                if r[0] == v.C and self.same_gap(u, v):
                    return 2, 0             # between the same two statements
                if r[0] == v.C:
                    _, lo, hi = r
                    # the slot only counts when u's own anchor statement has no
                    # counterpart (removed / restructured); otherwise the
                    # corresponding place is exactly at that counterpart (L3)
                    free = (ku == "node" and self.local(xu, u) is None
                            and self.section_anchor(u) is None)
                    if not free and ku == "node" and u.own_line and v.own_line and kv == "node":
                        # u's statement (or the first surviving one of its
                        # section) is there, but new statements (e.g. an
                        # extracted local) were inserted between v and it
                        y = self.local(xu, u)
                        if y is None:
                            y = self.section_anchor(u)
                        kI = I.code_children(v.C)
                        if y in kI and xv in kI and not M.i_existed(xv):
                            jy, jv = kI.index(y), kI.index(xv)
                            if lo <= v.gap <= jy and jv < jy and all(not M.i_existed(kI[j]) for j in range(jv, jy)):
                                return 1.9, jy - jv
                    # ... and v must document new code (what replaced u's statement)
                    if free and lo <= v.gap <= hi and kv == "node" and not M.i_existed(xv):
                        return 2, hi - lo
                    return 1, 0
            else:
                rg = self.region(u)
                if rg is not None:
                    A2, lo, hi = rg
                    if v.C == A2 and lo <= v.gap <= hi:
                        return 1.5, hi - lo
                    kI = I.code_children(A2)
                    for j in range(lo, min(hi, len(kI))):
                        if I.inside(kI[j], v.C):
                            return 1.5, hi - lo
        if u.scope is not None and v.scope is not None and M.fwd(u.scope) == v.scope:
            return 1, 0
        return 0, 0


# ============================================================================
# text similarity
# ============================================================================
def contain(a_words, b_words):
    """share of a's words (multiset) that occur in b"""
    if not a_words:
        return 0.0
    cb = Counter(b_words)
    hit = 0
    for w in a_words:
        if cb[w] > 0:
            cb[w] -= 1
            hit += 1
    return hit / len(a_words)


def ratio(a: str, b: str) -> float:
    a, b = a.lower(), b.lower()
    if a == b:
        return 1.0
    if not a or not b:
        return 0.0
    return SequenceMatcher(None, a, b, autojunk=False).ratio()


def near_same(u, v) -> bool:
    if u.norm == v.norm:
        return True
    if len(u.words) < MOVE_MIN_TOKENS or len(v.words) < MOVE_MIN_TOKENS:
        return False
    if u.words == v.words:
        return True
    if min(len(u.words), len(v.words)) <= 2:
        return False
    return ratio(u.norm, v.norm) >= MOVE_RATIO


def gt_linked(u, v) -> bool:
    return v.id in u.gt_partners


def phrase_common(a, b) -> int:
    """number of words of a that reappear in b inside shared phrases of at
    least two consecutive words (word order kept)"""
    if not a or not b:
        return 0
    sm = SequenceMatcher(None, a, b, autojunk=False)
    return sum(blk.size for blk in sm.get_matching_blocks() if blk.size >= 2)


def carried(u, v, gt=False) -> bool:
    """the text of one comment is recognisably reused in the other (shared
    phrases cover most of the smaller one)"""
    a, b = u.words, v.words
    if not a or not b:
        return False
    common = phrase_common(a, b)
    if common < CARRY_MIN_COMMON:
        return False
    lo, hi = min(len(a), len(b)), max(len(a), len(b))
    need = CARRY_GT if gt else CARRY
    return common / lo >= need and common / hi >= CARRY_FLOOR


def conv_content(u, v) -> bool:
    """O's text is recognisably carried into a unit of another kind"""
    if len(u.words) < CONV_MIN_TOKENS:
        return False
    common = phrase_common(u.words, v.words)
    return common >= CONV_MIN_TOKENS and common / len(u.words) >= CONV_CONTAIN


BANNER_RE = re.compile(r"([-=*#~_/])\1{3,}")


def is_banner(raw: str) -> bool:
    return bool(BANNER_RE.search(raw or ""))


ANNOTATIONS = frozenset(("MarkerAnnotation", "NormalAnnotation", "SingleMemberAnnotation"))
TYPE_DECL = frozenset(("TypeDeclaration", "EnumDeclaration", "RecordDeclaration", "AnnotationTypeDeclaration"))


def about_removed_annotation(u, O, M) -> bool:
    """u sits right above an annotation of the declaration below it and that
    annotation is gone in I -> the comment was about the annotation"""
    d = u.doc_target
    if d is None or u.kind == "Javadoc":
        return False
    kids = O.code_children(d)
    if not kids or O.type[kids[0]] not in ANNOTATIONS:
        return False
    a = kids[0]
    if O.line(O.pos[a]) != O.line(u.end) + 1:
        return False
    return M.fwd(a) is None


def conv_role_ok(u, v, O, I, M) -> bool:
    if not u.words or is_separator(u.norm) or looks_like_code(u.norm):
        return False
    if is_banner(v.raw) or is_separator(v.norm):
        return False
    if about_removed_annotation(u, O, M):
        return False
    return True


# ============================================================================
# the per-test labeller
# ============================================================================
def label_test(t: dict) -> dict:
    rel = t["relative_test"]
    ou_raw, iu_raw = t["o_units"], t["i_units"]
    res = {"relative_test": rel, "o": {}, "i": {}}
    if not ou_raw and not iu_raw:
        return res
    osrc, isrc = read_src(t["original"]), read_src(t["improved"])
    O, I = load_tree(t["ast"]["o"], osrc), load_tree(t["ast"]["i"], isrc)
    M = Mapping(O, I, json.loads(Path(t["diff"]).read_text()))
    ou, iu = attach_units(O, ou_raw, "o"), attach_units(I, iu_raw, "i")
    PL = Places(M)

    lev = {}
    for u in ou:
        for v in iu:
            lev[(u.id, v.id)] = PL.level(u, v)

    o_lab = {}                 # uid -> [label, [partners], note, confidence]
    i_part = {v.id: [] for v in iu}
    free_o = {u.id for u in ou}
    free_i = {v.id for v in iu}
    def take(u, v, label, note, conf="high"):
        o_lab[u.id] = [label, [v.id], note, conf]
        i_part[v.id].append(u.id)
        free_o.discard(u.id)
        free_i.discard(v.id)

    def greedy(cands, label_fn):
        cands.sort(key=lambda c: c[0], reverse=True)
        for sc, u, v in cands:
            if u.id in free_o and v.id in free_i:
                lab, note, conf = label_fn(u, v)
                take(u, v, lab, note, conf)

    # ---- pass 1: UNCHANGED (same kind, same text, corresponding place) ------
    cands = []
    for u in ou:
        for v in iu:
            if u.kind != v.kind or u.norm != v.norm:
                continue
            L, w = lev[(u.id, v.id)]
            if L < 1.5 and u.anchor[0] == "node" and v.anchor[0] == "node" and u.words:
                # identical comment above a look-alike statement: the comment
                # travelled with its (moved / re-matched) code
                xu, xv = u.anchor[1], v.anchor[1]
                if O.type[xu] not in MEMBER_DECL and (PL.same_scope(u, v) or M.bwd(v.scope) is None
                                                      or M.fwd(u.scope) is None):
                    if M.dice(xu, PL.stmt_level(xv) or xv) >= TEXT_ANCHOR_SAME:
                        L, w = 2.8, 0
                        lev[(u.id, v.id)] = (L, w)
            if L >= 1.5:
                cands.append(((L, gt_linked(u, v), -w, -abs(u.lines[0] - v.lines[0]) / 1e4), u, v))
    greedy(cands, lambda u, v: ("UNCHANGED", {3: "same-text-anchor", 2.9: "same-text-anchor-text",
                                              2.8: "same-text-anchor-text", 2.5: "same-text-section",
                                              1.9: "same-text-above-new-code",
                                              2: "same-text-slot"}.get(lev[(u.id, v.id)][0], "same-text-region"),
                                "high"))

    # ---- pass 2: UPDATED (same kind, corresponding place) --------------------
    cands = []
    for u in ou:
        for v in iu:
            if u.kind != v.kind or u.id not in free_o or v.id not in free_i:
                continue
            L, w = lev[(u.id, v.id)]
            if L < 1.5:
                continue
            sim = contain(u.words, v.words) if u.words else 0.0
            gl = gt_linked(u, v)
            if L == 2 and w > WIDE_GAP and sim < 0.3:
                continue            # wide, weak slot: needs some textual evidence
            if L < 2 and sim < 0.3:
                continue            # region / separated by new code: needs textual evidence
            cands.append(((L, -min(w, 5) if L < 3 else 0, round(sim, 1), gl, -abs(u.lines[0] - v.lines[0]) / 1e4), u, v))

    def upd_label(u, v):
        L, w = lev[(u.id, v.id)]
        if u.norm == v.norm:
            return "UNCHANGED", "same-text-slot", "high"
        if L == 3:
            return "UPDATED", "anchor", "high"
        if L == 2.9:
            return "UPDATED", "anchor-text", "high"
        if L == 2.5:
            return "UPDATED", "section", "high"
        if L == 2:
            return "UPDATED", "slot", ("high" if w <= WIDE_GAP else "low")
        if L == 1.9:
            return "UPDATED", "above-new-code", "low"
        return "UPDATED", "region", "low"
    greedy(cands, upd_label)

    # ---- pass 3: merges and splits (UPDATED) -------------------------------
    # an O-unit left over next to an already paired I-unit whose text absorbs
    # it (merge), or an I-unit left over next to a paired O-unit whose text it
    # continues (split).  Evidence = shared phrases, and never a unit that has
    # a still-free twin with the very same text on the other side.
    free_norm_o = Counter(u.norm for u in ou if u.id in free_o)
    free_norm_i = Counter(v.norm for v in iu if v.id in free_i)
    for u in ou:
        if u.id not in free_o or len(u.words) < MERGE_MIN_TOKENS or free_norm_i[u.norm]:
            continue
        best = None
        for v in iu:
            if v.id in free_i or v.kind != u.kind:
                continue
            L, w = lev[(u.id, v.id)]
            if L < 2:
                continue
            c = phrase_common(u.words, v.words) / len(u.words)
            if c >= MERGE_CONTAIN and (best is None or c > best[0]):
                best = (c, v)
        if best:
            v = best[1]
            o_lab[u.id] = ["UPDATED", [v.id], "merge", "low"]
            i_part[v.id].append(u.id)
            free_o.discard(u.id)
    for v in iu:
        if v.id not in free_i or len(v.words) < MERGE_MIN_TOKENS or free_norm_o[v.norm]:
            continue
        best = None
        for u in ou:
            if u.id in free_o or u.kind != v.kind or o_lab[u.id][0] not in ("UNCHANGED", "UPDATED"):
                continue
            L, w = lev[(u.id, v.id)]
            if L < 2:
                continue
            c = phrase_common(v.words, u.words) / len(v.words)
            if c >= MERGE_CONTAIN and (best is None or c > best[0]):
                best = (c, u)
        if best:
            u = best[1]
            o_lab[u.id][0] = "UPDATED"
            o_lab[u.id][1].append(v.id)
            o_lab[u.id][2] = "split"
            o_lab[u.id][3] = "low"
            i_part[v.id].append(u.id)
            free_i.discard(v.id)

    # ---- pass 4: MOVED (same kind, same/near text, other place) --------------
    cands = []
    for u in ou:
        if u.id not in free_o or is_separator(u.norm):
            continue
        for v in iu:
            if v.id not in free_i or u.kind != v.kind:
                continue
            if not near_same(u, v):
                continue
            L, w = lev[(u.id, v.id)]
            if len(u.words) < MOVE_SHORT and L < 1:
                continue            # a short text repeated elsewhere is a coincidence
            cands.append(((u.norm == v.norm, ratio(u.norm, v.norm), L, gt_linked(u, v)), u, v))
    greedy(cands, lambda u, v: ("MOVED", "same-text-elsewhere", "high"))

    # ---- pass 5: MOVED with edits (content carried to another place) ---------
    cands = []
    for u in ou:
        if u.id not in free_o or is_separator(u.norm) or looks_like_code(u.norm):
            continue
        for v in iu:
            if v.id not in free_i or u.kind != v.kind:
                continue
            if not carried(u, v, gt_linked(u, v)):
                continue
            L, w = lev[(u.id, v.id)]
            cands.append(((contain(u.words, v.words), L, gt_linked(u, v)), u, v))
    greedy(cands, lambda u, v: ("MOVED", "content-moved", "low"))

    # ---- pass 6: CONVERTED (different kind) ----------------------------------
    cands = []
    for u in ou:
        if u.id not in free_o:
            continue
        for v in iu:
            if v.id not in free_i or u.kind == v.kind:
                continue
            L, w = lev[(u.id, v.id)]
            c = contain(u.words, v.words)
            content = conv_content(u, v)
            place = False
            if L == 3 and conv_role_ok(u, v, O, I, M):
                # same place: same role unless the new unit is clearly about
                # something else (class Javadocs are new descriptions)
                if c >= 0.2 or not (v.doc_target is not None and I.type[v.doc_target] in TYPE_DECL):
                    place = True
            elif 1.5 <= L < 3 and c >= 0.3 and len(u.words) >= 2 and w <= WIDE_GAP:
                place = True
            if content or place:
                cands.append(((L == 3, content, c, L), u, v))

    def conv_label(u, v):
        L = lev[(u.id, v.id)][0]
        c = contain(u.words, v.words)
        if L == 3:
            return "CONVERTED", "conv-place", ("high" if c >= 0.2 else "low")
        return "CONVERTED", "conv-content", "low"
    greedy(cands, conv_label)

    # ---- output ----------------------------------------------------------
    for u in ou:
        if u.id in o_lab:
            L, P, note, conf = o_lab[u.id]
            res["o"][u.id] = {"label": L, "partners": sorted(set(P), key=lambda s: int(s[1:])),
                              "confidence": conf, "note": note}
        else:
            wc, note = with_code_o(u, M, PL)
            conf = "low" if note in ("anchor-removed", "block-removed", "anchor-replaced") else "high"
            res["o"][u.id] = {"label": "DELETED", "partners": [], "with_code": wc,
                              "confidence": conf, "note": note}
    for v in iu:
        if i_part[v.id]:
            ps = sorted(set(i_part[v.id]), key=lambda s: int(s[1:]))
            conf = "low" if any(res["o"][p]["confidence"] == "low" for p in ps) else "high"
            res["i"][v.id] = {"label": "COUNTERPART", "partners": ps, "confidence": conf,
                              "note": "/".join(sorted({res["o"][p]["label"].lower() for p in ps}))}
        else:
            wc, note = with_code_i(v, M)
            res["i"][v.id] = {"label": "ADDED", "partners": [], "with_code": wc,
                              "confidence": "high", "note": note}
    return res


# ============================================================================
# with_code
# ============================================================================
def with_code_o(u, M: Mapping, PL: Places):
    O = M.O
    # the declaration it documents / sits in
    if u.doc_target is not None:
        if M.fwd(u.doc_target) is None and not M.o_survives(u.doc_target):
            return True, "decl-removed"
        return False, "decl-kept"
    d = O.up_to(u.C, MEMBER_DECL)
    if d is not None and M.fwd(d) is None and not M.o_survives(d):
        return True, "in-removed-decl"
    kind, x = u.anchor
    if kind in ("node",):
        if M.o_survives(x) or PL.amap(x) is not None:
            return False, "anchor-kept"
        if PL.section_anchor(u) is not None:
            return False, "section-kept"
        if M.o_text_survives(x):
            return False, "anchor-text-kept"
        # replaced in place?  new code in the projected slot
        r = PL.image_interval(u)
        if r is not None:
            C2, lo, hi = r
            kI = M.I.code_children(C2)
            if u.own_line:
                rng = range(lo, min(hi + 1, len(kI)))
            else:
                rng = range(max(lo - 1, 0), min(hi, len(kI)))
            for j in rng:
                if not M.i_existed(kI[j]):
                    return False, "anchor-replaced"
        return True, "anchor-removed"
    # 'end' / 'start' of a container: the container itself
    C = x
    if M.fwd(C) is not None or M.o_survives(C):
        return False, "block-kept"
    for c in O.code_children(C):
        if M.o_survives(c) or M.o_text_survives(c):
            return False, "block-content-kept"
    return True, "block-removed"


def with_code_i(v, M: Mapping):
    """ADDED comment: true iff the declaration it documents or sits in is new
    in I (a new helper, constant, class).  Comments on new/extracted statements
    inside an existing method annotate restructured existing code -> false."""
    I = M.I
    if v.doc_target is not None:
        new = M.bwd(v.doc_target) is None
        return new, ("new-decl" if new else "decl-existed")
    d = I.up_to(v.C, MEMBER_DECL)
    while d is not None:
        if M.bwd(d) is None:
            return True, "in-new-decl"
        # an existing inner declaration inside a new outer one is still new code?
        # no: the innermost declaration decides
        break
    return False, "in-existing-decl"


# ============================================================================
# driver (one test per call; the comments stage of feature_analysis.py runs it
# in a process pool)
# ============================================================================
def label_one(job) -> tuple[str, str | None]:
    """Worker: (relative_test, original, improved, diff, ast_o, ast_i, out).

    Writes {"relative_test", "status", "units", "labels"} to `out`. A failure
    is recorded as status ERROR with EMPTY labels -- never as a guess -- so the
    merge stage leaves the test out of the frequency tables, the rule every
    other tool already follows."""
    rel, original, improved, diff, ast_o, ast_i, out = job
    record = {"relative_test": rel, "status": "OK"}
    try:
        t = build_test(rel, original, improved, diff)
        t["ast"] = {"o": ast_o, "i": ast_i}
        res = label_test(t)
        record["units"] = {side: [{k: u[k] for k in ("id", "kind", "lines", "own_line", "norm")}
                                  for u in t[f"{side}_units"]] for side in ("o", "i")}
        record["labels"] = {"o": res["o"], "i": res["i"]}
        err = None
    except Exception as e:  # noqa: BLE001 -- recorded, never raised
        err = f"{type(e).__name__}: {e}"
        record.update(status="ERROR", error=err, units={}, labels={})
    Path(out).parent.mkdir(parents=True, exist_ok=True)
    Path(out).write_text(json.dumps(record, ensure_ascii=False, indent=1), encoding="utf-8")
    return out, err
