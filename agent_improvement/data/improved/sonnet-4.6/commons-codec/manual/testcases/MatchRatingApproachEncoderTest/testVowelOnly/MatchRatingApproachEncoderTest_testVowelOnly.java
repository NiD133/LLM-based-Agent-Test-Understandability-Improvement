package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

public class MatchRatingApproachEncoderTest_testVowelOnly extends AbstractStringEncoderTest<MatchRatingApproachEncoder> {

    @Override
    protected MatchRatingApproachEncoder createStringEncoder() {
        return new MatchRatingApproachEncoder();
    }

    /**
     * Verifies that a string composed entirely of vowels encodes to just "A".
     *
     * The MRA algorithm uppercases the input, then removes all vowels except the one
     * that begins the word. Since every character in "aeiouAEIOU" is a vowel, only the
     * leading "A" survives the vowel-removal step, producing the single-character code "A".
     */
    @Test
    final void testVowelOnly() {
        MatchRatingApproachEncoder encoder = getStringEncoder();

        String allVowels = "aeiouAEIOU";
        String expectedCode = "A";

        assertEquals(encoder.encode(allVowels), expectedCode);
    }
}
