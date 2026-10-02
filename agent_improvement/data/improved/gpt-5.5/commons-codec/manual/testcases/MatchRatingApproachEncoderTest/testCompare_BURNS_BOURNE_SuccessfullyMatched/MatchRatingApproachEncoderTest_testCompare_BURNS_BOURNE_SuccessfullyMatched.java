package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class MatchRatingApproachEncoderTest_testCompare_BURNS_BOURNE_SuccessfullyMatched {

    protected MatchRatingApproachEncoder createStringEncoder() {
        return new MatchRatingApproachEncoder();
    }

    private MatchRatingApproachEncoder getStringEncoder() {
        return createStringEncoder();
    }

    @Test
    final void testCompare_BURNS_BOURNE_SuccessfullyMatched() {
        final String firstName = "Burns";
        final String secondName = "Bourne";

        assertTrue(getStringEncoder().isEncodeEquals(firstName, secondName));
    }
}
