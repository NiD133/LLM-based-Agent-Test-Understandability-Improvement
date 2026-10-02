package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

/**
 * Verifies that a {@link RefinedSoundex} built from the public US English mapping
 * string produces the expected refined Soundex encoding.
 */
public class RefinedSoundexTest_testNewInstance3 extends AbstractStringEncoderTest<RefinedSoundex> {

    @Override
    protected RefinedSoundex createStringEncoder() {
        return new RefinedSoundex();
    }

    @Test
    void testNewInstance3() {
        // Construct the encoder explicitly from the US English mapping string.
        final RefinedSoundex encoder = new RefinedSoundex(RefinedSoundex.US_ENGLISH_MAPPING_STRING);

        final String expectedCode = "D6043";
        final String actualCode = encoder.soundex("dogs");

        assertEquals(expectedCode, actualCode);
    }
}
