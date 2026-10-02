package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

/**
 * Verifies that a {@link Soundex} instance built from a custom char[] mapping
 * encodes names the same way as the default US-English instance.
 */
public class SoundexTest_testNewInstance2 extends AbstractStringEncoderTest<Soundex> {

    @Override
    protected Soundex createStringEncoder() {
        return new Soundex();
    }

    @Test
    void testCharArrayMappingConstructorEncodesName() {
        // Build a Soundex using the standard US-English mapping supplied as a char[].
        final char[] usEnglishMapping = Soundex.US_ENGLISH_MAPPING_STRING.toCharArray();
        final Soundex soundex = new Soundex(usEnglishMapping);

        // "Williams" -> W (first letter), then 4 (l), 5 (m), 2 (s); vowels/repeats dropped.
        final String expectedSoundexCode = "W452";

        assertEquals(expectedSoundexCode, soundex.soundex("Williams"));
    }
}
