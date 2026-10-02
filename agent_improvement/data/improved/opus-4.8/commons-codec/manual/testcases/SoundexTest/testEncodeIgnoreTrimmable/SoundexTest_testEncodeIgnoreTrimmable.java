package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link Soundex} ignores leading and trailing whitespace
 * (spaces, tabs, newlines and carriage returns) when encoding a word.
 */
public class SoundexTest_testEncodeIgnoreTrimmable extends AbstractStringEncoderTest<Soundex> {

    @Override
    protected Soundex createStringEncoder() {
        return new Soundex();
    }

    @Test
    void testEncodeIgnoreTrimmable() {
        // "Washington" is padded with surrounding whitespace characters.
        final String paddedWord = " \t\n\r Washington \t\n\r ";

        // The Soundex code for "Washington" is "W252"; the padding must not affect it.
        final String expectedSoundexCode = "W252";

        assertEquals(expectedSoundexCode, getStringEncoder().encode(paddedWord));
    }
}
