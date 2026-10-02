package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class MatchRatingApproachEncoderTest_testCompare_Forenames_SEAN_JOHN_MatchExpected {

    private static final String FIRST_FORENAME = "Sean";
    private static final String SECOND_FORENAME = "John";

    protected MatchRatingApproachEncoder createStringEncoder() {
        return new MatchRatingApproachEncoder();
    }

    private MatchRatingApproachEncoder getStringEncoder() {
        return createStringEncoder();
    }

    @Test
    final void testCompare_Forenames_SEAN_JOHN_MatchExpected() {
        final boolean forenamesMatch = getStringEncoder().isEncodeEquals(FIRST_FORENAME, SECOND_FORENAME);

        assertTrue(forenamesMatch);
    }
}
