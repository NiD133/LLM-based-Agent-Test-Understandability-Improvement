"""
Main orchestrator for the LLM Test Understandability Improvement pipeline.

Usage:
    python3 agent_improvement/scripts/main.py --config agent_improvement/config.yaml \
        --subjects agent_improvement/data/dataset.json

Pipeline steps (each independently toggled in config.yaml → pipeline_control):
  1.  clone_and_build              — Clone from GitHub, compile with Maven
  2a. generate_evosuite            — Run EvoSuite to generate automated test suites
  2b. measure_baseline             — Coverage + mutation for the manual + EvoSuite
                                     SUITES → writes to data/baseline/
  2c. filter_out                   — Apply the 60/60/40 gate, copy passing
                                     classes' suites to data/original/
  3.  split_and_measure_cases      — Split each gate-passing suite into test cases
                                     and measure every case → data/original/
  3b. generate_improvement_prompts — Dry-run: write the prompts each agent would receive
  4a. run_claude_code              — Claude Agent SDK agent (Opus 4.8, Sonnet 4.6)
  4b. run_codex                    — Codex CLI agent (GPT-5.5)
  5.  compile_improvement_outputs  — Compile each improved test + coverage + mutation,
                                     exact-match verdict → metrics.json
  5b. generate_summary             — data/improved/summary/ (summarize_improvements.py)

Stability re-runs (stability_check/run_stability.py) drive the same Step 4/5 code
through the STABILITY_* environment hooks below.
"""

import argparse
import json
import logging
import os
import re
import shutil
import sys
import types
from dataclasses import dataclass
from pathlib import Path
from typing import Optional

import yaml
from dotenv import load_dotenv

# ── Setup paths ───────────────────────────────────────────────────────────────
PROJECT_ROOT = Path(__file__).parent.parent
sys.path.insert(0, str(PROJECT_ROOT / "scripts"))

import pipeline_paths

load_dotenv(PROJECT_ROOT.parent / ".env")   # repository root

# ── Env hygiene for Claude Code CLI subprocess ───────────────────────────────
# When this process was launched from inside a Claude Code app/CLI session,
# the parent inherited env vars from a previous ~/.claude/settings.json
# (e.g. ANTHROPIC_BASE_URL pointing to a third-party gateway, ANTHROPIC_MODEL
# pinned to a non-Anthropic model name). Those would propagate into the claude
# CLI subprocesses we spawn for agent improvement and break model resolution.
#
# Strip the override knobs unconditionally so the claude CLI uses its own
# defaults plus the ANTHROPIC_API_KEY loaded from .env above. Keep
# ANTHROPIC_API_KEY itself (set by .env) — do NOT remove it.
_LEAKY_ANTHROPIC_ENV = (
    "ANTHROPIC_AUTH_TOKEN",
    "ANTHROPIC_BASE_URL",
    "ANTHROPIC_MODEL",
    "ANTHROPIC_DEFAULT_HAIKU_MODEL",
    "ANTHROPIC_DEFAULT_SONNET_MODEL",
    "ANTHROPIC_DEFAULT_OPUS_MODEL",
    "ANTHROPIC_REASONING_MODEL",
)
for _var in _LEAKY_ANTHROPIC_ENV:
    if _var in os.environ:
        os.environ.pop(_var, None)

logging.basicConfig(
    level=logging.INFO,
    format="%(asctime)s [%(levelname)s] %(message)s",
    datefmt="%H:%M:%S",
)
log = logging.getLogger(__name__)

# Surface the resolved auth surface at startup so the user can confirm.
# Two credentials may coexist (agent_with_repair tries OAuth first, then API):
#   CLAUDE_CODE_OAUTH_TOKEN → Claude Pro/Max subscription billing (preferred)
#   ANTHROPIC_API_KEY       → pay-per-token API fallback
_have_oauth = bool(os.environ.get("CLAUDE_CODE_OAUTH_TOKEN", "").strip())
_have_apikey = bool(os.environ.get("ANTHROPIC_API_KEY", "").strip())
if _have_oauth and _have_apikey:
    log.info("AUTH: OAuth token + API key both present → "
             "OAuth (subscription) first, API-key fallback on usage limit")
elif _have_oauth:
    log.info("AUTH: OAuth token only → subscription billing (no API fallback)")
elif _have_apikey:
    _key_tail = os.environ["ANTHROPIC_API_KEY"][-6:]
    log.info(f"AUTH: API key only (ends ...{_key_tail}) → pay-per-token billing")
else:
    log.warning(
        "AUTH: neither CLAUDE_CODE_OAUTH_TOKEN nor ANTHROPIC_API_KEY set — "
        "claude CLI will fall back to ambient credentials or fail."
    )

import build_executor
import coverage_runner
import mutation_measurement
import evosuite_runner
import git_manager
import llm_refactor
import test_splitter
import agent_with_repair
import codex_agent_with_repair


# ── Config loading ────────────────────────────────────────────────────────────

def _deep_merge(base: dict, over: dict) -> dict:
    """`over` wins, except that two dicts at the same key are merged key-by-key.

    Lists and scalars are REPLACED, not appended — an `active_prompt_version`
    or a `models:` list in the child config fully supersedes the parent's
    rather than quietly accumulating both."""
    out = dict(base)
    for k, v in over.items():
        if isinstance(v, dict) and isinstance(out.get(k), dict):
            out[k] = _deep_merge(out[k], v)
        else:
            out[k] = v
    return out


def load_config(config_path: str) -> types.SimpleNamespace:
    """Load a config file into a nested SimpleNamespace for attribute access.

    Supports ONE extra key, `extends: <path>`, which loads that config first
    and deep-merges this one on top. It lets a sub-study keep its own config
    without duplicating the shared blocks that would then drift:

        # downstream/config.yaml
        extends: "../config.yaml"      # inherit tools:, mutation_testing:, …
        paths:
          data_root: "downstream/data" # …but redirect the whole output tree
        downstream:
          ...

    `extends` is resolved relative to the config file that declares it, and
    chains (a parent may extend a grandparent). A config without the key
    behaves exactly as before.

    Every string value may reference an environment variable as `${VAR}`
    (the JDK homes in tools: come from .env this way). An unset variable
    expands to "", which the callers treat as "not configured"."""
    seen: list[Path] = []

    def _load(path: Path) -> dict:
        path = path.resolve()
        if path in seen:
            chain = " → ".join(p.name for p in seen + [path])
            raise RuntimeError(f"config `extends` cycle: {chain}")
        seen.append(path)
        raw = yaml.safe_load(path.read_text(encoding="utf-8")) or {}
        parent_ref = raw.pop("extends", None)
        if not parent_ref:
            return raw
        parent_path = Path(str(parent_ref)).expanduser()
        if not parent_path.is_absolute():
            parent_path = path.parent / parent_path
        if not parent_path.exists():
            raise RuntimeError(
                f"{path.name} extends {parent_ref}, which does not exist "
                f"(resolved to {parent_path})")
        return _deep_merge(_load(parent_path), raw)

    return _to_ns(_expand_env(_load(Path(config_path))))


def _expand_env(d):
    """Replace `${VAR}` in every string of a nested dict/list with os.environ[VAR] ("" if unset)."""
    if isinstance(d, dict):
        return {k: _expand_env(v) for k, v in d.items()}
    if isinstance(d, list):
        return [_expand_env(v) for v in d]
    if isinstance(d, str) and "${" in d:
        return re.sub(r"\$\{([A-Za-z_][A-Za-z0-9_]*)\}", lambda m: os.environ.get(m.group(1), ""), d)
    return d


def _to_ns(d):
    if isinstance(d, dict):
        return types.SimpleNamespace(**{k: _to_ns(v) for k, v in d.items()})
    return d


def _apply_path_config(cfg) -> None:
    """Recompute every DATA_ROOT-derived module global from cfg.paths, so a
    downstream task's config can redirect its entire output tree (clones +
    all data) into its own folder. Call ONCE at main() startup, right after
    load_config(). No-op-equivalent when cfg has no `paths:` section (falls
    back to the historical project-root defaults).

    Consumers reference these names as module globals at call time, so
    reassigning them here propagates everywhere in main.py; git_manager /
    coverage_runner / etc. read pipeline_paths directly, which
    configure_from_cfg() updates in lock-step."""
    pipeline_paths.configure_from_cfg(cfg)

    global DATA_ROOT, BASELINE_ROOT, ORIGINAL_ROOT, IMPROVED_ROOT
    global TESTS_FOR_IMPROVEMENTS_PATH

    DATA_ROOT     = pipeline_paths.data_root()
    BASELINE_ROOT = DATA_ROOT / "baseline"
    ORIGINAL_ROOT = DATA_ROOT / "original"
    IMPROVED_ROOT = DATA_ROOT / "improved"
    TESTS_FOR_IMPROVEMENTS_PATH = DATA_ROOT / "tests_for_improvements.json"

    # Env-var overrides must still WIN over config (they target a specific
    # single artifact for stability runs). Re-apply them last.
    if _stability_improved_root:
        IMPROVED_ROOT = Path(_stability_improved_root)
    if _stability_manifest:
        TESTS_FOR_IMPROVEMENTS_PATH = Path(_stability_manifest)


def load_subjects(subjects_path: str) -> list:
    """Load subjects.json and normalise to the in-memory dict shape used
    downstream.

    Supports the slim per-repo schema:
        {
          "repo": "apache/commons-codec",
          "commit_hash": "",                    # optional
          "class_paths": ["src/main/java/.../Foo.java", ...],
          "test_paths":  ["src/test/java/.../FooTest.java", ...]
        }

    `class_paths[i]` is paired positionally with `test_paths[i]`; the FQN
    of the class under test is derived from the path (the segment after
    `src/<sourceSet>/java/`). `build_system` is intentionally omitted —
    main() auto-detects maven vs gradle from the cloned repo.
    """
    with open(subjects_path) as f:
        raw = json.load(f)
    return [_normalize_subject(s) for s in raw]


def _normalize_subject(entry: dict) -> dict:
    """Convert one subjects.json record to the internal dict shape."""
    repo = entry.get("repo") or ""
    if not repo:
        raise ValueError(
            f"subjects.json entry missing 'repo' field: {entry!r}"
        )

    class_paths = entry.get("class_paths") or []
    test_paths  = entry.get("test_paths")  or []
    if len(class_paths) != len(test_paths):
        raise ValueError(
            f"[{repo}] class_paths ({len(class_paths)}) and test_paths "
            f"({len(test_paths)}) must have equal length and pair positionally"
        )

    targets = []
    for class_path, test_path in zip(class_paths, test_paths):
        class_fqn = _java_path_to_fqn(class_path)
        test_fqn  = _java_path_to_fqn(test_path)
        # Sanity nudge: test class name should reference the CUT name
        # somehow. Accept the common conventions:
        #   <Class>Test  (Apache Commons, most projects)
        #   <Class>Tests (Guava, some Google projects)
        #   Test<Class>  (Joda-Time, some older projects)
        # Warn only when none of those substrings is present.
        cut_simple  = class_fqn.rsplit(".", 1)[-1]
        test_simple = test_fqn.rsplit(".", 1)[-1]
        if not (test_simple.endswith(cut_simple + "Test")
                or test_simple.endswith(cut_simple + "Tests")
                or test_simple == "Test" + cut_simple):
            log.warning(
                "[%s] class/test pair may not match: %s vs %s",
                repo, class_path, test_path,
            )
        targets.append({
            "class_name": class_fqn,
            "manual_test_file": test_path,
            "class_path": class_path,        # retained for multi-module detection
        })

    return {
        "project_id":     repo.split("/")[-1],
        "github_url":     f"https://github.com/{repo}.git",
        "commit_hash":    entry.get("commit_hash", ""),
        # build_system left empty → main() auto-detects from the cloned repo
        "build_system":   "",
        "target_classes": targets,
    }


_TOP_PACKAGES = ("com", "org", "io", "net", "edu", "gov", "uk", "eu", "de",
                 "fr", "cn", "jp", "us", "ai")


def _java_path_to_fqn(java_path: str) -> str:
    """Derive a Java FQN from a source-path string.

    Examples (Maven/Gradle standard):
      src/main/java/org/apache/commons/codec/language/Soundex.java
        → org.apache.commons.codec.language.Soundex
      src/test/java/org/apache/commons/cli/PosixParserTest.java
        → org.apache.commons.cli.PosixParserTest

    Examples (non-standard, e.g. guava `guava/src/...`):
      guava/src/com/google/common/collect/ForwardingQueue.java
        → com.google.common.collect.ForwardingQueue

    Strategy:
      1. If the path contains `/java/`, treat everything after it as the FQN.
      2. Otherwise, scan from the right for the last segment that begins a
         well-known top-level Java package (`com`, `org`, `io`, …).
    """
    p = java_path.replace("\\", "/")
    if not p.endswith(".java"):
        raise ValueError(f"expected .java path, got {java_path!r}")

    idx = p.rfind("/java/")
    if idx >= 0:
        return p[idx + len("/java/"):-len(".java")].replace("/", ".")

    for top in _TOP_PACKAGES:
        marker = f"/{top}/"
        idx = p.rfind(marker)
        if idx >= 0:
            return p[idx + 1:-len(".java")].replace("/", ".")
        if p.startswith(f"{top}/"):
            return p[:-len(".java")].replace("/", ".")

    raise ValueError(
        f"could not derive FQN from {java_path!r} — no `/java/` marker and "
        f"no recognisable top-level Java package prefix ({', '.join(_TOP_PACKAGES)})"
    )


# ── Data layout ───────────────────────────────────────────────────────────────
#
# Everything the pipeline produces lives under data/. Two top-level trees:
#
#   data/original/<project>/{manual|auto}/{testsuites|testcases|_meta}/
#       Original (baseline) test code + JaCoCo/PIT metrics.
#
#   data/improved/<model>/<prompt>/<mode>/<project>/{manual|auto}/{testsuites|testcases}/
#       Agent-improved test code + metrics + every diagnostic file
#       (state.json, session.jsonl, trace.txt, diff.patch, …) co-located so
#       a single folder holds the full record of one (model, prompt, mode,
#       project) experiment.
#
# Inside testsuites/ and testcases/<SuiteClass>/, files are kept FLAT and
# prefixed with the test class name, so multiple tests share a directory
# without colliding:
#       PosixParserTest.java
#       PosixParserTest_metrics.json
#       PosixParserTest_state.json
#       PosixParserTest_session.jsonl
#       PosixParserTest_metrics_comparison.json
#       PosixParserTest_diff.patch
#       …
# Per-test JaCoCo/PIT intermediates go under a `_work/<TestClass>/`
# sub-directory so they stay out of the way.
# DATA_ROOT and every path derived from it below are the DEFAULT (project
# root) values used at import time. main() calls _apply_path_config(cfg) right
# after load_config() to recompute them all from config.yaml's `paths:` section
# (so a downstream task can redirect its whole output tree). Because every
# consumer reads these module globals at call time, that one recompute
# propagates everywhere. See pipeline_paths.py.
DATA_ROOT     = pipeline_paths.data_root()
# Raw measurement output — every class's metrics land here, no filtering.
BASELINE_ROOT = DATA_ROOT / "baseline"
# Filtered (or fully-mirrored) subset — what downstream steps consume.
ORIGINAL_ROOT = DATA_ROOT / "original"
IMPROVED_ROOT = DATA_ROOT / "improved"
# ── Stability-check override (no-op unless STABILITY_IMPROVED_ROOT is set) ──
# Redirects every Step-4 write and Step-5 measurement walk to a per-run root
# (e.g. stability_check/results/run2). Leaves the default untouched.
# Driven by stability_check/run_stability.py.
_stability_improved_root = os.environ.get("STABILITY_IMPROVED_ROOT", "").strip()
if _stability_improved_root:
    IMPROVED_ROOT = Path(_stability_improved_root)
# Top-level folder for inspectable prompts written by
# `generate_improvement_prompts`. Kept OUTSIDE data/ so it is easy to
# eyeball and never collides with authoritative improvement state.
IMPROVE_PROMPTS_ROOT = PROJECT_ROOT / "ImprovePrompts"


# ── Path builders ─────────────────────────────────────────────────────────────

def _src_for_category(test_category: str) -> str:
    """Map an internal `test_category` to its data/-tree subdir ("manual" | "auto")."""
    if test_category in ("manual_suite", "manual_cases"):
        return "manual"
    if test_category in ("auto_suite", "auto_cases"):
        return "auto"
    raise ValueError(f"Unsupported test_category '{test_category}'")


def _is_suite_category(test_category: str) -> bool:
    return test_category.endswith("_suite")


def _per_test_dir(root: Path, project_id: str, test_category: str,
                    suite_class: Optional[str] = None) -> Path:
    """Build a {testsuites | testcases/<SuiteClass>} path under any data root."""
    src = _src_for_category(test_category)
    if _is_suite_category(test_category):
        d = root / project_id / src / "testsuites"
    else:
        if not suite_class:
            raise ValueError("suite_class is required for testcases")
        d = root / project_id / src / "testcases" / suite_class
    d.mkdir(parents=True, exist_ok=True)
    return d


def baseline_dir(project_id: str, test_category: str,
                  suite_class: Optional[str] = None) -> Path:
    """Folder under data/baseline/ for one (project, source, suite_or_cases) leaf.

    `measure_baseline` writes here BEFORE any filtering. Every measured class's
    suite + cases land here regardless of stage_gates thresholds."""
    return _per_test_dir(BASELINE_ROOT, project_id, test_category, suite_class)


