package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class MatchRatingApproachEncoderTest_testgetMinRating_7_Returns4_Successfully {

    private static final int LENGTH_SUM_AT_RATING_FOUR_UPPER_BOUNDARY = 7;
    private static final int EXPECTED_MINIMUM_RATING = 4;

    protected MatchRatingApproachEncoder createStringEncoder() {
        return new MatchRatingApproachEncoder();
    }

    private MatchRatingApproachEncoder getStringEncoder() {
        return createStringEncoder();
    }

    @Test
    final void returnsMinimumRatingFourWhenLengthSumIsSeven() {
        assertEquals(EXPECTED_MINIMUM_RATING,
                getStringEncoder().getMinRating(LENGTH_SUM_AT_RATING_FOUR_UPPER_BOUNDARY));
    }
}
