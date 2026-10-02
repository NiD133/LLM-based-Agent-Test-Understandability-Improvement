"""
JavaParser @Test-body block counts.

JavaParser (a small Java helper, compiled on first use) locates every method and
whether it carries a JUnit test annotation, with its body's line span. Python then
counts BLOCKS inside each @Test body: a block is a run of consecutive non-blank
lines, so blank lines are what separate blocks.

Entry point: run_blocks(pairs, data_root, out_root, jar, java_home)
  writes <out_root>/<rel test>/test_blocks_javaparser.json   one per test pair
         <out_root>/summary.json + test_block_detection_summary.md
"""

from __future__ import annotations

import json
import os
import subprocess
from collections import Counter
from datetime import datetime
from pathlib import Path
from typing import Any


TEST_ANNOTATIONS = {
    "Test",
    "ParameterizedTest",
    "RepeatedTest",
    "TestFactory",
    "TestTemplate",
}

JAVA_HELPER = r"""
import com.github.javaparser.ParserConfiguration;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.AnnotationExpr;
import com.github.javaparser.ast.stmt.BlockStmt;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class JavaParserTestBlockExtractor {
    public static void main(String[] args) throws Exception {
        if (args.length < 1) {
            System.err.println("Usage: JavaParserTestBlockExtractor <java-file> [<java-file> ...]");
            System.exit(2);
        }
        // JavaParser defaults to an old language level and REJECTS records, text
        // blocks, switch expressions, etc. -- which used to surface as a silent
        // "0 test methods" and a fake block delta. Parse at Java 21.
        StaticJavaParser.getParserConfiguration()
                .setLanguageLevel(ParserConfiguration.LanguageLevel.JAVA_21);
        for (String arg : args) {
            parseOne(Paths.get(arg));
        }
    }

    private static void parseOne(Path path) {
        try {
        CompilationUnit cu = StaticJavaParser.parse(path);
        List<MethodDeclaration> methods = new ArrayList<>(cu.findAll(MethodDeclaration.class));
        methods.sort(Comparator.comparingInt(m -> m.getBegin().map(p -> p.line).orElse(0)));
        int order = 0;
        for (MethodDeclaration method : methods) {
            order++;
            Optional<BlockStmt> body = method.getBody();
            String annotations = method.getAnnotations()
                    .stream()
                    .map(AnnotationExpr::getNameAsString)
                    .collect(Collectors.joining(","));
            boolean isTest = false;
            for (AnnotationExpr annotation : method.getAnnotations()) {
                String name = annotation.getNameAsString();
                if (name.equals("Test") || name.endsWith(".Test") ||
                        name.equals("ParameterizedTest") || name.endsWith(".ParameterizedTest") ||
                        name.equals("RepeatedTest") || name.endsWith(".RepeatedTest") ||
                        name.equals("TestFactory") || name.endsWith(".TestFactory") ||
                        name.equals("TestTemplate") || name.endsWith(".TestTemplate")) {
                    isTest = true;
                }
            }
            String[] fields = new String[] {
                    "METHOD",
                    path.toString(),
                    Integer.toString(order),
                    method.getNameAsString(),
                    Boolean.toString(isTest),
                    Integer.toString(method.getBegin().map(p -> p.line).orElse(-1)),
                    Integer.toString(method.getEnd().map(p -> p.line).orElse(-1)),
                    Integer.toString(body.flatMap(BlockStmt::getBegin).map(p -> p.line).orElse(-1)),
                    Integer.toString(body.flatMap(BlockStmt::getEnd).map(p -> p.line).orElse(-1)),
                    annotations
            };
            System.out.println(String.join("\t", fields));
        }
        } catch (Exception exc) {
            System.out.println("ERROR\t" + path.toString() + "\t" + exc.getClass().getSimpleName() + ": " + exc.getMessage());
        }
    }
}
"""


def java_tool(java_home: str, name: str) -> str:
    """`java` / `javac` from java_home/bin if given, else whatever is on PATH."""
    if java_home:
        for cand in (Path(java_home) / "bin" / (name + ".exe"), Path(java_home) / "bin" / name):
            if cand.exists():
                return str(cand)
    return name


