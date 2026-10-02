package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class NysiisTest_testSpecialBranches {

    private static final int INPUT = 0;
    private static final int EXPECTED_ENCODING = 1;

    private static final String[][] SPECIAL_BRANCH_CASES = {
        { "Kobwick", "CABWAC" },
        { "Kocher", "CACAR" },
        { "Fesca", "FASC" },
        { "Shom", "SAN" },
        { "Ohlo", "OL" },
        { "Uhu", "UH" },
        { "Um", "UN" },
    };

    private final Nysiis nysiis = new Nysiis();

    @Test
    void testSpecialBranches() {
        for (final String[] testCase : SPECIAL_BRANCH_CASES) {
            assertEquals(
                testCase[EXPECTED_ENCODING],
                nysiis.encode(testCase[INPUT]),
                "Problem with " + testCase[INPUT]);
        }
    }
}
