package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

public class MatchRatingApproachEncoderTest_testCompare_Forenames_UNA_OONAGH_ShouldSuccessfullyMatchButDoesNot {

    private MatchRatingApproachEncoder createStringEncoder() {
        return new MatchRatingApproachEncoder();
    }

    @Test
    final void testCompare_Forenames_UNA_OONAGH_ShouldSuccessfullyMatchButDoesNot() {
        // The encoder currently does not match these forename variants.
        assertFalse(createStringEncoder().isEncodeEquals("Úna", "Oonagh"));
    }
}
