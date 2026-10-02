package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.apache.commons.codec.AbstractStringEncoderTest;
import org.apache.commons.codec.EncoderException;
import org.junit.jupiter.api.Test;

public class SoundexTest_testMsSqlServer2 extends AbstractStringEncoderTest<Soundex> {

    @Override
    protected Soundex createStringEncoder() {
        return new Soundex();
    }

    /**
     * Verifies that common spelling variations of "Erickson" all produce the same
     * Soundex code "E625", as described in Microsoft SQL Server Soundex examples.
     *
     * <p>The code "E625" breaks down as:
     * <ul>
     *   <li>E - first letter preserved</li>
     *   <li>6 - R sound</li>
     *   <li>2 - C/K sound (C, K, and CK are phonetically equivalent here)</li>
     *   <li>5 - N sound (both "-son" and "-sen" endings map to the same code)</li>
     * </ul>
     *
     * @throws EncoderException if encoding fails
     */
    @Test
    void testMsSqlServer2() throws EncoderException {
        // All variants are phonetically equivalent spellings of the surname "Erickson"
        final String expectedSoundexCode = "E625";
        checkEncodingVariations(expectedSoundexCode,
                "Erickson", "Erickson", "Erikson", "Ericson", "Ericksen", "Ericsen");
    }
}
