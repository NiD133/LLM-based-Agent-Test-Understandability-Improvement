package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

/**
 * Tests {@link MatchRatingApproachEncoder} with an input consisting solely of
 * vowels and punctuation.
 */
public class MatchRatingApproachEncoderTest_testVowelAndPunctuationOnly
        extends AbstractStringEncoderTest<MatchRatingApproachEncoder> {

    @Override
    protected MatchRatingApproachEncoder createStringEncoder() {
        return new MatchRatingApproachEncoder();
    }

    /**
     * The encoder strips punctuation and removes vowels, keeping a leading vowel
     * only. For "uoiea.,-AEIOU" the punctuation ('.', ',', '-') is dropped and
     * every vowel is removed except the very first one, leaving only "U".
     */
    @Test
    final void testVowelAndPunctuationOnly() {
        final String vowelsAndPunctuation = "uoiea.,-AEIOU";
        final String expectedCode = "U";

        assertEquals(getStringEncoder().encode(vowelsAndPunctuation), expectedCode);
    }
}
