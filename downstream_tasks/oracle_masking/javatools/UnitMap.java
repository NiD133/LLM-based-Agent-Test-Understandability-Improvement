import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ParserConfiguration;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.*;
import com.github.javaparser.ast.expr.*;
import com.github.javaparser.ast.stmt.ExpressionStmt;
import java.io.File;
import java.util.*;

/**
 * Structural map of one JUnit file for the v2 masker.
 *
 * ORACLE = a FRAMEWORK assertion/verification statement only (assertX / verifyX /
 * assertThat(..).isX / Mockito verify(..).x, not a call to a method defined in this
 * file). A call to a local helper - even one named assertSomething - is NOT an oracle
 * here: the v2 rule keeps those calls and masks the helper's body instead.
 *
 * Output (1-based lines):
 *   METHOD <idx> <kind> <firstLine> <bodyBegin> <bodyEnd> <name>
 *          kind = TEST (any @Test-style annotation) | HELPER (anything else with a body)
 *          firstLine = first annotation line, or the declaration line if unannotated
 *   ORACLE <begin> <end> <methodIdx>
 *   CALL <callerIdx> <calleeName>        calls to methods declared in this file
 */
public class UnitMap {
    static final Set<String> TEST = Set.of("Test", "ParameterizedTest", "RepeatedTest", "TestFactory", "TestTemplate");
    static Set<String> local = new HashSet<>();

    static boolean staticStyle(Expression scope) {
        if (scope == null) return true;
        if (scope.isNameExpr()) { String s = scope.asNameExpr().getNameAsString(); return !s.isEmpty() && Character.isUpperCase(s.charAt(0)); }
        return scope.isTypeExpr() || scope.isFieldAccessExpr();
    }
    static boolean frameworkName(String n) { return (n.startsWith("assert") || n.startsWith("verify")) && !local.contains(n); }
    static boolean rootsAtOracle(Expression e) {
        Expression cur = e;
        while (cur != null && cur.isMethodCallExpr()) {
            MethodCallExpr mc = cur.asMethodCallExpr();
            if (frameworkName(mc.getNameAsString()) && staticStyle(mc.getScope().orElse(null))) return true;
            cur = mc.getScope().orElse(null);
        }
        return false;
    }
    static boolean isOracle(ExpressionStmt st) {
        Expression ex = st.getExpression();
        if (ex.isMethodCallExpr()) return rootsAtOracle(ex);
        if (ex.isVariableDeclarationExpr())
            for (VariableDeclarator vd : ex.asVariableDeclarationExpr().getVariables())
                if (vd.getInitializer().isPresent() && rootsAtOracle(vd.getInitializer().get())) return true;
        if (ex.isAssignExpr()) return rootsAtOracle(ex.asAssignExpr().getValue());
        return false;
    }

    public static void main(String[] a) throws Exception {
        StaticJavaParser.getParserConfiguration().setLanguageLevel(ParserConfiguration.LanguageLevel.BLEEDING_EDGE);
        CompilationUnit cu = StaticJavaParser.parse(new File(a[0]));
        List<CallableDeclaration<?>> methods = new ArrayList<>();
        for (MethodDeclaration md : cu.findAll(MethodDeclaration.class)) { if (md.getBody().isPresent()) { methods.add(md); local.add(md.getNameAsString()); } }
        StringBuilder sb = new StringBuilder();
        Map<Node, Integer> idx = new IdentityHashMap<>();
        for (int i = 0; i < methods.size(); i++) {
            MethodDeclaration md = (MethodDeclaration) methods.get(i);
            idx.put(md, i);
            boolean test = md.getAnnotations().stream().anyMatch(x -> TEST.contains(x.getNameAsString()));
            int first = md.getAnnotations().stream().mapToInt(x -> x.getBegin().get().line).min()
                    .orElse(md.getBegin().get().line);
            var body = md.getBody().get();
            sb.append("METHOD ").append(i).append(' ').append(test ? "TEST" : "HELPER").append(' ').append(first).append(' ')
              .append(body.getBegin().get().line).append(' ').append(body.getEnd().get().line).append(' ')
              .append(md.getNameAsString()).append('\n');
        }
        for (ExpressionStmt st : cu.findAll(ExpressionStmt.class)) {
            if (!isOracle(st) || st.getBegin().isEmpty()) continue;
            Optional<MethodDeclaration> owner = st.findAncestor(MethodDeclaration.class);
            // an oracle inside a lambda/anonymous class still belongs to its outermost declared method
            MethodDeclaration m = owner.orElse(null);
            while (m != null && !idx.containsKey(m)) m = m.findAncestor(MethodDeclaration.class).orElse(null);
            if (m == null) continue;
            sb.append("ORACLE ").append(st.getBegin().get().line).append(' ').append(st.getEnd().get().line).append(' ').append(idx.get(m)).append('\n');
        }
        for (MethodCallExpr mc : cu.findAll(MethodCallExpr.class)) {
            if (!local.contains(mc.getNameAsString())) continue;
            Expression sc = mc.getScope().orElse(null);
            if (sc != null && !sc.isThisExpr() && !(sc.isNameExpr() && Character.isUpperCase(sc.asNameExpr().getNameAsString().charAt(0)))) continue;
            MethodDeclaration m = mc.findAncestor(MethodDeclaration.class).orElse(null);
            while (m != null && !idx.containsKey(m)) m = m.findAncestor(MethodDeclaration.class).orElse(null);
            if (m == null) continue;
            sb.append("CALL ").append(idx.get(m)).append(' ').append(mc.getNameAsString()).append('\n');
        }
        System.out.print(sb);
    }
}
