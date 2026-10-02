package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class MatchRatingApproachEncoderTest_testLeftToRightThenRightToLeft_EINSTEIN_MICHAELA_Returns0 {

    @Test
    final void testLeftToRightThenRightToLeft_EINSTEIN_MICHAELA_Returns0() {
        final String firstName = "EINSTEIN";
        final String secondName = "MICHAELA";
        final int expectedSimilarityScore = 0;
        final MatchRatingApproachEncoder encoder = new MatchRatingApproachEncoder();

        final int actualSimilarityScore = encoder.leftToRightThenRightToLeftProcessing(firstName, secondName);

        assertEquals(expectedSimilarityScore, actualSimilarityScore);
    }
}
