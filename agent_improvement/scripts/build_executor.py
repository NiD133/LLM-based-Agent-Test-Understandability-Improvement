"""
Maven build and classpath extraction (all 14 subject projects are Maven builds).
"""

import logging
import os
import re
import subprocess
import tempfile
from pathlib import Path
from typing import Optional

log = logging.getLogger(__name__)


def detect_build_system(project_dir: Path) -> str:
    """Return 'maven' or 'gradle' based on project files. Only Maven projects
    can be built and measured; a Gradle checkout is reported so the error is clear."""
    if (project_dir / "pom.xml").exists():
        return "maven"
    if (project_dir / "build.gradle").exists() or (project_dir / "build.gradle.kts").exists():
        return "gradle"
    raise RuntimeError(f"Cannot detect build system in {project_dir}")


def is_aggregator_pom(project_dir: Path) -> bool:
    """True if the root pom is a multi-module aggregator (<packaging>pom</packaging>
    with <module> children, e.g. commons-math).

    Such a reactor has NO classes or classpath at the root — they live in each
    submodule's target/classes. `mvn dependency:build-classpath` on the root
    therefore produces nothing and our extract_classpath() raises. That is NOT
    a real failure: measure_baseline resolves the correct submodule per target
    via resolve_maven_module_spec(), so callers should DEFER to per-target
    submodule resolution instead of skipping the whole project."""
    pom = project_dir / "pom.xml"
    if not pom.exists():
        return False
    try:
        txt = pom.read_text(encoding="utf-8", errors="replace")
    except OSError:
        return False
    return "<packaging>pom</packaging>" in txt and "<module>" in txt


# Patterns to scan a pom.xml for. Order matters: prefer the most explicit
# declaration first. A `<maven.compiler.release>` overrides `<target>`/`<source>`
# under maven-compiler-plugin >= 3.6.
# The capture group accepts both modern ("11", "17") and legacy ("1.8") forms.
_JAVA_RELEASE_PATTERNS = (
    r'<maven\.compiler\.release>\s*([\d.]+)\s*</maven\.compiler\.release>',
    r'<release>\s*([\d.]+)\s*</release>',
    r'<maven\.compiler\.target>\s*([\d.]+)\s*</maven\.compiler\.target>',
    r'<maven\.compiler\.source>\s*([\d.]+)\s*</maven\.compiler\.source>',
    r'<target>\s*([\d.]+)\s*</target>',
    r'<source>\s*([\d.]+)\s*</source>',
    # Property-style declarations used by some parents (e.g. mybatis-parent
    # sets <java.release.version>/<java.version>; jackson/others use jdk.version).
    # Lower priority than the explicit compiler tags above so they only fire
    # when nothing more specific is present.
    r'<java\.release\.version>\s*([\d.]+)\s*</java\.release\.version>',
    r'<java\.version>\s*([\d.]+)\s*</java\.version>',
    r'<jdk\.version>\s*([\d.]+)\s*</jdk\.version>',
    r'<javac\.target>\s*([\d.]+)\s*</javac\.target>',
)


def detect_java_release(project_dir: Path) -> Optional[int]:
    """Return the Java release version the project compiles with, or None.

    Parses pom.xml for explicit version declarations. Strips a leading "1."
    so values like "1.8" return 8.

    Returns None if pom.xml is missing or no version markers are found —
    callers should fall back to whatever javac's default is in that case.
    """
    pom = project_dir / "pom.xml"
    if not pom.exists():
        return None
    try:
        content = pom.read_text(encoding="utf-8", errors="replace")
    except OSError as e:
        log.warning("[build_executor] Could not read %s: %s", pom, e)
        return None

    for pattern in _JAVA_RELEASE_PATTERNS:
        m = re.search(pattern, content)
        if not m:
            continue
        raw = m.group(1)
        # Normalize legacy "1.8" → 8, modern "11" → 11.
        if raw.startswith("1.") and raw.count(".") == 1:
            try:
                return int(raw.split(".", 1)[1])
            except ValueError:
                continue
        try:
            return int(raw.split(".", 1)[0])
        except ValueError:
            continue
    return None


def resolve_java_home(release: Optional[int], cfg, purpose: str = "build") -> Optional[str]:
    """Pick a JDK home for a project's required Java `release`, from the
    `cfg.tools.java_homes` version→path map. Returns the path string, or None
    to fall back to the caller's default (cfg.tools.java_home_compile / ambient).

    Two purposes, because BUILD and RUN have different constraints:
      • build — must COMPILE the project. Old projects pin `-source 1.5/1.6`,
        which modern javac rejects ("Source option 5 is no longer supported"),
        so they need an OLD JDK; Java-17/21 projects need a matching-or-newer
        JDK ("invalid target release: 17" on JDK 11).
            release ≤ 8  → JDK 8   (handles -source 1.5..1.8)
            9..11        → JDK 11
            12..17       → JDK 17
            ≥ 18         → JDK 21
      • run — must LOAD the compiled bytecode AND support modern JVM flags
        (--add-opens for JaCoCo/EvoSuite/PIT minions, introduced Java 9).
        Floored at 11 so old-bytecode projects still measure on a JDK that
        accepts --add-opens.
            release ≤ 11 → JDK 11
            12..17       → JDK 17
            ≥ 18         → JDK 21

    release=None → None (caller keeps its current default — preserves the
    behaviour of projects that already work)."""
    if release is None:
        return None
    homes = getattr(getattr(cfg, "tools", None), "java_homes", None)
    if homes is None:
        return None
    # SimpleNamespace (yaml maps with int-ish keys become attrs/dict) → dict
    if not isinstance(homes, dict):
        homes = {k: getattr(homes, k) for k in vars(homes)}
    # yaml keys may be ints or strings ("8"); normalise lookups to str.
    def _home(v: int) -> Optional[str]:
        return homes.get(v) or homes.get(str(v))

    if purpose == "run":
        want = 11 if release <= 11 else (17 if release <= 17 else 21)
    else:  # build
        want = 8 if release <= 8 else (11 if release <= 11 else (17 if release <= 17 else 21))

    # Try the exact tier, then fall back UP to the next installed JDK.
    for v in (want, 11, 17, 21, 8):
        h = _home(v)
        if h and Path(h).exists():
            return h
    return None


