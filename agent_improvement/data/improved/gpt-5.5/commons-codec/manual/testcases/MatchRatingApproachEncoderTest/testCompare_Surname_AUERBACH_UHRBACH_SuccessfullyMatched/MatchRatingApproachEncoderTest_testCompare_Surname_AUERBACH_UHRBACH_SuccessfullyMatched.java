package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class MatchRatingApproachEncoderTest_testCompare_Surname_AUERBACH_UHRBACH_SuccessfullyMatched {

    private static final String SURNAME_AUERBACH = "Auerbach";
    private static final String SURNAME_UHRBACH = "Uhrbach";

    protected MatchRatingApproachEncoder createStringEncoder() {
        return new MatchRatingApproachEncoder();
    }

    private MatchRatingApproachEncoder getStringEncoder() {
        return createStringEncoder();
    }

    @Test
    final void testCompare_Surname_AUERBACH_UHRBACH_SuccessfullyMatched() {
        final boolean surnameMatch = getStringEncoder().isEncodeEquals(SURNAME_AUERBACH, SURNAME_UHRBACH);

        assertTrue(surnameMatch);
    }
}
