package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.mutable.MutableObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link StringSubstitutor} handles a variable whose key is unknown but which supplies
 * an inline default value (the {@code ${key:-default}} syntax).
 *
 * <p>The template mixes three cases:</p>
 * <ul>
 *   <li>{@code ${person}} &mdash; an unknown key with no default, so it is left untouched.</li>
 *   <li>{@code ${target}} &mdash; a known key, so it is replaced with its mapped value.</li>
 *   <li>{@code ${undefined.number:-1234567890}} &mdash; an unknown key with a default, so the
 *       default value is substituted.</li>
 * </ul>
 */
public class StringSubstitutorTest_testReplaceUnknownKeyDefaultValue {

    private static final String ACTUAL_TARGET = "lazy dog";

    /** Variable values shared by every overload exercised in the test. */
    private Map<String, String> values;

    @BeforeEach
    public void setUp() {
        values = new HashMap<>();
        values.put("target", ACTUAL_TARGET);
    }

    /**
     * Tests that an unknown variable key carrying an inline default value is replaced by that default,
     * consistently across every {@code replace}/{@code replaceIn} overload of {@link StringSubstitutor}.
     */
    @Test
    void testReplaceUnknownKeyDefaultValue() throws IOException {
        final String template = "The ${person} jumps over the ${target}. ${undefined.number:-1234567890}.";
        final String expected = "The ${person} jumps over the lazy dog. 1234567890.";

        assertReplacedEverywhere(new StringSubstitutor(values), template, expected);
    }

    /**
     * Asserts that substituting {@code template} yields {@code expected} for every read-only
     * {@code replace} overload and every in-place {@code replaceIn} overload, including the
     * offset/length ("substring") variants.
     *
     * <p>For a substring call we strip the leading "T" and trailing "." from the template/result so
     * the substituted region is offset by one on each side; the surrounding characters are expected
     * to be preserved.</p>
     */
    private void assertReplacedEverywhere(final StringSubstitutor sub, final String template,
            final String expected) {
        final String expectedSubstring = expected.substring(1, expected.length() - 1);

        // replace using String
        final String actual = sub.replace(template);
        assertEquals(expected, actual,
                () -> String.format("Index of difference: %,d", StringUtils.indexOfDifference(expected, actual)));
        assertEquals(expectedSubstring, sub.replace(template, 1, template.length() - 2));

        // replace using char[]
        final char[] chars = template.toCharArray();
        assertEquals(expected, sub.replace(chars));
        assertEquals(expectedSubstring, sub.replace(chars, 1, chars.length - 2));

        // replace using StringBuffer
        StringBuffer buf = new StringBuffer(template);
        assertEquals(expected, sub.replace(buf));
        assertEquals(expectedSubstring, sub.replace(buf, 1, buf.length() - 2));

        // replace using StringBuilder
        StringBuilder builder = new StringBuilder(template);
        assertEquals(expected, sub.replace(builder));
        assertEquals(expectedSubstring, sub.replace(builder, 1, builder.length() - 2));

        // replace using TextStringBuilder
        TextStringBuilder bld = new TextStringBuilder(template);
        assertEquals(expected, sub.replace(bld));
        assertEquals(expectedSubstring, sub.replace(bld, 1, bld.length() - 2));

        // replace using Object (whose toString returns the template)
        final MutableObject<String> obj = new MutableObject<>(template);
        assertEquals(expected, sub.replace(obj));

        // replace in StringBuffer
        buf = new StringBuffer(template);
        assertTrue(sub.replaceIn(buf), template);
        assertEquals(expected, buf.toString());
        buf = new StringBuffer(template);
        assertTrue(sub.replaceIn(buf, 1, buf.length() - 2));
        // expect the full result, as the remainder outside the region is untouched
        assertEquals(expected, buf.toString());

        // replace in StringBuilder
        builder = new StringBuilder(template);
        assertTrue(sub.replaceIn(builder));
        assertEquals(expected, builder.toString());
        builder = new StringBuilder(template);
        assertTrue(sub.replaceIn(builder, 1, builder.length() - 2));
        // expect the full result, as the remainder outside the region is untouched
        assertEquals(expected, builder.toString());

        // replace in TextStringBuilder
        bld = new TextStringBuilder(template);
        assertTrue(sub.replaceIn(bld));
        assertEquals(expected, bld.toString());
        bld = new TextStringBuilder(template);
        assertTrue(sub.replaceIn(bld, 1, bld.length() - 2));
        // expect the full result, as the remainder outside the region is untouched
        assertEquals(expected, bld.toString());
    }
}
