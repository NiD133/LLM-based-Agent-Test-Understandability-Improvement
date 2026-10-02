import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ParserConfiguration;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.stmt.ExpressionStmt;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * AST oracle finder. A test oracle is an ASSERTION or VERIFICATION *statement*:
 * a call to assertXxx(...) (JUnit/AssertJ) or verifyXxx(...) (Mockito). This tool
 * prints the 1-based line ranges of every such statement so the caller can delete
 * the WHOLE lines. Unlike a text regex it will NOT match a helper METHOD DEFINITION
 * that merely happens to be named assertX (that is a MethodDeclaration, never an
 * ExpressionStmt), and it will not match an assertX used as an argument of an
 * unrelated call (that is not on the call's scope chain).
 *
 * A locally-defined helper named assertX/verifyX counts as an oracle when its
 * body TRANSITIVELY reaches a framework assertion. This matters because
 * understandability improvement routinely EXTRACTS assertions into named
 * helpers: excluding them made the tool blind to almost every oracle in a
 * refactored test (GrepFilesToolTest: 46 oracles found in the original, 10 in
 * the improved version, with 48 assertion call sites left standing after
 * masking). A local assertX that never actually asserts is still excluded, so
 * the original intent of the exclusion is preserved.
 *
 * Pass --framework-only to reproduce the pre-2026-08-31 behaviour.
 * Diagnostic lines (ignored by the line-range parser):
 *   FRAMEWORK_ONLY_COUNT <n>   what the old rule would have found
 *   HELPER <name>              local helper counted as an oracle
 *   UNNAMED_HELPER <name>      asserts but is not named assertX or verifyX
 *
 * Usage: java -cp javaparser-core-3.27.0.jar:. OracleStripper <test.java>
 * Output: one "DELETE <begin> <end>" per oracle statement, then "COUNT <n>".
 */
public class OracleStripper {

    // Names of methods DEFINED in this test file.
    static Set<String> localMethods = new HashSet<>();
    static Map<String, MethodDeclaration> localByName = new HashMap<>();

    // Local helpers named assert*/verify* whose body TRANSITIVELY reaches a
    // framework assertion. See the class javadoc for why these count as oracles.
    static Set<String> assertHelpers = new HashSet<>();
    // Local methods that assert but are NOT named assertX/verifyX - never
    // stripped, only reported, so the naming rule can be widened on evidence.
    static Set<String> unnamedHelpers = new HashSet<>();

    static boolean isFrameworkOracleName(String n) {
        return (n.startsWith("assert") || n.startsWith("verify")) && !localMethods.contains(n);
    }

    static boolean isOracleName(String n) {
        return isFrameworkOracleName(n) || assertHelpers.contains(n);
    }

    static boolean namedLikeAssertion(String n) {
        return n.startsWith("assert") || n.startsWith("verify");
    }

    /** Does this method body call a framework assertion, or an already-known helper? */
    static boolean bodyReachesOracle(MethodDeclaration md) {
        for (MethodCallExpr mc : md.findAll(MethodCallExpr.class)) {
            String n = mc.getNameAsString();
            if (isFrameworkOracleName(n) || assertHelpers.contains(n)) return true;
        }
        return false;
    }

    /** Fixpoint: a helper that calls a helper that asserts is itself an oracle. */
    static void resolveAssertHelpers() {
        boolean changed = true;
        while (changed) {
            changed = false;
            for (Map.Entry<String, MethodDeclaration> e : localByName.entrySet()) {
                String name = e.getKey();
                if (assertHelpers.contains(name) || !namedLikeAssertion(name)) continue;
                if (bodyReachesOracle(e.getValue())) { assertHelpers.add(name); changed = true; }
            }
        }
        for (Map.Entry<String, MethodDeclaration> e : localByName.entrySet()) {
            String name = e.getKey();
            if (!namedLikeAssertion(name) && bodyReachesOracle(e.getValue())) {
                unnamedHelpers.add(name);
            }
        }
    }

    // A real oracle call is STATIC-style (assertEquals(...), Assertions.assertThat(...),
    // verify(mock), Mockito.verify(...)) — no scope, or a Type scope (Uppercase name).
    // This excludes instance calls like validator.verify() or obj.assertState().
    static boolean isStaticStyle(Expression scope) {
        if (scope == null) return true;
        if (scope.isNameExpr()) {
            String s = scope.asNameExpr().getNameAsString();
            return !s.isEmpty() && Character.isUpperCase(s.charAt(0));
        }
        return scope.isTypeExpr();
    }

    // Follow the call's SCOPE chain (fluent chain) to see if it roots at an oracle
    // call: assertThat(x).isEqualTo(y) → root assertThat ; verify(m).foo() → root verify.
    static boolean chainRootsAtOracle(Expression e) {
        Expression cur = e;
        while (cur != null && cur.isMethodCallExpr()) {
            MethodCallExpr mc = cur.asMethodCallExpr();
            if (isOracleName(mc.getNameAsString()) && isStaticStyle(mc.getScope().orElse(null))) {
                return true;
            }
            cur = mc.getScope().orElse(null);
        }
        return false;
    }

    static boolean isOracleStmt(ExpressionStmt st) {
        Expression ex = st.getExpression();
        if (ex.isMethodCallExpr()) {
            return chainRootsAtOracle(ex);
        }
        if (ex.isVariableDeclarationExpr()) {   // e.g. SomeException e = assertThrows(...)
            for (VariableDeclarator vd : ex.asVariableDeclarationExpr().getVariables()) {
                if (vd.getInitializer().isPresent() && chainRootsAtOracle(vd.getInitializer().get())) {
                    return true;
                }
            }
        }
        if (ex.isAssignExpr()) {                 // e.g. e = assertThrows(...)
            return chainRootsAtOracle(ex.asAssignExpr().getValue());
        }
        return false;
    }

    public static void main(String[] args) throws Exception {
        StaticJavaParser.getParserConfiguration()
                .setLanguageLevel(ParserConfiguration.LanguageLevel.BLEEDING_EDGE);
        boolean frameworkOnly = false;
        String path = null;
        for (String a : args) {
            if ("--framework-only".equals(a)) frameworkOnly = true; else path = a;
        }
        CompilationUnit cu = StaticJavaParser.parse(new File(path));
        for (MethodDeclaration md : cu.findAll(MethodDeclaration.class)) {
            localMethods.add(md.getNameAsString());
            localByName.putIfAbsent(md.getNameAsString(), md);
        }
        if (!frameworkOnly) resolveAssertHelpers();

        List<int[]> ranges = new ArrayList<>();
        for (ExpressionStmt st : cu.findAll(ExpressionStmt.class)) {
            if (isOracleStmt(st) && st.getBegin().isPresent() && st.getEnd().isPresent()) {
                ranges.add(new int[]{st.getBegin().get().line, st.getEnd().get().line});
            }
        }
        // What the pre-fix rule alone would have matched, for the migration diff.
        Set<String> savedHelpers = new HashSet<>(assertHelpers);
        assertHelpers.clear();
        int frameworkCount = 0;
        for (ExpressionStmt st : cu.findAll(ExpressionStmt.class)) {
            if (isOracleStmt(st) && st.getBegin().isPresent()) frameworkCount++;
        }
        assertHelpers.addAll(savedHelpers);

        StringBuilder sb = new StringBuilder();
        for (int[] r : ranges) sb.append("DELETE ").append(r[0]).append(' ').append(r[1]).append('\n');
        sb.append("COUNT ").append(ranges.size()).append('\n');
        sb.append("FRAMEWORK_ONLY_COUNT ").append(frameworkCount).append('\n');
        for (String h : assertHelpers) sb.append("HELPER ").append(h).append('\n');
        for (String h : unnamedHelpers) sb.append("UNNAMED_HELPER ").append(h).append('\n');
        System.out.print(sb);
    }
}
