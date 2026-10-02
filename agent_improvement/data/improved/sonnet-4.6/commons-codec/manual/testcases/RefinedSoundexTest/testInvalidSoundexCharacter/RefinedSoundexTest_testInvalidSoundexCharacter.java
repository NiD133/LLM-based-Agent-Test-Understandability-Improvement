package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

/**
 * Verifies that RefinedSoundex gracefully handles the full range of ASCII
 * characters (0–255), including non-alphabetic and control characters that
 * are not valid Soundex input. Non-letter characters must be silently skipped
 * rather than causing an exception or corrupting the output.
 */
public class RefinedSoundexTest_testInvalidSoundexCharacter extends AbstractStringEncoderTest<RefinedSoundex> {

    @Override
    protected RefinedSoundex createStringEncoder() {
        return new RefinedSoundex();
    }

    /**
     * Encodes a string that contains every character from ASCII 0 to 255,
     * including characters that have no Soundex mapping (digits, punctuation,
     * control characters). The expected value captures only the letters that
     * appear in that range; all other characters must be ignored silently.
     */
    @Test
    void testInvalidSoundexCharacter() {
        // Build a string containing every possible ASCII/Latin-1 character (0–255).
        // Characters outside A–Z are not valid Soundex input and must be skipped.
        char[] allAsciiAndLatin1Chars = new char[256];
        for (int i = 0; i < allAsciiAndLatin1Chars.length; i++) {
            allAsciiAndLatin1Chars[i] = (char) i;
        }
        String inputWithAllCharacters = new String(allAsciiAndLatin1Chars);

        // The expected output reflects only the alphabetic letters found in the
        // 0–255 range (A–Z appear twice: once in 65–90, once in 97–122), with
        // consecutive duplicate Soundex codes collapsed per the Soundex rules.
        String expectedSoundexCode = "A0136024043780159360205050136024043780159360205053";

        assertEquals(expectedSoundexCode, new RefinedSoundex().encode(inputWithAllCharacters));
    }
}
