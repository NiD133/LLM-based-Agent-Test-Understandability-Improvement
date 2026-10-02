package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang3.mutable.MutableObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class StrSubstitutorTest_testReplaceIncompletePrefix {

    private Map<String, String> values;

    /**
     * Asserts that {@code sub.replace(replaceTemplate)} returns {@code expectedResult},
     * and (when {@code substring} is true) that replacing only the inner substring
     * (offset 1, length - 2) returns {@code expectedResult} with its first and last
     * characters stripped.  The same assertions are repeated for every overload of
     * {@code replace} / {@code replaceIn} (char[], StringBuffer, StringBuilder,
     * StrBuilder, and Object).
     */
    private void doTestReplace(final StrSubstitutor sub, final String expectedResult,
            final String replaceTemplate, final boolean substring) {
        final String expectedShortResult = expectedResult.substring(1, expectedResult.length() - 1);

        // replace using String
        assertEquals(expectedResult, sub.replace(replaceTemplate));
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

        // replace using StrBuilder
        StrBuilder bld = new StrBuilder(replaceTemplate);
        assertEquals(expectedResult, sub.replace(bld));
        if (substring) {
            assertEquals(expectedShortResult, sub.replace(bld, 1, bld.length() - 2));
        }

        // replace using Object (toString returns the template)
        final MutableObject<String> obj = new MutableObject<>(replaceTemplate);
        assertEquals(expectedResult, sub.replace(obj));

        // replaceIn StringBuffer
        buf = new StringBuffer(replaceTemplate);
        assertTrue(sub.replaceIn(buf));
        assertEquals(expectedResult, buf.toString());
        if (substring) {
            buf = new StringBuffer(replaceTemplate);
            assertTrue(sub.replaceIn(buf, 1, buf.length() - 2));
            // remainder outside the range is untouched, so the full result still matches
            assertEquals(expectedResult, buf.toString());
        }

        // replaceIn StringBuilder
        builder = new StringBuilder(replaceTemplate);
        assertTrue(sub.replaceIn(builder));
        assertEquals(expectedResult, builder.toString());
        if (substring) {
            builder = new StringBuilder(replaceTemplate);
            assertTrue(sub.replaceIn(builder, 1, builder.length() - 2));
            assertEquals(expectedResult, builder.toString());
        }

        // replaceIn StrBuilder
        bld = new StrBuilder(replaceTemplate);
        assertTrue(sub.replaceIn(bld));
        assertEquals(expectedResult, bld.toString());
        if (substring) {
            bld = new StrBuilder(replaceTemplate);
            assertTrue(sub.replaceIn(bld, 1, bld.length() - 2));
            assertEquals(expectedResult, bld.toString());
        }
    }

    private void doTestReplace(final String expectedResult, final String replaceTemplate, final boolean substring) {
        final StrSubstitutor sub = new StrSubstitutor(values);
        doTestReplace(sub, expectedResult, replaceTemplate, substring);
    }

    @BeforeEach
    public void setUp() throws Exception {
        values = new HashMap<>();
        values.put("animal", "quick brown fox");
        values.put("target", "lazy dog");
    }

    @AfterEach
    public void tearDown() throws Exception {
        values = null;
    }

    /**
     * Verifies that a token using an incomplete variable prefix (e.g. {@code {animal}}
     * instead of {@code ${animal}}) is treated as plain text and left unchanged, while
     * a correctly prefixed variable ({@code ${target}}) is still substituted normally.
     */
    @Test
    void testReplaceIncompletePrefix() {
        // "{animal}" has no leading "$" so it is NOT a valid variable reference –
        // StrSubstitutor must leave it as-is.  "${target}" IS a valid reference and
        // must be resolved to "lazy dog".
        doTestReplace(
                "The {animal} jumps over the lazy dog.",
                "The {animal} jumps over the ${target}.",
                true);
    }
}
