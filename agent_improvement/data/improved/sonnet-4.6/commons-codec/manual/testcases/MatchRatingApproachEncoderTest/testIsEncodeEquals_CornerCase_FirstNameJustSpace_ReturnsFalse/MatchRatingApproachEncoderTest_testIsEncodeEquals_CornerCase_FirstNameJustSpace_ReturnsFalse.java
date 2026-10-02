package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertFalse;
import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

public class MatchRatingApproachEncoderTest_testIsEncodeEquals_CornerCase_FirstNameJustSpace_ReturnsFalse extends AbstractStringEncoderTest<MatchRatingApproachEncoder> {

    @Override
    protected MatchRatingApproachEncoder createStringEncoder() {
        return new MatchRatingApproachEncoder();
    }

    /**
     * A first name consisting of only a space is treated as trivially invalid by
     * the MRA algorithm, so isEncodeEquals must return false immediately rather
     * than attempting phonetic comparison.
     */
    @Test
    final void testIsEncodeEquals_CornerCase_FirstNameJustSpace_ReturnsFalse() {
        String spaceOnlyFirstName = " ";
        String validSecondName = "test";

        assertFalse(getStringEncoder().isEncodeEquals(spaceOnlyFirstName, validSecondName));
    }
}
