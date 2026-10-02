package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.apache.commons.codec.AbstractStringEncoderTest;
import org.apache.commons.codec.EncoderException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class SoundexTest_testEncodeIgnoreApostrophes extends AbstractStringEncoderTest<Soundex> {

    @Override
    protected Soundex createStringEncoder() {
        return new Soundex();
    }

    /**
     * Soundex strips non-alphabetic characters (including apostrophes) before encoding,
     * so "O'Brien" and all variants with an apostrophe at any position must produce
     * the same code as the base form "OBrien" → "O165" (O=first letter, 1=B, 6=R, 5=N).
     */
    @Test
    @DisplayName("Apostrophes at any position in a name are ignored during Soundex encoding")
    void testEncodeIgnoreApostrophes() throws EncoderException {
        final String expectedCode = "O165"; // OBrien: O(keep) B→1, R→6, N→5

        // Each string is "OBrien" with an apostrophe inserted at a different position,
        // plus the base form without any apostrophe.
        final String[] variants = {
            "OBrien",   // no apostrophe (baseline)
            "'OBrien",  // apostrophe before first letter
            "O'Brien",  // apostrophe after first letter
            "OB'rien",  // apostrophe after second letter
            "OBr'ien",  // apostrophe after third letter
            "OBri'en",  // apostrophe after fourth letter
            "OBrie'n",  // apostrophe after fifth letter
            "OBrien'"   // apostrophe after last letter
        };

        checkEncodingVariations(expectedCode, variants);
    }
}
