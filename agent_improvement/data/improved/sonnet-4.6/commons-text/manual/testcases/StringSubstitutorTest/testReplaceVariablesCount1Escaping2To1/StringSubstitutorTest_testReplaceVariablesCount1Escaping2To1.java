package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
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
 * Tests that the escape prefix ("$$") prevents variable substitution,
 * leaving the literal "${varName}" in the output instead of resolving it.
 *
 * <p>StringSubstitutor uses "$${...}" to produce a literal "${...}" in output.
 * The first "$" is the escape character; the second begins what looks like a
 * variable reference, but the whole expression is output verbatim without
 * looking up the variable's value.</p>
 */
public class StringSubstitutorTest_testReplaceVariablesCount1Escaping2To1 {

    private static final String ACTUAL_ANIMAL = "quick brown fox";
    private static final String ACTUAL_TARGET = "lazy dog";

    protected Map<String, String> values;

    // -------------------------------------------------------------------------
    // Infrastructure helpers shared with other test scenarios
    // -------------------------------------------------------------------------

    private void assertEqualsCharSeq(final CharSequence expected, final CharSequence actual) {
        assertEquals(expected, actual,
                () -> String.format("expected.length()=%,d, actual.length()=%,d",
                        StringUtils.length(expected), StringUtils.length(actual)));
    }

    protected void doNotReplace(final String replaceTemplate) throws IOException {
        doTestNoReplace(new StringSubstitutor(values), replaceTemplate);
    }

    protected void doReplace(final String expectedResult, final String replaceTemplate, final boolean substring)
            throws IOException {
        doTestReplace(new StringSubstitutor(values), expectedResult, replaceTemplate, substring);
    }

    protected void doTestNoReplace(final StringSubstitutor substitutor, final String replaceTemplate)
            throws IOException {
        if (replaceTemplate == null) {
            assertNull(replace(substitutor, (String) null));
            assertNull(substitutor.replace((String) null, 0, 100));
            assertNull(substitutor.replace((char[]) null));
            assertNull(substitutor.replace((char[]) null, 0, 100));
            assertNull(substitutor.replace((StringBuffer) null));
            assertNull(substitutor.replace((StringBuffer) null, 0, 100));
            assertNull(substitutor.replace((TextStringBuilder) null));
            assertNull(substitutor.replace((TextStringBuilder) null, 0, 100));
            assertNull(substitutor.replace((Object) null));
            assertFalse(substitutor.replaceIn((StringBuffer) null));
            assertFalse(substitutor.replaceIn((StringBuffer) null, 0, 100));
            assertFalse(substitutor.replaceIn((TextStringBuilder) null));
            assertFalse(substitutor.replaceIn((TextStringBuilder) null, 0, 100));
        } else {
            assertEquals(replaceTemplate, replace(substitutor, replaceTemplate));
            final TextStringBuilder builder = new TextStringBuilder(replaceTemplate);
            assertFalse(substitutor.replaceIn(builder));
            assertEquals(replaceTemplate, builder.toString());
        }
    }

