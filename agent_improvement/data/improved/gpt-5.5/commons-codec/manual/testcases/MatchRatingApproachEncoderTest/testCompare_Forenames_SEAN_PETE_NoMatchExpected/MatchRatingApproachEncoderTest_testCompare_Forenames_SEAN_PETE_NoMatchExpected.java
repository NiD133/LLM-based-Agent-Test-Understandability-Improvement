package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

public class MatchRatingApproachEncoderTest_testCompare_Forenames_SEAN_PETE_NoMatchExpected {

    private static final String FIRST_FORENAME = "Sean";
    private static final String SECOND_FORENAME = "Pete";

    private MatchRatingApproachEncoder createStringEncoder() {
        return new MatchRatingApproachEncoder();
    }

    @Test
    final void testCompare_Forenames_SEAN_PETE_NoMatchExpected() {
        assertFalse(createStringEncoder().isEncodeEquals(FIRST_FORENAME, SECOND_FORENAME));
    }
}
