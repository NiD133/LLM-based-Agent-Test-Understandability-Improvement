package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class MatchRatingApproachEncoderTest_testCompare_SMITH_SMYTH_SuccessfullyMatched {

    private static final String ORIGINAL_NAME = "smith";
    private static final String SIMILAR_SOUNDING_NAME = "smyth";

    private MatchRatingApproachEncoder getStringEncoder() {
        return new MatchRatingApproachEncoder();
    }

    @Test
    final void testCompare_SMITH_SMYTH_SuccessfullyMatched() {
        assertTrue(
                getStringEncoder().isEncodeEquals(ORIGINAL_NAME, SIMILAR_SOUNDING_NAME),
                "Smith and Smyth should be matched by the Match Rating Approach encoder");
    }
}