def original_dir(project_id: str, test_category: str,
                  suite_class: Optional[str] = None) -> Path:
    """Folder under data/original/ for one (project, source, suite_or_cases) leaf.

    `filter_out_baseline` writes here AFTER applying stage_gates. Only classes
    that passed the gate (or all classes when apply_filter=false) are copied
    from baseline/ to original/. Downstream steps (Step 4 agent improvement,
    Step 5 compile improved, Step 6 evaluation) consume from here."""
    return _per_test_dir(ORIGINAL_ROOT, project_id, test_category, suite_class)


# ── Per-model output-folder overrides ────────────────────────────────────────
# Populated by _apply_model_output_dirs(cfg) at main() startup from
#   agent.models[].output_dir / codex_agent.models[].output_dir
# Keys are model ids AS THEY REACH improved_dir(). Codex passes its ids through
# codex_agent_with_repair._output_label(), which prefixes "codex-", so every
# codex entry is registered under BOTH "gpt-5.5" and "codex-gpt-5.5".
_MODEL_OUTPUT_DIRS: dict = {}


def _apply_model_output_dirs(cfg) -> None:
    """Read the `output_dir` overrides out of both backend sections.

    Call ONCE at startup, before any improvement/measurement path is built.
    Raises when two ACTIVE (model, prompt) combinations would land in the same
    folder — the override drops the prompt version from the path, so running
    two prompts at once needs two distinct folder names or one silently
    overwrites the other."""
    global _MODEL_OUTPUT_DIRS
    mapping: dict = {}
    # One canonical (backend, model id, folder) per configured model. The
    # `mapping` may hold TWO keys per codex entry (bare + "codex-" prefixed),
    # but those are aliases for one run — the collision check below must look
    # at the canonical list, or every codex model reports a false collision
    # against its own alias.
    canonical: list = []
    for section_name, prefix in (("agent", ""), ("codex_agent", "codex-")):
        section = getattr(cfg, section_name, None)
        entries = getattr(section, "models", None) if section is not None else None
        for entry in (entries or []):
            mid, out = _model_entry_id(entry), _model_entry_output_dir(entry)
            if not (mid and out):
                continue
            mapping[mid] = out
            if prefix:
                mapping[prefix + mid] = out
            canonical.append((section_name, mid, out))
    _MODEL_OUTPUT_DIRS = mapping
    if not canonical:
        return

    # Collision check across every (model × prompt) combination. The study
    # uses a single prompt template (llm_refactor.PROMPT_VERSION).
    prompts = list(llm_refactor.PROMPT_VERSIONS)
    seen: dict = {}
    for section_name, mid, out in canonical:
        for prompt in prompts:
            label = model_prompt_label(mid, prompt)
            key = (section_name, mid, prompt)
            if label in seen and seen[label] != key:
                raise RuntimeError(
                    f"output_dir collision: {seen[label]} and {key} both map to "
                    f"data/improved/{label}/. Give each (model, prompt) pair its "
                    f"own output_dir, or activate only one prompt version.")
            seen[label] = key
    log.info("[output_dir] %s", ", ".join(
        f"{mid} → {out}" for _s, mid, out in canonical))


def model_prompt_label(model: str, prompt: str) -> str:
    """The one path segment under data/improved/ that identifies a run.

    With an `output_dir` override configured for this model, that name is used
    VERBATIM:

        data/improved/opus-4.8/<project>/<src>/{testsuites|testcases}/...

    which is exactly the <model>/<project>/<src>/<gran>/ shape feature_analysis
    expects as its DATA_ROOT — no renamed export copy needed.

    Without an override the legacy format `<model>-<prompt>` is used (e.g.
    `claude-opus-4-7-no_constraints`), so old trees keep resolving unchanged.
    The mode is omitted because only IMPROVE-AND-REPAIR is supported now."""
    override = _MODEL_OUTPUT_DIRS.get(model)
    if override:
        return override
    return f"{model}-{prompt}"


def improved_case_id(suite_class: str, test_id: str) -> str:
    """Strip the suite-class prefix from a test_id to get a short case folder
    name. e.g. ('BoundedReaderTest', 'BoundedReaderTest_testCloseTest') →
    'testCloseTest'. Falls back to test_id verbatim if it doesn't start with
    the suite prefix."""
    if suite_class and test_id and test_id.startswith(f"{suite_class}_"):
        return test_id[len(suite_class) + 1:]
    return test_id


def improved_dir(
    project_id: str, model: str, prompt: str,
    test_category: str,
    suite_class: Optional[str] = None,
    test_id: Optional[str] = None,
) -> Path:
    """Per-target output folder under data/improved/<model>-<prompt>/<project>/.

    NEW LAYOUT (one folder per suite or per case so artifacts can drop their
    redundant <test_id>_ prefix):
        testsuites/<SuiteClass>/                   — for *_suite categories
        testcases/<SuiteClass>/<CaseName>/         — for *_cases categories

    `suite_class` is required for both. `test_id` is additionally required
    for cases; the inner folder name is derived via `improved_case_id`.
    """
    if not suite_class:
        raise ValueError("suite_class is required for improved_dir (folder name)")
    src = _src_for_category(test_category)
    label = model_prompt_label(model, prompt)
    base = IMPROVED_ROOT / label / project_id / src
    if _is_suite_category(test_category):
        d = base / "testsuites" / suite_class
    else:
        if not test_id:
            raise ValueError("test_id is required for testcases (per-case folder name)")
        d = base / "testcases" / suite_class / improved_case_id(suite_class, test_id)
    d.mkdir(parents=True, exist_ok=True)
    return d


def evosuite_raw_dir(project_id: str) -> Path:
    """Staging dir for raw EvoSuite output (`<Class>_ESTest.java` +
    `<Class>_ESTest_scaffolding.java`, organised by EvoSuite's natural
    per-package layout). Lives under data/baseline/ since EvoSuite is part
    of the raw measurement step."""
    d = BASELINE_ROOT / project_id / "_evosuite_raw"
    d.mkdir(parents=True, exist_ok=True)
    return d


def baseline_meta_dir(project_id: str, class_simple: str) -> Path:
    """Per-CUT-class meta folder under baseline: gate_result.json,
    suite_coverage_comparison.{json,md}. Written by measure_baseline +
    filter_out (the latter for the gate decision)."""
    d = BASELINE_ROOT / project_id / "_meta" / class_simple
    d.mkdir(parents=True, exist_ok=True)
    return d


def original_meta_dir(project_id: str, class_simple: str) -> Path:
    """Per-CUT-class meta folder under original (mirror of baseline's, but
    only for classes that filter_out kept)."""
    d = ORIGINAL_ROOT / project_id / "_meta" / class_simple
    d.mkdir(parents=True, exist_ok=True)
    return d


# ── Flat artifact-naming helper ──────────────────────────────────────────────

@dataclass
class TestArtifacts:
    """One test's flat-named files inside a shared `testsuites/` or
    `testcases/<SuiteClass>/` folder. `test_id` is the file-name prefix
    (e.g. `PosixParserTest` or `PosixParserTest_testDoubleDash2`).

    Used for baseline + original (flat layout). The newer per-target layout
    under data/improved/ uses `ImprovedTargetDir` instead (see below)."""
    base_dir: Path
    test_id: str

    def file(self, suffix: str) -> Path:
        """Return `<base_dir>/<test_id>_<suffix>`."""
        return self.base_dir / f"{self.test_id}_{suffix}"

    @property
    def java(self) -> Path:
        return self.base_dir / f"{self.test_id}.java"

    @property
    def metrics(self) -> Path:
        return self.file("metrics.json")

    @property
    def work_dir(self) -> Path:
        d = self.base_dir / "_work" / self.test_id
        d.mkdir(parents=True, exist_ok=True)
        return d


@dataclass
class ImprovedTargetDir:
    """Self-contained per-target folder under data/improved/. Sibling of
    `TestArtifacts` (which is flat for baseline). Layout:

        <base_dir>/                                # per-suite or per-case
        ├── <ClassName>.java                       # improved test (canonical)
        ├── original/<ClassName>.java              # original test for diff
        ├── metrics.json                           # merged improved+baseline+diff
        ├── state.json
        ├── session.jsonl
        ├── trace.txt
        └── prompt.txt

    Inside this folder no file-name prefix is needed — context comes from the
    folder name (`testsuites/<Suite>/` or `testcases/<Suite>/<Case>/`).
    """
    base_dir: Path
    suite_class: str
    test_id: str                  # 'BoundedReaderTest' for suite, '<Suite>_<method>' for case

    # ── Files ────────────────────────────────────────────────────────────────
    @property
    def metrics_path(self) -> Path:
        return self.base_dir / "metrics.json"

    @property
    def state_path(self) -> Path:
        return self.base_dir / "state.json"

    @property
    def prompt_path(self) -> Path:
        return self.base_dir / "prompt.txt"

    @property
    def session_jsonl_path(self) -> Path:
        return self.base_dir / "session.jsonl"

    @property
    def trace_path(self) -> Path:
        return self.base_dir / "trace.txt"

    # ── Subdirs ──────────────────────────────────────────────────────────────
    @property
    def original_subdir(self) -> Path:
        d = self.base_dir / "original"
        d.mkdir(parents=True, exist_ok=True)
        return d

    @property
    def work_dir(self) -> Path:
        d = self.base_dir / "_work"
        d.mkdir(parents=True, exist_ok=True)
        return d

    # ── Lookups ──────────────────────────────────────────────────────────────
    def improved_java_path(self) -> Optional[Path]:
        """Find the agent's improved Java file (skips files inside original/)."""
        for p in self.base_dir.glob("*.java"):
            return p
        return None

    def original_java_path(self) -> Optional[Path]:
        for p in self.original_subdir.glob("*.java"):
            return p
        return None

    @classmethod
    def from_state_path(cls, state_path: Path) -> "ImprovedTargetDir":
        """Reconstruct from a state.json on disk by reading the JSON."""
        state = json.loads(state_path.read_text(encoding="utf-8"))
        return cls(
            base_dir=state_path.parent,
            suite_class=state.get("suite_class") or state.get("test_id") or "",
            test_id=state.get("test_id") or state.get("file_prefix") or "",
        )


def _test_id_from_path(test_file: str) -> str:
    """e.g. .../PosixParserTest.java → PosixParserTest"""
    return Path(test_file).stem


def _maven_module_from_relpath(project_dir: Path, rel_path: str) -> str:
    """Detect the Maven submodule a source file belongs to.

    Returns the first directory segment of `rel_path` iff that segment is
    itself a Maven module (i.e. `<project_dir>/<segment>/pom.xml` exists).
    Otherwise returns "" (single-module project).

    Examples:
      guava + "guava/src/com/google/.../ForwardingQueue.java"          → "guava"
      guava + "guava-tests/test/com/google/.../ForwardingQueueTest.java" → "guava-tests"
      gson + "gson/src/main/java/.../TypeAdapter.java"                 → "gson"
      itextpdf + "itext/src/main/java/.../PdfImage.java"               → "itext"
      commons-cli + "src/main/java/.../PosixParser.java"               → ""  (single-module)
    """
    rp = rel_path.replace("\\", "/").lstrip("/")
    if "/" not in rp:
        return ""
    first = rp.split("/", 1)[0]
    if first in ("src", "test"):
        return ""
    if (project_dir / first / "pom.xml").exists():
        return first
    return ""


@dataclass
class MavenModuleSpec:
    """How to drive Maven for one (project, target) pair.

    For single-module projects everything points at the project root and
    the `-pl` flag is omitted. For multi-module projects (guava, gson,
    itextpdf, …) the SUT and test may live in different submodules; we
    pass `-pl <test_module> -am` so Maven only operates on that subtree.
    """
    project_root: Path             # absolute path of cloned repo
    sut_module: str                # e.g. "guava" / "gson" / "" for single-module
    test_module: str               # e.g. "guava-tests" — module that owns the test
    sut_classes_dir: Path          # where the SUT .class files live
    src_main_java: Path            # where to point jacococli --sourcefiles

    @property
    def is_multimodule(self) -> bool:
        return bool(self.sut_module or self.test_module)

    @property
    def mvn_module(self) -> str:
        """The single -pl value to pass to Maven (test module wins because
        we're running tests)."""
        return self.test_module or self.sut_module


def resolve_maven_module_spec(project_dir: Path, target: dict) -> MavenModuleSpec:
    """Compute the Maven module layout for one target.

    For single-module: everything points at the project root.
    For multi-module: derive the SUT module from `class_path` and the test
    module from `manual_test_file`, then compute the right `target/classes`
    and `src/main/java` for that SUT module.
    """
    sut_path  = target.get("class_path", "")
    test_path = target.get("manual_test_file", "")

    sut_module  = _maven_module_from_relpath(project_dir, sut_path)
    test_module = _maven_module_from_relpath(project_dir, test_path)
    sut_root  = project_dir / sut_module if sut_module else project_dir

    return MavenModuleSpec(
        project_root    = project_dir,
        sut_module      = sut_module,
        test_module     = test_module,
        sut_classes_dir = sut_root / "target" / "classes",
        src_main_java   = sut_root / "src" / "main" / "java",
    )


def _project_id_from_path(p: Path) -> str:
    """Derive the project_id segment from a data/-tree path.

    Layout:
      data/baseline/<project>/{manual,auto}/{testsuites|testcases/<Suite>}/
      data/original/<project>/{manual,auto}/{testsuites|testcases/<Suite>}/
      data/improved/<model>-<prompt>/<project>/{manual,auto}/...

    Returns "" when the path isn't recognisably under data/.
    """
    rp = p.resolve()
    for root in (BASELINE_ROOT, ORIGINAL_ROOT):
        try:
            rel = rp.relative_to(root)
            return rel.parts[0] if rel.parts else ""
        except ValueError:
            pass
    try:
        rel = rp.relative_to(IMPROVED_ROOT)
        # parts: <model>-<prompt>, <project>, {manual|auto}, ...
        return rel.parts[1] if len(rel.parts) > 1 else ""
    except ValueError:
        pass
    return ""


def save_metrics(artifacts: TestArtifacts, data: dict) -> None:
    """Write metrics.json for one test, using the flat prefix layout."""
    artifacts.metrics.write_text(
        json.dumps(data, indent=2, ensure_ascii=False),
        encoding="utf-8",
    )
    log.debug("Saved metrics: %s", artifacts.metrics)


# ── Per-folder summary CSV ───────────────────────────────────────────────────

_SUMMARY_COLUMNS = [
    "project_id", "test_id", "test_type", "source",
    "compile_success",
    "line_covered", "line_missed", "line_pct",
    "branch_covered", "branch_missed", "branch_pct",
    "method_covered", "method_missed",
    "mutants_killed", "mutants_total", "mutation_score_pct",
]


def write_summary_csv(folder: Path, project_id: str) -> Optional[Path]:
    """Aggregate every `<test_id>_metrics.json` in *folder* into one summary.csv.

    Used by baseline/ and original/ which keep the FLAT prefix layout.
    For the per-target improved/ layout, use `write_improved_summary_csv`.

    Written in place at `<folder>/summary.csv`. Idempotent: every call
    rebuilds the file from scratch so the rows always match what's on disk.
    Returns the path to the written CSV, or None when the folder has no
    metrics files yet.
    """
    import csv

    if not folder.exists():
        return None
    metric_files = sorted(folder.glob("*_metrics.json"))
    if not metric_files:
        return None

    rows: list[dict] = []
    for mfile in metric_files:
        try:
            data = json.loads(mfile.read_text(encoding="utf-8"))
        except Exception:
            continue
        row = {"project_id": project_id,
               "test_id": mfile.stem.removesuffix("_metrics")}
        for col in _SUMMARY_COLUMNS:
            if col in row:
                continue
            row[col] = data.get(col, "")
        rows.append(row)

    if not rows:
        return None

    csv_path = folder / "summary.csv"
    with csv_path.open("w", newline="", encoding="utf-8") as f:
        writer = csv.DictWriter(f, fieldnames=_SUMMARY_COLUMNS, extrasaction="ignore")
        writer.writeheader()
        writer.writerows(rows)
    log.info(f"[summary_csv] {csv_path.relative_to(DATA_ROOT)} — {len(rows)} row(s)")
    return csv_path


def write_improved_summary_csv(parent: Path, project_id: str) -> Optional[Path]:
    """Aggregate every per-target metrics.json under *parent* into one summary.csv
    at <parent>/summary.csv. Recurses one level for testsuites/ (each child
    folder = one suite) and two levels for testcases/ (Suite/Case/).

    Reads the MERGED metrics format: lifts `improved.<field>` into the row.
    """
    import csv

    if not parent.exists():
        return None

    # testsuites/ → per-suite folders; testcases/ → Suite/Case folders.
    metric_files = sorted(parent.glob("*/metrics.json")) \
                 + sorted(parent.glob("*/*/metrics.json"))
    if not metric_files:
        return None

    rows: list[dict] = []
    for mfile in metric_files:
        try:
            blob = json.loads(mfile.read_text(encoding="utf-8"))
        except Exception:
            continue
        inner = blob.get("improved") if isinstance(blob, dict) and "improved" in blob else blob
        if not isinstance(inner, dict):
            continue
        # Folder name carries identity in the new layout. For suites: <Suite>/
        # For cases: <Suite>/<Case>/ — use Suite_Case to match flat-layout test_id.
        rel_parts = mfile.parent.relative_to(parent).parts
        if len(rel_parts) == 1:
            test_id = rel_parts[0]
        else:
            suite, case = rel_parts[0], rel_parts[1]
            test_id = inner.get("test_id") or f"{suite}_{case}"
        row = {"project_id": project_id, "test_id": test_id}
        for col in _SUMMARY_COLUMNS:
            if col in row:
                continue
            row[col] = inner.get(col, "")
        rows.append(row)

    if not rows:
        return None

    csv_path = parent / "summary.csv"
    with csv_path.open("w", newline="", encoding="utf-8") as f:
        writer = csv.DictWriter(f, fieldnames=_SUMMARY_COLUMNS, extrasaction="ignore")
        writer.writeheader()
        writer.writerows(rows)
    try:
        rel = csv_path.relative_to(DATA_ROOT)
    except ValueError:
        rel = csv_path
    log.info(f"[summary_csv] {rel} — {len(rows)} row(s) (improved)")
    return csv_path


