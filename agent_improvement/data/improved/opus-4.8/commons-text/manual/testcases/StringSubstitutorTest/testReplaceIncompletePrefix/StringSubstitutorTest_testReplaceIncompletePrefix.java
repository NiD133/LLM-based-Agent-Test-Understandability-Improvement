package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.mutable.MutableObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests {@link StringSubstitutor} behaviour when a variable reference has an
 * incomplete prefix.
 *
 * <p>In {@code "The {animal} jumps over the ${target}."} the {@code {animal}}
 * token is missing the leading {@code $}, so it is NOT a variable reference and
 * is left untouched. Only the well-formed {@code ${target}} reference is
 * replaced. The single test below verifies this across every {@code replace} /
 * {@code replaceIn} overload offered by {@link StringSubstitutor}.</p>
 */
public class StringSubstitutorTest_testReplaceIncompletePrefix {

    /** Replacement values keyed by variable name. */
    private Map<String, String> values;

    @BeforeEach
    public void setUp() {
        values = new HashMap<>();
        values.put("animal", "quick brown fox");
        values.put("target", "lazy dog");
    }

    @Test
    void testReplaceIncompletePrefix() throws IOException {
        final String template = "The {animal} jumps over the ${target}.";
        final String expected = "The {animal} jumps over the lazy dog.";

        assertReplacedEverywhere(new StringSubstitutor(values), template, expected);
    }

    /**
     * Asserts that {@code substitutor} turns {@code template} into {@code expected}
     * through every input type it accepts, and that the offset/length aware
     * overloads behave consistently.
     *
     * <p>When a sub-range {@code [1, length - 2]} is replaced, the framing
     * characters at the edges are preserved, so:</p>
     * <ul>
     *   <li>{@code replace(input, start, length)} returns only the processed
     *       middle slice ({@code expected} without its first and last char);</li>
     *   <li>{@code replaceIn(buffer, start, length)} processes the middle in place
     *       but leaves the untouched edges intact, so the buffer still equals the
     *       full {@code expected}.</li>
     * </ul>
     */
    private void assertReplacedEverywhere(final StringSubstitutor sub, final String template,
            final String expected) {
        final String expectedMiddle = expected.substring(1, expected.length() - 1);

        // replace(String) and the offset/length variant
        assertEquals(expected, sub.replace(template));
        assertEquals(expectedMiddle, sub.replace(template, 1, template.length() - 2));

        // replace(char[]) and the offset/length variant
        final char[] chars = template.toCharArray();
        assertEquals(expected, sub.replace(chars));
        assertEquals(expectedMiddle, sub.replace(chars, 1, chars.length - 2));

        // replace(StringBuffer) and the offset/length variant
        final StringBuffer stringBuffer = new StringBuffer(template);
        assertEquals(expected, sub.replace(stringBuffer));
        assertEquals(expectedMiddle, sub.replace(stringBuffer, 1, stringBuffer.length() - 2));

        // replace(StringBuilder) and the offset/length variant
        final StringBuilder stringBuilder = new StringBuilder(template);
        assertEquals(expected, sub.replace(stringBuilder));
        assertEquals(expectedMiddle, sub.replace(stringBuilder, 1, stringBuilder.length() - 2));

        // replace(TextStringBuilder) and the offset/length variant
        final TextStringBuilder textBuilder = new TextStringBuilder(template);
        assertEquals(expected, sub.replace(textBuilder));
        assertEquals(expectedMiddle, sub.replace(textBuilder, 1, textBuilder.length() - 2));

        // replace(Object) -- the object's toString() supplies the template
        assertEquals(expected, sub.replace(new MutableObject<>(template)));

        // replaceIn(StringBuffer): full buffer, then a middle sub-range
        StringBuffer inPlaceBuffer = new StringBuffer(template);
        assertTrue(sub.replaceIn(inPlaceBuffer));
        assertEquals(expected, inPlaceBuffer.toString());
        inPlaceBuffer = new StringBuffer(template);
        assertTrue(sub.replaceIn(inPlaceBuffer, 1, inPlaceBuffer.length() - 2));
        assertEquals(expected, inPlaceBuffer.toString());

        // replaceIn(StringBuilder): full buffer, then a middle sub-range
        StringBuilder inPlaceStringBuilder = new StringBuilder(template);
        assertTrue(sub.replaceIn(inPlaceStringBuilder));
        assertEquals(expected, inPlaceStringBuilder.toString());
        inPlaceStringBuilder = new StringBuilder(template);
        assertTrue(sub.replaceIn(inPlaceStringBuilder, 1, inPlaceStringBuilder.length() - 2));
        assertEquals(expected, inPlaceStringBuilder.toString());

        // replaceIn(TextStringBuilder): full buffer, then a middle sub-range
        TextStringBuilder inPlaceTextBuilder = new TextStringBuilder(template);
        assertTrue(sub.replaceIn(inPlaceTextBuilder));
        assertEquals(expected, inPlaceTextBuilder.toString());
        inPlaceTextBuilder = new TextStringBuilder(template);
        assertTrue(sub.replaceIn(inPlaceTextBuilder, 1, inPlaceTextBuilder.length() - 2));
        assertEquals(expected, inPlaceTextBuilder.toString());
    }
}
