package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertFalse;

import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

/**
 * Verifies {@link MatchRatingApproachEncoder#isVowel(String)} for consonant input.
 */
public class MatchRatingApproachEncoderTest_testIsVowel_SmallD_ReturnsFalse
        extends AbstractStringEncoderTest<MatchRatingApproachEncoder> {

    @Override
    protected MatchRatingApproachEncoder createStringEncoder() {
        return new MatchRatingApproachEncoder();
    }

    /**
     * The lowercase consonant "d" is not a vowel, so {@code isVowel} must return false.
     */
    @Test
    final void isVowel_returnsFalse_forLowercaseConsonantD() {
        final MatchRatingApproachEncoder encoder = getStringEncoder();

        final boolean result = encoder.isVowel("d");

        assertFalse(result, "Lowercase consonant \"d\" should not be considered a vowel");
    }
}
