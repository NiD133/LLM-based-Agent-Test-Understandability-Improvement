package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class MatchRatingApproachEncoderTest_testgetMinRating_5_Returns4_Successfully2 {

    private static final int MINIMUM_SUM_LENGTH_FOR_RATING_FOUR = 5;
    private static final int EXPECTED_MIN_RATING = 4;

    private MatchRatingApproachEncoder getStringEncoder() {
        return new MatchRatingApproachEncoder();
    }

    @Test
    final void returnsRatingFourForSumLengthFive() {
        assertEquals(EXPECTED_MIN_RATING,
                getStringEncoder().getMinRating(MINIMUM_SUM_LENGTH_FOR_RATING_FOUR));
    }
}
