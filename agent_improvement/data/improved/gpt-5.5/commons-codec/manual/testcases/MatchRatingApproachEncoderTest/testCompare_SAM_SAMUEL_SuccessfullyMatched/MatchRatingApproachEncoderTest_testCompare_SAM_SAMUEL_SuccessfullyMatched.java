package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class MatchRatingApproachEncoderTest_testCompare_SAM_SAMUEL_SuccessfullyMatched {

    @Test
    final void testCompare_SAM_SAMUEL_SuccessfullyMatched() {
        final MatchRatingApproachEncoder encoder = new MatchRatingApproachEncoder();
        final String shortName = "Sam";
        final String expandedName = "Samuel";

        assertTrue(encoder.isEncodeEquals(shortName, expandedName));
    }
}
