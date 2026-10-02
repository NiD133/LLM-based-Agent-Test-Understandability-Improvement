package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class MatchRatingApproachEncoderTest_testCompare_CATHERINE_KATHRYN_SuccessfullyMatched {

    private static final String ORIGINAL_NAME = "Catherine";
    private static final String PHONETIC_VARIANT = "Kathryn";

    private MatchRatingApproachEncoder createStringEncoder() {
        return new MatchRatingApproachEncoder();
    }

    @Test
    final void testCompare_CATHERINE_KATHRYN_SuccessfullyMatched() {
        assertTrue(createStringEncoder().isEncodeEquals(ORIGINAL_NAME, PHONETIC_VARIANT));
    }
}
