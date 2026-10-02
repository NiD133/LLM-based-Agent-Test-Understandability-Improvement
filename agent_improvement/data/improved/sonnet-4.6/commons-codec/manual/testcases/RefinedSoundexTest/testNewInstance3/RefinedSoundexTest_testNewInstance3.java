package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

public class RefinedSoundexTest_testNewInstance3 extends AbstractStringEncoderTest<RefinedSoundex> {

    @Override
    protected RefinedSoundex createStringEncoder() {
        return new RefinedSoundex();
    }

    /**
     * Verifies that a RefinedSoundex instance constructed with the standard
     * US-English mapping string produces the correct Refined Soundex code
     * for the word "dogs".
     *
     * Expected encoding of "dogs":
     *   D -> D  (first letter, kept as-is)
     *   o -> 0  (vowel group)
     *   g -> 4  (G/J group)
     *   s -> 3  (C/K/S group)
     * Result: "D6043"
     * Note: 'd' maps to code '6' but the first character is always preserved
     * as the literal letter, so the code appended for 'd' (6) follows 'D'.
     */
    @Test
    void testNewInstance3() {
        // Construct encoder explicitly using the standard US-English mapping string
        RefinedSoundex encoder = new RefinedSoundex(RefinedSoundex.US_ENGLISH_MAPPING_STRING);

        String input = "dogs";
        String expectedCode = "D6043";

        assertEquals(expectedCode, encoder.soundex(input));
    }
}
