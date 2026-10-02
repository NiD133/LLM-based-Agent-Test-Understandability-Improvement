package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

/**
 * Tests how {@link StringSubstitutor} handles the escape character when the
 * "preserve escapes" option is toggled.
 *
 * <p>The template mixes two kinds of expressions:</p>
 * <ul>
 *   <li>{@code ${not-escaped}} &mdash; a normal variable that is always resolved.</li>
 *   <li>{@code $${escaped}} &mdash; an escaped expression: the leading {@code $}
 *       escapes the following {@code $}.</li>
 * </ul>
 */
public class StringSubstitutorTest_testSubstitutePreserveEscape {

    /** Template containing one normal variable and one escaped expression. */
    private static final String TEMPLATE = "${not-escaped} $${escaped}";

    @Test
    void testSubstitutePreserveEscape() throws IOException {
        final Map<String, String> values = new HashMap<>();
        values.put("not-escaped", "value");

        // Prefix "${", suffix "}", escape character '$'.
        final StringSubstitutor substitutor = new StringSubstitutor(values, "${", "}", '$');

        // By default escapes are NOT preserved: the escaped "$${escaped}" collapses
        // to the literal "${escaped}".
        assertFalse(substitutor.isPreserveEscapes());
        assertEquals("value ${escaped}", substitutor.replace(TEMPLATE));

        // With escapes preserved the original "$${escaped}" is left untouched.
        substitutor.setPreserveEscapes(true);
        assertTrue(substitutor.isPreserveEscapes());
        assertEquals("value $${escaped}", substitutor.replace(TEMPLATE));
    }
}
