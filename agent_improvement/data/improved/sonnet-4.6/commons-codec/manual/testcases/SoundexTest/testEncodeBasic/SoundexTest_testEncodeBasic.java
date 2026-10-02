package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

/**
 * Verifies that {@link Soundex#encode(String)} produces the correct 4-character
 * Soundex code for a representative set of common English words.
 *
 * <p>The Soundex format is: first-letter + three digits (e.g. "T235").
 * Words that share the same Soundex code are considered phonetically similar.</p>
 */
public class SoundexTest_testEncodeBasic extends AbstractStringEncoderTest<Soundex> {

    @Override
    protected Soundex createStringEncoder() {
        return new Soundex();
    }

    /**
     * Each row specifies an input word and its expected Soundex code.
     * The words are drawn from the classic "quick brown fox" pangram to cover
     * a broad range of consonant groups and initial letters.
     */
    @ParameterizedTest(name = "encode(\"{0}\") => \"{1}\"")
    @CsvSource({
        "testing, T235",
        "The,     T000",
        "quick,   Q200",
        "brown,   B650",
        "fox,     F200",
        "jumped,  J513",
        "over,    O160",
        "the,     T000",
        "lazy,    L200",
        "dogs,    D200"
    })
    void testEncodeBasic(String word, String expectedCode) {
        assertEquals(expectedCode, getStringEncoder().encode(word),
                "Soundex code for '" + word + "'");
    }
}
