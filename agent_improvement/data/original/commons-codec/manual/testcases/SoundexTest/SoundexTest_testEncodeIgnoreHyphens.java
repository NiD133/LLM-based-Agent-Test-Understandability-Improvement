package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.apache.commons.codec.AbstractStringEncoderTest;
import org.apache.commons.codec.EncoderException;
import org.junit.jupiter.api.Test;

public class SoundexTest_testEncodeIgnoreHyphens extends AbstractStringEncoderTest<Soundex> {

    @Override
    protected Soundex createStringEncoder() {
        return new Soundex();
    }

    /**
     * Test data from http://www.myatt.demon.co.uk/sxalg.htm
     *
     * @throws EncoderException for some failure scenarios
     */
    @Test
    void testEncodeIgnoreHyphens() throws EncoderException {
        // @formatter:off
        checkEncodingVariations("K525", "KINGSMITH", "-KINGSMITH", "K-INGSMITH", "KI-NGSMITH", "KIN-GSMITH", "KING-SMITH", "KINGS-MITH", "KINGSM-ITH", "KINGSMI-TH", "KINGSMIT-H", "KINGSMITH-");
        // @formatter:on
    }
}
