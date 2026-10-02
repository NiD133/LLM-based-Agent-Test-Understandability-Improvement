package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class MatchRatingApproachEncoderTest_testLeftToRightThenRightToLeft_ALEXANDER_ALEXANDRA_Returns4 {

    private static final String FIRST_NAME = "ALEXANDER";
    private static final String SECOND_NAME = "ALEXANDRA";
    private static final int EXPECTED_SIMILARITY_RATING = 4;

    protected MatchRatingApproachEncoder createStringEncoder() {
        return new MatchRatingApproachEncoder();
    }

    private MatchRatingApproachEncoder getStringEncoder() {
        return createStringEncoder();
    }

    @Test
    final void testLeftToRightThenRightToLeft_ALEXANDER_ALEXANDRA_Returns4() {
        final int similarityRating = getStringEncoder().leftToRightThenRightToLeftProcessing(FIRST_NAME, SECOND_NAME);

        assertEquals(EXPECTED_SIMILARITY_RATING, similarityRating);
    }
}
