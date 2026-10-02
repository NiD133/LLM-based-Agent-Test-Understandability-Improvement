package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

/**
 * Tests the preserve-escapes feature of {@link StringSubstitutor}.
 *
 * <p>When preserve-escapes is {@code false} (default), a doubled escape prefix
 * (e.g. {@code $$}) is consumed and the escaped variable marker is stripped,
 * producing just the literal text without the escape character.
 * When preserve-escapes is {@code true}, the doubled escape prefix is kept
 * in the output so the string remains escapable for further substitution passes.</p>
 */
public class StringSubstitutorTest_testSubstitutePreserveEscape {

    /**
     * Delegates to {@link StringSubstitutor#replace(String)} so subclasses can
     * override the replace strategy without touching individual tests.
     */
    protected String replace(final StringSubstitutor sub, final String template) throws IOException {
        return sub.replace(template);
    }

    @Test
    void testSubstitutePreserveEscape() throws IOException {
        // Template contains one normal variable and one escape-prefixed variable.
        // "$${escaped}" uses the '$' escape character to prevent substitution.
        final String template = "${not-escaped} $${escaped}";

        final Map<String, String> map = new HashMap<>();
        map.put("not-escaped", "value");

        // Prefix "${", suffix "}", escape character '$'
        final StringSubstitutor sub = new StringSubstitutor(map, "${", "}", '$');

        // --- Default behaviour: escape character is consumed ---
        // "$${escaped}" becomes "${escaped}" (escape char stripped, variable kept as literal)
        assertFalse(sub.isPreserveEscapes());
        assertEquals("value ${escaped}", replace(sub, template));

        // --- Preserve-escapes enabled: escape character is retained ---
        // "$${escaped}" stays "$${escaped}" so a second pass could still escape it
        sub.setPreserveEscapes(true);
        assertTrue(sub.isPreserveEscapes());
        assertEquals("value $${escaped}", replace(sub, template));
    }

    // -------------------------------------------------------------------------
    // Helpers
    // -------------------------------------------------------------------------

    /**
     * Assertion helper that includes string lengths in the failure message,
     * making length mismatches easier to spot.
     */
    private void assertEqualsCharSeq(final CharSequence expected, final CharSequence actual) {
        assertEquals(expected, actual,
                () -> String.format("expected.length()=%,d, actual.length()=%,d",
                        StringUtils.length(expected), StringUtils.length(actual)));
    }
}