    protected void doTestReplace(final StringSubstitutor sub, final String expectedResult,
            final String replaceTemplate, final boolean substring) throws IOException {
        final String expectedShortResult =
                substring ? expectedResult.substring(1, expectedResult.length() - 1) : expectedResult;

        // replace using String
        final String actual = replace(sub, replaceTemplate);
        assertEquals(expectedResult, actual,
                () -> String.format("Index of difference: %,d",
                        StringUtils.indexOfDifference(expectedResult, actual)));
        if (substring) {
            assertEquals(expectedShortResult, sub.replace(replaceTemplate, 1, replaceTemplate.length() - 2));
        }
        // replace using char[]
        final char[] chars = replaceTemplate.toCharArray();
        assertEquals(expectedResult, sub.replace(chars));
        if (substring) {
            assertEquals(expectedShortResult, sub.replace(chars, 1, chars.length - 2));
        }
        // replace using StringBuffer
        StringBuffer buf = new StringBuffer(replaceTemplate);
        assertEquals(expectedResult, sub.replace(buf));
        if (substring) {
            assertEquals(expectedShortResult, sub.replace(buf, 1, buf.length() - 2));
        }
        // replace using StringBuilder
        StringBuilder builder = new StringBuilder(replaceTemplate);
        assertEquals(expectedResult, sub.replace(builder));
        if (substring) {
            assertEquals(expectedShortResult, sub.replace(builder, 1, builder.length() - 2));
        }
        // replace using TextStringBuilder
        TextStringBuilder bld = new TextStringBuilder(replaceTemplate);
        assertEquals(expectedResult, sub.replace(bld));
        if (substring) {
            assertEquals(expectedShortResult, sub.replace(bld, 1, bld.length() - 2));
        }
        // replace using Object (toString returns the template)
        final MutableObject<String> obj = new MutableObject<>(replaceTemplate);
        assertEquals(expectedResult, sub.replace(obj));

        // replace in StringBuffer (in-place mutation)
        buf = new StringBuffer(replaceTemplate);
        assertTrue(sub.replaceIn(buf), replaceTemplate);
        assertEquals(expectedResult, buf.toString());
        if (substring) {
            buf = new StringBuffer(replaceTemplate);
            assertTrue(sub.replaceIn(buf, 1, buf.length() - 2));
            assertEquals(expectedResult, buf.toString());
        }
        // replace in StringBuilder (in-place mutation)
        builder = new StringBuilder(replaceTemplate);
        assertTrue(sub.replaceIn(builder));
        assertEquals(expectedResult, builder.toString());
        if (substring) {
            builder = new StringBuilder(replaceTemplate);
            assertTrue(sub.replaceIn(builder, 1, builder.length() - 2));
            assertEquals(expectedResult, builder.toString());
        }
        // replace in TextStringBuilder (in-place mutation)
        bld = new TextStringBuilder(replaceTemplate);
        assertTrue(sub.replaceIn(bld));
        assertEquals(expectedResult, bld.toString());
        if (substring) {
            bld = new TextStringBuilder(replaceTemplate);
            assertTrue(sub.replaceIn(bld, 1, bld.length() - 2));
            assertEquals(expectedResult, bld.toString());
        }
    }

    /**
     * Delegates to {@link StringSubstitutor#replace(String)}. Subclasses may
     * override to exercise a different entry point.
     */
    protected String replace(final StringSubstitutor stringSubstitutor, final String template) throws IOException {
        return stringSubstitutor.replace(template);
    }

    // -------------------------------------------------------------------------
    // Fixture
    // -------------------------------------------------------------------------

    @BeforeEach
    public void setUp() {
        values = new HashMap<>();
        // single-character keys
        values.put("a", "1");
        values.put("b", "2");
        // two-character keys
        values.put("aa", "11");
        values.put("bb", "22");
        // three-character keys
        values.put("aaa", "111");
        values.put("bbb", "222");
        // composite key
        values.put("a2b", "b");
        // descriptive keys used in "classic" templates
        values.put("animal", ACTUAL_ANIMAL);
        values.put("target", ACTUAL_TARGET);
    }

    @AfterEach
    public void tearDown() {
        values = null;
    }

    // -------------------------------------------------------------------------
    // Test
    // -------------------------------------------------------------------------

    /**
     * Verifies that a double-dollar escape prefix ("$$") causes the following
     * variable expression to be emitted literally instead of being resolved.
     *
     * <ul>
     *   <li>{@code "$${a}"}      &rarr; {@code "${a}"}      (short key, value "1" is NOT substituted)</li>
     *   <li>{@code "$${animal}"} &rarr; {@code "${animal}"} (long key, value "quick brown fox" is NOT substituted)</li>
     * </ul>
     */
    @Test
    void testReplaceVariablesCount1Escaping2To1() throws IOException {
        // "$${a}" should produce the literal "${a}", not the resolved value "1"
        doReplace("${a}", "$${a}", false);

        // "$${animal}" should produce the literal "${animal}", not "quick brown fox"
        doReplace("${animal}", "$${animal}", false);
    }
}
