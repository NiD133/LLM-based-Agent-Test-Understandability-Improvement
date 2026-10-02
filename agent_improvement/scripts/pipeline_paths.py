"""
Single source of truth for the two run-time output roots:

    workplace_dir()  where repos get cloned      (default: <root>/local_workplace)
    data_root()      where all measurement data lands (default: <root>/data)

WHY THIS EXISTS
---------------
These two roots used to be hardcoded independently in ~6 scripts
(git_manager, coverage_runner, main, downstream_agent, evosuite_runner,
jacoco_html), so there was no way to point one experiment's output somewhere
else without editing every file. Now every script asks THIS module at
run time, and the roots are driven by config.yaml:

    paths:
      workplace_dir: "local_workplace"   # relative to project root, or absolute
      data_root:     "data"

A downstream task can therefore keep its clones + data fully self-contained:

    # downstream/config.yaml
    paths:
      workplace_dir: "downstream/local_workplace"
      data_root:     "downstream/data"

so running the pipeline with --config downstream/config.yaml never touches
the main study's local_workplace/ or data/ (including the shared
exact_match.json / SUMMARY / tests_for_improvements.json aggregation files).

HOW IT WORKS
------------
main() calls configure_from_cfg(cfg) once, right after load_config(), BEFORE
any clone/measure work. Consumers read workplace_dir()/data_root() at call
time (not import time), so the one configure() call propagates everywhere.
When no `paths:` section is present, both fall back to the historical
defaults, so existing configs behave exactly as before.
"""

import os
from pathlib import Path

PROJECT_ROOT = Path(__file__).resolve().parent.parent

_DEFAULT_WORKPLACE = "local_workplace"
_DEFAULT_DATA = "data"


def _resolve(value: object, default_rel: str) -> Path:
    """A blank/None value → the historical default under the project root.
    A relative path → resolved against the project root (so config files stay
    portable). An absolute path → used as-is."""
    if not value:
        return PROJECT_ROOT / default_rel
    p = Path(str(value)).expanduser()
    return p if p.is_absolute() else (PROJECT_ROOT / p)


_workplace_dir = _resolve(None, _DEFAULT_WORKPLACE)
_data_root = _resolve(None, _DEFAULT_DATA)


def configure(workplace_dir: object = None, data_root: object = None) -> None:
    """Set both roots. Missing/blank args reset to the historical defaults,
    so a call with no args is a clean reset to <root>/local_workplace + <root>/data."""
    global _workplace_dir, _data_root
    _workplace_dir = _resolve(workplace_dir, _DEFAULT_WORKPLACE)
    _data_root = _resolve(data_root, _DEFAULT_DATA)


def configure_from_cfg(cfg) -> None:
    """Pull the `paths:` section out of a loaded config namespace (if any)
    and apply it. Safe to call on a config with no `paths:` section."""
    paths = getattr(cfg, "paths", None)
    wp = getattr(paths, "workplace_dir", None) if paths is not None else None
    dr = getattr(paths, "data_root", None) if paths is not None else None
    configure(wp, dr)


def workplace_dir() -> Path:
    return _workplace_dir


def data_root() -> Path:
    return _data_root
