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
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

/**
 * Tests that {@link StringSubstitutor} correctly resolves a variable whose key is exactly two characters long.
 *
 * <p>The map contains several keys of different lengths (1, 2, and 3 characters) to verify that the substitutor
 * matches precisely the right key without spilling into adjacent entries.</p>
 */
@TestMethodOrder(MethodOrderer.MethodName.class)
public class StringSubstitutorTest_testReplaceSimpleKeySize2 {

    /** Animal value used to populate the lookup map for multi-word key tests. */
    private static final String ACTUAL_ANIMAL = "quick brown fox";

    /** Target value used to populate the lookup map for multi-word key tests. */
    private static final String ACTUAL_TARGET = "lazy dog";

    /** The two-character key whose substitution is the focus of this test class. */
    private static final String KEY_SIZE_2 = "aa";

    /** The expected value resolved when {@link #KEY_SIZE_2} is substituted. */
    private static final String VALUE_FOR_KEY_SIZE_2 = "11";

    /** Template that wraps the two-character key in the default variable syntax. */
    private static final String TEMPLATE_KEY_SIZE_2 = "${" + KEY_SIZE_2 + "}";

    protected Map<String, String> values;

    // -------------------------------------------------------------------------
    // Helper: assertion with a length-diagnostic message on mismatch
    // -------------------------------------------------------------------------

    private void assertEqualsCharSeq(final CharSequence expected, final CharSequence actual) {
        assertEquals(expected, actual,
                () -> String.format("expected.length()=%,d, actual.length()=%,d",
                        StringUtils.length(expected), StringUtils.length(actual)));
    }

    // -------------------------------------------------------------------------
    // Convenience wrappers used by concrete test methods
    // -------------------------------------------------------------------------

    /**
     * Asserts that {@code replaceTemplate} is returned unchanged when passed to a default substitutor.
     */
    protected void doNotReplace(final String replaceTemplate) throws IOException {
        doTestNoReplace(new StringSubstitutor(values), replaceTemplate);
    }

    /**
     * Asserts that {@code replaceTemplate} is resolved to {@code expectedResult} by a default substitutor.
     *
     * @param expectedResult  the fully substituted string
     * @param replaceTemplate the template containing variable references
     * @param substring       if {@code true}, also tests sub-range replacement
     */
    protected void doReplace(final String expectedResult, final String replaceTemplate, final boolean substring) throws IOException {
        doTestReplace(new StringSubstitutor(values), expectedResult, replaceTemplate, substring);
    }

    // -------------------------------------------------------------------------
    // Core test helpers that exercise every replace/replaceIn overload
    // -------------------------------------------------------------------------

    /**
     * Verifies that {@code replaceTemplate} is left untouched (no variables resolved).
     * When {@code replaceTemplate} is {@code null}, all overloads that accept {@code null} must return {@code null}
     * or {@code false}.
     */
    protected void doTestNoReplace(final StringSubstitutor substitutor, final String replaceTemplate) throws IOException {
        if (replaceTemplate == null) {
            // Null inputs must produce null/false results — never throw
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
            // Non-null template with no matching variable: must be returned as-is
            assertEquals(replaceTemplate, replace(substitutor, replaceTemplate));
            final TextStringBuilder builder = new TextStringBuilder(replaceTemplate);
            assertFalse(substitutor.replaceIn(builder));
            assertEquals(replaceTemplate, builder.toString());
        }
    }