def _load_json_if_exists(path: Path) -> Optional[dict]:
    if not path.exists():
        return None
    try:
        return json.loads(path.read_text(encoding="utf-8"))
    except Exception:
        return None


def _baseline_metrics_path(project_id: str, test_category: str,
                            suite_class: str, test_id: str) -> Path:
    """Locate the baseline metrics.json for an improved test.

    `suite_class` is the suite the test belongs to (used only for cases).
    `test_id` is the file-name prefix:
       - for suites:  the suite test-class name (== suite_class)
       - for cases:   the split case class name (e.g. SuiteClass_methodName)
    """
    base = original_dir(project_id, test_category,
                        suite_class=suite_class if not _is_suite_category(test_category) else None)
    return base / f"{test_id}_metrics.json"


def _metric_delta(original: dict, improved: dict, key: str):
    a = original.get(key)
    b = improved.get(key)
    if isinstance(a, (int, float)) and isinstance(b, (int, float)):
        return round(b - a, 2)
    return None


def _branches_by_line_to_dict(metrics: dict) -> dict[int, dict]:
    """Index `branches_by_line` (a list of {line, covered_branches, missed_branches})
    by line number for set-diff. Empty dict on missing/malformed data."""
    out: dict[int, dict] = {}
    for entry in (metrics or {}).get("branches_by_line", []) or []:
        ln = entry.get("line")
        if isinstance(ln, int):
            out[ln] = entry
    return out


def _build_merged_metrics(
    *,
    compile_status: str,
    improved_metrics: Optional[dict],
    baseline_metrics: Optional[dict],
    baseline_metrics_path: Path,
) -> dict:
    """Build the unified per-target metrics.json blob.

    Replaces the old (metrics.json + metrics_comparison.json + .md) trio.
    Layout:
        {
          "compile_status": "...",
          "improved":  { ...full improved metrics... },
          "baseline":  { ...full baseline metrics... },
          "comparison": {
              "deltas": {line_pct, branch_pct, mutation_score_pct},
              "covered_lines_diff":  {only_in_improved, only_in_baseline, common_count},
              "branches_by_line_diff": [ {line, baseline, improved, delta}, ... ],
              "killed_mutants_diff": {only_in_improved, only_in_baseline},
              "verdict": {is_exact_match, is_superset_or_equal}
          }
        }
    """
    imp = improved_metrics or {}
    base = baseline_metrics or {}

    deltas = {
        "line_pct":           _metric_delta(base, imp, "line_pct"),
        "branch_pct":         _metric_delta(base, imp, "branch_pct"),
        "mutation_score_pct": _metric_delta(base, imp, "mutation_score_pct"),
    }

    imp_lines = set(imp.get("covered_lines") or [])
    base_lines = set(base.get("covered_lines") or [])
    covered_lines_diff = {
        "only_in_improved": sorted(imp_lines - base_lines),
        "only_in_baseline": sorted(base_lines - imp_lines),
        "common_count": len(imp_lines & base_lines),
    }

    imp_bbl = _branches_by_line_to_dict(imp)
    base_bbl = _branches_by_line_to_dict(base)
    all_lines = sorted(set(imp_bbl) | set(base_bbl))
    branches_by_line_diff = []
    for ln in all_lines:
        b = base_bbl.get(ln, {}).get("covered_branches", 0)
        i = imp_bbl.get(ln, {}).get("covered_branches", 0)
        if b != i:
            branches_by_line_diff.append({"line": ln, "baseline": b, "improved": i, "delta": i - b})

    # killed_mutants is a list of DICTS (one per PIT mutation), so we can't
    # set() them directly — dicts aren't hashable. Use _killed_mutant_key()
    # to derive a hashable identity tuple per mutant; preserve the original
    # dicts in the diff output so the report stays human-readable.
    # (Same key function is used by Step 6 _is_exact_match, so the two
    #  diff/verdict views stay consistent.)
    imp_by_key  = {_killed_mutant_key(m): m
                   for m in (imp.get("killed_mutants") or [])
                   if isinstance(m, dict)}
    base_by_key = {_killed_mutant_key(m): m
                   for m in (base.get("killed_mutants") or [])
                   if isinstance(m, dict)}
    imp_mut_keys  = set(imp_by_key.keys())
    base_mut_keys = set(base_by_key.keys())
    killed_mutants_diff = {
        "only_in_improved": [imp_by_key[k]  for k in sorted(imp_mut_keys  - base_mut_keys)],
        "only_in_baseline": [base_by_key[k] for k in sorted(base_mut_keys - imp_mut_keys)],
    }

    # Exact-match criterion used by Step 6 (build_evaluation_set):
    #   line covered_lines set-equal AND branches_by_line set-equal
    #   AND killed_mutants ⊇ baseline.killed_mutants
    is_lines_equal   = (imp_lines == base_lines)
    is_branches_equal = (len(branches_by_line_diff) == 0)
    is_mut_superset  = base_mut_keys.issubset(imp_mut_keys)

    return {
        "compile_status": compile_status,
        "baseline_metrics_path": str(baseline_metrics_path),
        "baseline_metrics_available": bool(baseline_metrics),
        "improved_metrics_available": bool(improved_metrics),
        "improved": imp,
        "baseline": base,
        "comparison": {
            "deltas": deltas,
            "covered_lines_diff":   covered_lines_diff,
            "branches_by_line_diff": branches_by_line_diff,
            "killed_mutants_diff":  killed_mutants_diff,
            "verdict": {
                "is_exact_match":         is_lines_equal and is_branches_equal and is_mut_superset,
                "is_superset_or_equal":   is_mut_superset,
            },
        },
    }


def write_merged_metrics(
    *,
    target_dir: ImprovedTargetDir,
    project_id: str,
    test_category: str,
    improved_metrics: Optional[dict],
    compile_status: str,
) -> Path:
    """Write the unified per-target metrics.json (replaces the old 3-file split)."""
    baseline_path = _baseline_metrics_path(
        project_id, test_category, target_dir.suite_class, target_dir.test_id
    )
    baseline_metrics = _load_json_if_exists(baseline_path)
    blob = _build_merged_metrics(
        compile_status=compile_status,
        improved_metrics=improved_metrics,
        baseline_metrics=baseline_metrics,
        baseline_metrics_path=baseline_path,
    )
    target_dir.metrics_path.write_text(
        json.dumps(blob, indent=2, ensure_ascii=False), encoding="utf-8")
    return target_dir.metrics_path


# ── Per-test measurement ──────────────────────────────────────────────────────

def _measure_and_save(
    test_java: str,
    target_class_fqn: str,
    project_classpath: str,
    sut_classes_dir: str,
    artifacts: TestArtifacts,
    cfg,
    project_dir: Path,
    is_evosuite: bool = False,
    scaffolding_java: str = None,
    extra_meta: dict = None,
    mvn_module: str = "",
) -> dict:
    """Measure one test and write `<test_id>_metrics.json` into artifacts.base_dir.

    Also copies the .java itself to `<test_id>.java` in the same folder so
    the on-disk record is self-contained (the original test file in the
    cloned repo may move/disappear later).

    `mvn_module` is the Maven `-pl` value for multi-module projects (e.g.
    "guava-tests"); pass "" for single-module projects."""
    # Co-locate the .java with its metrics. Use copy2 to preserve mtime;
    # silently skip if `test_java` already IS the destination.
    src = Path(test_java)
    if src.resolve() != artifacts.java.resolve():
        shutil.copy2(src, artifacts.java)

    work = artifacts.work_dir
    result = coverage_runner.measure(
        test_java_path=test_java,
        target_class_fqn=target_class_fqn,
        project_classpath=project_classpath,
        sut_classes_dir=str(sut_classes_dir),
        work_dir=str(work),
        cfg=cfg,
        project_dir=str(project_dir),
        is_evosuite=is_evosuite,
        scaffolding_java=scaffolding_java,
        mvn_module=mvn_module,
    )
    if result is None:
        result = {"compile_success": False}
    _merge_mutation_metrics(
        result=result,
        test_java=test_java,
        target_class_fqn=target_class_fqn,
        project_classpath=project_classpath,
        sut_classes_dir=str(sut_classes_dir),
        work_dir=str(work),
        cfg=cfg,
        is_evosuite=is_evosuite,
        scaffolding_java=scaffolding_java,
    )
    if extra_meta:
        result.update(extra_meta)
    save_metrics(artifacts, result)
    # Drop the per-test JaCoCo/PIT intermediates (HTML reports, jacoco.exec, PIT
    # mutation reports) now that the metrics are extracted into _metrics.json —
    # they are large (~0.2 GB/test) and otherwise accumulate until the disk fills
    # (a full 14-repo run is ~120 GB of _work). Set `measurement.keep_work: true`
    # in config to retain them for manual inspection.
    if not bool(getattr(getattr(cfg, "measurement", None), "keep_work", False)):
        shutil.rmtree(work, ignore_errors=True)
    # Refresh the folder's summary.csv so it always reflects every test
    # already measured into this folder. This couples summary generation
    # with measurement so the same logic works for both baseline (in
    # data/original/.../{testsuites,testcases/<Suite>}/) and improved
    # (in data/improved/<model>/.../{testsuites,testcases/<Suite>}/) trees.
    proj_id = _project_id_from_path(artifacts.base_dir)
    if proj_id:
        write_summary_csv(artifacts.base_dir, proj_id)
    return result


def _merge_mutation_metrics(
    *, result: dict, test_java: str, target_class_fqn: str,
    project_classpath: str, sut_classes_dir: str, work_dir: str, cfg,
    is_evosuite: bool, scaffolding_java: Optional[str],
) -> None:
    """Run PIT (when enabled) and merge its namespaced keys into `result`.

    PIT keys (`mutants_*`, `mutation_*`, `killed_mutants*`) never collide
    with coverage keys, so `result.update(mutation)` is safe. A None return
    from `mutation_measurement.measure` means "configured but report
    unparseable" — we leave `result` untouched.
    """
    if not mutation_measurement.enabled(cfg):
        return
    if not result.get("compile_success"):
        return
    try:
        mutation = mutation_measurement.measure(
            test_java_path=test_java,
            target_class_fqn=target_class_fqn,
            project_classpath=project_classpath,
            sut_classes_dir=sut_classes_dir,
            work_dir=work_dir,
            cfg=cfg,
            is_evosuite=is_evosuite,
            scaffolding_java=scaffolding_java,
        )
    except Exception as e:
        log.warning("[mutation_measurement] failed for %s: %s", test_java, e)
        result["mutation_error"] = str(e)
        return
    if mutation:
        result.update(mutation)


# ── Step 3: Baseline measurement ──────────────────────────────────────────────

def measure_baseline(
    subject: dict, target: dict,
    project_dir: Path, classpath: str, sut_classes_dir: Path,
    evosuite_out_dir: Path, cfg
):
    """Baseline measurement (Step 2b) — SUITE level only, written to data/baseline/.

      Phase 1   Measure manual + evosuite SUITES → write to
                data/baseline/<proj>/{manual,auto}/testsuites/<SuiteClass>{.java,
                _metrics.json,…}.
      Phase 1.5 Suite-level comparison → write to
                data/baseline/<proj>/_meta/<Class>/suite_coverage_comparison.{json,md}.
                The gate is NOT applied here — it belongs to the separate
                `filter_out` step (2c). Every measured class is written,
                regardless of whether it would pass the thresholds.

    Splitting the suites into test cases and measuring each case happens
    AFTER the gate, in `split_and_measure_cases` (Step 3), and only for the
    classes that reached data/original/.
    """
    class_name = target["class_name"]
    project_id = subject["project_id"]

    # Resolve the Maven module layout for this (project, target) pair.
    # For single-module projects (Apache Commons etc.) this is a no-op —
    # `mod.is_multimodule` is False and we keep the caller-supplied
    # classpath + sut_classes_dir. For multi-module repos (guava, gson,
    # itextpdf), we override both so coverage runs against the right
    # submodule.
    mod = resolve_maven_module_spec(project_dir, target)
    if mod.is_multimodule:
        log.info(f"  multi-module: sut={mod.sut_module or '<root>'} "
                 f"test={mod.test_module or '<root>'} → "
                 f"sut_classes={mod.sut_classes_dir}")
        sut_classes_dir = mod.sut_classes_dir
        try:
            classpath = build_executor.extract_classpath(
                project_dir / (mod.sut_module or "."),
                "maven", cfg.tools.maven_path,
            )
        except Exception as e:
            log.warning(f"  multi-module classpath extraction failed: {e}; "
                        f"falling back to subject-level classpath")

    # ─── Phase 1 — Suite-level measurement → data/baseline/ ────────────────
    manual_suite_java = str(project_dir / target["manual_test_file"])
    manual_suite_present = Path(manual_suite_java).exists()
    manual_suite_class = _test_id_from_path(manual_suite_java)
    if not manual_suite_present:
        log.warning(f"Manual test file not found: {manual_suite_java}")
    else:
        log.info(f"Measuring baseline: manual suite ({manual_suite_class})")
        manual_suite_dir = baseline_dir(project_id, "manual_suite")
        suite_artifacts = TestArtifacts(
            base_dir=manual_suite_dir,
            test_id=manual_suite_class,
        )
        _measure_and_save(
            manual_suite_java, class_name, classpath, sut_classes_dir,
            suite_artifacts, cfg, project_dir,
            extra_meta={"test_type": "suite", "source": "manual", "model": "original"},
            mvn_module=mod.mvn_module,
        )

    _evo = evosuite_runner.find_evosuite_outputs(
        str(evosuite_out_dir), class_name
    )
    evo_suite, evo_scaffolding = _evo["test"], _evo["scaffolding"]
    evo_suite_class = _test_id_from_path(evo_suite) if evo_suite else ""
    if not evo_suite:
        log.warning(f"EvoSuite tests not found in {evosuite_out_dir}, "
                    "skipping EvoSuite baseline.")
    else:
        log.info(f"Measuring baseline: EvoSuite suite ({evo_suite_class})")
        evo_suite_dir = baseline_dir(project_id, "auto_suite")
        evo_artifacts = TestArtifacts(
            base_dir=evo_suite_dir,
            test_id=evo_suite_class,
        )
        _measure_and_save(
            evo_suite, class_name, classpath, sut_classes_dir,
            evo_artifacts, cfg, project_dir,
            is_evosuite=True, scaffolding_java=evo_scaffolding,
            extra_meta={"test_type": "suite", "source": "evosuite", "model": "original"},
            mvn_module=mod.mvn_module,
        )

    # ─── Phase 1.5 — Suite-level comparison (gate is now in filter_out) ────
    write_baseline_suite_comparison(
        project_id, class_name,
        manual_test_id=manual_suite_class if manual_suite_present else "",
        evo_test_id=evo_suite_class,
    )


