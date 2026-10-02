package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class MatchRatingApproachEncoderTest_testCompareNameSameNames_ReturnsFalseSuccessfully {

    private static final String NAME = "John";

    @Test
    final void testCompareNameSameNames_ReturnsFalseSuccessfully() {
        final MatchRatingApproachEncoder encoder = new MatchRatingApproachEncoder();
        final boolean sameNameMatches = encoder.isEncodeEquals(NAME, NAME);

        assertTrue(sameNameMatches);
    }
}
