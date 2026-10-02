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
