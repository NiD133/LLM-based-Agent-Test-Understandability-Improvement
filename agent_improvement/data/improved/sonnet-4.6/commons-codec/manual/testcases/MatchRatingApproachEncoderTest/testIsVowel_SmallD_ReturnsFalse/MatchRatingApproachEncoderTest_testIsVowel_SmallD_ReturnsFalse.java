package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertFalse;
import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class MatchRatingApproachEncoderTest_testIsVowel_SmallD_ReturnsFalse extends AbstractStringEncoderTest<MatchRatingApproachEncoder> {

    @Override
    protected MatchRatingApproachEncoder createStringEncoder() {
        return new MatchRatingApproachEncoder();
    }

    /**
     * Verifies that a lowercase consonant ('d') is not classified as a vowel.
     * The MRA encoder only recognises A, E, I, O, U as vowels; all other letters
     * must return false so that consonants are processed correctly during encoding.
     */
    @Test
    @DisplayName("isVowel(\"d\") returns false because 'd' is a consonant, not a vowel")
    final void testIsVowel_SmallD_ReturnsFalse() {
        String consonant = "d";
        assertFalse(getStringEncoder().isVowel(consonant));
    }
}
