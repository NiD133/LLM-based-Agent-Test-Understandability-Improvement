package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class NysiisTest_testRule1 {

    private static final String[][] RULE_1_ENCODINGS = {
        { "MACX", "MCX" },
        { "KNX", "NX" },
        { "KX", "CX" },
        { "PHX", "FX" },
        { "PFX", "FX" },
        { "SCHX", "SX" }
    };

    private final Nysiis fullNysiis = new Nysiis(false);

    private void assertEncodings(final String[][] testValues) {
        for (final String[] testValue : testValues) {
            final String input = testValue[0];
            final String expectedEncoding = testValue[1];

            assertEquals(expectedEncoding, this.fullNysiis.encode(input), "Problem with " + input);
        }
    }

    /**
     * Tests rule 1: Translate first characters of name: MAC -> MCC, KN -> N, K -> C, PH, PF -> FF, SCH -> SSS
     */
    @Test
    void testRule1() {
        assertEncodings(RULE_1_ENCODINGS);
    }
}
