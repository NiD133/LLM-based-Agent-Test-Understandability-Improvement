package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class SoundexTest_testEncodeIgnoreTrimmable extends AbstractStringEncoderTest<Soundex> {

    @Override
    protected Soundex createStringEncoder() {
        return new Soundex();
    }

    @Test
    @DisplayName("Soundex encoder ignores leading and trailing whitespace (space, tab, newline, carriage return)")
    void testEncodeIgnoreTrimmable() {
        // "Washington" padded on both sides with space, tab, newline, and carriage return
        // should produce the same Soundex code as the plain word
        String inputWithSurroundingWhitespace = " \t\n\r Washington \t\n\r ";
        String expectedSoundexCode = "W252";

        assertEquals(expectedSoundexCode, getStringEncoder().encode(inputWithSurroundingWhitespace));
    }
}
