package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class StrSubstitutorTest_testReplacePartialString_noReplace {

    /**
     * Verifies that replacing over a sub-range of the source leaves that range
     * unchanged when the substitutor has no variables configured.
     */
    @Test
    void testReplacePartialString_noReplace() {
        // A substitutor with no variable values: nothing can ever be substituted.
        final StrSubstitutor sub = new StrSubstitutor();

        final String source = "The ${animal} jumps over the ${target}.";
        // Replace only the 15 characters starting at offset 4, i.e. "${animal} jumps".
        final int offset = 4;
        final int length = 15;

        // With no values defined, the selected sub-range is returned verbatim.
        assertEquals("${animal} jumps", sub.replace(source, offset, length));
    }
}
