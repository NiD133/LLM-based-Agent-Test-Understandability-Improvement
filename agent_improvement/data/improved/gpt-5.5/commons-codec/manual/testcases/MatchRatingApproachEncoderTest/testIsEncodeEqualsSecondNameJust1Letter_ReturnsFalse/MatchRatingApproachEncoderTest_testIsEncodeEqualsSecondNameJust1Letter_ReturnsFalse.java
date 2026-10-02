package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

public class MatchRatingApproachEncoderTest_testIsEncodeEqualsSecondNameJust1Letter_ReturnsFalse {

    private static final String FIRST_NAME = "test";
    private static final String ONE_LETTER_SECOND_NAME = "t";

    @Test
    final void testIsEncodeEqualsSecondNameJust1Letter_ReturnsFalse() {
        final MatchRatingApproachEncoder encoder = new MatchRatingApproachEncoder();
        final boolean namesMatch = encoder.isEncodeEquals(FIRST_NAME, ONE_LETTER_SECOND_NAME);

        assertFalse(namesMatch);
    }
}
