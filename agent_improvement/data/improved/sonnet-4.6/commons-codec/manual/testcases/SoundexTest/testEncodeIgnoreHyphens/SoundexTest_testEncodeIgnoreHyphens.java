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
     * Verifies that hyphens are ignored during Soundex encoding, regardless of where they appear.
     * All variants of "KINGSMITH" — with a hyphen at each possible position, including at the
     * start and end — must produce the same Soundex code "K525".
     *
     * <p>Test data from http://www.myatt.demon.co.uk/sxalg.htm
     *
     * @throws EncoderException for some failure scenarios
     */
    @Test
    void testEncodeIgnoreHyphens() throws EncoderException {
        final String expectedCode = "K525";

        // Every position a hyphen can occupy in "KINGSMITH", including boundaries
        final String[] kingsmithWithHyphenAtEachPosition = {
            "KINGSMITH",   // no hyphen (baseline)
            "-KINGSMITH",  // hyphen before first letter
            "K-INGSMITH",  // hyphen after K
            "KI-NGSMITH",  // hyphen after KI
            "KIN-GSMITH",  // hyphen after KIN
            "KING-SMITH",  // hyphen after KING
            "KINGS-MITH",  // hyphen after KINGS
            "KINGSM-ITH",  // hyphen after KINGSM
            "KINGSMI-TH",  // hyphen after KINGSMI
            "KINGSMIT-H",  // hyphen after KINGSMIT
            "KINGSMITH-",  // hyphen after last letter
        };

        checkEncodingVariations(expectedCode, kingsmithWithHyphenAtEachPosition);
    }
}
