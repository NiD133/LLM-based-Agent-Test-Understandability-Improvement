package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class MatchRatingApproachEncoderTest_testgetMinRating_10_Returns3_Successfully {

    private static final int SUM_LENGTH_IN_EIGHT_TO_ELEVEN_RANGE = 10;
    private static final int EXPECTED_MIN_RATING = 3;

    private MatchRatingApproachEncoder createStringEncoder() {
        return new MatchRatingApproachEncoder();
    }

    private MatchRatingApproachEncoder getStringEncoder() {
        return createStringEncoder();
    }

    @Test
    final void testgetMinRating_10_Returns3_Successfully() {
        assertEquals(EXPECTED_MIN_RATING,
                getStringEncoder().getMinRating(SUM_LENGTH_IN_EIGHT_TO_ELEVEN_RANGE));
    }
}
