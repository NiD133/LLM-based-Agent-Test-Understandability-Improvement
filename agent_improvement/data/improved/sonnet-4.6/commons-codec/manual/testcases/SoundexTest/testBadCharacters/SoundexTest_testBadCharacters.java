package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

public class SoundexTest_testBadCharacters extends AbstractStringEncoderTest<Soundex> {

    @Override
    protected Soundex createStringEncoder() {
        return new Soundex();
    }

    /**
     * Verifies that non-alphabetic characters embedded in the input are silently
     * stripped before Soundex encoding.  "HOL>MES" is cleaned to "HOLMES" first,
     * so the result is the same as encoding "HOLMES" → "H452".
     */
    @Test
    void testBadCharacters() {
        // '>' is not an alphabetic character and is removed by SoundexUtils.clean()
        // before encoding, making "HOL>MES" equivalent to "HOLMES".
        String inputWithBadChar  = "HOL>MES";
        String expectedSoundex   = "H452";   // Soundex of "HOLMES"

        assertEquals(expectedSoundex, getStringEncoder().encode(inputWithBadChar));
    }
}
