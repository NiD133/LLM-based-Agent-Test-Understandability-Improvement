package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.SystemProperties;
import org.apache.commons.lang3.mutable.MutableObject;
import org.apache.commons.text.lookup.StringLookup;
import org.apache.commons.text.lookup.StringLookupFactory;
import org.apache.commons.text.matcher.StringMatcher;
import org.apache.commons.text.matcher.StringMatcherFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

/**
 * Tests for {@link StringSubstitutor#replace(String)} and related overloads,
 * covering the simple key-substitution scenario using a map-backed substitutor.
 *
 * <p>The "classic" sentence – "The quick brown fox jumps over the lazy dog." –
 * serves as the primary fixture because it exercises a typical two-variable
 * template in a highly readable way.</p>
 */
@TestMethodOrder(MethodOrderer.MethodName.class)
public class StringSubstitutorTest_testReplaceSimple {

    // -----------------------------------------------------------------------
    // Fixture constants – the "quick brown fox" classic sentence
    // -----------------------------------------------------------------------

    private static final String ACTUAL_ANIMAL = "quick brown fox";

    private static final String ACTUAL_TARGET = "lazy dog";

    /** Expected output after both variables are resolved. */
    private static final String CLASSIC_RESULT = "The quick brown fox jumps over the lazy dog.";

    /** Template that contains two variables to be substituted. */
    private static final String CLASSIC_TEMPLATE = "The ${animal} jumps over the ${target}.";

    /** A template containing an empty variable expression (key is blank). */
    private static final String EMPTY_EXPR = "${}";

    // -----------------------------------------------------------------------
    // Shared state
    // -----------------------------------------------------------------------

    /** Variable-to-value lookup map, populated in {@link #setUp()} before each test. */
    protected Map<String, String> values;

    // -----------------------------------------------------------------------
    // Test lifecycle
    // -----------------------------------------------------------------------

    /**
     * Populates {@link #values} with short single/double/triple-letter keys and
     * the two variables used in the classic sentence fixture.
     */
    @BeforeEach
    public void setUp() throws Exception {
        values = new HashMap<>();
        // Minimal-length keys and values – stress-test boundary parsing
        values.put("a", "1");
        values.put("aa", "11");
        values.put("aaa", "111");
        values.put("b", "2");
        values.put("bb", "22");
        values.put("bbb", "222");
        values.put("a2b", "b");
        // Human-readable keys used in the classic sentence template
        values.put("animal", ACTUAL_ANIMAL);
        values.put("target", ACTUAL_TARGET);
    }

    @AfterEach
    public void tearDown() {
        values = null;
    }

    // -----------------------------------------------------------------------
    // Test methods
    // -----------------------------------------------------------------------

    /**
     * Verifies that a template containing two named variables is resolved to
     * the expected sentence when the substitutor is backed by the standard
     * values map.
     */
    @Test
    void testReplaceSimple() throws IOException {
        doReplace(CLASSIC_RESULT, CLASSIC_TEMPLATE, true);
    }

    // -----------------------------------------------------------------------
    // Helper – delegating to a fresh substitutor built from the values map
    // -----------------------------------------------------------------------

    /**
     * Asserts that the given template produces no substitution changes when
     * processed by a substitutor built from {@link #values}.
     */
    protected void doNotReplace(final String replaceTemplate) throws IOException {
        doTestNoReplace(new StringSubstitutor(values), replaceTemplate);
    }

    /**
     * Asserts that the given template is resolved to {@code expectedResult} by a
     * substitutor built from {@link #values}, testing both full-string and
     * (when {@code substring} is {@code true}) sub-range variants.
     */
    protected void doReplace(final String expectedResult, final String replaceTemplate, final boolean substring) throws IOException {
        doTestReplace(new StringSubstitutor(values), expectedResult, replaceTemplate, substring);
    }

    // -----------------------------------------------------------------------
    // Core assertion helpers
    // -----------------------------------------------------------------------

