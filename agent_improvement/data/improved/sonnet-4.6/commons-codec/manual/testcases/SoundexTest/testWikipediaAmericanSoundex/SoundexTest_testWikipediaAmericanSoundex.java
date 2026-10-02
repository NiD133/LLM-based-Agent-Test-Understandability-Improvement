package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

/**
 * Verifies the American Soundex encoding against examples from Wikipedia
 * (https://en.wikipedia.org/wiki/Soundex#American_Soundex, snapshot 2015-03-22).
 *
 * <p>The Wikipedia table shows that phonetically similar names (e.g. "Robert"
 * and "Rupert") are expected to produce the same four-character Soundex code.
 * Rows that share an expected code illustrate this equivalence.</p>
 */
public class SoundexTest_testWikipediaAmericanSoundex extends AbstractStringEncoderTest<Soundex> {

    @Override
    protected Soundex createStringEncoder() {
        return new Soundex();
    }

    /**
     * Each row is: (name, expectedSoundexCode).
     *
     * <ul>
     *   <li>"Robert" and "Rupert" are phonetically close and share R163.</li>
     *   <li>"Ashcraft" and "Ashcroft" differ only in a silent vowel and share A261.</li>
     *   <li>"Tymczak" encodes to T522 (the repeated '2' in "zak" is collapsed to one digit).</li>
     *   <li>"Pfister" encodes to P236 (the silent 'f' after 'P' is skipped).</li>
     * </ul>
     */
    @ParameterizedTest(name = "soundex(\"{0}\") == \"{1}\"")
    @CsvSource({
        "Robert,   R163",
        "Rupert,   R163",
        "Ashcraft, A261",
        "Ashcroft, A261",
        "Tymczak,  T522",
        "Pfister,  P236"
    })
    void testWikipediaAmericanSoundex(final String name, final String expectedCode) {
        assertEquals(expectedCode.trim(), getStringEncoder().encode(name),
                "Soundex code for \"" + name + "\"");
    }
}