def run_blocks(
    pairs: list[tuple[Path, Path, Path]],
    data_root: Path,
    out_root: Path,
    jar: Path,
    java_home: str = "",
) -> dict[str, Any]:
    """Count @Test-body blocks for every (original, improved, test_dir) pair.

    Returns the summary dict (also written to <out_root>/summary.json)."""
    out_root.mkdir(parents=True, exist_ok=True)
    if not jar or not Path(jar).exists():
        raise SystemExit(f"javaparser-core jar not found: {jar}  (set JAVAPARSER_JAR in feature_analysis.py)")

    helper_classpath = compile_helper(out_root / "_javaparser_helper", Path(jar), java_home)

    java_files = sorted({p for original, improved, _ in pairs for p in (original, improved)})
    methods_by_file = run_javaparser_batch(java_files, helper_classpath, java_home)

    records = []
    totals = Counter()
    for original, improved, test_dir in pairs:
        rel = test_dir.relative_to(data_root)
        record = analyze_pair(rel, original, improved, methods_by_file)
        records.append(record)
        write_json(out_root / rel / "test_blocks_javaparser.json", record)
        totals["pairs_analyzed"] += 1
        totals["original_test_methods"] += record["original"]["test_method_count"]
        totals["improved_test_methods"] += record["improved"]["test_method_count"]
        totals["original_blocks"] += record["original"]["total_blocks"]
        totals["improved_blocks"] += record["improved"]["total_blocks"]
        delta = record["delta"]["total_blocks"]
        if delta > 0:
            totals["pairs_with_more_blocks"] += 1
        elif delta < 0:
            totals["pairs_with_fewer_blocks"] += 1
        else:
            totals["pairs_with_same_blocks"] += 1

    summary = {
        "generated_at": datetime.now().isoformat(timespec="seconds"),
        "data_root": str(data_root),
        "javaparser_jar": str(jar),
        "definition": {
            "test_method": "Method annotated with @Test, @ParameterizedTest, @RepeatedTest, @TestFactory, or @TestTemplate.",
            "block": "A contiguous group of non-blank source lines inside a test method body, excluding the method body's opening and closing brace lines.",
            "suite_total_blocks": "Sum of block counts over all test methods in the Java file.",
        },
        "totals": dict(totals),
        "top_increases": top_records(records, reverse=True),
        "top_decreases": top_records(records, reverse=False),
        "records": records,
    }
    write_json(out_root / "summary.json", summary)
    (out_root / "test_block_detection_summary.md").write_text(render_markdown(summary), encoding="utf-8")
    return summary


def compile_helper(helper_dir: Path, jar: Path, java_home: str = "") -> str:
    helper_dir.mkdir(parents=True, exist_ok=True)
    java_file = helper_dir / "JavaParserTestBlockExtractor.java"
    class_file = helper_dir / "JavaParserTestBlockExtractor.class"
    source = JAVA_HELPER.strip() + "\n"
    # Rewrite only when the source changed, so the mtime check below stays meaningful.
    if not java_file.exists() or java_file.read_text(encoding="utf-8") != source:
        java_file.write_text(source, encoding="utf-8")

    needs_compile = not class_file.exists() or class_file.stat().st_mtime < java_file.stat().st_mtime
    if needs_compile:
        result = subprocess.run(
            [java_tool(java_home, "javac"), "-cp", str(jar), str(java_file)],
            text=True,
            stdout=subprocess.PIPE,
            stderr=subprocess.PIPE,
            check=False,
        )
        if result.returncode != 0:
            raise SystemExit(f"javac failed:\n{result.stdout}\n{result.stderr}")
    return os.pathsep.join([str(helper_dir), str(jar)])


def analyze_pair(
    rel: Path,
    original: Path,
    improved: Path,
    methods_by_file: dict[str, list[dict[str, Any]]],
) -> dict[str, Any]:
    original_analysis = analyze_file(original, methods_by_file)
    improved_analysis = analyze_file(improved, methods_by_file)
    method_pairs = pair_methods_by_order(original_analysis["test_methods"], improved_analysis["test_methods"])
    return {
        "relative_test": rel.as_posix(),
        "original_path": str(original),
        "improved_path": str(improved),
        "original": original_analysis,
        "improved": improved_analysis,
        "delta": {
            "test_methods": improved_analysis["test_method_count"] - original_analysis["test_method_count"],
            "total_blocks": improved_analysis["total_blocks"] - original_analysis["total_blocks"],
            "total_blank_lines_inside_tests": (
                improved_analysis["total_blank_lines_inside_tests"]
                - original_analysis["total_blank_lines_inside_tests"]
            ),
        },
        "method_pairs_by_order": method_pairs,
    }


