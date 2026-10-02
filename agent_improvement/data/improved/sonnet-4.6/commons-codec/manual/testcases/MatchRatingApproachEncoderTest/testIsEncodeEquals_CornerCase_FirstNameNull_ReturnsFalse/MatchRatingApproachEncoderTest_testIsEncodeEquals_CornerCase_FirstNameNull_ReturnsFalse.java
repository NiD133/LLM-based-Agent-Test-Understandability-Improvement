package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertFalse;
import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

public class MatchRatingApproachEncoderTest_testIsEncodeEquals_CornerCase_FirstNameNull_ReturnsFalse extends AbstractStringEncoderTest<MatchRatingApproachEncoder> {

    @Override
    protected MatchRatingApproachEncoder createStringEncoder() {
        return new MatchRatingApproachEncoder();
    }

    /**
     * The MRA algorithm treats a null first name as a trivial/invalid input and
     * immediately returns false without attempting a phonetic comparison.
     */
    @Test
    final void testIsEncodeEquals_CornerCase_FirstNameNull_ReturnsFalse() {
        MatchRatingApproachEncoder encoder = getStringEncoder();
        // null first name is a guard-clause exit: no phonetic comparison is performed
        assertFalse(encoder.isEncodeEquals(null, "test"));
    }
}
