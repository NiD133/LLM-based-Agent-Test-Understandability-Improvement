package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

public class MatchRatingApproachEncoderTest_testIsEncodeEquals_CornerCase_SecondNameNull_ReturnsFalse {

    @Test
    final void isEncodeEqualsReturnsFalseWhenSecondNameIsNull() {
        final MatchRatingApproachEncoder encoder = new MatchRatingApproachEncoder();
        final String firstName = "test";
        final String secondName = null;

        assertFalse(encoder.isEncodeEquals(firstName, secondName));
    }
}
