package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class SoundexTest_testEncodeBatch2 extends AbstractStringEncoderTest<Soundex> {

    @Override
    protected Soundex createStringEncoder() {
        return new Soundex();
    }

    /**
     * Verifies Soundex encoding for a batch of names sourced from
     * http://www.bradandkathy.com/genealogy/overviewofsoundex.html
     *
     * Each row is: expectedCode, inputName
     */
    @ParameterizedTest(name = "encode(\"{1}\") == \"{0}\"")
    @CsvSource({
        "A462, Allricht",
        "E166, Eberhard",
        "E521, Engebrethson",
        "H512, Heimbach",
        "H524, Hanselmann",
        "H431, Hildebrand",
        "K152, Kavanagh",
        "L530, Lind",
        "L222, Lukaschowsky",
        "M235, McDonnell",
        "M200, McGee",
        "O155, Opnian",
        "O155, Oppenheimer",
        "R355, Riedemanas",
        "Z300, Zita",
        "Z325, Zitzmeinn"
    })
    void testEncodeBatch2(String expectedCode, String name) {
        assertEquals(expectedCode, getStringEncoder().encode(name));
    }
}
