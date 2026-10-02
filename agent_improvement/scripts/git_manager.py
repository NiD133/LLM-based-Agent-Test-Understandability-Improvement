"""
Handles cloning, checkout, and shadow directory management.
"""

import logging
import subprocess
from pathlib import Path

import pipeline_paths

log = logging.getLogger(__name__)

SHADOW_DIR_NAME = "src/test/llm_enhanced"


def get_project_dir(project_id: str) -> Path:
    # Read the workplace root at call time so a config-driven override (see
    # pipeline_paths.configure_from_cfg) is always honoured.
    return pipeline_paths.workplace_dir() / project_id


def clone_project(github_url: str, project_id: str, commit_hash: str = "") -> Path:
    """
    Clone a GitHub repo into <workplace_dir>/<project_id>.
    If the directory already exists, skip cloning (use existing checkout).
    Optionally checks out a specific commit hash for reproducibility.
    Returns the project directory path.
    """
    dest = get_project_dir(project_id)
    pipeline_paths.workplace_dir().mkdir(parents=True, exist_ok=True)

    if dest.exists():
        log.info(f"[git_manager] {project_id} already cloned at {dest}, skipping.")
        return dest

    log.info(f"[git_manager] Cloning {github_url} → {dest}")
    result = subprocess.run(
        ["git", "clone", "--depth", "1", github_url, str(dest)],
        capture_output=True, text=True
    )
    if result.returncode != 0:
        # Retry without --depth if shallow clone fails
        log.warning("[git_manager] Shallow clone failed, retrying full clone...")
        result = subprocess.run(
            ["git", "clone", github_url, str(dest)],
            capture_output=True, text=True
        )
    if result.returncode != 0:
        raise RuntimeError(f"git clone failed:\n{result.stderr}")

    if commit_hash:
        log.info(f"[git_manager] Checking out {commit_hash}")
        # Need full history for checkout; re-fetch if shallow
        subprocess.run(["git", "-C", str(dest), "fetch", "--unshallow"],
                       capture_output=True)
        r = subprocess.run(
            ["git", "-C", str(dest), "checkout", commit_hash],
            capture_output=True, text=True
        )
        if r.returncode != 0:
            raise RuntimeError(f"git checkout {commit_hash} failed:\n{r.stderr}")

    log.info(f"[git_manager] Cloned to {dest}")
    return dest


def create_shadow_dir(project_dir: Path) -> Path:
    """
    Create the shadow directory for LLM-improved tests inside the cloned project.
    Returns the path to the shadow directory.
    """
    shadow = project_dir / SHADOW_DIR_NAME
    shadow.mkdir(parents=True, exist_ok=True)
    log.info(f"[git_manager] Shadow dir ready: {shadow}")
    return shadow


