package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

public class MatchRatingApproachEncoderTest_testIsEncodeEquals_CornerCase_FirstNameNothing_ReturnsFalse {

    private static final String EMPTY_FIRST_NAME = "";
    private static final String COMPARISON_NAME = "test";

    @Test
    final void testIsEncodeEquals_CornerCase_FirstNameNothing_ReturnsFalse() {
        final MatchRatingApproachEncoder encoder = new MatchRatingApproachEncoder();

        assertFalse(encoder.isEncodeEquals(EMPTY_FIRST_NAME, COMPARISON_NAME));
    }
}