def split_and_measure_cases(
    subject: dict, target: dict,
    project_dir: Path, classpath: str, sut_classes_dir: Path,
    evosuite_out_dir: Path, cfg
) -> Optional[dict]:
    """Step 3 — split the two suites of a GATE-PASSING class into per-test-case
    .java files and measure every case (JaCoCo + PIT), writing to

        data/original/<proj>/{manual,auto}/testcases/<SuiteClass>/<Case>{.java,_metrics.json,…}

    Runs only for classes whose suites are already in data/original/, i.e.
    classes that passed `filter_out` (Step 2c). Classes that failed the gate
    keep suite-level artifacts only, in data/baseline/. Returns a summary dict
    {"manual_cases": n, "auto_cases": n} or None when the class is not in
    data/original/.

    Re-running `filter_out` clears a class's folder under data/original/
    (including its testcases/), so this step has to be re-run afterwards.
    """
    class_name = target["class_name"]
    project_id = subject["project_id"]

    manual_suite_java = str(project_dir / target["manual_test_file"])
    manual_suite_class = _test_id_from_path(manual_suite_java)
    manual_in_original = (original_dir(project_id, "manual_suite")
                          / f"{manual_suite_class}.java").exists()

    _evo = evosuite_runner.find_evosuite_outputs(str(evosuite_out_dir), class_name)
    evo_suite, evo_scaffolding = _evo["test"], _evo["scaffolding"]
    evo_suite_class = _test_id_from_path(evo_suite) if evo_suite else ""
    evo_in_original = bool(evo_suite) and (original_dir(project_id, "auto_suite")
                                           / f"{evo_suite_class}.java").exists()

    if not manual_in_original and not evo_in_original:
        log.info(f"  [split_cases] {class_name}: not in data/original/ "
                 f"(did not pass the gate) — skipping")
        return None

    # Same module resolution as measure_baseline, so cases are measured
    # against the same classpath / classes dir as their suite.
    mod = resolve_maven_module_spec(project_dir, target)
    if mod.is_multimodule:
        sut_classes_dir = mod.sut_classes_dir
        try:
            classpath = build_executor.extract_classpath(
                project_dir / (mod.sut_module or "."), "maven", cfg.tools.maven_path,
            )
        except Exception as e:
            log.warning(f"  multi-module classpath extraction failed: {e}; "
                        f"falling back to subject-level classpath")

    summary = {"manual_cases": 0, "auto_cases": 0}

    if manual_in_original and Path(manual_suite_java).exists():
        split_out = original_dir(project_id, "manual_cases",
                                 suite_class=manual_suite_class)
        log.info(f"Splitting manual test suite into cases → {split_out}")
        splits = test_splitter.split_suite(manual_suite_java, str(split_out),
                                           is_evosuite=False, cfg=cfg)
        log.info(f"  {len(splits)} manual test cases split")
        for sp in splits:
            case_id = Path(sp.java_path).stem
            log.info(f"  Measuring case: {case_id}")
            case_artifacts = TestArtifacts(base_dir=split_out, test_id=case_id)
            _measure_and_save(
                sp.java_path, class_name, classpath, sut_classes_dir,
                case_artifacts, cfg, project_dir,
                extra_meta={"test_type": "case", "source": "manual",
                            "model": "original", "method_name": sp.method_name},
                mvn_module=mod.mvn_module,
            )
        summary["manual_cases"] = len(splits)
        write_summary_csv(split_out, project_id)

    if evo_in_original:
        evo_split_out = original_dir(project_id, "auto_cases",
                                     suite_class=evo_suite_class)
        log.info(f"Splitting EvoSuite test suite into cases → {evo_split_out}")
        evo_splits = test_splitter.split_suite(
            evo_suite, str(evo_split_out),
            is_evosuite=True, scaffolding_java=evo_scaffolding, cfg=cfg,
        )
        log.info(f"  {len(evo_splits)} EvoSuite test cases split")
        for sp in evo_splits:
            case_id = Path(sp.java_path).stem
            log.info(f"  Measuring EvoSuite case: {case_id}")
            case_artifacts = TestArtifacts(base_dir=evo_split_out, test_id=case_id)
            _measure_and_save(
                sp.java_path, class_name, classpath, sut_classes_dir,
                case_artifacts, cfg, project_dir,
                is_evosuite=True, scaffolding_java=sp.scaffolding_path,
                extra_meta={"test_type": "case", "source": "evosuite",
                            "model": "original", "method_name": sp.method_name},
                mvn_module=mod.mvn_module,
            )
        summary["auto_cases"] = len(evo_splits)
        write_summary_csv(evo_split_out, project_id)

    log.info(f"  [split_cases] {class_name}: manual={summary['manual_cases']} "
             f"auto={summary['auto_cases']} cases written to data/original/")
    return summary


def write_baseline_suite_comparison(
    project_id: str, class_name: str,
    *, manual_test_id: str, evo_test_id: str,
) -> None:
    """Write a side-by-side comparison of manual vs EvoSuite SUITE coverage.

    Reads
      data/baseline/<proj>/manual/testsuites/<manual_test_id>_metrics.json
      data/baseline/<proj>/auto/testsuites/<evo_test_id>_metrics.json
    and writes
      data/baseline/<proj>/_meta/<Class>/suite_coverage_comparison.{json,md}

    Skip with a warning when either side is missing.
    """
    class_simple = class_name.split(".")[-1]
    meta_dir = baseline_meta_dir(project_id, class_simple)
    manual_path = (baseline_dir(project_id, "manual_suite")
                   / f"{manual_test_id}_metrics.json") if manual_test_id else None
    evo_path = (baseline_dir(project_id, "auto_suite")
                / f"{evo_test_id}_metrics.json") if evo_test_id else None

    if not manual_path and not evo_path:
        log.warning(f"[suite_comparison] no baseline metrics for {class_name}, skipping")
        return
    if not manual_path or not manual_path.exists():
        log.warning(f"[suite_comparison] manual suite metrics missing for "
                    f"{class_name}; cannot build comparison")
        return
    if not evo_path or not evo_path.exists():
        log.warning(f"[suite_comparison] evosuite suite metrics missing for "
                    f"{class_name}; cannot build comparison")
        return

    manual = json.loads(manual_path.read_text(encoding="utf-8"))
    evo    = json.loads(evo_path.read_text(encoding="utf-8"))

    def _row(src: dict) -> dict:
        row = {
            "line_covered":  src.get("line_covered", 0),
            "line_missed":   src.get("line_missed", 0),
            "line_pct":      src.get("line_pct", 0.0),
            "branch_covered": src.get("branch_covered", 0),
            "branch_missed":  src.get("branch_missed", 0),
            "branch_pct":     src.get("branch_pct", 0.0),
        }
        # Mutation fields: only present if mutation_testing.enabled was set.
        # Pass them through as-is so downstream consumers can tell "not run"
        # (None) from "ran, zero mutants" (0).
        if "mutants_total" in src:
            row["mutants_total"]      = src.get("mutants_total", 0)
            row["mutants_killed"]     = src.get("mutants_killed", 0)
            row["mutation_score_pct"] = src.get("mutation_score_pct", 0.0)
        return row

    m_row = _row(manual)
    e_row = _row(evo)
    delta = {
        "line_covered":   e_row["line_covered"]   - m_row["line_covered"],
        "line_missed":    e_row["line_missed"]    - m_row["line_missed"],
        "line_pct":       round(e_row["line_pct"]    - m_row["line_pct"], 2),
        "branch_covered": e_row["branch_covered"] - m_row["branch_covered"],
        "branch_missed":  e_row["branch_missed"]  - m_row["branch_missed"],
        "branch_pct":     round(e_row["branch_pct"]  - m_row["branch_pct"], 2),
    }
    # Mutation delta only when BOTH suites measured mutation
    has_mutation = "mutation_score_pct" in m_row and "mutation_score_pct" in e_row
    if has_mutation:
        delta["mutants_killed"] = (e_row["mutants_killed"]
                                    - m_row["mutants_killed"])
        delta["mutants_total"]  = (e_row["mutants_total"]
                                    - m_row["mutants_total"])
        delta["mutation_score_pct"] = round(
            e_row["mutation_score_pct"] - m_row["mutation_score_pct"], 2
        )

    winner = {
        "line_pct":   ("evosuite" if delta["line_pct"]   > 0
                       else "manual" if delta["line_pct"]   < 0 else "tie"),
        "branch_pct": ("evosuite" if delta["branch_pct"] > 0
                       else "manual" if delta["branch_pct"] < 0 else "tie"),
    }
    if has_mutation:
        winner["mutation_score_pct"] = (
            "evosuite" if delta["mutation_score_pct"] > 0
            else "manual" if delta["mutation_score_pct"] < 0 else "tie"
        )

    comparison = {
        "project_id":    project_id,
        "target_class":  class_name,
        "manual_suite":   m_row,
        "auto_suite": e_row,
        "delta_evo_minus_manual": delta,
        "winner":        winner,
    }

    # Write JSON (machine-readable)
    json_path = meta_dir / "suite_coverage_comparison.json"
    json_path.write_text(json.dumps(comparison, indent=2), encoding="utf-8")

    # Write Markdown (human-readable)
    def _fmt_delta(v, is_pct=False):
        if isinstance(v, float):
            sign = "+" if v > 0 else ""
            return f"{sign}{v:.2f}" + ("%" if is_pct else "")
        sign = "+" if v > 0 else ""
        return f"{sign}{v}"

    md_lines = [
        f"# Suite Coverage Comparison — {class_name}",
        "",
        f"Project: **{project_id}**",
        "",
        "| Metric            | Manual Suite | EvoSuite Suite | Δ (evo − manual) | Winner   |",
        "|-------------------|--------------|----------------|------------------|----------|",
        (f"| Line covered      | {m_row['line_covered']:>12} | {e_row['line_covered']:>14} "
         f"| {_fmt_delta(delta['line_covered']):>16} | {'—':^8} |"),
        (f"| Line missed       | {m_row['line_missed']:>12} | {e_row['line_missed']:>14} "
         f"| {_fmt_delta(delta['line_missed']):>16} | {'—':^8} |"),
        (f"| **Line %**        | **{m_row['line_pct']:.2f}%** | **{e_row['line_pct']:.2f}%** "
         f"| **{_fmt_delta(delta['line_pct'], is_pct=True)}** | **{winner['line_pct']}** |"),
        (f"| Branch covered    | {m_row['branch_covered']:>12} | {e_row['branch_covered']:>14} "
         f"| {_fmt_delta(delta['branch_covered']):>16} | {'—':^8} |"),
        (f"| Branch missed     | {m_row['branch_missed']:>12} | {e_row['branch_missed']:>14} "
         f"| {_fmt_delta(delta['branch_missed']):>16} | {'—':^8} |"),
        (f"| **Branch %**      | **{m_row['branch_pct']:.2f}%** | **{e_row['branch_pct']:.2f}%** "
         f"| **{_fmt_delta(delta['branch_pct'], is_pct=True)}** | **{winner['branch_pct']}** |"),
    ]
    if has_mutation:
        md_lines += [
            (f"| Mutants killed    | {m_row['mutants_killed']:>12} | "
             f"{e_row['mutants_killed']:>14} | "
             f"{_fmt_delta(delta['mutants_killed']):>16} | {'—':^8} |"),
            (f"| Mutants total     | {m_row['mutants_total']:>12} | "
             f"{e_row['mutants_total']:>14} | "
             f"{_fmt_delta(delta['mutants_total']):>16} | {'—':^8} |"),
            (f"| **Mutation %**    | **{m_row['mutation_score_pct']:.2f}%** | "
             f"**{e_row['mutation_score_pct']:.2f}%** | "
             f"**{_fmt_delta(delta['mutation_score_pct'], is_pct=True)}** | "
             f"**{winner['mutation_score_pct']}** |"),
        ]
    md_lines += [
        "",
        f"_Generated from `{manual_test_id}_metrics.json` and "
        f"`{evo_test_id}_metrics.json`._",
    ]
    md_path = meta_dir / "suite_coverage_comparison.md"
    md_path.write_text("\n".join(md_lines) + "\n", encoding="utf-8")

    log.info(f"[suite_comparison] {class_name}: "
             f"manual line={m_row['line_pct']:.2f}% / evo line={e_row['line_pct']:.2f}% "
             f"(Δ {_fmt_delta(delta['line_pct'], is_pct=True)}), "
             f"manual branch={m_row['branch_pct']:.2f}% / evo branch={e_row['branch_pct']:.2f}% "
             f"(Δ {_fmt_delta(delta['branch_pct'], is_pct=True)})")
    if has_mutation:
        log.info(f"[suite_comparison] {class_name}: "
                 f"manual mutation={m_row['mutation_score_pct']:.2f}% / "
                 f"evo mutation={e_row['mutation_score_pct']:.2f}% "
                 f"(Δ {_fmt_delta(delta['mutation_score_pct'], is_pct=True)})")
    log.info(f"[suite_comparison] wrote {md_path}")


# ── Stage gate: baseline → improvement ───────────────────────────────────────

def _gate_thresholds(cfg) -> Optional[dict]:
    """Read stage_gates.baseline_to_improvement from cfg.

    Returns the parsed thresholds dict, or None if the section is absent
    (which means "no gate, never block").

    Two threshold groups:
      - require_manual     absolute floor for the manual suite
      - require_evosuite   absolute floor for the EvoSuite suite
    Any field set to null disables that check.
    """
    # The gate now lives under pipeline_control.filter_out (co-located with its
    # flag). Fall back to the legacy top-level stage_gates.baseline_to_improvement
    # so older config files still work.
    pc = getattr(cfg, "pipeline_control", None)
    bi = getattr(pc, "filter_out", None) if pc is not None else None
    if bi is None or isinstance(bi, bool):
        sg = getattr(cfg, "stage_gates", None)
        bi = getattr(sg, "baseline_to_improvement", None) if sg is not None else None
    if bi is None:
        return None

    def _ns_to_dict(ns):
        if isinstance(ns, dict) or ns is None:
            return ns or {}
        return {k: getattr(ns, k) for k in vars(ns)}

    return {
        "require_manual":   _ns_to_dict(getattr(bi, "require_manual", {})),
        "require_evosuite": _ns_to_dict(getattr(bi, "require_evosuite", {})),
        "on_fail":          getattr(bi, "on_fail", "skip_class"),
    }


def _check_one_suite_against_thresholds(
    suite_metrics: Optional[dict], thresholds: dict,
) -> dict:
    """For a single suite (manual OR evosuite), return {metric: {value, threshold, ok}}.

    Missing metrics in suite_metrics are treated as 0.0 (i.e. they fail any
    positive threshold). If suite_metrics is None entirely, every metric is
    marked failed with value=None.
    """
    out = {}
    for metric, thr in (thresholds or {}).items():
        if thr is None:
            out[metric] = {"value": None, "threshold": None, "ok": True}
            continue
        if suite_metrics is None:
            out[metric] = {"value": None, "threshold": float(thr), "ok": False}
            continue
        val = suite_metrics.get(metric, 0.0)
        try:
            v = float(val) if val is not None else 0.0
        except (TypeError, ValueError):
            v = 0.0
        out[metric] = {
            "value": v, "threshold": float(thr), "ok": v >= float(thr),
        }
    return out


def _check_baseline_gate(
    project_id: str, class_name: str, cfg,
    *, manual_test_id: str = "", evo_test_id: str = "",
) -> Optional[dict]:
    """Evaluate the stage-1 baseline gate for one class.

    Reads:
      - cfg.stage_gates.baseline_to_improvement (or returns None if absent)
      - data/original/<proj>/manual/testsuites/<manual_test_id>_metrics.json
      - data/original/<proj>/auto/testsuites/<evo_test_id>_metrics.json

    Writes:
      - data/original/<proj>/_meta/<Class>/gate_result.json

    Returns the gate decision dict (also persisted), or None if no gate is
    configured at all (callers treat None as "PASS, never block").
    """
    cfg_dict = _gate_thresholds(cfg)
    if cfg_dict is None:
        return None  # no gate configured

    class_simple = class_name.split(".")[-1]
    meta_dir = baseline_meta_dir(project_id, class_simple)

    def _load_or_none(p: Optional[Path]):
        if not p or not p.exists():
            return None
        try:
            return json.loads(p.read_text(encoding="utf-8"))
        except (json.JSONDecodeError, OSError) as e:
            # A truncated/corrupt baseline metrics file (e.g. from a previously
            # interrupted run) must not crash the whole pipeline. Treat as
            # missing → the suite fails the gate, and the reason is recorded in
            # tests_for_improvements.json's _excluded.
            log.warning(f"[filter_out] unreadable baseline metrics {p}: {e} "
                        f"— treating the suite as missing")
            return None

    manual_path = (baseline_dir(project_id, "manual_suite")
                   / f"{manual_test_id}_metrics.json") if manual_test_id else None
    evo_path    = (baseline_dir(project_id, "auto_suite")
                   / f"{evo_test_id}_metrics.json") if evo_test_id else None
    manual = _load_or_none(manual_path)
    evo    = _load_or_none(evo_path)

    # ── COMBINED gate ────────────────────────────────────────────────────────
    # Each metric is enforced when its threshold is set, SKIPPED when null.
    # A class passes only if EVERY enabled check on BOTH suites passes.
    manual_check = _check_one_suite_against_thresholds(
        manual, cfg_dict["require_manual"]
    )
    evo_check = _check_one_suite_against_thresholds(
        evo, cfg_dict["require_evosuite"]
    )

    def _failed_keys(check: dict) -> list:
        return [k for k, v in check.items() if not v["ok"]]

    manual_failed = _failed_keys(manual_check)
    evo_failed    = _failed_keys(evo_check)

    passed = (not manual_failed) and (not evo_failed)
    reason_parts = []
    if manual_failed:
        reason_parts.append(f"manual_suite failed: {', '.join(manual_failed)}")
    if evo_failed:
        reason_parts.append(f"auto_suite failed: {', '.join(evo_failed)}")
    reason = "; ".join(reason_parts) if reason_parts else "all enabled checks met"

    decision = {
        "passed": passed,
        "on_fail": cfg_dict["on_fail"],
        "manual_suite": manual_check,
        "auto_suite": evo_check,
        "reason": reason,
    }
    (meta_dir / "gate_result.json").write_text(
        json.dumps(decision, indent=2, ensure_ascii=False), encoding="utf-8"
    )
    return decision


# ── Step 2c: filter_out (baseline → original) ────────────────────────────────

def _apply_filter_enabled(cfg) -> bool:
    """Read stage_gates.baseline_to_improvement.apply_filter.

    True  → use the require_* thresholds to decide which classes are copied.
    False → passthrough copy (every class in baseline/ goes to original/).
    Default: True (so existing thresholds aren't accidentally ignored).
    Reads pipeline_control.filter_out.apply_filter (with a legacy fallback to
    the old top-level stage_gates.baseline_to_improvement.apply_filter).
    """
    pc = getattr(cfg, "pipeline_control", None)
    bi = getattr(pc, "filter_out", None) if pc is not None else None
    if bi is None or isinstance(bi, bool):
        sg = getattr(cfg, "stage_gates", None)
        bi = getattr(sg, "baseline_to_improvement", None) if sg is not None else None
    if bi is None:
        return False
    return bool(getattr(bi, "apply_filter", True))


