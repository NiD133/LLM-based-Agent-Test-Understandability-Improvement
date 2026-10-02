package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class MatchRatingApproachEncoderTest_testGetMinRating_7_Return4_Successfully {

    @Test
    final void testGetMinRating_7_Return4_Successfully() {
        final int sumLengthAtUpperBoundaryForRatingFour = 7;
        final int expectedMinimumRating = 4;

        final MatchRatingApproachEncoder encoder = new MatchRatingApproachEncoder();

        assertEquals(expectedMinimumRating, encoder.getMinRating(sumLengthAtUpperBoundaryForRatingFour));
    }
}
