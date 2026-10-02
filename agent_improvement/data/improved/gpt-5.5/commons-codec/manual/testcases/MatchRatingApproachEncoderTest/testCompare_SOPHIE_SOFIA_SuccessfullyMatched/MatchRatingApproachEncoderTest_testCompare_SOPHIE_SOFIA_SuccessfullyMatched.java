package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class MatchRatingApproachEncoderTest_testCompare_SOPHIE_SOFIA_SuccessfullyMatched {

    private static final String GIVEN_NAME = "Sophie";
    private static final String PHONETICALLY_SIMILAR_NAME = "Sofia";

    private MatchRatingApproachEncoder createStringEncoder() {
        return new MatchRatingApproachEncoder();
    }

    @Test
    final void testCompare_SOPHIE_SOFIA_SuccessfullyMatched() {
        assertTrue(createStringEncoder().isEncodeEquals(GIVEN_NAME, PHONETICALLY_SIMILAR_NAME));
    }
}
