package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class StrSubstitutorTest_testReplacePartialString_noReplace {

    private static final String TEMPLATE = "The ${animal} jumps over the ${target}.";
    private static final String UNCHANGED_PARTIAL_TEMPLATE = "${animal} jumps";

    /**
     * A partial range that excludes the complete variable expression should be
     * returned unchanged.
     */
    @Test
    void testReplacePartialString_noReplace() {
        final StrSubstitutor substitutor = new StrSubstitutor();

        assertEquals(UNCHANGED_PARTIAL_TEMPLATE, substitutor.replace(TEMPLATE, 4, 15));
    }
}
