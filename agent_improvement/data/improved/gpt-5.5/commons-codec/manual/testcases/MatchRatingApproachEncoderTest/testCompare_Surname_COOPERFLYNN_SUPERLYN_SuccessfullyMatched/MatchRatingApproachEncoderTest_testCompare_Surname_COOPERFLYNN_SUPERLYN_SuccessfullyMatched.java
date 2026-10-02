package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class MatchRatingApproachEncoderTest_testCompare_Surname_COOPERFLYNN_SUPERLYN_SuccessfullyMatched {

    private static final String SURNAME_WITH_HYPHENATED_PARTS = "Cooper-Flynn";
    private static final String SIMILAR_SOUNDING_SURNAME = "Super-Lyn";

    private MatchRatingApproachEncoder getStringEncoder() {
        return new MatchRatingApproachEncoder();
    }

    @Test
    final void testCompare_Surname_COOPERFLYNN_SUPERLYN_SuccessfullyMatched() {
        assertTrue(getStringEncoder().isEncodeEquals(SURNAME_WITH_HYPHENATED_PARTS, SIMILAR_SOUNDING_SURNAME));
    }
}
