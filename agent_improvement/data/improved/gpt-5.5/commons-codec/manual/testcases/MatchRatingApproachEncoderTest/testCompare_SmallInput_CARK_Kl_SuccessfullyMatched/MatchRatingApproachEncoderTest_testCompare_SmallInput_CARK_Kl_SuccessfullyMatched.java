package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class MatchRatingApproachEncoderTest_testCompare_SmallInput_CARK_Kl_SuccessfullyMatched {

    private static final String SHORT_NAME = "Kl";
    private static final String MATCHING_FULL_NAME = "Karl";

    @Test
    final void testCompare_SmallInput_CARK_Kl_SuccessfullyMatched() {
        assertTrue(new MatchRatingApproachEncoder().isEncodeEquals(SHORT_NAME, MATCHING_FULL_NAME));
    }
}