def _filter_out_enabled(pc) -> bool:
    """Whether the Step-2c filter_out step runs. Accepts the new nested form
    (`filter_out: {enabled: bool, ...}`) and the legacy bare bool."""
    fo = getattr(pc, "filter_out", None)
    if isinstance(fo, bool):
        return fo
    return bool(getattr(fo, "enabled", False))


def _copy_class_artifacts_from_baseline(
    project_id: str, class_simple: str,
    manual_test_id: str, evo_test_id: str,
) -> dict:
    """Copy one class's full artifact set from data/baseline/ to data/original/.

    Returns a dict summary of what was copied (suite count + case count per
    source). Idempotent — `shutil.copytree(..., dirs_exist_ok=True)`.
    """
    summary: dict[str, int] = {"manual_suite": 0, "manual_cases": 0,
                                "auto_suite": 0, "auto_cases": 0}

    # ── Suites ─────────────────────────────────────────────────────────────
    for (src_name, test_id) in (("manual_suite", manual_test_id),
                                  ("auto_suite", evo_test_id)):
        if not test_id:
            continue
        src_dir = baseline_dir(project_id, src_name)
        dst_dir = original_dir(project_id, src_name)
        for f in src_dir.glob(f"{test_id}*"):
            if f.is_file():
                shutil.copy2(f, dst_dir / f.name)
                summary[src_name] += 1

    # ── Cases (per-suite subfolder) ─────────────────────────────────────────
    for (cat, test_id) in (("manual_cases", manual_test_id),
                            ("auto_cases", evo_test_id)):
        if not test_id:
            continue
        # Plain path (no mkdir): since Step 3 writes cases straight into
        # data/original/, baseline normally has no testcases/ at all.
        src_cases_dir = (BASELINE_ROOT / project_id / _src_for_category(cat)
                         / "testcases" / test_id)
        if not src_cases_dir.exists():
            continue
        dst_cases_dir = original_dir(project_id, cat, suite_class=test_id)
        for f in src_cases_dir.iterdir():
            if f.is_file():
                shutil.copy2(f, dst_cases_dir / f.name)
                summary[cat] += 1

    # ── Meta files (gate_result + suite_coverage_comparison) ────────────────
    src_meta = baseline_meta_dir(project_id, class_simple)
    dst_meta = original_meta_dir(project_id, class_simple)
    for f in src_meta.iterdir():
        if f.is_file():
            shutil.copy2(f, dst_meta / f.name)

    return summary


def _clear_class_artifacts_from_original(
    project_id: str, class_simple: str,
    manual_test_id: str, evo_test_id: str,
) -> dict:
    """Delete this class's artifacts from data/original/ before re-filtering.

    Used by `filter_out_baseline` so changing the threshold + re-running
    produces a CONSISTENT data/original/ — stale copies of classes that
    USED to pass but no longer do are removed.

    Removes:
      - testsuites/<test_id>{.java, _metrics.json, _state.json, ...}
      - testcases/<test_id>/  (whole per-suite folder)
      - _meta/<Class>/        (whole per-class meta folder)

    Does NOT touch summary.csv (refreshed separately by the caller).
    Returns counts of removed files/dirs for logging."""
    summary = {"suite_files": 0, "case_dirs": 0, "meta_dir": 0}

    # ── Suite-level files (prefix-matched) ──────────────────────────────────
    for (src_name, test_id) in (("manual_suite", manual_test_id),
                                  ("auto_suite", evo_test_id)):
        if not test_id:
            continue
        dst_dir = original_dir(project_id, src_name)
        for f in dst_dir.glob(f"{test_id}*"):
            if f.is_file():
                f.unlink()
                summary["suite_files"] += 1

    # ── Per-suite case folder ───────────────────────────────────────────────
    for (cat, test_id) in (("manual_cases", manual_test_id),
                            ("auto_cases", evo_test_id)):
        if not test_id:
            continue
        case_dir = ORIGINAL_ROOT / project_id / _src_for_category(cat) / "testcases" / test_id
        if case_dir.exists():
            shutil.rmtree(case_dir, ignore_errors=True)
            summary["case_dirs"] += 1

    # ── _meta/<Class>/ folder ────────────────────────────────────────────────
    meta_dir = ORIGINAL_ROOT / project_id / "_meta" / class_simple
    if meta_dir.exists():
        shutil.rmtree(meta_dir, ignore_errors=True)
        summary["meta_dir"] = 1

    return summary


def _refresh_class_summary_csvs(project_id: str,
                                  manual_test_id: str, evo_test_id: str) -> None:
    """Re-write summary.csv in every original/ folder this class touched.

    Called after copy OR after deletion — both cases need the CSV to
    reflect the current on-disk state."""
    if manual_test_id:
        d = original_dir(project_id, "manual_suite")
        write_summary_csv(d, project_id)
        cases = ORIGINAL_ROOT / project_id / "manual" / "testcases" / manual_test_id
        if cases.exists():
            write_summary_csv(cases, project_id)
    if evo_test_id:
        d = original_dir(project_id, "auto_suite")
        write_summary_csv(d, project_id)
        cases = ORIGINAL_ROOT / project_id / "auto" / "testcases" / evo_test_id
        if cases.exists():
            write_summary_csv(cases, project_id)


def filter_out_baseline(subject: dict, target: dict, cfg) -> dict:
    """Re-filter one class. Always clears the class's old copy from
    data/original/ FIRST, then decides whether to re-copy from data/baseline/.

    Returns {"passed": bool, "reason": str} so the caller can (a) write the
    tests_for_improvements.json manifest and (b) gate the improvement step.

    Behaviour:
      stage_gates.baseline_to_improvement.apply_filter = True
        - Evaluate the require_* thresholds against baseline metrics.
        - When the class FAILS → leave the directory state as
          "deleted" — class is absent from data/original/.
        - When the class PASSES → re-copy all suite + case + meta artifacts
          from data/baseline/.
      apply_filter = False
        - Skip threshold evaluation entirely.
        - Re-copy every class's artifacts (full mirror).

    Because deletion happens FIRST, this function is the *single source of
    truth* about what's in data/original/ for this class. Changing your
    threshold from 60% → 70% and re-running with `filter_out: true` will:
      • re-evaluate the gate for every class
      • drop classes that NO LONGER pass (their stale copies disappear)
      • keep classes that still pass (refreshed copies)

    Refreshes summary.csv at each affected folder."""
    class_name = target["class_name"]
    project_id = subject["project_id"]
    class_simple = class_name.split(".")[-1]

    # Derive the suite test ids that measure_baseline wrote
    manual_suite_java = str(project_dir_for_target(subject, target))
    manual_test_id = _test_id_from_path(manual_suite_java) if manual_suite_java else ""
    evo_outputs = evosuite_runner.find_evosuite_outputs(
        str(evosuite_raw_dir(project_id)), class_name,
    )
    evo_test_id = _test_id_from_path(evo_outputs["test"]) if evo_outputs.get("test") else ""

    # ── Step 1: clear any stale copy from previous filter_out runs ──────────
    cleared = _clear_class_artifacts_from_original(
        project_id, class_simple, manual_test_id, evo_test_id,
    )
    if any(cleared.values()):
        log.info(f"[filter_out] {class_name}: cleared stale "
                 f"suite_files={cleared['suite_files']} "
                 f"case_dirs={cleared['case_dirs']} "
                 f"meta_dir={cleared['meta_dir']} from data/original/")

    apply_filter = _apply_filter_enabled(cfg)

    # ── Step 2: evaluate gate (when filtering) or just continue (passthrough) ─
    decision_reason = "apply_filter=false (passthrough copy)"
    if apply_filter:
        gate = _check_baseline_gate(
            project_id, class_name, cfg,
            manual_test_id=manual_test_id, evo_test_id=evo_test_id,
        )
        if gate is None:
            log.warning(f"[filter_out] {class_name}: apply_filter=true but no "
                        "stage_gates.baseline_to_improvement configured — "
                        "treating as passthrough")
            decision_reason = "no stage_gates configured (passthrough)"
        elif not gate.get("passed"):
            log.warning(f"[filter_out] {class_name}: FAILED gate → not copied "
                        f"to data/original/  ({gate.get('reason')})")
            # Still refresh summary.csv so previous rows for this class disappear.
            _refresh_class_summary_csvs(project_id, manual_test_id, evo_test_id)
            return {"passed": False,
                    "reason": gate.get("reason") or "failed stage gate"}
        else:
            log.info(f"[filter_out] {class_name}: passed gate, copying to data/original/")
            decision_reason = gate.get("reason") or "all thresholds met"
    else:
        log.info(f"[filter_out] {class_name}: apply_filter=false, passthrough copy "
                 "to data/original/")

    # ── Step 3: re-copy fresh from data/baseline/ ───────────────────────────
    summary = _copy_class_artifacts_from_baseline(
        project_id, class_simple, manual_test_id, evo_test_id,
    )
    _refresh_class_summary_csvs(project_id, manual_test_id, evo_test_id)
    log.info(f"[filter_out] {class_name}: copied "
             f"manual={summary['manual_suite']}+{summary['manual_cases']} "
             f"evo={summary['auto_suite']}+{summary['auto_cases']} files")
    return {"passed": True, "reason": decision_reason}


# ── tests_for_improvements.json manifest (filter_out → improvement) ──────────
#
# data/tests_for_improvements.json is the selection manifest between filter_out
# and the agent-improvement step. filter_out REGENERATES it (default = every
# class that passed the gate, in dataset.json format); run_claude_code/run_codex
# improve ONLY classes listed in it. Deleting a class from the manifest excludes
# ALL of its tests (manual+auto, suite+cases). Set "preserve_manual_edits": true
# in the file to stop filter_out from overwriting your hand-edited selection.

TESTS_FOR_IMPROVEMENTS_PATH = DATA_ROOT / "tests_for_improvements.json"
# ── Stability-check override (no-op unless STABILITY_MANIFEST is set) ──────
# Points the improvement allowlist at a restricted manifest so only the
# stability-check classes pass the per-class gate.
_stability_manifest = os.environ.get("STABILITY_MANIFEST", "").strip()
if _stability_manifest:
    TESTS_FOR_IMPROVEMENTS_PATH = Path(_stability_manifest)


def _group_excluded_by_repo(flat: list) -> list:
    """Collapse a flat [{repo, class_path, reason}] list into the compact
    grouped shape the manifests use: [{repo, classes:[{class_path, reason}]}].
    Repo appears once; its excluded classes are listed together. Order of first
    appearance is preserved for stable, reviewable diffs."""
    order: list = []
    by_repo: dict = {}
    for x in flat:
        repo = x.get("repo", "")
        if repo not in by_repo:
            by_repo[repo] = []
            order.append(repo)
        by_repo[repo].append({"class_path": x.get("class_path", ""),
                              "reason": x.get("reason", "")})
    return [{"repo": r, "classes": by_repo[r]} for r in order]


def write_tests_for_improvements(subjects_path: str, decisions: dict) -> None:
    """Rewrite data/tests_for_improvements.json from this run's filter results.

    `decisions` maps (project_id, class_path) → {"passed": bool, "reason": str}.
    Entries keep dataset.json format under `projects`; classes that did NOT
    pass are listed under `_excluded` with the gate reason (the user asked for
    a why-not record that doesn't break the json format)."""
    try:
        raw = json.loads(Path(subjects_path).read_text(encoding="utf-8"))
    except Exception as e:
        log.warning(f"[tests_for_improvements] cannot read dataset "
                    f"{subjects_path}: {e}")
        return

    existing = {}
    if TESTS_FOR_IMPROVEMENTS_PATH.exists():
        try:
            existing = json.loads(
                TESTS_FOR_IMPROVEMENTS_PATH.read_text(encoding="utf-8"))
        except Exception:
            existing = {}
    if existing.get("preserve_manual_edits"):
        log.info("[tests_for_improvements] preserve_manual_edits=true — "
                 "keeping the hand-edited manifest, not regenerating")
        return

    # MERGE, don't clobber (review finding): a partial run — e.g. one project's
    # build failed, or a subset --subjects — must not erase classes that passed
    # the gate in an earlier run. Classes WITH a fresh decision are updated;
    # classes WITHOUT one carry their previous manifest status forward.
    prev_kept: set = set()
    for e in existing.get("projects", []) or []:
        prepo = (e.get("repo", "") or "")
        for pcp in e.get("class_paths", []) or []:
            prev_kept.add((prepo, pcp))
    # Read previous _excluded — accept BOTH the old flat shape
    # ({repo, class_path, reason} per entry) and the new grouped shape
    # ({repo, classes:[{class_path, reason}]}) so a re-run after the format
    # change still carries forward correctly.
    prev_excluded: dict = {}
    for x in existing.get("_excluded", []) or []:
        xrepo = x.get("repo", "")
        if isinstance(x.get("classes"), list):           # new grouped shape
            for c in x["classes"]:
                prev_excluded[(xrepo, c.get("class_path", ""))] = \
                    c.get("reason", "")
        else:                                            # old flat shape
            prev_excluded[(xrepo, x.get("class_path", ""))] = \
                x.get("reason", "")

    projects, excluded = [], []
    for entry in raw if isinstance(raw, list) else []:
        repo = entry.get("repo", "")
        pid = repo.split("/")[-1]
        kept_c, kept_t = [], []
        for cp, tp in zip(entry.get("class_paths", []) or [],
                          entry.get("test_paths", []) or []):
            d = decisions.get((pid, cp))
            if d is not None:                       # fresh decision wins
                if d.get("passed"):
                    kept_c.append(cp)
                    kept_t.append(tp)
                else:
                    excluded.append({"repo": repo, "class_path": cp,
                                     "reason": d.get("reason",
                                                     "failed stage gate")})
            elif (repo, cp) in prev_kept:           # carry forward: still in
                kept_c.append(cp)
                kept_t.append(tp)
            elif (repo, cp) in prev_excluded:       # carry forward: still out
                excluded.append({"repo": repo, "class_path": cp,
                                 "reason": prev_excluded[(repo, cp)]})
            else:                                   # never processed
                excluded.append({
                    "repo": repo, "class_path": cp,
                    "reason": "not processed in this run (no gate decision "
                              "yet — e.g. build/classpath failed or the "
                              "project was skipped); re-run measure_baseline "
                              "+ filter_out for this project to include it",
                })
        if kept_c:
            projects.append(
                {"repo": repo, "class_paths": kept_c, "test_paths": kept_t})

    blob = {
        "projects": projects,
        "_excluded": _group_excluded_by_repo(excluded),
    }
    TESTS_FOR_IMPROVEMENTS_PATH.write_text(
        json.dumps(blob, indent=2, ensure_ascii=False), encoding="utf-8")
    n_kept = sum(len(p["class_paths"]) for p in projects)
    log.info(f"[tests_for_improvements] wrote {n_kept} class(es) "
             f"(+{len(excluded)} excluded with reasons) → "
             f"{TESTS_FOR_IMPROVEMENTS_PATH.relative_to(PROJECT_ROOT)}")


def _improvement_manifest_allowlist() -> Optional[set]:
    """Read data/tests_for_improvements.json → set of (project_id, class_path).

    Returns None ONLY when the manifest is absent or is still the unpopulated
    skeleton (never written by a filter run AND not hand-maintained) — callers
    then fall back to in-run filter decisions / legacy behaviour.

    Fail-closed rules (review findings):
      • preserve_manual_edits=true → the projects list IS the selection, even
        when empty (an emptied hand-edited manifest means "improve nothing",
        NOT "improve everything").
      • unparseable JSON → empty set (block all). A corrupt manifest must not
        silently degrade into paid agent runs on unfiltered classes.
      • skeleton detection uses key ABSENCE of `_excluded` (generated
        manifests always carry the key, even as []), not its truthiness.
    """
    p = TESTS_FOR_IMPROVEMENTS_PATH
    if not p.exists():
        return None
    try:
        blob = json.loads(p.read_text(encoding="utf-8"))
    except Exception as e:
        log.error(f"[tests_for_improvements] {p} is not valid JSON ({e}) — "
                  f"BLOCKING all improvement until the file is fixed/deleted.")
        return set()
    projects = blob.get("projects")
    preserve = bool(blob.get("preserve_manual_edits"))
    if not preserve and not projects and "_excluded" not in blob:
        return None        # untouched skeleton — treat as "no manifest yet"
    allow: set = set()
    for e in projects or []:
        pid = (e.get("repo", "") or "").split("/")[-1]
        for cp in e.get("class_paths", []) or []:
            allow.add((pid, cp))
    return allow


def improvement_allowed(subject: dict, target: dict,
                        filter_decision: Optional[dict]) -> bool:
    """Decide whether the agent-improvement step may run for this class.

    Priority:
      1. manifest with preserve_manual_edits=true → manifest IS the selection;
      2. filter ran in THIS run → use its fresh decision (the manifest is
         rewritten from these same decisions after the loop);
      3. manifest exists from an earlier run → use it;
      4. nothing known → allow (legacy behaviour).
    """
    pid = subject.get("project_id", "")
    cp = target.get("class_path", "")
    key = (pid, cp)

    preserve = False
    if TESTS_FOR_IMPROVEMENTS_PATH.exists():
        try:
            preserve = bool(json.loads(
                TESTS_FOR_IMPROVEMENTS_PATH.read_text(encoding="utf-8")
            ).get("preserve_manual_edits"))
        except Exception:
            preserve = False
    allow = _improvement_manifest_allowlist()

    if preserve and allow is not None:
        allowed = key in allow
        if allowed and filter_decision is not None \
                and not filter_decision.get("passed"):
            # The hand-maintained manifest wins, but the user should know the
            # class FAILED today's gate (its data/original artifacts were just
            # cleared, so only suite-level targets remain improvable).
            log.warning(
                f"  [improvement] {pid}/{Path(cp).stem}: manifest "
                f"(preserve_manual_edits) overrides a FAILED fresh gate "
                f"({filter_decision.get('reason', '')[:120]}) — improving "
                f"anyway per the manifest")
        return allowed
    if filter_decision is not None:
        return bool(filter_decision.get("passed"))
    if allow is not None:
        return key in allow
    return True


