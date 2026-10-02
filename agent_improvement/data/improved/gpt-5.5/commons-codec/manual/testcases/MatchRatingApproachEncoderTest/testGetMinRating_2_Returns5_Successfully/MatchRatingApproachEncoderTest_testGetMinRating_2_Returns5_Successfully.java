package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class MatchRatingApproachEncoderTest_testGetMinRating_2_Returns5_Successfully {

    private final MatchRatingApproachEncoder stringEncoder = createStringEncoder();

    protected MatchRatingApproachEncoder createStringEncoder() {
        return new MatchRatingApproachEncoder();
    }

    private MatchRatingApproachEncoder getStringEncoder() {
        return stringEncoder;
    }

    @Test
    final void testGetMinRating_2_Returns5_Successfully() {
        final int combinedNameLength = 2;
        final int expectedMinimumRating = 5;

        assertEquals(expectedMinimumRating, getStringEncoder().getMinRating(combinedNameLength));
    }
}
