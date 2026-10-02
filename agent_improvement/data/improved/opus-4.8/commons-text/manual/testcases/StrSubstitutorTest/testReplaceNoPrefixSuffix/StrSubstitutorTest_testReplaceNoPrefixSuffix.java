package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.mutable.MutableObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link StrSubstitutor} leaves a dangling suffix ("}") untouched
 * when the matching prefix ("${") is missing, while still resolving a complete,
 * well-formed variable reference elsewhere in the same template.
 */
public class StrSubstitutorTest_testReplaceNoPrefixSuffix {

    /** Variable name -> value pairs made available to the substitutor. */
    private Map<String, String> values;

    @BeforeEach
    public void setUp() {
        values = new HashMap<>();
        values.put("animal", "quick brown fox");
        values.put("target", "lazy dog");
    }

    /**
     * The template contains a stray "}" (no preceding "${") that must survive
     * verbatim, plus a proper "${target}" reference that must be resolved.
     */
    @Test
    void testReplaceNoPrefixSuffix() {
        final String template = "The animal} jumps over the ${target}.";
        final String expected = "The animal} jumps over the lazy dog.";

        assertReplacedAcrossAllInputTypes(expected, template);
    }

    /**
     * Runs the same substitution through every {@code replace} / {@code replaceIn}
     * overload of {@link StrSubstitutor} so that all input representations are
     * exercised identically.
     *
     * <p>For each overload the whole template is replaced, and additionally a
     * sub-range (the template minus its first and last characters) is replaced to
     * confirm that offset/length variants behave consistently. The expected
     * sub-range result is simply {@code expected} without its outer characters.</p>
     */
    private void assertReplacedAcrossAllInputTypes(final String expected, final String template) {
        final StrSubstitutor sub = new StrSubstitutor(values);
        final String expectedSubRange = expected.substring(1, expected.length() - 1);

        // --- replace(...) returning a new String, source left unchanged ---

        // String source
        assertEquals(expected, sub.replace(template));
        assertEquals(expectedSubRange, sub.replace(template, 1, template.length() - 2));

        // char[] source
        final char[] chars = template.toCharArray();
        assertEquals(expected, sub.replace(chars));
        assertEquals(expectedSubRange, sub.replace(chars, 1, chars.length - 2));

        // StringBuffer source
        final StringBuffer readOnlyBuffer = new StringBuffer(template);
        assertEquals(expected, sub.replace(readOnlyBuffer));
        assertEquals(expectedSubRange, sub.replace(readOnlyBuffer, 1, readOnlyBuffer.length() - 2));

        // StringBuilder source
        final StringBuilder readOnlyBuilder = new StringBuilder(template);
        assertEquals(expected, sub.replace(readOnlyBuilder));
        assertEquals(expectedSubRange, sub.replace(readOnlyBuilder, 1, readOnlyBuilder.length() - 2));

        // StrBuilder source
        final StrBuilder readOnlyStrBuilder = new StrBuilder(template);
        assertEquals(expected, sub.replace(readOnlyStrBuilder));
        assertEquals(expectedSubRange, sub.replace(readOnlyStrBuilder, 1, readOnlyStrBuilder.length() - 2));

        // Object source (its toString() yields the template)
        final MutableObject<String> objectSource = new MutableObject<>(template);
        assertEquals(expected, sub.replace(objectSource));

        // --- replaceIn(...) mutating the source in place, returning true on change ---

        // StringBuffer in place: full range, then sub-range
        StringBuffer mutableBuffer = new StringBuffer(template);
        assertTrue(sub.replaceIn(mutableBuffer));
        assertEquals(expected, mutableBuffer.toString());
        mutableBuffer = new StringBuffer(template);
        assertTrue(sub.replaceIn(mutableBuffer, 1, mutableBuffer.length() - 2));
        // The untouched remainder keeps the result equal to the full expected output.
        assertEquals(expected, mutableBuffer.toString());

        // StringBuilder in place: full range, then sub-range
        StringBuilder mutableBuilder = new StringBuilder(template);
        assertTrue(sub.replaceIn(mutableBuilder));
        assertEquals(expected, mutableBuilder.toString());
        mutableBuilder = new StringBuilder(template);
        assertTrue(sub.replaceIn(mutableBuilder, 1, mutableBuilder.length() - 2));
        assertEquals(expected, mutableBuilder.toString());

        // StrBuilder in place: full range, then sub-range
        StrBuilder mutableStrBuilder = new StrBuilder(template);
        assertTrue(sub.replaceIn(mutableStrBuilder));
        assertEquals(expected, mutableStrBuilder.toString());
        mutableStrBuilder = new StrBuilder(template);
        assertTrue(sub.replaceIn(mutableStrBuilder, 1, mutableStrBuilder.length() - 2));
        assertEquals(expected, mutableStrBuilder.toString());
    }
}