def project_dir_for_target(subject: dict, target: dict) -> str:
    """Return absolute path of the manual test file inside its cloned repo."""
    project_id = subject["project_id"]
    return str(git_manager.get_project_dir(project_id) / target["manual_test_file"])


# ══════════════════════════════════════════════════════════════════════════════
# Improvement artifact layout
#
#   ImprovePrompts/<project>/<class>/<prompt_version>/<test_category>/<test_key>/
#       improvement_prompt.txt (one per prompt_version × test target — Step 1 dry run)
#
#   data/improved/<model>/<prompt>/<mode>/<project>/{manual|auto}/testsuites/
#       <TestClass>.java
#       <TestClass>_metrics.json
#       <TestClass>_state.json, _session.jsonl, _trace.txt, _prompt.txt,
#                   _compile_log.jsonl, _compile_stderr.txt, _diff.patch,
#                   _metrics_comparison.json, _metrics_comparison.md,
#                   _original.java
#       _work/<TestClass>/         JaCoCo + PIT intermediates
#
#   data/improved/<model>/<prompt>/<mode>/<project>/{manual|auto}/testcases/<SuiteClass>/
#       <CaseClass>.java
#       <CaseClass>_metrics.json
#       <CaseClass>_state.json …                same flat-prefix convention
# ══════════════════════════════════════════════════════════════════════════════

# ── Path helpers ──────────────────────────────────────────────────────────────

def improve_prompts_dir(project_id: str, class_name: str,
                        prompt_version: str, test_category: str,
                        test_key: str = "") -> Path:
    """Top-level tree for Step 1 debug prompts (one entry per prompt version)."""
    d = (IMPROVE_PROMPTS_ROOT / project_id / class_name.split(".")[-1]
         / prompt_version / test_category)
    if test_key:
        d = d / test_key
    d.mkdir(parents=True, exist_ok=True)
    return d


# ── Target iteration (reuses baseline-split files) ────────────────────────────

@dataclass
class ImprovementTarget:
    test_category: str      # "manual_suite" | "manual_cases" | "auto_suite" | "auto_cases"
    test_id: str            # file-name prefix in flat layout (e.g. "PosixParserTest" or "PosixParserTest_testFoo")
    suite_class: str        # parent SUITE class — for cases, names the testcases/<SuiteClass>/ folder
    java_path: str          # absolute path to source test file
    is_evosuite: bool
    scaffolding_java: str   # empty string when not evosuite


import functools as _functools


@_functools.lru_cache(maxsize=1)
def _stability_target_allowlist():
    """Return a set of (project_id, test_category, suite_class, test_id) the
    stability check restricts improvement to, or None when unset (no-op).

    Driven by env STABILITY_TARGET_ALLOWLIST → a json file
    {"targets": [[project_id, test_category, suite_class, test_id], ...]}.
    """
    p = os.environ.get("STABILITY_TARGET_ALLOWLIST", "").strip()
    if not p:
        return None
    blob = json.loads(Path(p).read_text(encoding="utf-8"))
    return {tuple(t) for t in blob.get("targets", [])}


def _stability_target_allowed(project_id, test_category, suite_class, test_id):
    allow = _stability_target_allowlist()
    if allow is None:
        return True
    return (project_id, test_category, suite_class, test_id) in allow


def _iter_improvement_targets(
    subject: dict, target: dict,
    project_dir: Path, evosuite_out_dir: Path
):
    """Yield ImprovementTarget objects for one (subject, class) pair.

    Covers manual_suite / manual_cases / auto_suite / auto_cases.
    For *cases*, split files are read from
        data/original/<proj>/{manual,auto}/testcases/<SuiteClass>/
    which `measure_baseline` populates via the JavaParser splitter.

    When STABILITY_TARGET_ALLOWLIST is set, only the exact
    (project_id, test_category, suite_class, test_id) tuples it lists are
    yielded — used by the stability check to improve individual test cases
    rather than the whole class.
    """
    project_id = subject["project_id"]
    class_name = target["class_name"]
    class_simple = class_name.split(".")[-1]

    # ── manual suite ────────────────────────────────────────────────────────
    manual_suite_path = project_dir / target["manual_test_file"]
    manual_suite_class = manual_suite_path.stem  # e.g. PosixParserTest
    if manual_suite_path.exists() and _stability_target_allowed(
            project_id, "manual_suite", manual_suite_class, manual_suite_class):
        yield ImprovementTarget(
            test_category="manual_suite",
            test_id=manual_suite_class,
            suite_class=manual_suite_class,
            java_path=str(manual_suite_path.resolve()),
            is_evosuite=False,
            scaffolding_java="",
        )
    else:
        log.warning(f"[iter_targets] manual suite not found: {manual_suite_path}")

    # ── manual cases (split files from baseline phase 2) ────────────────────
    if manual_suite_path.exists():
        cases_dir = original_dir(project_id, "manual_cases",
                                  suite_class=manual_suite_class)
        case_files = sorted(cases_dir.glob("*.java"))
        if not case_files:
            log.warning(f"[iter_targets] no manual case splits under {cases_dir} "
                        "(run measure_baseline first)")
        for jf in case_files:
            if not _stability_target_allowed(
                    project_id, "manual_cases", manual_suite_class, jf.stem):
                continue
            yield ImprovementTarget(
                test_category="manual_cases",
                test_id=jf.stem,                  # e.g. PosixParserTest_testFoo
                suite_class=manual_suite_class,
                java_path=str(jf.resolve()),
                is_evosuite=False,
                scaffolding_java="",
            )

    # ── evosuite suite ──────────────────────────────────────────────────────
    _evo = evosuite_runner.find_evosuite_outputs(str(evosuite_out_dir), class_name)
    evo_suite = _evo.get("test")
    evo_scaffolding = _evo.get("scaffolding") or ""
    evo_suite_class = Path(evo_suite).stem if evo_suite else ""
    if evo_suite and Path(evo_suite).exists() and _stability_target_allowed(
            project_id, "auto_suite", evo_suite_class, evo_suite_class):
        yield ImprovementTarget(
            test_category="auto_suite",
            test_id=evo_suite_class,              # e.g. PosixParser_ESTest
            suite_class=evo_suite_class,
            java_path=str(Path(evo_suite).resolve()),
            is_evosuite=True,
            scaffolding_java=str(Path(evo_scaffolding).resolve()) if evo_scaffolding else "",
        )
    else:
        log.warning(f"[iter_targets] evosuite suite not found in {evosuite_out_dir}")

    # ── evosuite cases (split files from baseline phase 2) ──────────────────
    if evo_suite_class:
        evo_cases_dir = original_dir(project_id, "auto_cases",
                                      suite_class=evo_suite_class)
        evo_files = sorted(
            jf for jf in evo_cases_dir.glob("*.java")
            if not jf.stem.endswith("_scaffolding")
        )
        if not evo_files:
            log.warning(f"[iter_targets] no evosuite case splits under {evo_cases_dir}")
        for jf in evo_files:
            if not _stability_target_allowed(
                    project_id, "auto_cases", evo_suite_class, jf.stem):
                continue
            scaf = evo_cases_dir / f"{jf.stem}_scaffolding.java"
            yield ImprovementTarget(
                test_category="auto_cases",
                test_id=jf.stem,
                suite_class=evo_suite_class,
                java_path=str(jf.resolve()),
                is_evosuite=True,
                scaffolding_java=(
                    str(scaf.resolve()) if scaf.exists()
                    else str(Path(evo_scaffolding).resolve()) if evo_scaffolding else ""
                ),
            )


# ── Shared closure builders (classpath + compile_fn) ──────────────────────────

def _build_cp_and_compile_fn(
    is_evosuite: bool, project_classpath: str, compiled_dir: str,
    scaffolding_java: str, cfg
):
    """Return (classpath, compile_fn) tailored to manual vs EvoSuite."""
    if is_evosuite:
        cp = coverage_runner.build_evosuite_classpath(project_classpath, compiled_dir, cfg)
    else:
        cp = coverage_runner.build_manual_classpath(project_classpath, compiled_dir, cfg)

    _java_home_compile = getattr(cfg.tools, "java_home_compile", "")
    if _java_home_compile:
        javac_path = str(Path(_java_home_compile) / "bin" / "javac")
    else:
        java_path = cfg.tools.java_path or "java"
        javac_path = java_path.replace("/java", "/javac") if "/java" in java_path else "javac"

    def compile_fn(java_file, classpath, out):
        extra = [scaffolding_java] if scaffolding_java else None
        return coverage_runner.compile_test(java_file, classpath, out, javac_path, extra)

    return cp, compile_fn


# ══════════════════════════════════════════════════════════════════════════════
# Step 1: generate_improvement_prompts (inspection)
# ══════════════════════════════════════════════════════════════════════════════

def _indent_quoted(s: str, indent: str = "        ") -> str:
    """Render a multi-line string as Python implicit-concatenated literals
    (one line per source line, each `"..."` quoted, indented). Used by the
    preview so the system_prompt block reads naturally even when long."""
    lines = s.rstrip("\n").splitlines() or [""]
    return "\n".join(f'{indent}"{ln}\\n"' for ln in lines)


def _build_claude_prompt_preview(
    *, target, cut_source: str, prompt_version: str, cfg
) -> str:
    """Three-section preview of what the Claude Code SDK would send.

    Faithful as far as the SDK options + user message we control —
    NOT faithful for the Claude Code "preset" default system prompt
    (depends on CLI version, can only be described, not reproduced).
    """
    import agent_with_repair

    test_file_name = Path(target.java_path).name
    cut_class_simple = (
        Path(cut_source).stem if cut_source else
        target.suite_class.split(".")[-1] if target.suite_class else "<unknown>"
    )

    # SDK option values mirror agent_with_repair.run_claude_session_for_one_target
    agent_cfg = getattr(cfg, "agent", None)
    models = _models_from_section(agent_cfg) if agent_cfg is not None else []
    model_str = ", ".join(f'"{m}"' for m in models) if models else "<not configured>"
    max_turns = int(getattr(agent_cfg, "max_turns_with_repair", 10)) if agent_cfg else 10
    permission_mode = (
        str(getattr(agent_cfg, "permission_mode", "acceptEdits")) if agent_cfg
        else "acceptEdits"
    )
    allowed_tools = list(getattr(
        agent_cfg, "allowed_tools_with_repair",
        ["Read", "Write", "Edit", "Bash", "Glob", "Grep"]
    )) if agent_cfg else ["Read", "Write", "Edit", "Bash", "Glob", "Grep"]

    # Build the actual per-CUT system_prompt the SDK call will use, so the
    # preview is byte-identical to the live run.
    sys_prompt_block = agent_with_repair.build_system_prompt_for_cut(
        cut_class_simple=cut_class_simple,
        has_cut_reference=bool(cut_source),
        cut_path=str(Path(cut_source).resolve()) if cut_source else "",
        original_test_path=str(Path(target.java_path).resolve()),
        scaffolding_path=(str(Path(target.scaffolding_java).resolve())
                          if getattr(target, "scaffolding_java", None)
                          and Path(target.scaffolding_java).exists() else ""),
    )

    # Extended thinking / effort (only shown if user set them in cfg.agent)
    thinking_cfg = getattr(agent_cfg, "thinking", None) if agent_cfg else None
    effort_cfg   = getattr(agent_cfg, "effort",   None) if agent_cfg else None
    # SimpleNamespace → dict for display
    if thinking_cfg is not None and not isinstance(thinking_cfg, dict):
        thinking_cfg = {
            k: getattr(thinking_cfg, k)
            for k in ("type", "budget_tokens", "display")
            if hasattr(thinking_cfg, k) and getattr(thinking_cfg, k) is not None
        }
    thinking_str = (f'thinking         = {thinking_cfg!r}\n'
                    if thinking_cfg else
                    '# thinking (not set; SDK/CLI default — required="adaptive" for opus-4-8+)\n')
    effort_str   = (f'effort           = "{effort_cfg}"\n'
                    if effort_cfg else
                    '# effort (not set; SDK/CLI default)\n')

    section1 = f'''═══ 1. ClaudeAgentOptions (SDK parameters) ═══

system_prompt = {{
    "type": "preset",
    "preset": "claude_code",
    "append": (
{_indent_quoted(sys_prompt_block["append"])}
    ),
}}
skills           = "all"     # SDK ≥ 0.2.x — auto-enables every SKILL.md in .claude/skills/
setting_sources  = ["project"]
cwd              = <the per-test improved output dir (the working directory)>
allowed_tools    = {allowed_tools}
permission_mode  = "{permission_mode}"
max_turns        = {max_turns}
model            = {model_str}
{thinking_str}{effort_str}'''

    # Section 2: turn-1 user message — byte-identical to what
    # build_filled_template produces inside the live SDK call. Sandbox
    # layout + tool permissions now live in system_prompt (section 1), so
    # the user message is just the rendered task template.
    # (Previously this was section 3, with a "Skills auto-discovered"
    # section 2 in between. That section was deleted because the
    # system_prompt above already names the available skills, and the full
    # SKILL.md descriptions can be inspected directly under .claude/skills/.)
    initial_prompt = agent_with_repair.build_filled_template(
        prompt_version=prompt_version,
        test_java_path=target.java_path,
        cut_source_path=Path(cut_source) if cut_source else None,
    )
    # EvoSuite guard-rails are currently DISABLED (run_claude_session_for_one_target's
    # append_evosuite_constraints defaults to False) — we no longer distinguish
    # EvoSuite vs manual tests. Mirror that here so the preview matches the live
    # run. Re-enable by flipping that default and restoring the append below.
    section2 = (
        "═══ 2. Turn 1 user message (initial_prompt) ═══\n\n"
        + initial_prompt
    )

    return "\n\n".join([section1, section2])


def run_generate_improvement_prompts(
    subject: dict, target: dict,
    project_dir: Path, evosuite_out_dir: Path, cfg
):
    """Build prompt previews for every (prompt_version × target) combination
    and write them to ImprovePrompts/<project>/<class>/<version>/... .

    No LLM calls. This is a pure inspection step so the user can eyeball the
    prompts before any agent run spends money.

    Per-target output:
      • claude_prompt.txt — faithful 3-section preview of what the Claude
                             Code SDK sends (system_prompt + skills + turn-1
                             user message). Mirrors agent_with_repair.py
                             configuration exactly.
      • codex_prompt.txt  — Codex CLI's eager prompt (SKILL.md bodies inlined,
                             since Codex doesn't have setting_sources).

    Note: improvement_prompt.txt (the bare task template) is no longer
    written here — manage it yourself if you need it for template diffing.
    """
    import agent_with_repair
    import codex_agent_with_repair

    class_name = target["class_name"]
    project_id = subject["project_id"]
    cut_source = _find_cut_source(project_dir, class_name)

    # Preview the improve prompt — the same single template the real run uses
    # (llm_refactor.PROMPT_VERSION). Do NOT glob every .txt under prompts/: that
    # folder also holds the system-prompt template, which is not a user message.
    prompt_versions = list(llm_refactor.PROMPT_VERSIONS)

    targets = list(_iter_improvement_targets(
        subject, target, project_dir, evosuite_out_dir
    ))
    if not targets:
        log.warning(f"[generate_improvement_prompts] no improvement targets for {class_name}")
        return

    log.info(f"[generate_improvement_prompts] {class_name}: "
             f"{len(prompt_versions)} prompt version(s) × {len(targets)} target(s)")

    n_written = 0
    for prompt_version in prompt_versions:
        try:
            llm_refactor.load_prompt_template(prompt_version)
        except FileNotFoundError as e:
            log.warning(f"  [generate_improvement_prompts] skip {prompt_version}: {e}")
            continue

        for t in targets:
            tk = t.test_id if not _is_suite_category(t.test_category) else ""
            out = improve_prompts_dir(
                project_id, class_name, prompt_version, t.test_category, tk,
            )

            # Clean up stale files from previous naming schemes so we don't
            # accumulate junk on re-runs.
            for stale in out.glob("actual_prompt*.txt"):
                try:
                    stale.unlink()
                except Exception:
                    pass
            for stale_name in (
                "template_improvement_prompt.txt",
                "improvement_prompt.txt",       # legacy: bare task template
            ):
                stale = out / stale_name
                if stale.exists():
                    try:
                        stale.unlink()
                    except Exception:
                        pass

            # 1. Claude Code preview — faithful to the SDK call
            claude_preview = _build_claude_prompt_preview(
                target=t, cut_source=cut_source,
                prompt_version=prompt_version, cfg=cfg,
            )
            (out / "claude_prompt.txt").write_text(
                claude_preview, encoding="utf-8")

            # 2. Codex preview — system-like text is prepended because this
            # Codex CLI path has no SDK-level system_prompt parameter. Skill
            # bodies are NOT eager-injected; this previews the native-discovery
            # experiment exactly.
            codex_base_prompt = codex_agent_with_repair.build_agent_improvement_prompt(
                test_java_path=t.java_path,
                cut_source=cut_source or "",
                prompt_version=prompt_version,
                working_dir="<the per-test improved output dir>",
            )
            # Option B parity: the live Codex session appends EVOSUITE_TASK_CONSTRAINTS
            # to the prompt for auto (scaffolding) targets BEFORE composing skill
            # cards (codex_agent_with_repair.py). Mirror that here so the preview
            # stays byte-identical for auto targets.
            if t.scaffolding_java:
                codex_base_prompt = codex_base_prompt + agent_with_repair.EVOSUITE_TASK_CONSTRAINTS
            codex_preview = codex_base_prompt
            (out / "codex_prompt.txt").write_text(
                codex_preview, encoding="utf-8")

            n_written += 1
            log.info(f"  [generate_improvement_prompts] {prompt_version}/"
                     f"{t.test_category}/{t.test_id} → "
                     f"{out / 'claude_prompt.txt'}")

    log.info(f"[generate_improvement_prompts] Wrote {n_written} preview pairs under "
             f"{IMPROVE_PROMPTS_ROOT / project_id / class_name.split('.')[-1]}")