def build_project(project_dir: Path, build_system: str, maven_path: str = "mvn",
                  java_home: Optional[str] = None) -> bool:
    """
    Compile the project (classes only, skip tests).
    Returns True on success.

    `java_home`: when given, the build runs under THIS JDK (JAVA_HOME + PATH),
    instead of whatever JDK the parent process happens to have. This is what
    lets one pipeline build a Java-8 project (joda-time `-source 5`), a Java-17
    project (Jackson 3.x), and a Java-21 project from the same run. Resolve it
    via resolve_java_home(detect_java_release(project_dir), cfg, "build").
    """
    log.info(f"[build_executor] Building {project_dir.name} with {build_system}"
             f"{' (JAVA_HOME='+java_home+')' if java_home else ''}...")
    if build_system == "maven":
        cmd = [maven_path, "-q", "compile", "test-compile", "-DskipTests",
               "-Drat.skip=true", "-Dcheckstyle.skip=true",
               # Skip maven-enforcer: some parents (e.g. mybatis-parent) pin an
               # exact build-JDK range that our chosen JDK may sit just outside,
               # which aborts the build before compile even though the code
               # compiles fine on that JDK.
               "-Denforcer.skip=true",
               # Skip the code formatter (e.g. mybatis' formatter-maven-plugin
               # 2.29.0 is Java-17 bytecode and is irrelevant to compilation).
               "-Dformatter.skip=true", "-Dimpsort.skip=true",
               "--no-transfer-progress", "-f", str(project_dir / "pom.xml")]
    else:
        raise RuntimeError(f"{build_system} projects are not supported by this package "
                           f"(every subject project is a Maven build)")

    env = os.environ.copy()
    if java_home:
        env["JAVA_HOME"] = java_home
        env["PATH"] = f"{java_home}/bin:" + env.get("PATH", "")

    result = subprocess.run(cmd, capture_output=True, text=True,
                            cwd=str(project_dir), env=env)
    if result.returncode != 0:
        log.error(f"[build_executor] Build failed:\n{result.stderr[-3000:]}")
        return False
    log.info("[build_executor] Build succeeded.")
    return True


def extract_classpath(project_dir: Path, build_system: str,
                      maven_path: str = "mvn", include_test: bool = True) -> str:
    """
    Return the full colon-separated classpath string (compiled classes + all dependencies).
    Includes target/classes and target/test-classes.
    """
    if build_system != "maven":
        raise RuntimeError(f"{build_system} projects are not supported by this package "
                           f"(every subject project is a Maven build)")
    return _maven_classpath(project_dir, maven_path, include_test)


def get_classes_dir(project_dir: Path, build_system: str) -> Path:
    """Return the path to compiled SUT .class files (Maven layout)."""
    if build_system != "maven":
        raise RuntimeError(f"{build_system} projects are not supported by this package "
                           f"(every subject project is a Maven build)")
    return project_dir / "target" / "classes"


# ── Maven ────────────────────────────────────────────────────────────────────

def _maven_classpath(project_dir: Path, maven_path: str, include_test: bool) -> str:
    scope = "test" if include_test else "runtime"
    with tempfile.NamedTemporaryFile(suffix=".txt", delete=False) as tf:
        cp_file = tf.name

    cmd = [
        maven_path, "-q",
        "-f", str(project_dir / "pom.xml"),
        "dependency:build-classpath",
        f"-DincludeScope={scope}",
        f"-Dmdep.outputFile={cp_file}",
        "-Drat.skip=true",
        "--no-transfer-progress",
    ]
    result = subprocess.run(cmd, capture_output=True, text=True, cwd=str(project_dir))
    if result.returncode != 0:
        raise RuntimeError(f"Maven classpath extraction failed:\n{result.stderr}")

    deps_cp = Path(cp_file).read_text().strip()
    os.unlink(cp_file)

    # Prepend compiled classes directories
    parts = [str(project_dir / "target" / "classes")]
    test_classes = project_dir / "target" / "test-classes"
    if include_test and test_classes.exists():
        parts.append(str(test_classes))
    if deps_cp:
        parts.append(deps_cp)

    return ":".join(parts)
