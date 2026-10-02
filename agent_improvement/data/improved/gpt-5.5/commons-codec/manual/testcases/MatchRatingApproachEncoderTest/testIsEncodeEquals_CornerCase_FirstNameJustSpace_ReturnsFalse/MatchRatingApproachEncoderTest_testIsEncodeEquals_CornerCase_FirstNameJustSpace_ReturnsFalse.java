package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

public class MatchRatingApproachEncoderTest_testIsEncodeEquals_CornerCase_FirstNameJustSpace_ReturnsFalse {

    @Test
    final void testIsEncodeEquals_CornerCase_FirstNameJustSpace_ReturnsFalse() {
        final MatchRatingApproachEncoder encoder = new MatchRatingApproachEncoder();
        final String blankFirstName = " ";
        final String comparisonName = "test";

        assertFalse(encoder.isEncodeEquals(blankFirstName, comparisonName));
    }
}
