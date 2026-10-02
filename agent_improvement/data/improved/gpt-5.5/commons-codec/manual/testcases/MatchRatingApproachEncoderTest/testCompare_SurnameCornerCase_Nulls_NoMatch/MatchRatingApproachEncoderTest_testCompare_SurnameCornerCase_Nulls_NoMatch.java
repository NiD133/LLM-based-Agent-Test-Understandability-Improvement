package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

public class MatchRatingApproachEncoderTest_testCompare_SurnameCornerCase_Nulls_NoMatch {

    protected MatchRatingApproachEncoder createStringEncoder() {
        return new MatchRatingApproachEncoder();
    }

    private MatchRatingApproachEncoder getStringEncoder() {
        return createStringEncoder();
    }

    @Test
    final void testCompare_SurnameCornerCase_Nulls_NoMatch() {
        final String firstSurname = null;
        final String secondSurname = null;

        assertFalse(getStringEncoder().isEncodeEquals(firstSurname, secondSurname));
    }
}
