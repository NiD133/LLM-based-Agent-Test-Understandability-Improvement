package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class MatchRatingApproachEncoderTest_testCompare_FRANCISZEK_FRANCES_SuccessfullyMatched {

    private static final String POLISH_NAME = "Franciszek";
    private static final String PHONETIC_MATCH = "Frances";

    protected MatchRatingApproachEncoder createStringEncoder() {
        return new MatchRatingApproachEncoder();
    }

    private MatchRatingApproachEncoder getStringEncoder() {
        return createStringEncoder();
    }

    @Test
    final void testCompare_FRANCISZEK_FRANCES_SuccessfullyMatched() {
        assertTrue(getStringEncoder().isEncodeEquals(POLISH_NAME, PHONETIC_MATCH));
    }
}
