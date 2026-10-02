package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

public class RefinedSoundexTest_testInvalidSoundexCharacter extends AbstractStringEncoderTest<RefinedSoundex> {

    @Override
    protected RefinedSoundex createStringEncoder() {
        return new RefinedSoundex();
    }

    /**
     * Encodes a string containing every char in the first 256 code points
     * (0x00..0xFF). Non-letters are ignored by the algorithm, so the result is
     * driven solely by the Latin letters {@code A-Z} and {@code a-z} present in
     * that range. This verifies that the encoder safely skips invalid (non-letter)
     * characters instead of failing on them.
     */
    @Test
    void testInvalidSoundexCharacter() {
        // Build a string holding every character from code point 0 through 255.
        final char[] allFirst256Chars = new char[256];
        for (int codePoint = 0; codePoint < allFirst256Chars.length; codePoint++) {
            allFirst256Chars[codePoint] = (char) codePoint;
        }
        final String input = new String(allFirst256Chars);

        // Expected Refined Soundex code for the letters A-Z then a-z found in the input.
        final String expectedEncoding = "A0136024043780159360205050136024043780159360205053";

        assertEquals(new RefinedSoundex().encode(input), expectedEncoding);
    }
}
