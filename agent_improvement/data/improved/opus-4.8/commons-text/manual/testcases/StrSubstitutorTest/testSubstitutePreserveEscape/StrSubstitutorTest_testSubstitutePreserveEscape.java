package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

/**
 * Verifies how {@link StrSubstitutor} treats escaped variable references
 * (a variable prefix doubled by the escape character, e.g. {@code $${escaped}})
 * depending on the {@code preserveEscapes} flag.
 */
public class StrSubstitutorTest_testSubstitutePreserveEscape {

    @Test
    void testSubstitutePreserveEscape() {
        // Template mixes a normal reference "${not-escaped}" with an escaped one "$${escaped}".
        // The escape character is '$', so the leading "$$" escapes the following "${escaped}".
        final String template = "${not-escaped} $${escaped}";

        final Map<String, String> variables = new HashMap<>();
        variables.put("not-escaped", "value");

        // Substitutor using "${" / "}" delimiters and '$' as the escape character.
        final StrSubstitutor sub = new StrSubstitutor(variables, "${", "}", '$');

        // By default escapes are NOT preserved: the escape character is consumed,
        // leaving the literal "${escaped}" in the output.
        assertFalse(sub.isPreserveEscapes());
        assertEquals("value ${escaped}", sub.replace(template));

        // With escape preservation enabled the escape character is kept,
        // so the output still contains the original "$${escaped}".
        sub.setPreserveEscapes(true);
        assertTrue(sub.isPreserveEscapes());
        assertEquals("value $${escaped}", sub.replace(template));
    }
}
