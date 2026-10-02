package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

/**
 * Tests the US_ENGLISH_GENEALOGY variant of Soundex encoding.
 *
 * Unlike the standard US_ENGLISH variant, this variant treats vowels (A, E, I, O, U, Y),
 * H, and W as completely silent letters — they are ignored and do NOT act as separators
 * between consonants with the same code. This produces encodings consistent with the
 * genealogy research rules described at:
 * http://www.genealogy.com/articles/research/00000060.html
 */
public class SoundexTest_testGenealogy extends AbstractStringEncoderTest<Soundex> {

    @Override
    protected Soundex createStringEncoder() {
        return new Soundex();
    }

    @Test
    void testGenealogy() {
        final Soundex genealogySoundex = Soundex.US_ENGLISH_GENEALOGY;

        // --- Examples taken directly from the genealogy.com reference ---
        // These are the canonical cases from the algorithm's source documentation.
        assertEquals("H251", genealogySoundex.encode("Heggenburger"));
        assertEquals("B425", genealogySoundex.encode("Blackman"));
        assertEquals("S530", genealogySoundex.encode("Schmidt"));
        assertEquals("L150", genealogySoundex.encode("Lippmann"));

        // --- Vowels are silent (not separators) ---
        // In standard Soundex, a vowel between two consonants with the same code keeps
        // both consonants encoded. In the genealogy variant, the 'o' in "Dodds" is silent,
        // so both 'd' sounds collapse to a single code: D200.
        assertEquals("D200", genealogySoundex.encode("Dodds"));

        // --- 'H' is silent (not a separator) ---
        // The 'h' between the two 'd' sounds in "Dhdds" is ignored entirely,
        // causing the duplicate 'd' codes to collapse: D200.
        assertEquals("D200", genealogySoundex.encode("Dhdds"));

        // --- 'W' is silent (not a separator) ---
        // The 'w' between the two 'd' sounds in "Dwdds" is ignored entirely,
        // causing the duplicate 'd' codes to collapse: D200.
        assertEquals("D200", genealogySoundex.encode("Dwdds"));
    }
}