def analyze_file(path: Path, methods_by_file: dict[str, list[dict[str, Any]]]) -> dict[str, Any]:
    text = path.read_text(encoding="utf-8", errors="replace")
    lines = text.splitlines()
    methods = methods_by_file.get(str(path), [])
    parse_errors = [m.get("error", "") for m in methods if m.get("name") == "<parse-error>"]
    test_methods = []
    for method in methods:
        if not method["is_test"]:
            continue
        block_info = count_method_blocks(lines, method["body_begin_line"], method["body_end_line"])
        test_methods.append({**method, **block_info})
    return {
        "path": str(path),
        "parse_error": parse_errors[0] if parse_errors else None,
        "all_method_count": len(methods),
        "test_method_count": len(test_methods),
        "total_blocks": sum(method["block_count"] for method in test_methods),
        "total_blank_lines_inside_tests": sum(len(method["blank_lines"]) for method in test_methods),
        "test_methods": test_methods,
    }


def run_javaparser_batch(paths: list[Path], classpath: str,
                         java_home: str = "") -> dict[str, list[dict[str, Any]]]:
    if not paths:
        return {}
    from concurrent.futures import ThreadPoolExecutor

    methods_by_file: dict[str, list[dict[str, Any]]] = {str(path): [] for path in paths}
    chunk_size = 40
    chunks = [paths[i:i + chunk_size] for i in range(0, len(paths), chunk_size)]
    # Each chunk is its own JVM; run several at once (I/O-bound from Python's side).
    workers = max(1, min(8, (os.cpu_count() or 2) // 2))
    with ThreadPoolExecutor(max_workers=workers) as pool:
        for result in pool.map(lambda c: run_javaparser_chunk(c, classpath, java_home), chunks):
            merge_methods(methods_by_file, result)
    return methods_by_file


def run_javaparser_chunk(paths: list[Path], classpath: str,
                         java_home: str = "") -> dict[str, list[dict[str, Any]]]:
    result = subprocess.run(
        [java_tool(java_home, "java"), "-cp", classpath, "JavaParserTestBlockExtractor",
         *[str(path) for path in paths]],
        text=True,
        stdout=subprocess.PIPE,
        stderr=subprocess.PIPE,
        check=False,
    )
    if result.returncode != 0:
        raise SystemExit(f"JavaParser helper failed:\n{result.stdout}\n{result.stderr}")

    methods_by_file: dict[str, list[dict[str, Any]]] = {str(path): [] for path in paths}
    for row in result.stdout.splitlines():
        fields = row.split("\t")
        if not fields:
            continue
        if fields[0] == "ERROR" and len(fields) >= 3:
            methods_by_file.setdefault(fields[1], []).append({
                "order": -1,
                "name": "<parse-error>",
                "is_test": False,
                "method_begin_line": -1,
                "method_end_line": -1,
                "body_begin_line": -1,
                "body_end_line": -1,
                "annotations": [],
                "error": "\t".join(fields[2:]),
            })
            continue
        if fields[0] != "METHOD" or len(fields) != 10:
            continue
        _tag, file_path, order, name, is_test, begin, end, body_begin, body_end, annotations = fields
        methods_by_file.setdefault(file_path, []).append({
            "order": int(order),
            "name": name,
            "is_test": is_test == "true",
            "method_begin_line": int(begin),
            "method_end_line": int(end),
            "body_begin_line": int(body_begin),
            "body_end_line": int(body_end),
            "annotations": [item for item in annotations.split(",") if item],
        })
    return methods_by_file


def merge_methods(target: dict[str, list[dict[str, Any]]], source: dict[str, list[dict[str, Any]]]) -> None:
    for path, methods in source.items():
        target[path] = methods


def count_method_blocks(lines: list[str], body_begin_line: int, body_end_line: int) -> dict[str, Any]:
    if body_begin_line <= 0 or body_end_line <= body_begin_line:
        return {"block_count": 0, "blocks": [], "blank_lines": []}

    start = body_begin_line + 1
    end = body_end_line - 1
    blocks = []
    current_start: int | None = None
    current_end: int | None = None
    blank_lines = []

    for line_no in range(start, end + 1):
        text = lines[line_no - 1] if 0 <= line_no - 1 < len(lines) else ""
        if text.strip():
            if current_start is None:
                current_start = line_no
            current_end = line_no
        else:
            blank_lines.append(line_no)
            if current_start is not None and current_end is not None:
                blocks.append(block_record(lines, current_start, current_end))
                current_start = None
                current_end = None

    if current_start is not None and current_end is not None:
        blocks.append(block_record(lines, current_start, current_end))

    return {
        "block_count": len(blocks),
        "blocks": blocks,
        "blank_lines": blank_lines,
    }


def block_record(lines: list[str], start: int, end: int) -> dict[str, Any]:
    snippet_lines = []
    for line_no in range(start, min(end, start + 2) + 1):
        if 0 <= line_no - 1 < len(lines):
            snippet_lines.append(lines[line_no - 1].strip())
    return {
        "start_line": start,
        "end_line": end,
        "line_count": end - start + 1,
        "preview": " ".join(snippet_lines)[:240],
    }


def pair_methods_by_order(original_methods: list[dict[str, Any]], improved_methods: list[dict[str, Any]]) -> list[dict[str, Any]]:
    out = []
    max_len = max(len(original_methods), len(improved_methods))
    for index in range(max_len):
        old = original_methods[index] if index < len(original_methods) else None
        new = improved_methods[index] if index < len(improved_methods) else None
        out.append({
            "order": index + 1,
            "original_method": old["name"] if old else "",
            "improved_method": new["name"] if new else "",
            "original_blocks": old["block_count"] if old else 0,
            "improved_blocks": new["block_count"] if new else 0,
            "delta_blocks": (new["block_count"] if new else 0) - (old["block_count"] if old else 0),
            "original_blank_lines": len(old["blank_lines"]) if old else 0,
            "improved_blank_lines": len(new["blank_lines"]) if new else 0,
        })
    return out


def top_records(records: list[dict[str, Any]], reverse: bool) -> list[dict[str, Any]]:
    ranked = sorted(records, key=lambda item: item["delta"]["total_blocks"], reverse=reverse)
    filtered = [item for item in ranked if (item["delta"]["total_blocks"] > 0 if reverse else item["delta"]["total_blocks"] < 0)]
    return [
        {
            "relative_test": item["relative_test"],
            "original_blocks": item["original"]["total_blocks"],
            "improved_blocks": item["improved"]["total_blocks"],
            "delta_blocks": item["delta"]["total_blocks"],
            "original_test_methods": item["original"]["test_method_count"],
            "improved_test_methods": item["improved"]["test_method_count"],
        }
        for item in filtered[:25]
    ]


def render_markdown(summary: dict[str, Any]) -> str:
    totals = summary["totals"]
    lines = [
        "# JavaParser Test Block Detection",
        "",
        f"Generated at: `{summary['generated_at']}`",
        f"Data root: `{summary['data_root']}`",
        f"JavaParser jar: `{summary['javaparser_jar']}`",
        "",
        "Definition:",
        "",
        "- Test methods are methods annotated with JUnit test annotations.",
        "- A block is a contiguous group of non-blank source lines inside a test method body.",
        "- Suite-level block count is the sum over all detected test methods in that Java file.",
        "",
        "## Totals",
        "",
        "| Metric | Count |",
        "|---|---:|",
    ]
    for key, value in totals.items():
        lines.append(f"| `{key}` | {value} |")

    lines.extend(["", "## Largest Block Count Increases", ""])
    append_record_table(lines, summary["top_increases"])
    lines.extend(["", "## Largest Block Count Decreases", ""])
    append_record_table(lines, summary["top_decreases"])
    return "\n".join(lines) + "\n"


def append_record_table(lines: list[str], records: list[dict[str, Any]]) -> None:
    if not records:
        lines.append("_No records._")
        return
    lines.append("| Test | Original | Improved | Delta | Methods original/improved |")
    lines.append("|---|---:|---:|---:|---:|")
    for item in records:
        lines.append(
            f"| `{item['relative_test']}` | {item['original_blocks']} | {item['improved_blocks']} | "
            f"{item['delta_blocks']} | {item['original_test_methods']}/{item['improved_test_methods']} |"
        )


def write_json(path: Path, data: Any) -> None:
    path.parent.mkdir(parents=True, exist_ok=True)
    path.write_text(json.dumps(data, indent=2, ensure_ascii=False), encoding="utf-8")

