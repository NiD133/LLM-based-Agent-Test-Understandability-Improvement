package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class MatchRatingApproachEncoderTest_testgetMinRating_8_Returns3_Successfully {

    private static final int SUM_LENGTH_IN_EIGHT_TO_ELEVEN_RANGE = 8;
    private static final int EXPECTED_MIN_RATING_FOR_RANGE = 3;

    @Test
    final void returnsMinRatingThreeForSumLengthEight() {
        assertEquals(EXPECTED_MIN_RATING_FOR_RANGE,
                new MatchRatingApproachEncoder().getMinRating(SUM_LENGTH_IN_EIGHT_TO_ELEVEN_RANGE));
    }
}
