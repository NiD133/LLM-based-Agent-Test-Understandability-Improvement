/*
 * TestSplitter — AST-based splitter for JUnit 3/4/5 test suites.
 *
 * Run as:
 *   java -cp tools/javaparser/javaparser-core-3.27.0.jar:tools/javaparser \
 *        TestSplitter --input <suite.java> --out <dir> \
 *                     [--include-junit3] [--include-junit4-5]
 *
 * Behaviour:
 *   • Detects every test method in the input compilation unit:
 *       JUnit 4/5: methods annotated with one of
 *                  @Test / @ParameterizedTest / @RepeatedTest /
 *                  @TestFactory / @TestTemplate
 *       JUnit 3 : methods whose enclosing class extends TestCase, whose
 *                  name starts with `test`, return void, take no parameters,
 *                  and are public.
 *   • Walks @Nested (or any inner class) so nested tests are also split.
 *     The output class for a nested test is named
 *         <Outer>_<Inner>_<method>
 *     and flattens fields + lifecycle methods from BOTH outer and inner.
 *   • Each split file inherits:
 *       - package declaration
 *       - all import declarations
 *       - the class header's `extends` clause (so JUnit 3 keeps TestCase)
 *       - every field reachable in the original nesting chain
 *       - every non-test method (@Before/@After/@BeforeEach/@AfterEach/
 *         @BeforeAll/@AfterAll, plus private helpers) reachable in chain
 *       - the single target @Test method
 *   • Prints a JSON manifest to stdout, one element per split:
 *         [{"method_name": "...", "class_name": "...",
 *           "file_name":   "...", "file_path":  "...",
 *           "junit_version": "3|4-5", "marker": "@Test|@ParameterizedTest|name_convention"}, ...]
 *
 * Exit codes: 0 = ok (manifest on stdout). Non-zero = unrecoverable error
 * (stderr carries the message).
 */

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.ImportDeclaration;
import com.github.javaparser.ast.Modifier;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.PackageDeclaration;
import com.github.javaparser.ast.body.BodyDeclaration;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.EnumDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.body.TypeDeclaration;
import com.github.javaparser.ast.expr.AnnotationExpr;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.github.javaparser.ast.type.VoidType;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Pattern;

public class TestSplitter {

    private static final Set<String> JUNIT_TEST_ANNOTATIONS = new LinkedHashSet<>(Arrays.asList(
            "Test", "ParameterizedTest", "RepeatedTest", "TestFactory", "TestTemplate"
    ));

    // Lifecycle annotations preserved on helper methods when flattening.
    private static final Set<String> LIFECYCLE_ANNOTATIONS = new LinkedHashSet<>(Arrays.asList(
            "Before", "After", "BeforeClass", "AfterClass",
            "BeforeEach", "AfterEach", "BeforeAll", "AfterAll"
    ));

    // FIX C: annotations stripped from a sibling @Test method that is pulled
    // into a split only because the target CALLS it (so it runs as a plain
    // helper, not its own test and without demanding its parameter source).
    private static final Set<String> HELPER_STRIP_ANNOTATIONS = new LinkedHashSet<>(Arrays.asList(
            "Test", "ParameterizedTest", "RepeatedTest", "TestFactory", "TestTemplate",
            "MethodSource", "ValueSource", "EnumSource", "CsvSource", "CsvFileSource",
            "NullSource", "EmptySource", "NullAndEmptySource", "FieldSource",
            "ArgumentsSource", "ArgumentsSources", "Disabled", "Nested", "Order"
    ));

