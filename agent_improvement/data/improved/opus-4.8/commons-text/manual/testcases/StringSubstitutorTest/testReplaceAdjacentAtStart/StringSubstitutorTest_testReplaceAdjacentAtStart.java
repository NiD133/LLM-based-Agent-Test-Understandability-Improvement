package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

/**
 * Tests that {@link StringSubstitutor} correctly resolves two variable
 * placeholders that sit directly next to each other at the start of a template.
 */
public class StringSubstitutorTest_testReplaceAdjacentAtStart {

    private static final String ANIMAL = "quick brown fox";

    private static final String TARGET = "lazy dog";

    /**
     * Compares two character sequences for equality, reporting both lengths in
     * the failure message to make length mismatches easy to spot.
     */
    private void assertEqualsCharSeq(final CharSequence expected, final CharSequence actual) {
        assertEquals(expected, actual,
            () -> String.format("expected.length()=%,d, actual.length()=%,d",
                StringUtils.length(expected), StringUtils.length(actual)));
    }

    /**
     * Builds the variable lookup map used by the test.
     */
    private Map<String, String> newValues() {
        final Map<String, String> values = new HashMap<>();
        values.put("animal", ANIMAL);
        values.put("target", TARGET);
        return values;
    }

    /**
     * Two adjacent placeholders {@code ${code}${amount}} at the very start of
     * the template should both be replaced, producing the concatenated values.
     */
    @Test
    void testReplaceAdjacentAtStart() throws IOException {
        final Map<String, String> values = newValues();
        values.put("code", "GBP");
        values.put("amount", "12.50");

        final StringSubstitutor sub = new StringSubstitutor(values);

        assertEqualsCharSeq("GBP12.50 charged", sub.replace("${code}${amount} charged"));
    }
}
