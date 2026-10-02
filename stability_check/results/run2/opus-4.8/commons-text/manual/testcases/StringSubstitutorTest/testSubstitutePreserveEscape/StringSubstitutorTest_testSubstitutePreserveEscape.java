package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

/**
 * Verifies how {@link StringSubstitutor} handles escaped variable references
 * (a variable prefixed with a doubled escape character, e.g. {@code $${escaped}})
 * depending on the {@code preserveEscapes} flag.
 */
public class StringSubstitutorTest_testSubstitutePreserveEscape {

    @Test
    void testSubstitutePreserveEscape() {
        // Template mixes a normal reference "${not-escaped}" with an escaped one "$${escaped}".
        final String template = "${not-escaped} $${escaped}";

        final Map<String, String> values = new HashMap<>();
        values.put("not-escaped", "value");

        // Prefix "${", suffix "}", escape character '$'.
        final StringSubstitutor sub = new StringSubstitutor(values, "${", "}", '$');

        // By default escapes are consumed: "$${escaped}" collapses to "${escaped}".
        assertFalse(sub.isPreserveEscapes());
        assertEquals("value ${escaped}", sub.replace(template));

        // When preserving escapes, the doubled prefix "$${escaped}" is left untouched.
        sub.setPreserveEscapes(true);
        assertTrue(sub.isPreserveEscapes());
        assertEquals("value $${escaped}", sub.replace(template));
    }
}
