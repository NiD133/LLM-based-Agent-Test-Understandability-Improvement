package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

public class MatchRatingApproachEncoderTest_testIsVowel_SingleVowel_ReturnsTrue extends AbstractStringEncoderTest<MatchRatingApproachEncoder> {

    @Override
    protected MatchRatingApproachEncoder createStringEncoder() {
        return new MatchRatingApproachEncoder();
    }

    @Test
    final void testIsVowel_SingleVowel_ReturnsTrue() {
        // "I" is a vowel; the encoder must recognize all five vowels (A, E, I, O, U)
        boolean isVowelResult = getStringEncoder().isVowel("I");
        assertTrue(isVowelResult);
    }
}
