package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class MatchRatingApproachEncoderTest_testgetMinRating_6_Returns4_Successfully {

    private static final int SUM_LENGTH_WITH_MIN_RATING_FOUR = 6;
    private static final int EXPECTED_MIN_RATING = 4;

    protected MatchRatingApproachEncoder createStringEncoder() {
        return new MatchRatingApproachEncoder();
    }

    @Test
    final void returnsFourWhenTheCombinedNameLengthIsSix() {
        assertEquals(EXPECTED_MIN_RATING, createStringEncoder().getMinRating(SUM_LENGTH_WITH_MIN_RATING_FOUR));
    }
}
