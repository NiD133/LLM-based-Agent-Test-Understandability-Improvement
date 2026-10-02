package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class MatchRatingApproachEncoderTest_testCompare_Surname_PRZEMYSL_PSHEMESHIL_SuccessfullyMatched {

    private static final String PRZEMYSL_WITH_SPACES = " P rz e m y s l";
    private static final String PSHEMESHIL_WITH_SPACES = " P sh e m e sh i l";

    protected MatchRatingApproachEncoder createStringEncoder() {
        return new MatchRatingApproachEncoder();
    }

    private MatchRatingApproachEncoder getStringEncoder() {
        return createStringEncoder();
    }

    @Test
    final void testCompare_Surname_PRZEMYSL_PSHEMESHIL_SuccessfullyMatched() {
        assertTrue(getStringEncoder().isEncodeEquals(PRZEMYSL_WITH_SPACES, PSHEMESHIL_WITH_SPACES));
    }
}
