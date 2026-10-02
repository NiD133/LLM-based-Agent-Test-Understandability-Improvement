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
 * Tests {@link StringSubstitutor} behavior when a variable expression has an empty key (e.g. "${}"}).
 * An empty-key expression cannot be resolved and must be left unchanged, while adjacent valid
 * variable references are still substituted normally.
 */
public class StringSubstitutorTest_testReplaceEmptyKey {

    /** The value mapped to the "target" key, used in the replacement template. */
    private static final String TARGET_VALUE = "lazy dog";

    /** Template containing both an unresolvable empty-key expression and a valid variable. */
    private static final String TEMPLATE_WITH_EMPTY_KEY = "The ${} jumps over the ${target}.";

    /** Expected output: empty-key expression preserved, valid variable substituted. */
    private static final String EXPECTED_RESULT = "The ${} jumps over the lazy dog.";

    protected Map<String, String> values;

    @BeforeEach
    public void setUp() {
        values = new HashMap<>();
        values.put("target", TARGET_VALUE);
    }

    @AfterEach
    public void tearDown() {
        values = null;
    }

    /**
     * Verifies that an empty variable key ("${}") is left unreplaced while a neighbouring valid
     * variable reference ("${target}") is still substituted with its mapped value.
     */
    @Test
    void testReplaceEmptyKey() throws IOException {
        doReplace(EXPECTED_RESULT, TEMPLATE_WITH_EMPTY_KEY, true);
    }

    // -------------------------------------------------------------------------
    // Helper methods
    // -------------------------------------------------------------------------

    protected void doReplace(final String expectedResult, final String replaceTemplate, final boolean substring)
            throws IOException {
        doTestReplace(new StringSubstitutor(values), expectedResult, replaceTemplate, substring);
    }

    protected void doNotReplace(final String replaceTemplate) throws IOException {
        doTestNoReplace(new StringSubstitutor(values), replaceTemplate);
    }

    /**
     * For subclasses to override.
     *
     * @throws IOException Thrown by subclasses.
     */
    protected String replace(final StringSubstitutor stringSubstitutor, final String template) throws IOException {
        return stringSubstitutor.replace(template);
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
        final String expectedShortResult = substring
                ? expectedResult.substring(1, expectedResult.length() - 1)
                : expectedResult;

        // replace using String
        final String actual = replace(sub, replaceTemplate);
        assertEquals(expectedResult, actual,
                () -> String.format("Index of difference: %,d", StringUtils.indexOfDifference(expectedResult, actual)));
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
        // replaceIn StringBuffer
        buf = new StringBuffer(replaceTemplate);
        assertTrue(sub.replaceIn(buf), replaceTemplate);
        assertEquals(expectedResult, buf.toString());
        if (substring) {
            buf = new StringBuffer(replaceTemplate);
            assertTrue(sub.replaceIn(buf, 1, buf.length() - 2));
            // remainder outside the range is untouched, so full result still matches
            assertEquals(expectedResult, buf.toString());
        }
        // replaceIn StringBuilder
        builder = new StringBuilder(replaceTemplate);
        assertTrue(sub.replaceIn(builder));
        assertEquals(expectedResult, builder.toString());
        if (substring) {
            builder = new StringBuilder(replaceTemplate);
            assertTrue(sub.replaceIn(builder, 1, builder.length() - 2));
            // remainder outside the range is untouched, so full result still matches
            assertEquals(expectedResult, builder.toString());
        }
        // replaceIn TextStringBuilder
        bld = new TextStringBuilder(replaceTemplate);
        assertTrue(sub.replaceIn(bld));
        assertEquals(expectedResult, bld.toString());
        if (substring) {
            bld = new TextStringBuilder(replaceTemplate);
            assertTrue(sub.replaceIn(bld, 1, bld.length() - 2));
            // remainder outside the range is untouched, so full result still matches
            assertEquals(expectedResult, bld.toString());
        }
    }
}
