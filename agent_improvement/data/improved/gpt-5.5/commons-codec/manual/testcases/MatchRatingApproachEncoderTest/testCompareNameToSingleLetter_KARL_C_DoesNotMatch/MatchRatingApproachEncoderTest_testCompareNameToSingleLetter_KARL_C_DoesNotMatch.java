package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

public class MatchRatingApproachEncoderTest_testCompareNameToSingleLetter_KARL_C_DoesNotMatch {

    @Test
    final void testCompareNameToSingleLetter_KARL_C_DoesNotMatch() {
        final String fullName = "Karl";
        final String singleLetterName = "C";
        final MatchRatingApproachEncoder encoder = new MatchRatingApproachEncoder();

        assertFalse(encoder.isEncodeEquals(fullName, singleLetterName));
    }
}
