package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

public class MatchRatingApproachEncoderTest_testCompare_SurnamesCornerCase_MURPHY_NoSpace_NoMatch {

    private static final String SURNAME = "Murphy";
    private static final String EMPTY_NAME = "";

    private MatchRatingApproachEncoder createStringEncoder() {
        return new MatchRatingApproachEncoder();
    }

    private MatchRatingApproachEncoder getStringEncoder() {
        return createStringEncoder();
    }

    @Test
    final void testCompare_SurnamesCornerCase_MURPHY_NoSpace_NoMatch() {
        assertFalse(getStringEncoder().isEncodeEquals(SURNAME, EMPTY_NAME));
    }
}