    public static void main(String[] args) throws IOException {
        // Parse modern Java: text blocks (Java 15+), records, etc. Without this,
        // StaticJavaParser defaults to an older level and fails on text blocks
        // ("""...""") with "Parse failed" → 0 cases split (e.g. DependenciesReporterTest).
        StaticJavaParser.getParserConfiguration()
                .setLanguageLevel(com.github.javaparser.ParserConfiguration.LanguageLevel.JAVA_17);
        Path input = null;
        Path outDir = null;
        boolean includeJunit3 = true;
        boolean includeJunit45 = true;
        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "--input":         input = Paths.get(args[++i]); break;
                case "--out":           outDir = Paths.get(args[++i]); break;
                case "--include-junit3":         includeJunit3  = true;  break;
                case "--no-include-junit3":      includeJunit3  = false; break;
                case "--include-junit4-5":       includeJunit45 = true;  break;
                case "--no-include-junit4-5":    includeJunit45 = false; break;
                default: System.err.println("Unknown arg: " + args[i]); System.exit(2);
            }
        }
        if (input == null || outDir == null) {
            System.err.println("usage: TestSplitter --input <file.java> --out <dir>");
            System.exit(2);
        }
        Files.createDirectories(outDir);

        CompilationUnit cu;
        try {
            cu = StaticJavaParser.parse(input);
        } catch (Exception e) {
            System.err.println("Parse failed: " + e.getMessage());
            System.exit(3);
            return;
        }

        // Flatten test-class inheritance BEFORE splitting: if a top-level test
        // class extends a base that itself declares @Test methods (e.g.
        // AbstractParserTestCase), inline the base's members and drop `extends`,
        // so each split runs ONLY its target instead of inheriting & RUNNING all
        // of the base's @Test methods (which otherwise breaks coverage + PIT).
        for (ClassOrInterfaceDeclaration topClass : new ArrayList<>(
                cu.findAll(ClassOrInterfaceDeclaration.class))) {
            if (topClass.isInterface() || !isTopLevel(topClass)) continue;
            try {
                flattenTestInheritance(topClass, input, cu);
            } catch (Exception e) {
                System.err.println("flatten warning: " + e.getMessage());
            }
        }

        Optional<PackageDeclaration> pkg = cu.getPackageDeclaration();
        NodeList<ImportDeclaration> imports = cu.getImports();

        // File-level helper candidates: top-level types declared ALONGSIDE the
        // test suite class(es) in the same .java file (siblings, NOT nested
        // inside the suite) that do not themselves declare test methods — e.g.
        // `enum Traffic { ... }` or `class BadHasher { ... }` sitting after the
        // suite class in the same file. These are invisible to the per-class
        // member walk below (they aren't members of the suite class at all),
        // so without this a split that references them fails to compile
        // ("cannot find symbol") — the type simply never got copied anywhere.
        List<BodyDeclaration<?>> fileLevelHelperCandidates = new ArrayList<>();
        for (TypeDeclaration<?> td : cu.getTypes()) {
            if (td instanceof ClassOrInterfaceDeclaration) {
                ClassOrInterfaceDeclaration cd = (ClassOrInterfaceDeclaration) td;
                if (cd.isInterface() || declaresTestGeneric(cd)) continue;
                fileLevelHelperCandidates.add(cd);
            } else if (td instanceof EnumDeclaration) {
                EnumDeclaration ed = (EnumDeclaration) td;
                if (declaresTestGeneric(ed)) continue;
                fileLevelHelperCandidates.add(ed);
            }
        }

        List<String> manifest = new ArrayList<>();
        for (ClassOrInterfaceDeclaration topClass : cu.findAll(ClassOrInterfaceDeclaration.class)) {
            if (topClass.isInterface()) continue;
            if (!isTopLevel(topClass)) continue;
            walkClass(topClass, new ArrayList<>(), pkg, imports, outDir,
                    includeJunit3, includeJunit45, manifest, fileLevelHelperCandidates);
        }

        System.out.print("[" + String.join(",", manifest) + "]");
    }

    private static boolean isTopLevel(ClassOrInterfaceDeclaration cls) {
        return cls.getParentNode().map(p -> !(p instanceof ClassOrInterfaceDeclaration)).orElse(true);
    }

    /** Recursively walk a class and any inner classes, emitting one split per test. */
    private static void walkClass(
            ClassOrInterfaceDeclaration cls,
            List<ClassOrInterfaceDeclaration> outerChain,
            Optional<PackageDeclaration> pkg,
            NodeList<ImportDeclaration> imports,
            Path outDir,
            boolean includeJunit3,
            boolean includeJunit45,
            List<String> manifest,
            List<BodyDeclaration<?>> fileLevelHelperCandidates) throws IOException {

        List<ClassOrInterfaceDeclaration> chain = new ArrayList<>(outerChain);
        chain.add(cls);

        for (MethodDeclaration m : cls.getMethods()) {
            TestMarker marker = detectMarker(cls, m, includeJunit3, includeJunit45);
            if (marker == null) continue;
            if (hasDisabled(m)) continue;   // @Disabled / @Ignore → no case

            String newClassName = joinClassNames(chain) + "_" + m.getNameAsString();
            Path outFile = outDir.resolve(newClassName + ".java");

            CompilationUnit out = buildSplitCu(pkg, imports, chain, m, newClassName,
                    fileLevelHelperCandidates);
            Files.writeString(outFile, out.toString());

            manifest.add(toJson(
                    m.getNameAsString(),
                    newClassName,
                    outFile.getFileName().toString(),
                    outFile.toAbsolutePath().toString(),
                    marker.junitVersion,
                    marker.label
            ));
        }
        for (ClassOrInterfaceDeclaration inner :
                cls.findAll(ClassOrInterfaceDeclaration.class, c -> isDirectMember(c, cls))) {
            if (inner.isInterface()) continue;
            walkClass(inner, chain, pkg, imports, outDir,
                    includeJunit3, includeJunit45, manifest, fileLevelHelperCandidates);
        }
    }

    private static boolean isDirectMember(ClassOrInterfaceDeclaration candidate,
                                          ClassOrInterfaceDeclaration outer) {
        if (candidate == outer) return false;
        return candidate.getParentNode().map(p -> p == outer).orElse(false);
    }

    private static String joinClassNames(List<ClassOrInterfaceDeclaration> chain) {
        StringBuilder b = new StringBuilder();
        for (int i = 0; i < chain.size(); i++) {
            if (i > 0) b.append("_");
            b.append(chain.get(i).getNameAsString());
        }
        return b.toString();
    }

    /** Decide whether `m` is a JUnit test, and if so under which version. */
    private static TestMarker detectMarker(ClassOrInterfaceDeclaration cls,
                                           MethodDeclaration m,
                                           boolean includeJunit3,
                                           boolean includeJunit45) {
        if (includeJunit45) {
            for (AnnotationExpr ann : m.getAnnotations()) {
                String name = ann.getNameAsString();
                // Strip qualifier: handle @org.junit.Test as well.
                int dot = name.lastIndexOf('.');
                if (dot >= 0) name = name.substring(dot + 1);
                if (JUNIT_TEST_ANNOTATIONS.contains(name)) {
                    return new TestMarker("4-5", "@" + name);
                }
            }
        }
        if (includeJunit3 && extendsTestCase(cls)) {
            boolean nameOk    = m.getNameAsString().startsWith("test")
                                && m.getNameAsString().length() > 4;
            boolean publicOk  = m.getModifiers().contains(Modifier.publicModifier());
            boolean voidOk    = m.getType() instanceof VoidType;
            boolean paramsOk  = m.getParameters().isEmpty();
            if (nameOk && publicOk && voidOk && paramsOk) {
                return new TestMarker("3", "name_convention");
            }
        }
        return null;
    }

    private static boolean extendsTestCase(ClassOrInterfaceDeclaration cls) {
        for (ClassOrInterfaceType t : cls.getExtendedTypes()) {
            String n = t.getNameAsString();
            int dot = n.lastIndexOf('.');
            if (dot >= 0) n = n.substring(dot + 1);
            if ("TestCase".equals(n)) return true;
        }
        return false;
    }

    /** True iff the method carries @Disabled (JUnit 5) or @Ignore (JUnit 4). */
    private static boolean hasDisabled(MethodDeclaration m) {
        for (AnnotationExpr a : m.getAnnotations()) {
            String n = stripQualifier(a.getNameAsString());
            if ("Disabled".equals(n) || "Ignore".equals(n)) return true;
        }
        return false;
    }

    /** name + parameter-type signature, for override detection. */
    private static String sig(MethodDeclaration m) {
        StringBuilder b = new StringBuilder(m.getNameAsString()).append('(');
        for (Parameter p : m.getParameters()) b.append(p.getType().asString()).append(',');
        return b.append(')').toString();
    }

    /** If `cls` extends a non-TestCase base that itself declares @Test methods,
     *  inline the base's members into `cls` and drop the `extends`, so the later
     *  per-method split runs ONLY its target. Subclass overrides win; a base
     *  method overridden by the subclass is kept as a private `__super_<name>`
     *  and `super.<name>(...)` calls are rewritten to it. Recurses for the base's
     *  own superclass. TestCase (JUnit 3) inheritance is intentionally NOT
     *  flattened (it must keep `extends TestCase`). */
    private static void flattenTestInheritance(ClassOrInterfaceDeclaration cls,
                                               Path inputPath,
                                               CompilationUnit cu) throws IOException {
        ClassOrInterfaceType baseType = null;
        for (ClassOrInterfaceType t : cls.getExtendedTypes()) {
            if (!"TestCase".equals(stripQualifier(t.getNameAsString()))) { baseType = t; break; }
        }
        if (baseType == null || inputPath.getParent() == null) return;

        String baseName = stripQualifier(baseType.getNameAsString());
        Path baseFile = inputPath.getParent().resolve(baseName + ".java");
        if (!Files.exists(baseFile)) return;                 // base source not local → leave as-is

        CompilationUnit baseCu;
        try { baseCu = StaticJavaParser.parse(baseFile); } catch (Exception e) { return; }
        Optional<ClassOrInterfaceDeclaration> baseOpt = baseCu.getClassByName(baseName);
        if (!baseOpt.isPresent()) {
            baseOpt = baseCu.findFirst(ClassOrInterfaceDeclaration.class,
                    c -> baseName.equals(c.getNameAsString()) && !c.isInterface());
        }
        if (!baseOpt.isPresent()) return;
        ClassOrInterfaceDeclaration base = baseOpt.get();

        flattenTestInheritance(base, baseFile, baseCu);      // transitive

        boolean baseHasTests = base.getMethods().stream().anyMatch(m -> isAnyTestMethod(base, m));
        if (!baseHasTests) return;

        // FAITHFUL-FLATTEN FIX 3: build type-argument substitution.
        // When the subclass binds the base's type parameter to a CONCRETE type
        // (e.g. `IndexedCollectionTest extends AbstractCollectionTest<String>`,
        // `... extends AbstractIteratorTest<List<Character>>`), the base members
        // we inline use the base's parameter name `E` — which is out of scope
        // once `extends` is dropped. Map each base type-parameter name to the
        // concrete argument the subclass supplied, and rewrite bare references
        // to it inside every inlined member. (When the subclass passes its OWN
        // parameter through — `extends AbstractIteratorTest<E>` — the argument is
        // just `E`, so this is an identity map and FIX 1 keeps `<E>` on the class.)
        java.util.Map<String, com.github.javaparser.ast.type.Type> tpSub = new java.util.HashMap<>();
        java.util.List<com.github.javaparser.ast.type.TypeParameter> baseTps = base.getTypeParameters();
        if (baseType.getTypeArguments().isPresent()) {
            NodeList<com.github.javaparser.ast.type.Type> args = baseType.getTypeArguments().get();
            for (int i = 0; i < baseTps.size() && i < args.size(); i++) {
                tpSub.put(baseTps.get(i).getNameAsString(), args.get(i));
            }
        }

        Set<String> subSigs = new HashSet<>();
        for (MethodDeclaration m : cls.getMethods()) subSigs.add(sig(m));
        Set<String> subFieldNames = new HashSet<>();
        for (FieldDeclaration f : cls.getFields())
            f.getVariables().forEach(v -> subFieldNames.add(v.getNameAsString()));
        Set<String> overriddenNames = new HashSet<>();

        for (BodyDeclaration<?> member : base.getMembers()) {
            if (member instanceof MethodDeclaration) {
                MethodDeclaration bm = (MethodDeclaration) member;
                if (subSigs.contains(sig(bm))) {             // subclass overrides
                    // FAITHFUL-FLATTEN FIX 2: only preserve the base body as a
                    // private __super_ shim when the base method ACTUALLY has a
                    // body. Abstract base methods (e.g. `public abstract Iterator<E>
                    // makeObject();` in AbstractIteratorTest) have none; cloning
                    // one yields an uncompilable `private Iterator<E>
                    // __super_makeObject();` bodyless declaration. There is also
                    // nothing to call (`super.makeObject()` is illegal on an
                    // abstract method), so the subclass's concrete override is the
                    // only implementation and no shim is needed.
                    if (bm.getBody().isPresent()) {
                        MethodDeclaration superCopy = bm.clone();
                        superCopy.setName("__super_" + bm.getNameAsString());
                        superCopy.getAnnotations().clear();
                        superCopy.setModifiers(Modifier.Keyword.PRIVATE);
                        substituteTypeParams(superCopy, tpSub);
                        cls.addMember(superCopy);
                        overriddenNames.add(bm.getNameAsString());
                    }
                } else if (bm.getBody().isPresent() || !bm.isAbstract()) {
                    MethodDeclaration mc = bm.clone();
                    substituteTypeParams(mc, tpSub);
                    cls.addMember(mc);
                }
            } else if (member instanceof FieldDeclaration) {
                FieldDeclaration bf = (FieldDeclaration) member;
                boolean clash = bf.getVariables().stream()
                        .anyMatch(v -> subFieldNames.contains(v.getNameAsString()));
                if (!clash) {
                    FieldDeclaration fc = bf.clone();
                    substituteTypeParams(fc, tpSub);
                    cls.addMember(fc);
                }
            } else if (member instanceof ClassOrInterfaceDeclaration
                    || member instanceof EnumDeclaration) {
                // FAITHFUL-FLATTEN FIX 4: inline the base's NESTED HELPER types
                // (e.g. `AbstractBloomFilterTest.BadHasher`). A split test that
                // references such a type by simple name (`new BadHasher(...)`)
                // otherwise fails to compile once `extends` is dropped. Skip
                // test-bearing nested classes (they become their own splits) and
                // any nested type the subclass already declares (name clash). The
                // tree-shaker in buildStandalone later drops the ones the target
                // never references, so over-including here is safe.
                TypeDeclaration<?> nt = (TypeDeclaration<?>) member;
                if (!declaresTestGeneric(nt)) {
                    String ntName = nt.getNameAsString();
                    boolean clash = cls.getMembers().stream()
                            .anyMatch(mm -> mm instanceof TypeDeclaration
                                    && ((TypeDeclaration<?>) mm).getNameAsString().equals(ntName));
                    if (!clash) {
                        BodyDeclaration<?> ntc = nt.clone();
                        substituteTypeParams(ntc, tpSub);
                        cls.addMember(ntc);
                    }
                }
            }
            // constructors are not inlined
        }

        for (MethodCallExpr call : cls.findAll(MethodCallExpr.class)) {
            if (call.getScope().isPresent() && call.getScope().get().isSuperExpr()
                    && overriddenNames.contains(call.getNameAsString())) {
                call.setName("__super_" + call.getNameAsString());
                call.removeScope();
            }
        }
        for (MethodDeclaration md : cls.getMethods()) {       // @Override is now invalid
            md.getAnnotations().removeIf(a -> "Override".equals(stripQualifier(a.getNameAsString())));
        }
        cls.getExtendedTypes().remove(baseType);
        for (ImportDeclaration imp : baseCu.getImports()) {
            boolean dup = false;
            for (ImportDeclaration ex : cu.getImports())
                if (ex.toString().equals(imp.toString())) { dup = true; break; }
            if (!dup) cu.addImport(imp.clone());
        }
    }

    /** FAITHFUL-FLATTEN FIX 3 helper: rewrite bare references to a base type
     *  parameter (`E`) with the concrete type argument the subclass bound
     *  (`String`, `List<Character>`), inside one inlined member. Only replaces a
     *  ClassOrInterfaceType that (a) has no scope and (b) has no type arguments
     *  of its own — i.e. a bare `E`, not `Collection` in `Collection<E>`. Matches
     *  are collected before replacing to avoid mutating the tree mid-traversal. */
    private static void substituteTypeParams(
            com.github.javaparser.ast.Node member,
            java.util.Map<String, com.github.javaparser.ast.type.Type> sub) {
        if (sub.isEmpty()) return;
        java.util.List<ClassOrInterfaceType> hits = new java.util.ArrayList<>();
        for (ClassOrInterfaceType t : member.findAll(ClassOrInterfaceType.class)) {
            if (t.getScope().isPresent()) continue;
            if (t.getTypeArguments().isPresent()) continue;
            if (sub.containsKey(t.getNameAsString())) hits.add(t);
        }
        for (ClassOrInterfaceType t : hits) {
            t.replace(sub.get(t.getNameAsString()).clone());
        }
    }

    /** Build a standalone CU containing just the target test plus shared body. */
    // Lifecycle method = annotated @Before.../@After... (JUnit 4/5) OR named
    // setUp/tearDown (JUnit 3). Always kept: it runs around every test.
    private static boolean isLifecycle(MethodDeclaration m) {
        for (AnnotationExpr a : m.getAnnotations()) {
            if (LIFECYCLE_ANNOTATIONS.contains(stripQualifier(a.getNameAsString()))) return true;
        }
        String n = m.getNameAsString();
        return n.equals("setUp") || n.equals("tearDown");
    }

    /** True iff this (nested) class declares at least one @Test-style method —
     *  such classes get their OWN split, so we never carry them as helpers. */
    private static boolean declaresTest(ClassOrInterfaceDeclaration cls) {
        return declaresTestGeneric(cls);
    }

    /** Same check as {@link #declaresTest}, but also works for EnumDeclaration
     *  (and any other NodeWithMembers-style top-level type), so file-level
     *  sibling helper types are screened the same way nested ones are. */
    private static boolean declaresTestGeneric(BodyDeclaration<?> td) {
        List<MethodDeclaration> methods;
        if (td instanceof ClassOrInterfaceDeclaration) {
            methods = ((ClassOrInterfaceDeclaration) td).getMethods();
        } else if (td instanceof EnumDeclaration) {
            methods = ((EnumDeclaration) td).getMethods();
        } else {
            return false;
        }
        for (MethodDeclaration m : methods) {
            for (AnnotationExpr a : m.getAnnotations()) {
                if (JUNIT_TEST_ANNOTATIONS.contains(stripQualifier(a.getNameAsString()))) return true;
            }
        }
        return false;
    }

    /** Simple-name of a class/enum helper, regardless of which subtype it is. */
    private static String helperName(BodyDeclaration<?> td) {
        if (td instanceof ClassOrInterfaceDeclaration) return ((ClassOrInterfaceDeclaration) td).getNameAsString();
        if (td instanceof EnumDeclaration) return ((EnumDeclaration) td).getNameAsString();
        return "";
    }

    /** Whole-word occurrence of `name` in `text`. Used for conservative,
     *  name-based reachability: catches direct calls `name(...)` AND string
     *  references like @MethodSource("name") / @FieldSource("name") that a
     *  call-graph walk would miss. Over-includes (overloads, coincidental
     *  names) rather than risk dropping a runtime-needed provider. */
    private static boolean nameReferenced(String name, String text) {
        return Pattern.compile("\\b" + Pattern.quote(name) + "\\b").matcher(text).find();
    }

    /** FIX C: true iff `name` appears as an actual method CALL — the identifier
     *  followed (after optional whitespace) by `(`. Stricter than nameReferenced
     *  so a test name that merely appears in a comment or string does not pull a
     *  sibling @Test method into an otherwise-working split. */
    private static boolean calledAsMethod(String name, String text) {
        // Require an UNQUALIFIED call: `name(` not preceded by `.` or a word
        // char. This is how a sibling helper is invoked; it excludes qualified
        // calls like `Entities.unescape(` (the CUT's own method that merely
        // shares a name with a sibling test) which would otherwise false-match.
        return Pattern.compile("(?<![\\w.])" + Pattern.quote(name) + "\\s*\\(").matcher(text).find();
    }

    private static CompilationUnit buildSplitCu(
            Optional<PackageDeclaration> pkg,
            NodeList<ImportDeclaration> imports,
            List<ClassOrInterfaceDeclaration> chain,
            MethodDeclaration target,
            String newClassName,
            List<BodyDeclaration<?>> fileLevelHelperCandidates) {

        CompilationUnit out = new CompilationUnit();
        pkg.ifPresent(p -> out.setPackageDeclaration(p.clone()));
        for (ImportDeclaration imp : imports) {
            out.addImport(imp.clone());
        }

        ClassOrInterfaceDeclaration innermost = chain.get(chain.size() - 1);
        ClassOrInterfaceDeclaration newClass = new ClassOrInterfaceDeclaration();
        newClass.setName(newClassName);
        newClass.setModifiers(Modifier.Keyword.PUBLIC);

        // FAITHFUL-FLATTEN FIX 1: preserve the test class's OWN type parameters.
        // A generic developer suite (e.g. `class BoundedIteratorTest<E> extends
        // AbstractIteratorTest<E>`) declares fields/locals in terms of `E`
        // (`List<E> testList; (E[]) testArray`). When we flatten inheritance and
        // drop the `extends`, `E` is only still in scope if the standalone class
        // keeps its own `<E>`. Without this the split fails to compile → JaCoCo
        // measures 0 coverage. JUnit instantiates the raw type, so keeping the
        // parameter is behaviour-preserving. Union across the chain (outer→inner)
        // so nested generic suites keep every visible parameter, deduped by name.
        java.util.Set<String> seenTp = new java.util.HashSet<>();
        for (ClassOrInterfaceDeclaration c : chain) {
            for (com.github.javaparser.ast.type.TypeParameter tp : c.getTypeParameters()) {
                if (seenTp.add(tp.getNameAsString())) {
                    newClass.addTypeParameter(tp.clone());
                }
            }
        }

        // Preserve `extends` from the innermost class (matters for JUnit 3).
        for (ClassOrInterfaceType ext : innermost.getExtendedTypes()) {
            newClass.addExtendedType(ext.clone());
        }
        // Preserve `implements` too — costs nothing and keeps semantics.
        for (ClassOrInterfaceType impl : innermost.getImplementedTypes()) {
            newClass.addImplementedType(impl.clone());
        }

        // Preserve the SUITE's class-level annotations on EVERY split case so
        // each case runs under the SAME runner/setup as the original suite —
        // e.g. @RunWith(EvoRunner.class) + @EvoRunnerParameters for EvoSuite
        // (auto), @RunWith(Parameterized.class) / @FixMethodOrder for manual.
        // Source = the OUTERMOST class (chain.get(0)), where @RunWith lives;
        // for non-nested suites that is the suite class itself. Previously
        // dropped → EvoSuite cases lost @RunWith and JaCoCo measured 0 coverage.
        for (AnnotationExpr classAnn : chain.get(0).getAnnotations()) {
            newClass.getAnnotations().add(classAnn.clone());
        }

        // ── TREE-SHAKE to the MINIMUM EXECUTABLE PART ────────────────────────
        // Keep ALL fields (cheap + safe), but keep only the helper methods +
        // nested helper classes the target test TRANSITIVELY references. This
        //   (a) drops the dead helpers the splitter used to over-copy (a case
        //       like testNullOption carried 5 @MethodSource providers + 2
        //       helpers it never calls), and
        //   (b) CARRIES nested provider classes that the old logic dropped
        //       (e.g. @ArgumentsSource(Provider.class)), which used to break
        //       parameterized splits → 0 coverage.
        // Reachability is by NAME (whole-word), so it catches BOTH direct calls
        // AND string refs like @MethodSource("parsers"). It over-includes
        // (overloads / coincidental names) rather than risk dropping a
        // runtime-needed provider — a 0-coverage measurement bug.
        List<FieldDeclaration> allFields = new ArrayList<>();
        List<MethodDeclaration> helpers = new ArrayList<>();          // non-test, non-target
        List<MethodDeclaration> testHelpers = new ArrayList<>();      // FIX C: sibling @Test methods, pulled in only if CALLED
        // BodyDeclaration<?> so this covers BOTH nested classes AND nested
        // enums (an enum member was previously invisible here entirely).
        List<BodyDeclaration<?>> nestedHelpers = new ArrayList<>();
        for (ClassOrInterfaceDeclaration src : chain) {
            for (BodyDeclaration<?> member : src.getMembers()) {
                if (member instanceof FieldDeclaration) {
                    allFields.add((FieldDeclaration) member);
                } else if (member instanceof MethodDeclaration) {
                    MethodDeclaration md = (MethodDeclaration) member;
                    if (md == target) continue;
                    // FAITHFUL-FLATTEN FIX C: a sibling @Test method can be called
                    // by the target as a plain helper (threeten
                    // `test_plus_leap_TemporalUnit` body calls `test_plus_TemporalUnit`,
                    // itself a @ParameterizedTest). Such methods go in a SEPARATE
                    // pool matched by actual CALL-SITE (`name(`), not by loose
                    // name-in-text — otherwise a test name appearing in a comment/
                    // string would drag unrelated tests into working splits (61
                    // false regressions when matched loosely). Non-test helpers
                    // keep the original loose matching.
                    if (isAnyTestMethod(src, md)) testHelpers.add(md);
                    else helpers.add(md);
                } else if (member instanceof ClassOrInterfaceDeclaration
                        || member instanceof EnumDeclaration) {
                    nestedHelpers.add(member);
                }
            }
        }
        // ALSO consider file-level siblings (helper classes/enums declared in
        // the same .java file but outside the suite class entirely, e.g.
        // `enum Traffic { ... }` after the suite's closing brace). Without
        // this, a target test that references such a type compiles fine in
        // the ORIGINAL suite (same file) but fails once split into its own
        // file, because the type was never copied anywhere.
        nestedHelpers.addAll(fileLevelHelperCandidates);

        // Seed: the target's own lifecycle methods always run around it.
        Set<MethodDeclaration> keptHelpers = new LinkedHashSet<>();
        Set<MethodDeclaration> keptFromTest = new LinkedHashSet<>();   // FIX C: kept @Test-as-helper → de-annotate on emit
        for (MethodDeclaration md : helpers) if (isLifecycle(md)) keptHelpers.add(md);
        Set<BodyDeclaration<?>> keptNested = new LinkedHashSet<>();
        // Fixpoint over names appearing in the kept text (target + kept helpers
        // + ALL fields + kept nested classes). Repeat until nothing new is added
        // so transitive references (helper→helper, provider→helper) are caught.
        boolean changed = true;
        while (changed) {
            changed = false;
            StringBuilder kt = new StringBuilder(target.toString());
            for (MethodDeclaration md : keptHelpers) kt.append('\n').append(md.toString());
            for (FieldDeclaration fd : allFields)    kt.append('\n').append(fd.toString());
            for (BodyDeclaration<?> nc : keptNested) kt.append('\n').append(nc.toString());
            String text = kt.toString();
            for (MethodDeclaration md : helpers) {
                if (keptHelpers.contains(md)) continue;
                if (nameReferenced(md.getNameAsString(), text)) { keptHelpers.add(md); changed = true; }
            }
            // FIX C: pull a sibling @Test method in ONLY when it is actually
            // CALLED (`name(`), then treat it as a plain helper. Strict call-site
            // match (not loose name-in-text) avoids dragging unrelated tests into
            // working splits.
            for (MethodDeclaration md : testHelpers) {
                if (keptHelpers.contains(md)) continue;
                // Skip overloads sharing the TARGET's name: the target's own
                // signature text `foo(` would falsely match calledAsMethod and
                // drag in an unrelated sibling `foo(...)` overload.
                if (md.getNameAsString().equals(target.getNameAsString())) continue;
                if (calledAsMethod(md.getNameAsString(), text)) {
                    keptHelpers.add(md); keptFromTest.add(md); changed = true;
                }
            }
            for (BodyDeclaration<?> nc : nestedHelpers) {
                if (keptNested.contains(nc) || declaresTestGeneric(nc)) continue;  // test-bearing → own split
                if (nameReferenced(helperName(nc), text)) { keptNested.add(nc); changed = true; }
            }
        }
        // Emit: all fields, then the reachable helpers + nested helper classes.
        for (FieldDeclaration fd : allFields) newClass.addMember(fd.clone());
        for (MethodDeclaration md : keptHelpers) {
            MethodDeclaration c = md.clone();
            c.getAnnotations().removeIf(a -> "Nested".equals(stripQualifier(a.getNameAsString())));
            // FIX C: only a sibling @Test method pulled in because the target
            // CALLS it gets its test-trigger + parameter-source annotations
            // stripped, so it runs as a plain helper (not its own test, and
            // without demanding its @MethodSource provider). Ordinary helpers
            // are untouched.
            if (keptFromTest.contains(md)) {
                c.getAnnotations().removeIf(a -> HELPER_STRIP_ANNOTATIONS.contains(
                        stripQualifier(a.getNameAsString())));
            }
            newClass.addMember(c);
        }
        for (BodyDeclaration<?> nc : keptNested) newClass.addMember((BodyDeclaration<?>) nc.clone());

        // FAITHFUL-FLATTEN FIX 5: carry the suite's NO-ARG CONSTRUCTOR. Some
        // suites initialize (often `final`) fields in a constructor rather than a
        // field initializer or @BeforeEach — e.g. commons-math
        // `EnumeratedRealDistributionTest()` sets `private final testDistribution`.
        // JUnit instantiates the test class via its no-arg constructor, so
        // carrying it (renamed to the split class) both compiles (final field is
        // definitely assigned) and reproduces the original setup. Parameterized
        // constructors are never invoked by JUnit and may reference dropped
        // context, so only the no-arg one is carried. GUARD: carry it ONLY when
        // the class actually needs it — it has an instance `final` field with no
        // initializer (must be assigned in a constructor, else the split won't
        // compile). Carrying unconditionally changes working splits that merely
        // declare a constructor (19 false regressions).
        boolean needsCtor = false;
        for (ClassOrInterfaceDeclaration src : chain)
            for (FieldDeclaration fd : src.getFields())
                if (!fd.isStatic() && fd.isFinal()
                        && fd.getVariables().stream().anyMatch(v -> !v.getInitializer().isPresent()))
                    needsCtor = true;
        if (needsCtor) {
            for (ClassOrInterfaceDeclaration src : chain) {
                for (com.github.javaparser.ast.body.ConstructorDeclaration ctor
                        : src.getConstructors()) {
                    if (!ctor.getParameters().isEmpty()) continue;
                    com.github.javaparser.ast.body.ConstructorDeclaration cc = ctor.clone();
                    cc.setName(newClassName);
                    newClass.addMember(cc);
                }
            }
        }

        MethodDeclaration cloned = target.clone();
        newClass.addMember(cloned);   // the single target @Test, added last

        out.addType(newClass);
        return out;
    }

    /** True iff `m` would itself be a split target — under any of the three
     *  conventions we recognise. We use this to drop *sibling* test methods
     *  from a split, so each output file contains exactly one runnable test. */
    private static boolean isAnyTestMethod(ClassOrInterfaceDeclaration src,
                                           MethodDeclaration m) {
        for (AnnotationExpr ann : m.getAnnotations()) {
            String name = stripQualifier(ann.getNameAsString());
            if (JUNIT_TEST_ANNOTATIONS.contains(name)) return true;
        }
        if (extendsTestCase(src)) {
            boolean nameOk    = m.getNameAsString().startsWith("test")
                                && m.getNameAsString().length() > 4;
            boolean publicOk  = m.getModifiers().contains(Modifier.publicModifier());
            boolean voidOk    = m.getType() instanceof VoidType;
            boolean paramsOk  = m.getParameters().isEmpty();
            return nameOk && publicOk && voidOk && paramsOk;
        }
        return false;
    }

    private static String stripQualifier(String name) {
        int dot = name.lastIndexOf('.');
        return dot >= 0 ? name.substring(dot + 1) : name;
    }

    private static String toJson(String methodName, String className,
                                 String fileName, String filePath,
                                 String junitVersion, String marker) {
        StringBuilder b = new StringBuilder();
        b.append('{');
        appendKv(b, "method_name", methodName, true);
        appendKv(b, "class_name", className, false);
        appendKv(b, "file_name", fileName, false);
        appendKv(b, "file_path", filePath, false);
        appendKv(b, "junit_version", junitVersion, false);
        appendKv(b, "marker", marker, false);
        b.append('}');
        return b.toString();
    }

    private static void appendKv(StringBuilder b, String k, String v, boolean first) {
        if (!first) b.append(',');
        b.append('"').append(k).append("\":\"").append(escape(v)).append('"');
    }

    private static String escape(String s) {
        StringBuilder b = new StringBuilder(s.length());
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            switch (c) {
                case '"': b.append("\\\""); break;
                case '\\': b.append("\\\\"); break;
                case '\n': b.append("\\n");  break;
                case '\r': b.append("\\r");  break;
                case '\t': b.append("\\t");  break;
                default:
                    if (c < 0x20) b.append(String.format("\\u%04x", (int) c));
                    else b.append(c);
            }
        }
        return b.toString();
    }

    static final class TestMarker {
        final String junitVersion;   // "3" or "4-5"
        final String label;          // e.g. "@Test", "@ParameterizedTest", "name_convention"
        TestMarker(String v, String l) { junitVersion = v; label = l; }
    }
}
