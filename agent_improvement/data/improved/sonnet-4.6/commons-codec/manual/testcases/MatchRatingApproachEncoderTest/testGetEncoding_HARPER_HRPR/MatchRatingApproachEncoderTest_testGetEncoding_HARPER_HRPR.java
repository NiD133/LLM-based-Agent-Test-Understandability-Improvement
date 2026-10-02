package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

public class MatchRatingApproachEncoderTest_testGetEncoding_HARPER_HRPR extends AbstractStringEncoderTest<MatchRatingApproachEncoder> {

    @Override
    protected MatchRatingApproachEncoder createStringEncoder() {
        return new MatchRatingApproachEncoder();
    }

    /**
     * Verifies that "HARPER" encodes to "HRPR" under the Match Rating Approach.
     * MRA steps: vowels A and E are removed (H is not a vowel so no prefix preserved),
     * leaving "HRPR"; no double consonants exist; length ≤ 6 so the result is returned as-is.
     */
    @Test
    final void testGetEncoding_HARPER_HRPR() {
        final String input = "HARPER";
        final String expectedCode = "HRPR";

        assertEquals(expectedCode, getStringEncoder().encode(input));
    }
}
