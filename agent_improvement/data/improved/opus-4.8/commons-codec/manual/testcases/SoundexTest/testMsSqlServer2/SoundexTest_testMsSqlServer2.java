package org.apache.commons.codec.language;

import org.apache.commons.codec.AbstractStringEncoderTest;
import org.apache.commons.codec.EncoderException;
import org.junit.jupiter.api.Test;

/**
 * Verifies that the default US-English {@link Soundex} encoder matches the
 * behavior documented by Microsoft SQL Server for its {@code SOUNDEX} function.
 */
public class SoundexTest_testMsSqlServer2 extends AbstractStringEncoderTest<Soundex> {

    @Override
    protected Soundex createStringEncoder() {
        return new Soundex();
    }

    /**
     * All common spelling variations of the surname "Erickson" should encode to
     * the same Soundex code "E625".
     * <p>
     * This mirrors the example published for the MS SQL Server {@code SOUNDEX}
     * function (Microsoft Knowledge Base article Q100365).
     * </p>
     *
     * @throws EncoderException if encoding any of the spellings fails
     */
    @Test
    void erickson_spellingVariations_allEncodeToE625() throws EncoderException {
        final String expectedSoundexCode = "E625";
        // Every spelling below is expected to produce the code above.
        checkEncodingVariations(
                expectedSoundexCode,
                "Erickson",
                "Erickson",
                "Erikson",
                "Ericson",
                "Ericksen",
                "Ericsen");
    }
}
