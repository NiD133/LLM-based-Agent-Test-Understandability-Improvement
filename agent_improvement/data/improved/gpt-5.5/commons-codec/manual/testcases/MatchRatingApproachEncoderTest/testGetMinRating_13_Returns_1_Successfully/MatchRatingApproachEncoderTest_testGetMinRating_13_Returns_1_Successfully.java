package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class MatchRatingApproachEncoderTest_testGetMinRating_13_Returns_1_Successfully {

    @Test
    final void testGetMinRating_13_Returns_1_Successfully() {
        final MatchRatingApproachEncoder encoder = new MatchRatingApproachEncoder();
        final int combinedEncodedNameLength = 13;
        final int expectedMinimumRating = 1;

        assertEquals(expectedMinimumRating, encoder.getMinRating(combinedEncodedNameLength));
    }
}
