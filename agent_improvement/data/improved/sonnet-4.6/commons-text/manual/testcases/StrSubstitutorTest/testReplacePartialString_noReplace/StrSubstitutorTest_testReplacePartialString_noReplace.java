package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class StrSubstitutorTest_testReplacePartialString_noReplace {

    /**
     * Verifies that replace(String, offset, length) returns the raw substring
     * unchanged when no variable values are configured.
     *
     * Template : "The ${animal} jumps over the ${target}."
     * Offset 4, length 15 selects: "${animal} jumps"
     * With no variable map the placeholder is left as-is.
     */
    @Test
    void testReplacePartialString_noReplace() {
        final String template = "The ${animal} jumps over the ${target}.";
        final int offset = 4;   // skip leading "The "
        final int length = 15;  // covers "${animal} jumps"

        final StrSubstitutor sub = new StrSubstitutor();

        assertEquals("${animal} jumps", sub.replace(template, offset, length));
    }
}
