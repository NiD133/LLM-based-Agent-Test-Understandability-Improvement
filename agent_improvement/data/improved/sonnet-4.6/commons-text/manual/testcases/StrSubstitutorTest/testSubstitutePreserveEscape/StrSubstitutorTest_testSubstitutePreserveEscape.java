package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

public class StrSubstitutorTest_testSubstitutePreserveEscape {

    /**
     * Verifies the preserveEscapes toggle on StrSubstitutor.
     *
     * The template "${not-escaped} $${escaped}" contains two variable-like tokens:
     *   - "${not-escaped}" — a normal variable reference that should be replaced.
     *   - "$${escaped}"    — an escaped sequence (escape char '$' followed by "${escaped}").
     *
     * When preserveEscapes is false (the default), the escape character is consumed and
     * the literal text "${escaped}" is emitted, giving: "value ${escaped}".
     *
     * When preserveEscapes is true, the escape character is kept in the output so the
     * result retains the original "$${escaped}", giving: "value $${escaped}".
     */
    @Test
    void testSubstitutePreserveEscape() {
        // Template has one resolvable variable and one escape-prefixed token.
        final String template = "${not-escaped} $${escaped}";

        final Map<String, String> map = new HashMap<>();
        map.put("not-escaped", "value");

        // Construct substitutor with prefix "${", suffix "}", and escape character '$'.
        final StrSubstitutor sub = new StrSubstitutor(map, "${", "}", '$');

        // --- Default behaviour: escape char is consumed, not preserved ---
        assertFalse(sub.isPreserveEscapes());
        assertEquals("value ${escaped}", sub.replace(template));

        // --- After enabling preserveEscapes: escape char is kept in output ---
        sub.setPreserveEscapes(true);
        assertTrue(sub.isPreserveEscapes());
        assertEquals("value $${escaped}", sub.replace(template));
    }
}
