package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

/**
 * Verifies the default {@link Soundex} encoder against the worked examples published in the
 * "American Soundex" section of the Wikipedia article on Soundex
 * (https://en.wikipedia.org/wiki/Soundex#American_Soundex, as of 2015-03-22).
 */
public class SoundexTest_testWikipediaAmericanSoundex extends AbstractStringEncoderTest<Soundex> {

    @Override
    protected Soundex createStringEncoder() {
        return new Soundex();
    }

    /**
     * Each input name is encoded with the default US-English Soundex and compared to the
     * four-character code listed in the Wikipedia examples. The chosen names exercise notable
     * rules of the algorithm:
     * <ul>
     *   <li>{@code Robert} / {@code Rupert} - different names that share the same code.</li>
     *   <li>{@code Ashcraft} / {@code Ashcroft} - adjacent letters mapping to the same digit are
     *       collapsed.</li>
     *   <li>{@code Tymczak} - H and W (here the silent letters) are skipped without separating
     *       duplicate codes.</li>
     *   <li>{@code Pfister} - the leading consonant cluster keeps only the first letter.</li>
     * </ul>
     */
    @Test
    void testWikipediaAmericanSoundex() {
        final Soundex soundex = getStringEncoder();

        assertEquals("R163", soundex.encode("Robert"));
        assertEquals("R163", soundex.encode("Rupert"));
        assertEquals("A261", soundex.encode("Ashcraft"));
        assertEquals("A261", soundex.encode("Ashcroft"));
        assertEquals("T522", soundex.encode("Tymczak"));
        assertEquals("P236", soundex.encode("Pfister"));
    }
}