    /**
     * Exercises every {@code replace} and {@code replaceIn} overload and verifies that each produces
     * {@code expectedResult}.
     *
     * <p>When {@code substring} is {@code true}, the test additionally verifies sub-range replacement: a one-character
     * strip from each end of the template is passed to the offset/length overloads and the inner portion of
     * {@code expectedResult} is expected.</p>
     */
    protected void doTestReplace(final StringSubstitutor sub, final String expectedResult,
            final String replaceTemplate, final boolean substring) throws IOException {

        // Derived expectation for sub-range calls (strip one char from each end)
        final String expectedShortResult = substring
                ? expectedResult.substring(1, expectedResult.length() - 1)
                : expectedResult;

        // -- replace(String) --------------------------------------------------
        final String actual = replace(sub, replaceTemplate);
        assertEquals(expectedResult, actual,
                () -> String.format("Index of difference: %,d",
                        StringUtils.indexOfDifference(expectedResult, actual)));
        if (substring) {
            assertEquals(expectedShortResult, sub.replace(replaceTemplate, 1, replaceTemplate.length() - 2));
        }

        // -- replace(char[]) --------------------------------------------------
        final char[] chars = replaceTemplate.toCharArray();
        assertEquals(expectedResult, sub.replace(chars));
        if (substring) {
            assertEquals(expectedShortResult, sub.replace(chars, 1, chars.length - 2));
        }

        // -- replace(StringBuffer) --------------------------------------------
        StringBuffer buf = new StringBuffer(replaceTemplate);
        assertEquals(expectedResult, sub.replace(buf));
        if (substring) {
            assertEquals(expectedShortResult, sub.replace(buf, 1, buf.length() - 2));
        }

        // -- replace(StringBuilder) -------------------------------------------
        StringBuilder builder = new StringBuilder(replaceTemplate);
        assertEquals(expectedResult, sub.replace(builder));
        if (substring) {
            assertEquals(expectedShortResult, sub.replace(builder, 1, builder.length() - 2));
        }

        // -- replace(TextStringBuilder) ---------------------------------------
        TextStringBuilder bld = new TextStringBuilder(replaceTemplate);
        assertEquals(expectedResult, sub.replace(bld));
        if (substring) {
            assertEquals(expectedShortResult, sub.replace(bld, 1, bld.length() - 2));
        }

        // -- replace(Object) — toString() returns the template ----------------
        final MutableObject<String> obj = new MutableObject<>(replaceTemplate);
        assertEquals(expectedResult, sub.replace(obj));

        // -- replaceIn(StringBuffer) ------------------------------------------
        buf = new StringBuffer(replaceTemplate);
        assertTrue(sub.replaceIn(buf), replaceTemplate);
        assertEquals(expectedResult, buf.toString());
        if (substring) {
            buf = new StringBuffer(replaceTemplate);
            assertTrue(sub.replaceIn(buf, 1, buf.length() - 2));
            // The portion outside the requested range is left untouched, so the full expected result still matches
            assertEquals(expectedResult, buf.toString());
        }

        // -- replaceIn(StringBuilder) -----------------------------------------
        builder = new StringBuilder(replaceTemplate);
        assertTrue(sub.replaceIn(builder));
        assertEquals(expectedResult, builder.toString());
        if (substring) {
            builder = new StringBuilder(replaceTemplate);
            assertTrue(sub.replaceIn(builder, 1, builder.length() - 2));
            assertEquals(expectedResult, builder.toString());
        }

        // -- replaceIn(TextStringBuilder) -------------------------------------
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
     * Delegates to {@link StringSubstitutor#replace(String)}; subclasses may override to test alternative entry points.
     */
    protected String replace(final StringSubstitutor stringSubstitutor, final String template) throws IOException {
        return stringSubstitutor.replace(template);
    }

    // -------------------------------------------------------------------------
    // Lifecycle
    // -------------------------------------------------------------------------

    /**
     * Populates the lookup map with keys of lengths 1, 2, and 3 so that the substitutor must match exactly.
     * Single-character numeric keys map to their digit equivalent; two-character and three-character keys
     * map to repeated-digit strings.  Multi-word keys support broader replace-classic tests elsewhere.
     */
    @BeforeEach
    public void setUp() throws Exception {
        values = new HashMap<>();
        // Single-character keys
        values.put("a", "1");
        values.put("b", "2");
        // Two-character keys (focus of this test class)
        values.put("aa", "11");
        values.put("bb", "22");
        // Three-character keys (present to ensure no over-matching)
        values.put("aaa", "111");
        values.put("bbb", "222");
        // Composite key used to test chained substitution
        values.put("a2b", "b");
        // Multi-word keys used by broader template tests
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
     * Verifies that a variable whose key is exactly two characters long ({@value #KEY_SIZE_2}) is resolved to
     * {@value #VALUE_FOR_KEY_SIZE_2} without accidentally matching the shorter key {@code "a"} or the longer key
     * {@code "aaa"}.
     */
    @Test
    void testReplaceSimpleKeySize2() throws IOException {
        doReplace(VALUE_FOR_KEY_SIZE_2, TEMPLATE_KEY_SIZE_2, false);
    }
}
