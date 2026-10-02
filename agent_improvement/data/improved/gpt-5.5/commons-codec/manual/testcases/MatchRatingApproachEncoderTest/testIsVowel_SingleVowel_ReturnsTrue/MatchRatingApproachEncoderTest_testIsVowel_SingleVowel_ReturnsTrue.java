package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class MatchRatingApproachEncoderTest_testIsVowel_SingleVowel_ReturnsTrue {

    private static final String SINGLE_VOWEL = "I";
    private final MatchRatingApproachEncoder encoder = new MatchRatingApproachEncoder();

    private MatchRatingApproachEncoder getStringEncoder() {
        return encoder;
    }

    @Test
    final void testIsVowel_SingleVowel_ReturnsTrue() {
        assertTrue(getStringEncoder().isVowel(SINGLE_VOWEL));
    }
}
