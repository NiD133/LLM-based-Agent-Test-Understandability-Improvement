package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

/**
 * Tests for {@link StringSubstitutor}'s "preserve escapes" feature.
 *
 * <p>When an escape character (here {@code $}) immediately precedes a variable
 * prefix (here {@code ${}), it prevents substitution of that variable. The
 * {@link StringSubstitutor#setPreserveEscapes(boolean)} flag controls whether
 * the escape character itself is kept in the output or silently consumed.</p>
 *
 * <p>Template used: {@code "${not-escaped} $${escaped}"}
 * <ul>
 *   <li>{@code ${not-escaped}} — normal variable reference, will be replaced.</li>
 *   <li>{@code $${escaped}} — escaped variable reference; the leading {@code $}
 *       is the escape character, so substitution is skipped for {@code ${escaped}}.</li>
 * </ul>
 * </p>
 */
public class StringSubstitutorTest_testSubstitutePreserveEscape {

    /** Template with one resolvable variable and one escaped (non-substituted) variable. */
    private static final String TEMPLATE = "${not-escaped} $${escaped}";

    /**
     * Expected result when preserveEscapes is false (default):
     * the escape character '$' is consumed and does not appear in the output.
     */
    private static final String EXPECTED_ESCAPE_CONSUMED = "value ${escaped}";

    /**
     * Expected result when preserveEscapes is true:
     * the escape character '$' is retained verbatim in the output.
     */
    private static final String EXPECTED_ESCAPE_PRESERVED = "value $${escaped}";

    @Test
    void testSubstitutePreserveEscape() throws IOException {
        final Map<String, String> variables = new HashMap<>();
        variables.put("not-escaped", "value");

        // Configure substitutor: prefix="${", suffix="}", escape character='$'
        final StringSubstitutor sub = new StringSubstitutor(variables, "${", "}", '$');

        // --- Default behaviour: preserveEscapes is false ---
        // The escape character is consumed: "$${escaped}" → "${escaped}"
        assertFalse(sub.isPreserveEscapes());
        assertEquals(EXPECTED_ESCAPE_CONSUMED, sub.replace(TEMPLATE));

        // --- After enabling preserveEscapes ---
        // The escape character is kept in the output: "$${escaped}" → "$${escaped}"
        sub.setPreserveEscapes(true);
        assertTrue(sub.isPreserveEscapes());
        assertEquals(EXPECTED_ESCAPE_PRESERVED, sub.replace(TEMPLATE));
    }
}
