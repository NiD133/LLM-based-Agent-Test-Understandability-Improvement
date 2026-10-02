package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class MatchRatingApproachEncoderTest_testPunctuationOnly {

    private static final String PUNCTUATION_ONLY_NAME = ".,-";
    private static final String EMPTY_ENCODING = "";

    @Test
    final void testPunctuationOnly() {
        assertEquals(EMPTY_ENCODING, new MatchRatingApproachEncoder().encode(PUNCTUATION_ONLY_NAME));
    }
}
