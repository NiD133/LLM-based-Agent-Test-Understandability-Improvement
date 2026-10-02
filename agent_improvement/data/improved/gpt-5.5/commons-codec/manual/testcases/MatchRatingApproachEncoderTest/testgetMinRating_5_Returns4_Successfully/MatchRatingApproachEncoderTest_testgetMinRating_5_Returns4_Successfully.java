package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class MatchRatingApproachEncoderTest_testgetMinRating_5_Returns4_Successfully {

    @Test
    final void getMinRatingReturnsFourWhenCombinedLengthIsFive() {
        final int combinedEncodedNameLength = 5;
        final int expectedMinimumRating = 4;
        final MatchRatingApproachEncoder encoder = new MatchRatingApproachEncoder();

        assertEquals(expectedMinimumRating, encoder.getMinRating(combinedEncodedNameLength));
    }
}
