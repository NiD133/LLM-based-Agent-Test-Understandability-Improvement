package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class MatchRatingApproachEncoderTest_testCompare_Surname_LIPSHITZ_LIPPSZYC_SuccessfullyMatched {

    private static final String ORIGINAL_SURNAME = "LIPSHITZ";
    private static final String COMPARISON_SURNAME = "LIPPSZYC";

    protected MatchRatingApproachEncoder createStringEncoder() {
        return new MatchRatingApproachEncoder();
    }

    private MatchRatingApproachEncoder getStringEncoder() {
        return createStringEncoder();
    }

    @Test
    final void testCompare_Surname_LIPSHITZ_LIPPSZYC_SuccessfullyMatched() {
        assertTrue(getStringEncoder().isEncodeEquals(ORIGINAL_SURNAME, COMPARISON_SURNAME));
    }
}
