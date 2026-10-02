package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.mutable.MutableObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Verifies how {@link StrSubstitutor} handles templates that contain an
 * <em>empty</em> variable key (i.e. {@code ${}}).
 */
public class StrSubstitutorTest_testReplaceEmptyKeys {

    /** Variable definitions shared by every substitution in this test. */
    private Map<String, String> values;

    @BeforeEach
    public void setUp() {
        values = new HashMap<>();
        values.put("animal", "quick brown fox");
        values.put("target", "lazy dog");
    }

    /**
     * An empty key with no default (<code>${}</code>) is left untouched, while a
     * real variable in the same template still gets replaced; an empty key that
     * supplies a default (<code>${:-animal}</code>) resolves to that default.
     */
    @Test
    void testReplaceEmptyKeys() {
        assertSubstitution(
                "The ${} jumps over the ${target}.",
                "The ${} jumps over the lazy dog.");

        assertSubstitution(
                "The ${:-animal} jumps over the ${target}.",
                "The animal jumps over the lazy dog.");
    }

    /**
     * Asserts that substituting {@code template} yields {@code expected}, checking
     * every {@code StrSubstitutor} input flavour (String, char[], StringBuffer,
     * StringBuilder, StrBuilder and arbitrary Object) plus the in-place
     * {@code replaceIn} variants. For each flavour it also exercises the
     * offset/length overload, which substitutes only the inner portion of the
     * template (dropping the first and last characters).
     */
    private void assertSubstitution(final String template, final String expected) {
        final StrSubstitutor sub = new StrSubstitutor(values);
        // Result of substituting only template[1 .. length-2].
        final String expectedInner = expected.substring(1, expected.length() - 1);

        // --- replace(...) returning a new String ---
        assertEquals(expected, sub.replace(template));
        assertEquals(expectedInner, sub.replace(template, 1, template.length() - 2));

        final char[] chars = template.toCharArray();
        assertEquals(expected, sub.replace(chars));
        assertEquals(expectedInner, sub.replace(chars, 1, chars.length - 2));

        final StringBuffer buffer = new StringBuffer(template);
        assertEquals(expected, sub.replace(buffer));
        assertEquals(expectedInner, sub.replace(buffer, 1, buffer.length() - 2));

        final StringBuilder builder = new StringBuilder(template);
        assertEquals(expected, sub.replace(builder));
        assertEquals(expectedInner, sub.replace(builder, 1, builder.length() - 2));

        final StrBuilder strBuilder = new StrBuilder(template);
        assertEquals(expected, sub.replace(strBuilder));
        assertEquals(expectedInner, sub.replace(strBuilder, 1, strBuilder.length() - 2));

        // Arbitrary object: its toString() supplies the template.
        assertEquals(expected, sub.replace(new MutableObject<>(template)));

        // --- replaceIn(...) mutating the argument in place ---
        // The whole-buffer overload rewrites the buffer to the full expected result.
        assertReplaceInWholeBuffer(sub, template, expected);
        // The offset/length overload rewrites only the inner portion, but because the
        // surrounding characters are untouched the buffer still equals the full result.
        assertReplaceInInnerPortion(sub, template, expected);
    }

    /** Runs the whole-buffer {@code replaceIn} overload for every mutable buffer type. */
    private void assertReplaceInWholeBuffer(final StrSubstitutor sub, final String template,
            final String expected) {
        final StringBuffer buffer = new StringBuffer(template);
        assertTrue(sub.replaceIn(buffer));
        assertEquals(expected, buffer.toString());

        final StringBuilder builder = new StringBuilder(template);
        assertTrue(sub.replaceIn(builder));
        assertEquals(expected, builder.toString());

        final StrBuilder strBuilder = new StrBuilder(template);
        assertTrue(sub.replaceIn(strBuilder));
        assertEquals(expected, strBuilder.toString());
    }

    /** Runs the offset/length {@code replaceIn} overload for every mutable buffer type. */
    private void assertReplaceInInnerPortion(final StrSubstitutor sub, final String template,
            final String expected) {
        final StringBuffer buffer = new StringBuffer(template);
        assertTrue(sub.replaceIn(buffer, 1, buffer.length() - 2));
        assertEquals(expected, buffer.toString());

        final StringBuilder builder = new StringBuilder(template);
        assertTrue(sub.replaceIn(builder, 1, builder.length() - 2));
        assertEquals(expected, builder.toString());

        final StrBuilder strBuilder = new StrBuilder(template);
        assertTrue(sub.replaceIn(strBuilder, 1, strBuilder.length() - 2));
        assertEquals(expected, strBuilder.toString());
    }
}