# Backward-compatible alias for older configs / scripts.


# ══════════════════════════════════════════════════════════════════════════════
# Compile step (shared by every improvement mode — agent improvement-only +
# agent improve-and-repair, Claude + Codex)
# ══════════════════════════════════════════════════════════════════════════════

def run_compile_improvement_outputs(
    subject: dict, target: dict,
    project_dir: Path, classpath: str, sut_classes_dir: Path,
    evosuite_out_dir: Path, cfg
):
    """Walk every <test_id>_state.json under data/improved/ for this project
    and compile + measure.

    Idempotent: states already in COMPILE_SUCCESS / MAX_ATTEMPTS / LLM_ERROR
    are left untouched. PENDING and COMPILE_FAIL states are (re)compiled.
    """
    class_name = target["class_name"]
    project_id = subject["project_id"]
    prompt_version = llm_refactor.PROMPT_VERSIONS

    # Scan every state.json belonging to this project. NEW LAYOUT:
    #   data/improved/<m>-<p>/<project>/{manual,auto}/testsuites/<Suite>/state.json
    #   data/improved/<m>-<p>/<project>/{manual,auto}/testcases/<Suite>/<Case>/state.json
    if not IMPROVED_ROOT.exists():
        log.info(f"[compile_improvement_outputs] no data/improved/ tree yet, nothing to compile")
        return
    state_files = sorted(IMPROVED_ROOT.glob(
        f"*/{project_id}/*/testsuites/*/state.json"
    )) + sorted(IMPROVED_ROOT.glob(
        f"*/{project_id}/*/testcases/*/*/state.json"
    ))
    if not state_files:
        log.info(f"[compile_improvement_outputs] no state.json files for "
                 f"{project_id} under {IMPROVED_ROOT}")
        return

    log.info(f"[compile_improvement_outputs] {class_name}: scanning "
             f"{len(state_files)} state file(s)")

    n_compiled_ok = 0
    n_compiled_fail = 0
    n_skipped = 0

    for state_file in state_files:
        try:
            state = llm_refactor.load_state(state_file)
        except Exception as e:
            log.warning(f"  could not load {state_file}: {e}")
            continue

        # Filter: only act on states whose target_class matches this CUT
        if state.get("target_class") != class_name:
            continue

        suite_class = state.get("suite_class") or state.get("test_id") or ""
        test_id = state.get("test_id") or state.get("file_prefix") or ""
        target_dir = ImprovedTargetDir(
            base_dir=state_file.parent,
            suite_class=suite_class,
            test_id=test_id,
        )
        test_category = state.get("test_category")

        status = state.get("status", "")
        if status in ("COMPILE_SUCCESS", "MAX_ATTEMPTS", "LLM_ERROR"):
            existing_metrics = _load_json_if_exists(target_dir.metrics_path)
            # If we already have the merged form on disk, lift the inner
            # `improved` block back out so the regen is idempotent.
            improved_block = (existing_metrics or {}).get("improved") \
                if isinstance(existing_metrics, dict) and "improved" in existing_metrics \
                else existing_metrics

            # CRITICAL: don't blindly trust state.status. If state says
            # COMPILE_SUCCESS but the improved metrics block is empty/missing,
            # an earlier Step-5 run crashed BEFORE write_merged_metrics
            # (e.g. the killed_mutants dict-set bug). Fall through to the
            # full measure path so we don't ship a metrics.json with an empty
            # `improved` block.
            has_real_improved_data = (
                status == "COMPILE_SUCCESS"
                and isinstance(improved_block, dict)
                and improved_block.get("line_pct") is not None
            )
            non_success_status = status in ("MAX_ATTEMPTS", "LLM_ERROR")

            if has_real_improved_data or non_success_status:
                write_merged_metrics(
                    target_dir=target_dir,
                    project_id=project_id,
                    test_category=test_category,
                    improved_metrics=improved_block,
                    compile_status=status,
                )
                n_skipped += 1
                continue
            # else: status is COMPILE_SUCCESS but metrics are empty → fall
            # through and re-measure coverage + mutation properly.
            log.info(f"  re-measuring (state says SUCCESS but metrics empty): "
                     f"{state_file.relative_to(IMPROVED_ROOT)}")

        work = target_dir.work_dir
        compiled_dir = str(work / "test-classes")

        is_evosuite = bool(state.get("is_evosuite"))
        scaffolding = state.get("scaffolding_java") or ""

        cp, compile_fn = _build_cp_and_compile_fn(
            is_evosuite, classpath, compiled_dir, scaffolding, cfg
        )

        log.info(f"  compiling {state_file.relative_to(IMPROVED_ROOT)}")

        try:
            state = llm_refactor.recompile_existing(
                state=state,
                compile_fn=compile_fn,
                classpath=cp,
                compiled_dir=compiled_dir,
                work_dir=str(target_dir.base_dir),
            )
        except ValueError as e:
            log.warning(f"    skipping (no usable code in state): {e}")
            continue

        llm_refactor.save_state(state, state_file)

        if state["status"] == "COMPILE_SUCCESS" and state.get("last_java_path"):
            n_compiled_ok += 1

            cov = coverage_runner.measure(
                test_java_path=state["last_java_path"],
                target_class_fqn=class_name,
                project_classpath=classpath,
                sut_classes_dir=str(sut_classes_dir),
                work_dir=str(work / "cov"),
                cfg=cfg,
                project_dir=str(project_dir),
                is_evosuite=is_evosuite,
                scaffolding_java=scaffolding or None,
            )
            metrics = cov or {"compile_success": True, "coverage_available": False}
            _merge_mutation_metrics(
                result=metrics,
                test_java=state["last_java_path"],
                target_class_fqn=class_name,
                project_classpath=classpath,
                sut_classes_dir=str(sut_classes_dir),
                work_dir=str(work / "cov"),
                cfg=cfg,
                is_evosuite=is_evosuite,
                scaffolding_java=scaffolding or None,
            )
            metrics["model"] = state.get("model")
            metrics["prompt_version"] = state.get("prompt_version", prompt_version)
            metrics["test_category"] = test_category
            metrics["test_id"] = test_id
            metrics["suite_class"] = suite_class
            metrics["target_class"] = class_name
            metrics["status"] = "COMPILE_SUCCESS"
            metrics["attempts_used"] = state.get("attempts_used", 0)
            write_merged_metrics(
                target_dir=target_dir,
                project_id=project_id,
                test_category=test_category,
                improved_metrics=metrics,
                compile_status=state["status"],
            )
            # Drop the per-test JaCoCo/PIT intermediates now that metrics.json
            # holds everything analysis needs (covered_lines, missed_lines,
            # branches_by_line, and the full per-mutant killed_mutants records).
            #
            # This mirrors the baseline-side cleanup in _measure_and_save(), which
            # this branch does NOT go through — it calls coverage_runner.measure()
            # + _merge_mutation_metrics() directly, so _work/ used to survive on
            # EVERY improved test. Measured cost of that leak: 27.5 GB across the
            # three model runs, 80% of the whole data tree.
            #
            # Only `_work/cov/` is removed (jacoco.xml + jacoco.exec + PIT report
            # + any HTML = 25.6 of those 27.5 GB). `_work/test-classes/` is KEPT:
            # it is the compiled test class later steps put on the classpath, and
            # it costs only ~0.13 GB across all three runs. Set
            # `measurement.keep_work: true` to retain the intermediates too.
            if not bool(getattr(getattr(cfg, "measurement", None),
                                "keep_work", False)):
                shutil.rmtree(work / "cov", ignore_errors=True)
            log.info(f"    → COMPILE_SUCCESS, coverage measured")
        else:
            n_compiled_fail += 1
            write_merged_metrics(
                target_dir=target_dir,
                project_id=project_id,
                test_category=test_category,
                improved_metrics=None,
                compile_status=state["status"],
            )
            log.warning(f"    → {state['status']}")

    # Refresh summary.csv at every testsuites/ + testcases/ parent that
    # this project touched. One CSV per parent gives a quick-glance roll-up
    # without having to open every metrics.json.
    refreshed: set[Path] = set()
    for state_file in state_files:
        # state_file path layout:
        #   …/<label>/<proj>/<src>/testsuites/<Suite>/state.json
        #   …/<label>/<proj>/<src>/testcases/<Suite>/<Case>/state.json
        parts = state_file.parts
        try:
            tsi = parts.index("testsuites")
            refreshed.add(Path(*parts[:tsi + 1]))
        except ValueError:
            pass
        try:
            tci = parts.index("testcases")
            refreshed.add(Path(*parts[:tci + 1]))
        except ValueError:
            pass
    for parent in sorted(refreshed):
        write_improved_summary_csv(parent, project_id)

    log.info(f"[compile_improvement_outputs] {class_name}: "
             f"ok={n_compiled_ok}, failed={n_compiled_fail}, "
             f"skipped(terminal)={n_skipped}")


# ══════════════════════════════════════════════════════════════════════════════
# Agent dispatchers
# ══════════════════════════════════════════════════════════════════════════════

def _model_entry_id(entry) -> Optional[str]:
    """Pull the model id out of one `models:` entry.

    An entry is either a bare string or a mapping carrying an output-folder
    override. NOTE: _to_ns() only recurses into dicts, not lists, so a YAML
    list of mappings arrives here as a list of plain dicts.

        models:
          - "claude-opus-4-8"                                  → str
          - {id: "claude-opus-4-8", output_dir: "opus-4.8"}    → dict
    """
    if isinstance(entry, str):
        return entry
    if isinstance(entry, dict):
        mid = entry.get("id") or entry.get("model")
        return str(mid) if mid else None
    mid = getattr(entry, "id", None) or getattr(entry, "model", None)
    return str(mid) if mid else None


def _model_entry_output_dir(entry) -> Optional[str]:
    """The `output_dir` override of one `models:` entry, or None."""
    if isinstance(entry, str):
        return None
    if isinstance(entry, dict):
        out = entry.get("output_dir")
    else:
        out = getattr(entry, "output_dir", None)
    return str(out) if out else None


def _models_from_section(section_obj) -> list[str]:
    """Read the model id(s) from one backend's config section.

    Accepts ALL of:
      model:  "claude-opus-4-7"                          # single string
      models: ["claude-opus-4-7", "..."]                 # list of strings
      models: [{id: "claude-opus-4-8",                   # list of mappings
                output_dir: "opus-4.8"}, ...]            #   (see _apply_model_output_dirs)

    Returns [] when the section is missing or has no model configured.
    """
    if section_obj is None:
        return []
    multi = getattr(section_obj, "models", None)
    if multi:
        return [m for m in (_model_entry_id(e) for e in multi) if m]
    single = getattr(section_obj, "model", None)
    return [str(single)] if single else []


def _requested_agent_models(cfg) -> dict[str, list[str]]:
    """Return {'claude': [...], 'codex': [...]} read from each backend's block.

    Routing is decided by which config section the model lives in, NOT by
    parsing the model id prefix. So `agent.model: "anything-you-want"`
    always goes to Claude Code SDK; `codex_agent.model: "anything"` goes to
    the configured Codex runtime (CLI by default; OpenAI Agents SDK when
    `codex_agent.runtime: agents_sdk`).
    """
    claude = _models_from_section(getattr(cfg, "agent", None))
    codex  = _models_from_section(getattr(cfg, "codex_agent", None))
    if not claude and not codex:
        raise RuntimeError(
            "config.yaml has no agent model configured. Set "
            "`agent.model: <claude_id>` and/or `codex_agent.model: <codex_id>`."
        )
    return {"claude": claude, "codex": codex}


def _prepare_agent_run(subject: dict, target: dict,
                        project_dir: Path, evosuite_out_dir: Path,
                        tag: str):
    """Shared setup for both Claude and Codex IMPROVE-AND-REPAIR steps.

    Returns (class_name, project_id, cut_source_path, targets, dir_fn) or
    None when there's nothing to run."""
    class_name = target["class_name"]
    project_id = subject["project_id"]
    cut_source = _find_cut_source(project_dir, class_name)
    cut_source_path = Path(cut_source) if cut_source else None
    targets = list(_iter_improvement_targets(
        subject, target, project_dir, evosuite_out_dir
    ))
    if not targets:
        log.warning(f"[{tag}] no targets for {class_name}")
        return None

    def _dir_fn(pid, _cls, prompt_version, model, t):
        # NEW LAYOUT: per-suite or per-case folder, so file_prefix is empty
        # (artifacts inside get short canonical names: state.json, metrics.json,
        # <ClassName>.java, …). Old flat layout used `t.test_id` as prefix.
        base = improved_dir(
            pid, model, prompt_version,
            t.test_category,
            suite_class=t.suite_class,
            test_id=t.test_id if not _is_suite_category(t.test_category) else None,
        )
        return base, ""

    return class_name, project_id, cut_source_path, targets, _dir_fn


def run_claude_code_step(
    subject: dict, target: dict,
    project_dir: Path, classpath: str, sut_classes_dir: Path,
    evosuite_out_dir: Path, cfg
):
    """Claude Code SDK agent (with self-repair). Reads the model from cfg.agent.model."""
    ctx = _prepare_agent_run(subject, target, project_dir, evosuite_out_dir,
                              tag="run_claude_code")
    if ctx is None:
        return
    class_name, project_id, cut_source_path, targets, dir_fn = ctx
    grouped = _requested_agent_models(cfg)
    if not grouped["claude"]:
        log.warning(f"[run_claude_code] no model configured under cfg.agent.model "
                    f"(or cfg.agent.models); nothing to do")
        return
    log.info(f"[run_claude_code] {class_name}: {len(targets)} target(s) × "
             f"{len(grouped['claude'])} Claude model(s): {grouped['claude']}")
    agent_with_repair.run(
        targets=targets,
        models=grouped["claude"],
        cut_source_path=cut_source_path,
        classpath=classpath,
        cfg=cfg,
        project_id=project_id,
        class_name=class_name,
        improvement_dir_fn=dir_fn,
    )


def run_codex_step(
    subject: dict, target: dict,
    project_dir: Path, classpath: str, sut_classes_dir: Path,
    evosuite_out_dir: Path, cfg
):
    """Run the configured Codex repair runtime against the selected model(s)."""
    ctx = _prepare_agent_run(subject, target, project_dir, evosuite_out_dir,
                              tag="run_codex")
    if ctx is None:
        return
    class_name, project_id, cut_source_path, targets, dir_fn = ctx
    grouped = _requested_agent_models(cfg)
    if not grouped["codex"]:
        log.warning(f"[run_codex] no model configured under cfg.codex_agent.model "
                    f"(or cfg.codex_agent.models); nothing to do")
        return
    runtime = str(getattr(cfg.codex_agent, "runtime", "cli")).strip().lower()
    if runtime != "cli":
        raise ValueError(
            "codex_agent.runtime must be `cli` (the study ran GPT-5.5 through the "
            f"Codex CLI; no other runtime is included), got {runtime!r}"
        )
    runner = codex_agent_with_repair
    log.info(f"[run_codex] {class_name}: {len(targets)} target(s) × "
             f"{len(grouped['codex'])} Codex model(s) via {runtime}: "
             f"{grouped['codex']}")
    runner.run(
        targets=targets,
        models=grouped["codex"],
        cut_source_path=cut_source_path,
        classpath=classpath,
        cfg=cfg,
        project_id=project_id,
        class_name=class_name,
        improvement_dir_fn=dir_fn,
    )


_LAMBDA_SUFFIX_RE = re.compile(r"lambda\$(\w+?)\$\d+")


def _normalize_mutated_method(name: str) -> str:
    """Drop the trailing ordinal from javac's synthetic lambda names
    (`lambda$merge$0` → `lambda$merge`).

    The ordinal is assigned per enclosing class at compile time, so it is NOT
    stable across the baseline and improved PIT runs even though the CUT is
    byte-for-byte identical. Keeping it in the identity tuple makes one and the
    same mutant look like two (one 'lost' from the baseline set, one 'gained' in
    the improved set) and produces spurious NOT_EXACT_MATCH verdicts.
    line_number + indexes still separate distinct lambdas inside one method."""
    return _LAMBDA_SUFFIX_RE.sub(r"lambda$\1", name or "")


