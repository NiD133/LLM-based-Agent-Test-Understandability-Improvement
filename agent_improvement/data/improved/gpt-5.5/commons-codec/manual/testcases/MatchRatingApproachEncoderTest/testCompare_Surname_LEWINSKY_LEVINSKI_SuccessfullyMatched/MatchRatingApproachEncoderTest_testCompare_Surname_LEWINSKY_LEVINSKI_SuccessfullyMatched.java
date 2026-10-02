package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class MatchRatingApproachEncoderTest_testCompare_Surname_LEWINSKY_LEVINSKI_SuccessfullyMatched {

    private static final String ORIGINAL_SURNAME = "LEWINSKY";
    private static final String SIMILAR_SURNAME = "LEVINSKI";

    private MatchRatingApproachEncoder createStringEncoder() {
        return new MatchRatingApproachEncoder();
    }

    @Test
    final void matchesSimilarLewinskyAndLevinskiSurnames() {
        assertTrue(createStringEncoder().isEncodeEquals(ORIGINAL_SURNAME, SIMILAR_SURNAME));
    }
}
