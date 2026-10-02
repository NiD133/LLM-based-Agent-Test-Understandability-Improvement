package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertFalse;
import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

public class MatchRatingApproachEncoderTest_testIsEncodeEquals_CornerCase_FirstNameNothing_ReturnsFalse extends AbstractStringEncoderTest<MatchRatingApproachEncoder> {

    @Override
    protected MatchRatingApproachEncoder createStringEncoder() {
        return new MatchRatingApproachEncoder();
    }

    // The MRA algorithm treats an empty first name as a trivial input and returns false immediately,
    // rather than attempting a phonetic comparison against the second name.
    @Test
    final void testIsEncodeEquals_CornerCase_FirstNameNothing_ReturnsFalse() {
        final String emptyFirstName = "";
        final String anySecondName = "test";

        assertFalse(getStringEncoder().isEncodeEquals(emptyFirstName, anySecondName));
    }
}
