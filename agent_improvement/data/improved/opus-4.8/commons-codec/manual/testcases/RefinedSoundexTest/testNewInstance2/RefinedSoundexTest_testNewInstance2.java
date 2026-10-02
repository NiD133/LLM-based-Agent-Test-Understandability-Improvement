package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

/**
 * Verifies that a {@link RefinedSoundex} created from the default US English
 * mapping (supplied explicitly as a {@code char[]}) encodes words the same way
 * as the built-in default instance.
 */
public class RefinedSoundexTest_testNewInstance2 extends AbstractStringEncoderTest<RefinedSoundex> {

    @Override
    protected RefinedSoundex createStringEncoder() {
        return new RefinedSoundex();
    }

    @Test
    void testNewInstance2() {
        // Build an encoder from the US English mapping passed as a char array,
        // exercising the RefinedSoundex(char[]) constructor.
        final char[] usEnglishMapping = RefinedSoundex.US_ENGLISH_MAPPING_STRING.toCharArray();
        final RefinedSoundex refinedSoundex = new RefinedSoundex(usEnglishMapping);

        final String actualCode = refinedSoundex.soundex("dogs");

        assertEquals("D6043", actualCode);
    }
}
