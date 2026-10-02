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
 * Verifies how {@link StringSubstitutor} treats escaped variable expressions,
 * depending on the {@code preserveEscapes} flag.
 *
 * <p>The default escape character {@code '$'} lets a template protect a
 * {@code ${...}} expression from being replaced by doubling the prefix, e.g.
 * {@code $${escaped}}. This test checks both flag states against a single
 * template that mixes one replaceable and one escaped expression.</p>
 */
public class StringSubstitutorTest_testSubstitutePreserveEscape {

    /**
     * Asserts that two character sequences are equal, reporting their lengths
     * when they differ to make mismatches easier to diagnose.
     */
    private void assertEqualsCharSeq(final CharSequence expected, final CharSequence actual) {
        assertEquals(expected, actual,
            () -> String.format("expected.length()=%,d, actual.length()=%,d",
                StringUtils.length(expected), StringUtils.length(actual)));
    }

    @Test
    void testSubstitutePreserveEscape() throws IOException {
        // Template with one normal expression and one escaped expression ("$${escaped}").
        final String template = "${not-escaped} $${escaped}";

        final Map<String, String> values = new HashMap<>();
        values.put("not-escaped", "value");

        // Prefix "${", suffix "}", escape character '$'.
        final StringSubstitutor substitutor = new StringSubstitutor(values, "${", "}", '$');

        // Default behaviour: escapes are consumed, so "$${escaped}" collapses to "${escaped}".
        assertFalse(substitutor.isPreserveEscapes());
        assertEqualsCharSeq("value ${escaped}", substitutor.replace(template));

        // With preserveEscapes enabled: the doubled prefix is kept as "$${escaped}".
        substitutor.setPreserveEscapes(true);
        assertTrue(substitutor.isPreserveEscapes());
        assertEqualsCharSeq("value $${escaped}", substitutor.replace(template));
    }
}
