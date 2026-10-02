package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.commons.codec.AbstractStringEncoderTest;
import org.apache.commons.codec.EncoderException;
import org.junit.jupiter.api.Test;

/**
 * Tests {@link RefinedSoundex#encode(String)}: each input word must map to its
 * expected Refined Soundex code.
 */
public class RefinedSoundexTest_testEncode extends AbstractStringEncoderTest<RefinedSoundex> {

    @Override
    protected RefinedSoundex createStringEncoder() {
        return new RefinedSoundex();
    }

    @Test
    void testEncode() {
        final RefinedSoundex encoder = getStringEncoder();

        // Encoding is case-insensitive: lower- and upper-case "testing" share a code.
        assertEquals("T6036084", encoder.encode("testing"));
        assertEquals("T6036084", encoder.encode("TESTING"));

        // Each word of "The quick brown fox jumped over the lazy dogs".
        assertEquals("T60", encoder.encode("The"));
        assertEquals("Q503", encoder.encode("quick"));
        assertEquals("B1908", encoder.encode("brown"));
        assertEquals("F205", encoder.encode("fox"));
        assertEquals("J408106", encoder.encode("jumped"));
        assertEquals("O0209", encoder.encode("over"));
        assertEquals("T60", encoder.encode("the"));
        assertEquals("L7050", encoder.encode("lazy"));
        assertEquals("D6043", encoder.encode("dogs"));

        // CODEC-56: the shared US_ENGLISH instance encodes identically to a new instance.
        assertEquals("D6043", RefinedSoundex.US_ENGLISH.encode("dogs"));
    }
}
