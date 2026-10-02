package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.mutable.MutableObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link StrSubstitutor} resolves a {@code ${variable}} reference embedded
 * in a template that has surrounding literal text (i.e. the variable is neither the very
 * first nor the very last token, so it has no prefix-only or suffix-only edge case).
 */
public class StrSubstitutorTest_testReplaceNoPrefixNoSuffix {

    /** Variable values made available to the substitutor under test. */
    private Map<String, String> values;

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
     * Tests substitution of a single {@code ${target}} variable surrounded by literal text.
     */
    @Test
    void testReplaceNoPrefixNoSuffix() {
        final String template = "The animal jumps over the ${target}.";
        final String expected = "The animal jumps over the lazy dog.";

        assertReplacesAcrossAllInputTypes(expected, template);
    }

    /**
     * Asserts that every {@code replace}/{@code replaceIn} overload of {@link StrSubstitutor}
     * turns {@code template} into {@code expected}.
     *
     * <p>The substitutor exposes the same logical operation through many input types
     * (String, char[], StringBuffer, StringBuilder, StrBuilder and arbitrary Object), plus
     * range-limited variants and in-place {@code replaceIn} variants. This helper exercises
     * all of them so the test covers the full API surface for one template.</p>
     *
     * <p>The range-limited overloads operate on the substring that strips the first and last
     * character of the template; the matching expected value is {@code expected} with its own
     * first and last character removed.</p>
     */
    private void assertReplacesAcrossAllInputTypes(final String expected, final String template) {
        final StrSubstitutor sub = new StrSubstitutor(values);
        final String expectedInnerRange = expected.substring(1, expected.length() - 1);

        // --- replace(...) overloads: return a new resolved value, leave the input untouched ---

        // String input
        assertEquals(expected, sub.replace(template));
        assertEquals(expectedInnerRange, sub.replace(template, 1, template.length() - 2));

        // char[] input
        final char[] chars = template.toCharArray();
        assertEquals(expected, sub.replace(chars));
        assertEquals(expectedInnerRange, sub.replace(chars, 1, chars.length - 2));

        // StringBuffer input
        final StringBuffer bufferInput = new StringBuffer(template);
        assertEquals(expected, sub.replace(bufferInput));
        assertEquals(expectedInnerRange, sub.replace(bufferInput, 1, bufferInput.length() - 2));

        // StringBuilder input
        final StringBuilder builderInput = new StringBuilder(template);
        assertEquals(expected, sub.replace(builderInput));
        assertEquals(expectedInnerRange, sub.replace(builderInput, 1, builderInput.length() - 2));

        // StrBuilder input
        final StrBuilder strBuilderInput = new StrBuilder(template);
        assertEquals(expected, sub.replace(strBuilderInput));
        assertEquals(expectedInnerRange, sub.replace(strBuilderInput, 1, strBuilderInput.length() - 2));

        // Object input (its toString() supplies the template)
        final MutableObject<String> objectInput = new MutableObject<>(template);
        assertEquals(expected, sub.replace(objectInput));

        // --- replaceIn(...) overloads: mutate the buffer in place, return true if changed ---

        // replaceIn over a whole StringBuffer
        StringBuffer bufferTarget = new StringBuffer(template);
        assertTrue(sub.replaceIn(bufferTarget));
        assertEquals(expected, bufferTarget.toString());
        // replaceIn over a StringBuffer range: the untouched remainder still yields the full result
        bufferTarget = new StringBuffer(template);
        assertTrue(sub.replaceIn(bufferTarget, 1, bufferTarget.length() - 2));
        assertEquals(expected, bufferTarget.toString());

        // replaceIn over a whole StringBuilder
        StringBuilder builderTarget = new StringBuilder(template);
        assertTrue(sub.replaceIn(builderTarget));
        assertEquals(expected, builderTarget.toString());
        // replaceIn over a StringBuilder range
        builderTarget = new StringBuilder(template);
        assertTrue(sub.replaceIn(builderTarget, 1, builderTarget.length() - 2));
        assertEquals(expected, builderTarget.toString());

        // replaceIn over a whole StrBuilder
        StrBuilder strBuilderTarget = new StrBuilder(template);
        assertTrue(sub.replaceIn(strBuilderTarget));
        assertEquals(expected, strBuilderTarget.toString());
        // replaceIn over a StrBuilder range
        strBuilderTarget = new StrBuilder(template);
        assertTrue(sub.replaceIn(strBuilderTarget, 1, strBuilderTarget.length() - 2));
        assertEquals(expected, strBuilderTarget.toString());
    }
}
