package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.mutable.MutableObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests StringSubstitutor behavior when an empty variable key is combined with a default value.
 * <p>
 * The syntax {@code ${:-defaultValue}} has an empty variable name followed by the ":-" delimiter
 * and a fallback. Because the key is empty, no map entry matches, so the substitutor must return
 * the default value alone.
 * </p>
 */
public class StringSubstitutorTest_testReplaceEmptyKeyWithDefaultOnlyShortest {

    // Named-variable values reused by setUp; kept as constants to document intent.
    private static final String ACTUAL_ANIMAL = "quick brown fox";
    private static final String ACTUAL_TARGET  = "lazy dog";

    protected Map<String, String> values;

    /**
     * Convenience wrapper: creates a fresh {@link StringSubstitutor} from {@link #values} and
     * delegates to {@link #doTestReplace}.
     */
    protected void doReplace(final String expectedResult, final String replaceTemplate, final boolean substring)
            throws IOException {
        doTestReplace(new StringSubstitutor(values), expectedResult, replaceTemplate, substring);
    }

    /**
     * Asserts that {@code sub} resolves {@code replaceTemplate} to {@code expectedResult} across
     * every input type supported by {@link StringSubstitutor}: String, char[], StringBuffer,
     * StringBuilder, TextStringBuilder, and Object. When {@code substring} is {@code true}, also
     * verifies the result for a sub-range that excludes the first and last characters.
     */
    protected void doTestReplace(final StringSubstitutor sub,
                                  final String expectedResult,
                                  final String replaceTemplate,
                                  final boolean substring) throws IOException {

        final String expectedShortResult =
                substring ? expectedResult.substring(1, expectedResult.length() - 1) : expectedResult;

        // --- replace(String) ---
        final String actual = replace(sub, replaceTemplate);
        assertEquals(expectedResult, actual,
                () -> String.format("Index of difference: %,d",
                        StringUtils.indexOfDifference(expectedResult, actual)));
        if (substring) {
            assertEquals(expectedShortResult,
                    sub.replace(replaceTemplate, 1, replaceTemplate.length() - 2));
        }

        // --- replace(char[]) ---
        final char[] chars = replaceTemplate.toCharArray();
        assertEquals(expectedResult, sub.replace(chars));
        if (substring) {
            assertEquals(expectedShortResult, sub.replace(chars, 1, chars.length - 2));
        }

        // --- replace(StringBuffer) ---
        StringBuffer buf = new StringBuffer(replaceTemplate);
        assertEquals(expectedResult, sub.replace(buf));
        if (substring) {
            assertEquals(expectedShortResult, sub.replace(buf, 1, buf.length() - 2));
        }

        // --- replace(StringBuilder) ---
        StringBuilder builder = new StringBuilder(replaceTemplate);
        assertEquals(expectedResult, sub.replace(builder));
        if (substring) {
            assertEquals(expectedShortResult, sub.replace(builder, 1, builder.length() - 2));
        }

        // --- replace(TextStringBuilder) ---
        TextStringBuilder bld = new TextStringBuilder(replaceTemplate);
        assertEquals(expectedResult, sub.replace(bld));
        if (substring) {
            assertEquals(expectedShortResult, sub.replace(bld, 1, bld.length() - 2));
        }

        // --- replace(Object) — toString() is called on the object to obtain the template ---
        final MutableObject<String> obj = new MutableObject<>(replaceTemplate);
        assertEquals(expectedResult, sub.replace(obj));

        // --- replaceIn(StringBuffer) — mutates the buffer in place ---
        buf = new StringBuffer(replaceTemplate);
        assertTrue(sub.replaceIn(buf), replaceTemplate);
        assertEquals(expectedResult, buf.toString());
        if (substring) {
            buf = new StringBuffer(replaceTemplate);
            assertTrue(sub.replaceIn(buf, 1, buf.length() - 2));
            assertEquals(expectedResult, buf.toString()); // characters outside the range are untouched
        }

        // --- replaceIn(StringBuilder) — mutates the builder in place ---
        builder = new StringBuilder(replaceTemplate);
        assertTrue(sub.replaceIn(builder));
        assertEquals(expectedResult, builder.toString());
        if (substring) {
            builder = new StringBuilder(replaceTemplate);
            assertTrue(sub.replaceIn(builder, 1, builder.length() - 2));
            assertEquals(expectedResult, builder.toString()); // characters outside the range are untouched
        }

        // --- replaceIn(TextStringBuilder) — mutates the builder in place ---
        bld = new TextStringBuilder(replaceTemplate);
        assertTrue(sub.replaceIn(bld));
        assertEquals(expectedResult, bld.toString());
        if (substring) {
            bld = new TextStringBuilder(replaceTemplate);
            assertTrue(sub.replaceIn(bld, 1, bld.length() - 2));
            assertEquals(expectedResult, bld.toString()); // characters outside the range are untouched
        }
    }

    /**
     * Performs the actual substitution. Subclasses may override to test a different code path.
     *
     * @throws IOException declared for subclass compatibility
     */
    protected String replace(final StringSubstitutor stringSubstitutor, final String template)
            throws IOException {
        return stringSubstitutor.replace(template);
    }

    @BeforeEach
    public void setUp() throws Exception {
        values = new HashMap<>();
        // Single-character and short keys exercise boundary conditions in the substitutor.
        values.put("a",   "1");
        values.put("aa",  "11");
        values.put("aaa", "111");
        values.put("b",   "2");
        values.put("bb",  "22");
        values.put("bbb", "222");
        values.put("a2b", "b");
        // Longer named variables used by the classic template tests.
        values.put("animal", ACTUAL_ANIMAL);
        values.put("target",  ACTUAL_TARGET);
    }

    @AfterEach
    public void tearDown() {
        values = null;
    }

    /**
     * Verifies that {@code ${:-a}} — an empty variable key followed only by a default value —
     * resolves to {@code "a"}.
     * <p>
     * The ":-" separator marks the default. Because the key is the empty string, no entry in the
     * values map matches, so the substitutor falls back to the literal default {@code "a"}.
     * </p>
     */
    @Test
    void testReplaceEmptyKeyWithDefaultOnlyShortest() throws IOException {
        // "${:-a}" has an empty key; the substitutor must fall back to the default value "a".
        doReplace("a", "${:-a}", false);
    }
}
