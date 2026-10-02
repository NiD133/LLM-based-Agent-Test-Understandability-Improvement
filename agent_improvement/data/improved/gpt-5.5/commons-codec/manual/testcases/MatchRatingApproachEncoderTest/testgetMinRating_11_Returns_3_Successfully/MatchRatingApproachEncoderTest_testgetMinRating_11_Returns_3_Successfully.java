package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class MatchRatingApproachEncoderTest_testgetMinRating_11_Returns_3_Successfully {

    private MatchRatingApproachEncoder getStringEncoder() {
        return new MatchRatingApproachEncoder();
    }

    @Test
    final void getMinRatingReturnsThreeForMaximumSumLengthInTheEightToElevenRange() {
        final int maximumSumLengthForRatingThree = 11;
        final int expectedMinimumRating = 3;

        assertEquals(expectedMinimumRating, getStringEncoder().getMinRating(maximumSumLengthForRatingThree));
    }
}
