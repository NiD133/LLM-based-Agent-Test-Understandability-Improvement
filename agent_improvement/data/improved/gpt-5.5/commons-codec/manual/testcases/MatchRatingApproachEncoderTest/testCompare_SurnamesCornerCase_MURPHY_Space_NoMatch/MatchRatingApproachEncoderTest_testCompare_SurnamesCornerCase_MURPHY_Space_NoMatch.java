package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

public class MatchRatingApproachEncoderTest_testCompare_SurnamesCornerCase_MURPHY_Space_NoMatch {

    private static final String SURNAME = "Murphy";
    private static final String SINGLE_SPACE = " ";

    protected MatchRatingApproachEncoder createStringEncoder() {
        return new MatchRatingApproachEncoder();
    }

    private MatchRatingApproachEncoder getStringEncoder() {
        return createStringEncoder();
    }

    @Test
    final void rejectsComparisonWhenSecondSurnameIsOnlyASpace() {
        assertFalse(getStringEncoder().isEncodeEquals(SURNAME, SINGLE_SPACE));
    }
}
