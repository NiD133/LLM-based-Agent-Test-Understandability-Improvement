package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class MatchRatingApproachEncoderTest_testCompare_MICKY_MICHAEL_SuccessfullyMatched {

    private static final String SHORT_NAME = "Micky";
    private static final String FULL_NAME = "Michael";

    private MatchRatingApproachEncoder createStringEncoder() {
        return new MatchRatingApproachEncoder();
    }

    private MatchRatingApproachEncoder getStringEncoder() {
        return createStringEncoder();
    }

    @Test
    final void testCompare_MICKY_MICHAEL_SuccessfullyMatched() {
        assertTrue(
                getStringEncoder().isEncodeEquals(SHORT_NAME, FULL_NAME),
                "Match Rating Approach should identify Micky and Michael as matching names");
    }
}
