package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class RefinedSoundexTest_testInvalidSoundexCharacter {

    private static final int BYTE_SIZED_CHARACTER_COUNT = 256;
    private static final String EXPECTED_ENCODING_FOR_BYTE_SIZED_CHARACTERS =
            "A0136024043780159360205050136024043780159360205053";

    protected RefinedSoundex createStringEncoder() {
        return new RefinedSoundex();
    }

    @Test
    void testInvalidSoundexCharacter() {
        final char[] byteSizedCharacters = new char[BYTE_SIZED_CHARACTER_COUNT];
        for (int codePoint = 0; codePoint < byteSizedCharacters.length; codePoint++) {
            byteSizedCharacters[codePoint] = (char) codePoint;
        }

        final String charactersToEncode = new String(byteSizedCharacters);

        assertEquals(
                new RefinedSoundex().encode(charactersToEncode),
                EXPECTED_ENCODING_FOR_BYTE_SIZED_CHARACTERS);
    }
}
