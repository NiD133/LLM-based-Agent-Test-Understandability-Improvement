package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

/**
 * Tests {@link Soundex#US_ENGLISH_GENEALOGY}, the Genealogy variant of Soundex.
 *
 * <p>In this variant the vowels (AEIOUY) as well as H and W are treated as
 * <em>silent</em> letters: after the first character they are ignored entirely
 * and never act as separators between consonants sharing the same Soundex code.</p>
 *
 * <p>Examples and algorithm rules are taken from
 * http://www.genealogy.com/articles/research/00000060.html</p>
 */
public class SoundexTest_testGenealogy extends AbstractStringEncoderTest<Soundex> {

    /** The Soundex variant under test: vowels and H/W are silent. */
    private static final Soundex GENEALOGY = Soundex.US_ENGLISH_GENEALOGY;

    @Override
    protected Soundex createStringEncoder() {
        return new Soundex();
    }

    @Test
    void testGenealogy() {
        // Reference examples from the genealogy.com article.
        assertEquals("H251", GENEALOGY.encode("Heggenburger"));
        assertEquals("B425", GENEALOGY.encode("Blackman"));
        assertEquals("S530", GENEALOGY.encode("Schmidt"));
        assertEquals("L150", GENEALOGY.encode("Lippmann"));

        // Additional cases showing that a silent letter does NOT separate the two
        // 'D's (code 3); all three words therefore encode identically to "D200".
        assertEquals("D200", GENEALOGY.encode("Dodds")); // 'o' is a silent vowel
        assertEquals("D200", GENEALOGY.encode("Dhdds")); // 'h' is silent
        assertEquals("D200", GENEALOGY.encode("Dwdds")); // 'w' is silent
    }
}
