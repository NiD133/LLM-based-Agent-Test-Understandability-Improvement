package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

public class StringSubstitutorTest_testReplacePartialString_noReplace {

    // "The ${animal} jumps over the ${target}."
    private static final String CLASSIC_TEMPLATE = "The ${animal} jumps over the ${target}.";

    // "The " occupies indices 0–3, so index 4 is where "${animal}..." begins
    private static final int OFFSET_AFTER_THE = 4;

    // "${animal} jumps" is exactly 15 characters
    private static final int LENGTH_OF_ANIMAL_JUMPS = 15;

    private void assertEqualsCharSeq(final CharSequence expected, final CharSequence actual) {
        assertEquals(expected, actual, () -> String.format("expected.length()=%,d, actual.length()=%,d",
                StringUtils.length(expected), StringUtils.length(actual)));
    }

    /**
     * Verifies that {@code replace(String, offset, length)} returns the selected
     * substring unchanged when no variable lookup map is configured. The default
     * {@code StringSubstitutor} has no variables, so the placeholder is left as-is.
     */
    @Test
    void testReplacePartialString_noReplace() {
        final StringSubstitutor sub = new StringSubstitutor();
        assertEqualsCharSeq("${animal} jumps",
                sub.replace(CLASSIC_TEMPLATE, OFFSET_AFTER_THE, LENGTH_OF_ANIMAL_JUMPS));
    }
}
