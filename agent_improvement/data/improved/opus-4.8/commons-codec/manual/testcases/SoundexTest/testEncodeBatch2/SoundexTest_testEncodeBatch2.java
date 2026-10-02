package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

/**
 * Verifies that the default US-English {@link Soundex} encoder produces the
 * expected four-character codes for a batch of sample surnames.
 *
 * <p>The name/code pairs are taken from the worked examples at
 * http://www.bradandkathy.com/genealogy/overviewofsoundex.html</p>
 */
public class SoundexTest_testEncodeBatch2 extends AbstractStringEncoderTest<Soundex> {

    @Override
    protected Soundex createStringEncoder() {
        return new Soundex();
    }

    /**
     * Each row supplies a surname together with the Soundex code it should
     * encode to. Encoding the surname must yield exactly that code.
     */
    @ParameterizedTest(name = "encode(\"{0}\") -> \"{1}\"")
    @CsvSource({
        "Allricht,     A462",
        "Eberhard,     E166",
        "Engebrethson, E521",
        "Heimbach,     H512",
        "Hanselmann,   H524",
        "Hildebrand,   H431",
        "Kavanagh,     K152",
        "Lind,         L530",
        "Lukaschowsky, L222",
        "McDonnell,    M235",
        "McGee,        M200",
        "Opnian,       O155",
        "Oppenheimer,  O155",
        "Riedemanas,   R355",
        "Zita,         Z300",
        "Zitzmeinn,    Z325",
    })
    void encodesSurnameToExpectedSoundexCode(final String surname, final String expectedCode) {
        assertEquals(expectedCode, getStringEncoder().encode(surname));
    }
}