    /**
     * Verifies that the given substitutor leaves {@code replaceTemplate}
     * unchanged (i.e. no variable placeholders are resolved).
     *
     * <p>When {@code replaceTemplate} is {@code null} each {@code replace}
     * overload is expected to return {@code null} and each {@code replaceIn}
     * overload is expected to return {@code false}.</p>
     */
    protected void doTestNoReplace(final StringSubstitutor substitutor, final String replaceTemplate) throws IOException {
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
            final TextStringBuilder textBuilder = new TextStringBuilder(replaceTemplate);
            assertFalse(substitutor.replaceIn(textBuilder));
            assertEquals(replaceTemplate, textBuilder.toString());
        }
    }

    /**
     * Exhaustively verifies that {@code sub} resolves {@code replaceTemplate}
     * to {@code expectedResult} across every supported input type
     * ({@link String}, {@code char[]}, {@link StringBuffer}, {@link StringBuilder},
     * {@link TextStringBuilder}, and {@link Object}).
     *
     * <p>When {@code substring} is {@code true}, the sub-range overloads are
     * also exercised: the first and last characters of the template are excluded,
     * and the expected result for that narrower range is derived by stripping the
     * first and last characters of {@code expectedResult}.</p>
     *
     * @param sub            the substitutor under test
     * @param expectedResult the fully resolved string
     * @param replaceTemplate the template containing variable placeholders
     * @param substring      whether to also test sub-range replacement
     */
    protected void doTestReplace(final StringSubstitutor sub, final String expectedResult, final String replaceTemplate, final boolean substring) throws IOException {
        // When testing a sub-range, strip the outermost characters of the expected result
        final String expectedShortResult = substring ? expectedResult.substring(1, expectedResult.length() - 1) : expectedResult;

        // --- replace(String) ---
        final String actualFromString = replace(sub, replaceTemplate);
        assertEquals(expectedResult, actualFromString, () -> String.format("Index of difference: %,d", StringUtils.indexOfDifference(expectedResult, actualFromString)));
        if (substring) {
            assertEquals(expectedShortResult, sub.replace(replaceTemplate, 1, replaceTemplate.length() - 2));
        }

        // --- replace(char[]) ---
        final char[] templateChars = replaceTemplate.toCharArray();
        assertEquals(expectedResult, sub.replace(templateChars));
        if (substring) {
            assertEquals(expectedShortResult, sub.replace(templateChars, 1, templateChars.length - 2));
        }

        // --- replace(StringBuffer) ---
        StringBuffer stringBuffer = new StringBuffer(replaceTemplate);
        assertEquals(expectedResult, sub.replace(stringBuffer));
        if (substring) {
            assertEquals(expectedShortResult, sub.replace(stringBuffer, 1, stringBuffer.length() - 2));
        }

        // --- replace(StringBuilder) ---
        StringBuilder stringBuilder = new StringBuilder(replaceTemplate);
        assertEquals(expectedResult, sub.replace(stringBuilder));
        if (substring) {
            assertEquals(expectedShortResult, sub.replace(stringBuilder, 1, stringBuilder.length() - 2));
        }

        // --- replace(TextStringBuilder) ---
        TextStringBuilder textStringBuilder = new TextStringBuilder(replaceTemplate);
        assertEquals(expectedResult, sub.replace(textStringBuilder));
        if (substring) {
            assertEquals(expectedShortResult, sub.replace(textStringBuilder, 1, textStringBuilder.length() - 2));
        }

        // --- replace(Object) – toString() returns the template ---
        final MutableObject<String> templateObject = new MutableObject<>(replaceTemplate);
        assertEquals(expectedResult, sub.replace(templateObject));

        // --- replaceIn(StringBuffer) – mutates the buffer in place ---
        stringBuffer = new StringBuffer(replaceTemplate);
        assertTrue(sub.replaceIn(stringBuffer), replaceTemplate);
        assertEquals(expectedResult, stringBuffer.toString());
        if (substring) {
            stringBuffer = new StringBuffer(replaceTemplate);
            assertTrue(sub.replaceIn(stringBuffer, 1, stringBuffer.length() - 2));
            // Characters outside the sub-range are untouched, so the full result still matches
            assertEquals(expectedResult, stringBuffer.toString());
        }

        // --- replaceIn(StringBuilder) – mutates the builder in place ---
        stringBuilder = new StringBuilder(replaceTemplate);
        assertTrue(sub.replaceIn(stringBuilder));
        assertEquals(expectedResult, stringBuilder.toString());
        if (substring) {
            stringBuilder = new StringBuilder(replaceTemplate);
            assertTrue(sub.replaceIn(stringBuilder, 1, stringBuilder.length() - 2));
            // Characters outside the sub-range are untouched, so the full result still matches
            assertEquals(expectedResult, stringBuilder.toString());
        }

        // --- replaceIn(TextStringBuilder) – mutates the builder in place ---
        textStringBuilder = new TextStringBuilder(replaceTemplate);
        assertTrue(sub.replaceIn(textStringBuilder));
        assertEquals(expectedResult, textStringBuilder.toString());
        if (substring) {
            textStringBuilder = new TextStringBuilder(replaceTemplate);
            assertTrue(sub.replaceIn(textStringBuilder, 1, textStringBuilder.length() - 2));
            // Characters outside the sub-range are untouched, so the full result still matches
            assertEquals(expectedResult, textStringBuilder.toString());
        }
    }

    /**
     * Delegates to {@link StringSubstitutor#replace(String)}.
     * Subclasses may override to exercise a different entry point.
     *
     * @throws IOException subclasses may throw
     */
    protected String replace(final StringSubstitutor stringSubstitutor, final String template) throws IOException {
        return stringSubstitutor.replace(template);
    }

    // -----------------------------------------------------------------------
    // Private utilities
    // -----------------------------------------------------------------------

    /**
     * Asserts character-sequence equality and includes both lengths in the
     * failure message to aid diagnosis of large-string mismatches.
     */
    private void assertEqualsCharSeq(final CharSequence expected, final CharSequence actual) {
        assertEquals(expected, actual, () -> String.format("expected.length()=%,d, actual.length()=%,d", StringUtils.length(expected), StringUtils.length(actual)));
    }
}
