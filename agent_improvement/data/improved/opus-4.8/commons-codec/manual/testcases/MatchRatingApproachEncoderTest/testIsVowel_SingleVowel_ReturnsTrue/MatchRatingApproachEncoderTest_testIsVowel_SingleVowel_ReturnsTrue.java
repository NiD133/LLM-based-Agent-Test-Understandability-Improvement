package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link MatchRatingApproachEncoder#isVowel(String)} recognizes a
 * single vowel letter as a vowel.
 */
public class MatchRatingApproachEncoderTest_testIsVowel_SingleVowel_ReturnsTrue
        extends AbstractStringEncoderTest<MatchRatingApproachEncoder> {

    @Override
    protected MatchRatingApproachEncoder createStringEncoder() {
        return new MatchRatingApproachEncoder();
    }

    @Test
    final void isVowelReturnsTrueForSingleVowelLetter() {
        final MatchRatingApproachEncoder encoder = getStringEncoder();

        final boolean isVowel = encoder.isVowel("I");

        assertTrue(isVowel, "The letter \"I\" should be recognized as a vowel");
    }
}
