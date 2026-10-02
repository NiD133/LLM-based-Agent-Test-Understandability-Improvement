package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.apache.commons.codec.AbstractStringEncoderTest;
import org.apache.commons.codec.EncoderException;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link RefinedSoundex#encode(String)}.
 *
 * <p>RefinedSoundex encodes a word to a phonetic key: the initial letter followed
 * by digit codes for consonant sounds, with adjacent duplicates collapsed.
 * The encoding is case-insensitive, so "testing" and "TESTING" produce the same key.
 *
 * <p>Also verifies that the pre-built {@link RefinedSoundex#US_ENGLISH} static
 * instance produces the same output as a freshly constructed encoder (regression
 * for CODEC-56).
 */
public class RefinedSoundexTest_testEncode extends AbstractStringEncoderTest<RefinedSoundex> {

    @Override
    protected RefinedSoundex createStringEncoder() {
        return new RefinedSoundex();
    }

    /**
     * Verifies that the encoding is case-insensitive: the same word in lower-case
     * and upper-case must produce identical Refined Soundex codes.
     */
    @Test
    void testEncode_caseInsensitivity() {
        String expectedCode = "T6036084";
        assertEquals(expectedCode, getStringEncoder().encode("testing"),
                "lower-case 'testing' should encode to " + expectedCode);
        assertEquals(expectedCode, getStringEncoder().encode("TESTING"),
                "upper-case 'TESTING' should produce the same code as 'testing'");
    }

    /**
     * Verifies Refined Soundex codes for every word in the classic pangram
     * "The quick brown fox jumped over the lazy dogs".
     *
     * <p>Each assertion message names the input word and the expected code so that
     * a failure pinpoints exactly which word regressed.
     */
    @Test
    void testEncode_commonEnglishWords() {
        RefinedSoundex encoder = getStringEncoder();

        assertEquals("T60",     encoder.encode("The"),    "'The'    should encode to T60");
        assertEquals("Q503",    encoder.encode("quick"),  "'quick'  should encode to Q503");
        assertEquals("B1908",   encoder.encode("brown"),  "'brown'  should encode to B1908");
        assertEquals("F205",    encoder.encode("fox"),    "'fox'    should encode to F205");
        assertEquals("J408106", encoder.encode("jumped"), "'jumped' should encode to J408106");
        assertEquals("O0209",   encoder.encode("over"),   "'over'   should encode to O0209");
        assertEquals("T60",     encoder.encode("the"),    "'the'    should encode to T60");
        assertEquals("L7050",   encoder.encode("lazy"),   "'lazy'   should encode to L7050");
        assertEquals("D6043",   encoder.encode("dogs"),   "'dogs'   should encode to D6043");
    }

    /**
     * Regression test for CODEC-56: the shared {@link RefinedSoundex#US_ENGLISH}
     * static instance must produce the same result as a per-test instance.
     */
    @Test
    void testEncode_staticUsEnglishInstanceMatchesDefaultInstance() {
        assertEquals("D6043", RefinedSoundex.US_ENGLISH.encode("dogs"),
                "US_ENGLISH static instance should encode 'dogs' identically to a default RefinedSoundex instance");
    }
}
