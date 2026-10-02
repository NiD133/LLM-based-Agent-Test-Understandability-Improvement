package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

public class MatchRatingApproachEncoderTest_testIsEncodeEquals_CornerCase_FirstNameJust1Letter_ReturnsFalse {

    private static final String ONE_LETTER_FIRST_NAME = "t";
    private static final String COMPARISON_NAME = "test";
    private final MatchRatingApproachEncoder encoder = new MatchRatingApproachEncoder();

    @Test
    final void testIsEncodeEquals_CornerCase_FirstNameJust1Letter_ReturnsFalse() {
        assertFalse(encoder.isEncodeEquals(ONE_LETTER_FIRST_NAME, COMPARISON_NAME));
    }
}
