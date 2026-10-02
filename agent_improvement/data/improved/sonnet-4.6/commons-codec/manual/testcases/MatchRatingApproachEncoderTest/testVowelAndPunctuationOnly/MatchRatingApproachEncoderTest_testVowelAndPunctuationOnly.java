package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

public class MatchRatingApproachEncoderTest_testVowelAndPunctuationOnly extends AbstractStringEncoderTest<MatchRatingApproachEncoder> {

    @Override
    protected MatchRatingApproachEncoder createStringEncoder() {
        return new MatchRatingApproachEncoder();
    }

    /**
     * Verifies the MRA encoding of a string that contains only vowels and punctuation.
     *
     * The MRA algorithm:
     *   1. Uppercases the input:             "uoiea.,-AEIOU"  ->  "UOIEA.,-AEIOU"
     *   2. Strips punctuation (. , -):       "UOIEA.,-AEIOU"  ->  "UOIEAAEIOU"
     *   3. Keeps the leading vowel but
     *      removes every other vowel:        "UOIEAAEIOU"     ->  "U"  (only the first 'U' is kept)
     *
     * Expected result: "U"
     */
    @Test
    final void testVowelAndPunctuationOnly() {
        String inputWithVowelsAndPunctuation = "uoiea.,-AEIOU";
        String expectedEncoding = "U"; // only the initial vowel survives encoding

        assertEquals(expectedEncoding, getStringEncoder().encode(inputWithVowelsAndPunctuation));
    }
}
