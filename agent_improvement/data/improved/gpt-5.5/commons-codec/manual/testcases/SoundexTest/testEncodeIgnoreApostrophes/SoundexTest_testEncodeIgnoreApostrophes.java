package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.commons.codec.EncoderException;
import org.junit.jupiter.api.Test;

public class SoundexTest_testEncodeIgnoreApostrophes {

    protected Soundex createStringEncoder() {
        return new Soundex();
    }

    private void checkEncodingVariations(final String expectedEncoding, final String... names) {
        final Soundex soundex = createStringEncoder();
        for (final String name : names) {
            assertEquals(expectedEncoding, soundex.encode(name));
        }
    }

    @Test
    void testEncodeIgnoreApostrophes() throws EncoderException {
        checkEncodingVariations("O165",
                "OBrien",
                "'OBrien",
                "O'Brien",
                "OB'rien",
                "OBr'ien",
                "OBri'en",
                "OBrie'n",
                "OBrien'");
    }
}
