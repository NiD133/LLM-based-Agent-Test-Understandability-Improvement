package org.apache.commons.codec.language;

import org.apache.commons.codec.AbstractStringEncoderTest;
import org.apache.commons.codec.EncoderException;
import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link Soundex} treats the hyphen ('-') as a silent marker, so a hyphen
 * placed anywhere in a name does not change the resulting Soundex code.
 */
public class SoundexTest_testEncodeIgnoreHyphens extends AbstractStringEncoderTest<Soundex> {

    /** The expected Soundex code for "KINGSMITH" and every hyphenated variant of it. */
    private static final String EXPECTED_CODE = "K525";

    @Override
    protected Soundex createStringEncoder() {
        return new Soundex();
    }

    /**
     * "KINGSMITH" with a hyphen inserted at each possible position (and at both ends) must all
     * encode to the same code as the plain name, because hyphens are ignored.
     *
     * <p>Test data from http://www.myatt.demon.co.uk/sxalg.htm</p>
     *
     * @throws EncoderException if encoding any of the inputs fails
     */
    @Test
    void testEncodeIgnoreHyphens() throws EncoderException {
        // The same name with a hyphen sliding from the front, through every gap, to the end.
        checkEncodingVariations(EXPECTED_CODE,
                "KINGSMITH",   // baseline: no hyphen
                "-KINGSMITH",  // leading hyphen
                "K-INGSMITH",
                "KI-NGSMITH",
                "KIN-GSMITH",
                "KING-SMITH",
                "KINGS-MITH",
                "KINGSM-ITH",
                "KINGSMI-TH",
                "KINGSMIT-H",
                "KINGSMITH-"); // trailing hyphen
    }
}
