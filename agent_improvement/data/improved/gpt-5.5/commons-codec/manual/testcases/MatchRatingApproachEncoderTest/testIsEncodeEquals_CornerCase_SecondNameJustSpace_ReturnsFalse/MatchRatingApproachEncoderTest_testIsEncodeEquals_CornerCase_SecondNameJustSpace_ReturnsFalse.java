package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

public class MatchRatingApproachEncoderTest_testIsEncodeEquals_CornerCase_SecondNameJustSpace_ReturnsFalse {

    protected MatchRatingApproachEncoder createStringEncoder() {
        return new MatchRatingApproachEncoder();
    }

    @Test
    final void testIsEncodeEquals_CornerCase_SecondNameJustSpace_ReturnsFalse() {
        final boolean namesMatch = createStringEncoder().isEncodeEquals("test", " ");

        assertFalse(namesMatch);
    }
}
