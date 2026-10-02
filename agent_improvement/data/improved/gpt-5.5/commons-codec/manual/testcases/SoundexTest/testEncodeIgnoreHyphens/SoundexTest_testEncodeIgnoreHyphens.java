package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.commons.codec.EncoderException;
import org.junit.jupiter.api.Test;

public class SoundexTest_testEncodeIgnoreHyphens {

    private static final String KINGSMITH_SOUNDEX = "K525";

    protected Soundex createStringEncoder() {
        return new Soundex();
    }

    private void checkEncodingVariations(final String expectedEncoding, final String... names) {
        final Soundex soundex = createStringEncoder();
        for (final String name : names) {
            assertEquals(expectedEncoding, soundex.encode(name));
        }
    }

    /**
     * Test data from http://www.myatt.demon.co.uk/sxalg.htm
     *
     * @throws EncoderException for some failure scenarios
     */
    @Test
    void testEncodeIgnoreHyphens() throws EncoderException {
        checkEncodingVariations(
                KINGSMITH_SOUNDEX,
                "KINGSMITH",
                "-KINGSMITH",
                "K-INGSMITH",
                "KI-NGSMITH",
                "KIN-GSMITH",
                "KING-SMITH",
                "KINGS-MITH",
                "KINGSM-ITH",
                "KINGSMI-TH",
                "KINGSMIT-H",
                "KINGSMITH-");
    }
}
