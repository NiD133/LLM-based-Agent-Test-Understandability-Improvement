package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class MatchRatingApproachEncoderTest_testVowelAndPunctuationOnly {

    private static final String VOWELS_AND_PUNCTUATION_ONLY = "uoiea.,-AEIOU";
    private static final String ENCODED_INITIAL_VOWEL = "U";

    @Test
    final void testVowelAndPunctuationOnly() {
        final String encodedValue = new MatchRatingApproachEncoder().encode(VOWELS_AND_PUNCTUATION_ONLY);

        assertEquals(encodedValue, ENCODED_INITIAL_VOWEL);
    }
}
