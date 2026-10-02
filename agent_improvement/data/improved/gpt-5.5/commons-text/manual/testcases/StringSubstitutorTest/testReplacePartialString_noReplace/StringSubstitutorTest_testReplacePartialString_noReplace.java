package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

public class StringSubstitutorTest_testReplacePartialString_noReplace {

    private static final String CLASSIC_TEMPLATE = "The ${animal} jumps over the ${target}.";

    private void assertEqualsCharSeq(final CharSequence expected, final CharSequence actual) {
        assertEquals(expected, actual, () -> String.format("expected.length()=%,d, actual.length()=%,d",
                StringUtils.length(expected), StringUtils.length(actual)));
    }

    /**
     * Verifies that replacing only the slice containing "${animal} jumps"
     * leaves the unresolved variable text unchanged when no resolver is set.
     */
    @Test
    void testReplacePartialString_noReplace() {
        final StringSubstitutor sub = new StringSubstitutor();

        assertEqualsCharSeq("${animal} jumps", sub.replace(CLASSIC_TEMPLATE, 4, 15));
    }
}
