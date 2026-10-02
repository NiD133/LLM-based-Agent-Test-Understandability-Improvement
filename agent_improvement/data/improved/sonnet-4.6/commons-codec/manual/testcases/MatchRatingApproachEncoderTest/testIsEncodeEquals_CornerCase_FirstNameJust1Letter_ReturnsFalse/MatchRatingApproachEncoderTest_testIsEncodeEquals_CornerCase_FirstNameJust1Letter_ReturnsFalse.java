package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertFalse;
import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

public class MatchRatingApproachEncoderTest_testIsEncodeEquals_CornerCase_FirstNameJust1Letter_ReturnsFalse extends AbstractStringEncoderTest<MatchRatingApproachEncoder> {

    @Override
    protected MatchRatingApproachEncoder createStringEncoder() {
        return new MatchRatingApproachEncoder();
    }

    @Test
    final void testIsEncodeEquals_CornerCase_FirstNameJust1Letter_ReturnsFalse() {
        // MRA rejects comparison when either name is a single letter — no meaningful phonetic code can be produced
        String singleLetterName = "t";
        String normalName = "test";

        assertFalse(getStringEncoder().isEncodeEquals(singleLetterName, normalName));
    }
}
