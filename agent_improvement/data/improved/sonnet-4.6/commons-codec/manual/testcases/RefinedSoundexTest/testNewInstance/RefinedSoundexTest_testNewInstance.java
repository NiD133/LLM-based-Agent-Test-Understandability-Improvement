package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

public class RefinedSoundexTest_testNewInstance extends AbstractStringEncoderTest<RefinedSoundex> {

    @Override
    protected RefinedSoundex createStringEncoder() {
        return new RefinedSoundex();
    }

    /**
     * Verifies that a default-constructed RefinedSoundex instance encodes
     * "dogs" to the expected Refined Soundex code "D6043".
     *
     * Refined Soundex mapping used (US English):
     *   D -> 6, O -> 0 (vowel, retained), G -> 4, S -> 3
     * The first letter is kept as-is, so the result is D-6-0-4-3.
     */
    @Test
    void testNewInstance() {
        RefinedSoundex encoder = new RefinedSoundex();
        String encoded = encoder.soundex("dogs");
        assertEquals("D6043", encoded);
    }
}