def _killed_mutant_key(m: dict) -> tuple:
    """Identity tuple for one killed mutant. Two killed-mutants entries
    are 'the same' iff every field below matches.

    NOTE: PIT's `indexes` field is a per-RUN mutation ordinal that shifts
    between the baseline and improved measurements for the SAME logical mutant
    (e.g. BinaryCodec:274 NullReturnVals encode killed in both, but indexes
    "19" vs "20" because the improved split its test into extra @Test methods).
    Including it here made the same mutant count as "lost from baseline + gained
    in improved" → false weakenings AND false strengthenings, and false
    NOT_EXACT_MATCH verdicts (e.g. kills 78→78 = but judged non-exact). The
    mutation POINT is uniquely identified by (class, method, description, line,
    mutator); `indexes` is therefore intentionally excluded. Dropping it can
    only make more mutants match (coarser key), so exact-match is monotonically
    non-decreasing — it never wrongly turns an exact case into a mismatch."""
    return (
        m.get("mutated_class", ""),
        _normalize_mutated_method(m.get("mutated_method", "")),
        m.get("method_description", ""),
        int(m.get("line_number", 0) or 0),
        m.get("mutator_short", "") or m.get("mutator", ""),
    )


# ── Step 6 — three INDEPENDENT evaluation tasks ─────────────────────────────
# Each is gated by its OWN `enabled:` flag, reads the shared per-test index
# data/exact_match.json, applies ITS OWN filtering metrics (from the same
# pipeline_control block), and writes ONE file under its own data/<task>/ folder.

def _enabled(block) -> bool:
    """True when a nested pipeline_control block carries `enabled: true`."""
    return bool(getattr(block, "enabled", False))


# ── Step 6a — human evaluation set ──────────────────────────────────────────


# ── Step 6b — structural analysis set ───────────────────────────────────────


# ── Step 6c — downstream task set (oracle kill-contribution) ─────────────────


    # NOTE: the three Step-6 OUTPUT files (human_eval / structural_analysis /
    # downstream_tasks) are NO LONGER written here. Each is now an independent,
    # separately-gated task that reads data/exact_match.json and applies its own
    # filtering metrics — see run_build_human_eval / run_structural_analysis /
    # run_filter_downstream_tests. build_evaluation_set only produces the
    # data/evaluation/ review tree + its SUMMARY now.


# ── Final report ──────────────────────────────────────────────────────────────


# ── Pipeline-control helpers ─────────────────────────────────────────────────

def _flag_on(pc, name: str) -> bool:
    """Whether one pipeline_control toggle is on, in EITHER of its two forms:

        measure_baseline: true                 → a plain bool
        filter_out:       {enabled: true, …}   → a nested block

    A nested block arrives as a SimpleNamespace, which is ALWAYS truthy, so a
    bare `bool(getattr(pc, name))` reports `filter_out: {enabled: false}` as
    enabled. That is what used to force the per-subject loop to run (and crash
    on a missing checkout) even with every step switched off."""
    v = getattr(pc, name, False)
    if isinstance(v, types.SimpleNamespace):
        return _enabled(v)
    return bool(v)


def _pipeline_has_work_enabled(pc) -> bool:
    """Return true when a regular pipeline phase is enabled."""
    flags = [
        "clone_and_build",
        "generate_evosuite",
        "measure_baseline",
        "filter_out",
        "split_and_measure_cases",
        "generate_improvement_prompts",
        "run_claude_code",
        "run_codex",
        "compile_improvement_outputs",
    ]
    return any(_flag_on(pc, flag) for flag in flags)


# ── Helper: find CUT source file ──────────────────────────────────────────────

def _find_cut_source(project_dir: Path, class_fqn: str) -> str:
    """Locate the .java source file for the class under test."""
    rel_path = class_fqn.replace(".", "/") + ".java"
    candidates = [
        project_dir / "src" / "main" / "java" / rel_path,
        project_dir / "src" / "java" / rel_path,
        project_dir / rel_path,
    ]
    for c in candidates:
        if c.exists():
            return str(c)
    log.warning(f"CUT source not found for {class_fqn}, using empty signature")
    return ""


# ── Main ──────────────────────────────────────────────────────────────────────

def main():
    parser = argparse.ArgumentParser(description="LLM Test Understandability Pipeline")
    parser.add_argument("--config", default=str(PROJECT_ROOT / "config.yaml"))
    # The dataset manifest moved: subjects.json (repo root) → data/dataset.json.
    # Same format (list of {repo, class_paths, test_paths}); it is the single
    # starting point for clone_and_build / generate_evosuite / measure_baseline
    # / filter_out. Keep a fallback to the legacy path for old checkouts.
    parser.add_argument(
        "--subjects",
        default=str((PROJECT_ROOT / "data" / "dataset.json")
                    if (PROJECT_ROOT / "data" / "dataset.json").exists()
                    else (PROJECT_ROOT / "subjects.json")),
    )
    args = parser.parse_args()

    cfg = load_config(args.config)
    # Redirect clones + all data output per cfg.paths BEFORE anything reads a
    # path (clone, measure, write). No-op-equivalent when cfg has no paths:.
    _apply_path_config(cfg)
    # Resolve the per-model output-folder overrides (and fail loudly on a
    # collision) BEFORE any improved/ path is built.
    _apply_model_output_dirs(cfg)
    subjects = load_subjects(args.subjects)
    pc = cfg.pipeline_control

    # Per-class gate decisions from this run's filter_out step, keyed by
    # (project_id, class_path). Used to regenerate tests_for_improvements.json
    # after the loop and to gate the improvement step within the loop.
    filter_decisions: dict = {}

    # The per-subject loop touches the LOCAL CHECKOUT of every subject (it has
    # to know maven-vs-gradle, resolve the classpath, …), so a missing clone is
    # a hard error there. But the trailing steps — generate_summary, the
    # evaluation tasks, the analysis passes — only read data/ and need no
    # checkout at all. Skip the loop entirely when no step inside it is on, so
    # `generate_summary: true` alone runs on a machine with no repos cloned.
    #
    # `subjects` itself is left INTACT — the trailing steps take it as a scope
    # filter, and emptying it would silently narrow them to nothing.
    loop_subjects = subjects
    if not _pipeline_has_work_enabled(pc):
        log.info("[pipeline] no per-subject step enabled — skipping the subject "
                 "loop (no checkout needed) and going straight to the "
                 "data-only steps")
        loop_subjects = []

    for subject in loop_subjects:
        project_id = subject["project_id"]
        github_url = subject["github_url"]
        commit_hash = subject.get("commit_hash", "")

        log.info(f"\n{'='*70}")
        log.info(f"PROJECT: {project_id}")
        log.info(f"{'='*70}")

        # ── Step 1: Clone and build ──────────────────────────────────────────
        project_dir = git_manager.get_project_dir(project_id)

        # Per-project JDK selection. Different subjects need different JDKs:
        # Jackson 3.x needs 17 ("invalid target release: 17" on JDK 11),
        # joda-time pins -source 5 (rejected by modern javac → needs JDK 8),
        # mybatis-parent's enforcer wants 21+. We detect the required release
        # from the pom (falling back to an explicit override in config for
        # projects whose version lives only in a parent pom — e.g. jackson-core
        # /jackson-dataformat-xml resolve to None), then map it to an installed
        # JDK. build_jdk COMPILES; measure_jdk RUNS coverage/PIT (floored at 11
        # for --add-opens). Both fall back to the current default when unset.
        _ovr = getattr(cfg.tools, "project_jdk_overrides", None)
        if _ovr is None:
            _jdk_overrides = {}
        elif isinstance(_ovr, dict):
            _jdk_overrides = _ovr
        else:                       # SimpleNamespace (hyphenated project ids live in __dict__)
            _jdk_overrides = vars(_ovr)
        _release = _jdk_overrides.get(project_id)
        if _release is None:
            _release = build_executor.detect_java_release(project_dir)
        else:
            _release = int(_release)
        _build_jdk   = build_executor.resolve_java_home(_release, cfg, "build")
        _measure_jdk = build_executor.resolve_java_home(_release, cfg, "run")
        if _release is not None:
            log.info(f"  JDK plan: release={_release} build={_build_jdk or 'default'} "
                     f"measure={_measure_jdk or 'default'}")
        # Route ALL downstream measurement (coverage compile/run + PIT) for this
        # subject through measure_jdk by overriding java_home_compile. The
        # subject loop is sequential, so this is set fresh per project.
        if _measure_jdk:
            cfg.tools.java_home_compile = _measure_jdk
        # PATH B (Maven Surefire coverage) RE-COMPILES the project, so it needs a
        # COMPILE-capable JDK — not the run JDK, which is floored at 11 for
        # --add-opens. Old `-source 1.5/1.6` projects (itextpdf, joda-time) are
        # rejected by JDK 11's javac ("Source option 5 is no longer supported"),
        # so `mvn test` aborts at default-compile and coverage comes back 0% even
        # though the suite is fine (PIT, which doesn't recompile, scored them
        # normally → the tell-tale "mutation>0 but line=0%"). Give the Maven path
        # the build JDK; PATH A/C (direct java) keep the run JDK for --add-opens.
        cfg.tools.java_home_maven = _build_jdk or _measure_jdk or \
            getattr(cfg.tools, "java_home_compile", "")

        if pc.clone_and_build:
            project_dir = git_manager.clone_project(github_url, project_id, commit_hash)
            build_system = build_executor.detect_build_system(project_dir)
            ok = build_executor.build_project(project_dir, build_system,
                                              cfg.tools.maven_path, java_home=_build_jdk)
            if not ok:
                log.error(f"Build failed for {project_id}, skipping.")
                continue
        else:
            # Even when we're not (re)cloning, downstream still needs to know
            # whether the local checkout is maven or gradle.
            build_system = build_executor.detect_build_system(project_dir)
        log.info(f"  build system: {build_system}")

        try:
            classpath = build_executor.extract_classpath(
                project_dir, build_system, cfg.tools.maven_path
            )
            sut_classes_dir = build_executor.get_classes_dir(project_dir, build_system)
        except Exception as e:
            # A multi-module aggregator (<packaging>pom</packaging>, e.g.
            # commons-math) has no classpath/classes at the root — they live in
            # submodules. extract_classpath on the reactor root legitimately
            # fails. This is NOT fatal: measure_baseline resolves the correct
            # submodule per target via resolve_maven_module_spec and extracts
            # that submodule's classpath itself. So DEFER instead of skipping
            # the whole project (previously commons-math produced nothing).
            if build_system == "maven" and build_executor.is_aggregator_pom(project_dir):
                log.warning(
                    f"[{project_id}] root classpath extraction failed but the "
                    f"parent is a pom-aggregator — deferring to per-target "
                    f"submodule resolution. ({str(e).splitlines()[0][:80]})"
                )
                classpath = ""
                sut_classes_dir = build_executor.get_classes_dir(project_dir, build_system)
            else:
                log.error(f"Classpath extraction failed: {e}")
                continue

        git_manager.create_shadow_dir(project_dir)

        # Cache submodule classpaths so multi-module aggregators (commons-math,
        # itextpdf) don't re-run `mvn dependency:build-classpath` per target.
        _submodule_cp_cache: dict = {}

        for target in subject["target_classes"]:
            class_name = target["class_name"]
            log.info(f"\n  Class: {class_name}")

            # Per-target classpath. For multi-module aggregators the reactor-root
            # classpath is empty (see the extract_classpath fallback above);
            # resolve the SUT submodule's classpath the SAME way measure_baseline
            # does, so run_claude_code's compile.sh AND compile_improvement_outputs
            # find the CUT classes (otherwise: cannot-find-symbol → 0% compile).
            target_cp, target_sut = classpath, sut_classes_dir
            if build_system == "maven":
                _mod = resolve_maven_module_spec(project_dir, target)
                if _mod.is_multimodule:
                    target_sut = _mod.sut_classes_dir
                    _sub = _mod.sut_module or "."
                    if _sub not in _submodule_cp_cache:
                        try:
                            _submodule_cp_cache[_sub] = build_executor.extract_classpath(
                                project_dir / _sub, "maven", cfg.tools.maven_path)
                        except Exception as _e:
                            log.warning(f"  [{class_name}] multi-module classpath "
                                        f"extraction failed: {_e}; using root classpath")
                            _submodule_cp_cache[_sub] = classpath
                    target_cp = _submodule_cp_cache[_sub]

            # EvoSuite raw output (one staging dir per project; EvoSuite
            # writes <Class>_ESTest.java + <Class>_ESTest_scaffolding.java
            # using its own per-package layout).
            evosuite_out = evosuite_raw_dir(project_id)

            # ── Step 2: Generate EvoSuite tests ─────────────────────────────
            if pc.generate_evosuite:
                result = evosuite_runner.generate(
                    class_name, classpath, str(evosuite_out), cfg,
                    project_dir=str(project_dir),
                    build_system=build_system,
                )
                if result is None:
                    log.error(f"  EvoSuite generation failed for {class_name}")

            # ── Step 2b: Measure baseline suites → writes to data/baseline/ ───
            if pc.measure_baseline:
                measure_baseline(subject, target, project_dir, classpath,
                                 sut_classes_dir, evosuite_out, cfg)

            # ── Step 2c: Filter out → writes to data/original/ ────────────────
            # When stage_gates.apply_filter=true, classes that fail the gate
            # do NOT land in data/original/ and consequently get skipped by
            # every downstream step (since they read from data/original/).
            # The per-class decision is collected so (a) the
            # tests_for_improvements.json manifest can be written after the
            # loop and (b) the improvement step below can be gated on it.
            filter_decision = None
            if _filter_out_enabled(pc):
                try:
                    filter_decision = filter_out_baseline(subject, target, cfg)
                except Exception as e:
                    # Never let one class's bad data abort the whole run (which
                    # could be mid-way through paid agent calls). Record it as a
                    # gate failure with the reason and keep going.
                    log.error(f"[filter_out] {class_name}: errored "
                              f"({type(e).__name__}: {e}) — excluding this class")
                    filter_decision = {"passed": False,
                                       "reason": f"filter error: {type(e).__name__}: {e}"}
                filter_decisions[(project_id, target.get("class_path", ""))] = \
                    filter_decision

            # ── Step 3: Split gate-passing suites into cases → data/original/ ──
            # Runs only for classes already in data/original/ (passed 2c).
            if getattr(pc, "split_and_measure_cases", False):
                split_and_measure_cases(subject, target, project_dir, classpath,
                                        sut_classes_dir, evosuite_out, cfg)

            # ── Step 4: Agent improvement (Claude Code SDK / Codex CLI) ─────
            # Two independent toggles — each one drives its own backend.
            # Only models matching that backend in active_models actually run:
            #   run_claude_code → claude-* and deepseek-* models
            #   run_codex       → gpt-* models
            # Gated by data/tests_for_improvements.json (and/or this run's
            # fresh filter decision): a class outside the manifest is NOT
            # improved — this also closes the old hole where suite-level
            # tests bypassed the filter (suites used to be read straight from
            # local_workplace regardless of the gate result).
            if bool(getattr(pc, "generate_improvement_prompts", False)):
                # Only preview classes that are in the tests_for_improvements
                # selection (i.e. passed the filtering metrics) — same gate the
                # real agent step uses below.
                if improvement_allowed(subject, target, filter_decision):
                    run_generate_improvement_prompts(
                        subject, target, project_dir, evosuite_out, cfg
                    )
                else:
                    log.info(f"  [generate_improvement_prompts] {class_name}: "
                             f"not in tests_for_improvements selection — "
                             f"skipping preview")

            _wants_improvement = (bool(getattr(pc, "run_claude_code", False))
                                  or bool(getattr(pc, "run_codex", False)))
            if _wants_improvement and not improvement_allowed(
                    subject, target, filter_decision):
                log.info(f"  [improvement] {class_name}: not in "
                         f"tests_for_improvements selection — skipping agent")
            else:
                if bool(getattr(pc, "run_claude_code", False)):
                    run_claude_code_step(
                        subject, target, project_dir, target_cp, target_sut,
                        evosuite_out, cfg
                    )
                if bool(getattr(pc, "run_codex", False)):
                    run_codex_step(
                        subject, target, project_dir, target_cp, target_sut,
                        evosuite_out, cfg
                    )

            # Compile step: walks every state.json under improvement/ and
            # compiles → post-processes → measures. Idempotent and shared
            # by both LLM and agent flows.
            if getattr(pc, "compile_improvement_outputs", False):
                run_compile_improvement_outputs(
                    subject, target, project_dir, target_cp, target_sut,
                    evosuite_out, cfg
                )

    # ── Post: regenerate the improvement selection manifest from this run's
    # gate decisions (default = all passing classes; excluded classes are
    # listed with their gate-failure reasons).
    if _filter_out_enabled(pc):
        try:
            write_tests_for_improvements(args.subjects, filter_decisions)
        except Exception as e:
            log.warning(f"[tests_for_improvements] failed: {e}")

    # ── Step 5b: data/improved/summary/ ─────────────────────────────────────
    # Aggregates EVERY model folder on disk (not just this run's) into
    # summary.md (the accepted-tests table), feature_analysis_input.json and
    # suite_cases.json. Reads only metrics.json + state.json: cheap, safe to
    # leave on.
    if bool(getattr(pc, "generate_summary", False)):
        try:
            import summarize_improvements
            summarize_improvements.run(IMPROVED_ROOT)
        except Exception as e:
            log.warning(f"[generate_summary] failed: {e}")

    log.info("\nPipeline complete.")


if __name__ == "__main__":
    main()
