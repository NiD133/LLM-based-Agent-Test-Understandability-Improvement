package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class MatchRatingApproachEncoderTest_testCompare_Surname_MOSKOWITZ_MOSKOVITZ_SuccessfullyMatched {

    private static final String MOSKOWITZ_SURNAME = "Moskowitz";
    private static final String MOSKOVITZ_SURNAME = "Moskovitz";

    @Test
    final void testCompare_Surname_MOSKOWITZ_MOSKOVITZ_SuccessfullyMatched() {
        assertTrue(new MatchRatingApproachEncoder().isEncodeEquals(MOSKOWITZ_SURNAME, MOSKOVITZ_SURNAME));
    }
}
