package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

public class MatchRatingApproachEncoderTest_testIsEncodeEquals_CornerCase_SecondNameNothing_ReturnsFalse {

    private final MatchRatingApproachEncoder stringEncoder = createStringEncoder();

    protected MatchRatingApproachEncoder createStringEncoder() {
        return new MatchRatingApproachEncoder();
    }

    private MatchRatingApproachEncoder getStringEncoder() {
        return stringEncoder;
    }

    @Test
    final void returnsFalseWhenSecondNameIsEmpty() {
        final String firstName = "test";
        final String emptySecondName = "";

        assertFalse(getStringEncoder().isEncodeEquals(firstName, emptySecondName));
    }
}
