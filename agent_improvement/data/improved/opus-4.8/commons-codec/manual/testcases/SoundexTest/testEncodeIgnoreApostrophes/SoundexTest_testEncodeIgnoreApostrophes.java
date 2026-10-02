package org.apache.commons.codec.language;

import org.apache.commons.codec.AbstractStringEncoderTest;
import org.apache.commons.codec.EncoderException;
import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link Soundex} ignores apostrophes regardless of where they
 * appear in the input.
 *
 * <p>The name "OBrien" encodes to the Soundex code {@code "O165"}. Inserting an
 * apostrophe at any position (leading, internal, or trailing) must not change
 * that result, because apostrophes are non-letter characters that Soundex strips
 * out before encoding.</p>
 */
public class SoundexTest_testEncodeIgnoreApostrophes extends AbstractStringEncoderTest<Soundex> {

    @Override
    protected Soundex createStringEncoder() {
        return new Soundex();
    }

    @Test
    void testEncodeIgnoreApostrophes() throws EncoderException {
        // Every spelling below is "OBrien" with an apostrophe at a different
        // position; all must encode to the same Soundex code.
        final String expectedSoundexCode = "O165";

        checkEncodingVariations(expectedSoundexCode,
                "OBrien",   // no apostrophe (baseline)
                "'OBrien",  // leading apostrophe
                "O'Brien",  // after the 1st letter
                "OB'rien",  // after the 2nd letter
                "OBr'ien",  // after the 3rd letter
                "OBri'en",  // after the 4th letter
                "OBrie'n",  // after the 5th letter
                "OBrien'"); // trailing apostrophe
    }
}
