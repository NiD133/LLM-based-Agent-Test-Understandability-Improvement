package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class SoundexTest_testEncodeBatch4 extends AbstractStringEncoderTest<Soundex> {

    @Override
    protected Soundex createStringEncoder() {
        return new Soundex();
    }

    /**
     * Verifies Soundex encoding for a representative batch of names taken from
     * http://www.myatt.demon.co.uk/sxalg.htm.
     *
     * <p>A Soundex code is always four characters: the initial letter of the name
     * followed by three digits that represent phonetically similar consonant groups.
     * Shorter names are padded with zeros (e.g. "BALL" → "B400").</p>
     *
     * @param name         the input name to encode
     * @param expectedCode the expected four-character Soundex code
     */
    @ParameterizedTest(name = "{0} -> {1}")
    @CsvSource({
        "HOLMES,     H452",
        "ADOMOMI,    A355",
        "VONDERLEHR, V536",
        "BALL,       B400",
        "SHAW,       S000",
        "JACKSON,    J250",
        "SCANLON,    S545",
        "SAINTJOHN,  S532"
    })
    void testEncodeBatch4(String name, String expectedCode) {
        assertEquals(expectedCode.trim(), getStringEncoder().encode(name.trim()));
    }
}
