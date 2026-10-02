package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class MatchRatingApproachEncoderTest_testGetMinRating_1_Returns5_Successfully {

    protected MatchRatingApproachEncoder createStringEncoder() {
        return new MatchRatingApproachEncoder();
    }

    private MatchRatingApproachEncoder getStringEncoder() {
        return createStringEncoder();
    }

    @Test
    final void testGetMinRatingReturnsFiveForShortestCombinedNameLength() {
        final int shortestCombinedNameLength = 1;
        final int expectedMinimumRating = 5;

        final int actualMinimumRating = getStringEncoder().getMinRating(shortestCombinedNameLength);

        assertEquals(expectedMinimumRating, actualMinimumRating);
    }
}
