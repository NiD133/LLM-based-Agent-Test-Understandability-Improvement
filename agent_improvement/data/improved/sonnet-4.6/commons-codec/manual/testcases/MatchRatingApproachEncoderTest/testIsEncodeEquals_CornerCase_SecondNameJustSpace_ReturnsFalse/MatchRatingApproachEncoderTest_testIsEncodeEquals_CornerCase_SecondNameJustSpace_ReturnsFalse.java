package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertFalse;
import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

public class MatchRatingApproachEncoderTest_testIsEncodeEquals_CornerCase_SecondNameJustSpace_ReturnsFalse extends AbstractStringEncoderTest<MatchRatingApproachEncoder> {

    @Override
    protected MatchRatingApproachEncoder createStringEncoder() {
        return new MatchRatingApproachEncoder();
    }

    /**
     * A single space is treated as a blank/trivial input by isEncodeEquals,
     * so the comparison must return false regardless of the first name.
     */
    @Test
    final void testIsEncodeEquals_CornerCase_SecondNameJustSpace_ReturnsFalse() {
        final String firstName = "test";
        final String secondName = " "; // single space — trivial input that should be rejected

        assertFalse(getStringEncoder().isEncodeEquals(firstName, secondName));
    }
}
