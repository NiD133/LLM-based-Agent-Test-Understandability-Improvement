package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link Soundex} ignores characters that are not letters
 * when computing a Soundex code, rather than failing on them.
 */
public class SoundexTest_testBadCharacters extends AbstractStringEncoderTest<Soundex> {

    @Override
    protected Soundex createStringEncoder() {
        return new Soundex();
    }

    @Test
    void testBadCharacters() {
        // The '>' is a non-letter ("bad") character. It should be stripped out,
        // so "HOL>MES" must encode identically to the cleaned name "HOLMES".
        final String nameWithBadCharacter = "HOL>MES";
        final String expectedSoundexCode = "H452";

        final String actualSoundexCode = getStringEncoder().encode(nameWithBadCharacter);

        assertEquals(expectedSoundexCode, actualSoundexCode);
    }
}
