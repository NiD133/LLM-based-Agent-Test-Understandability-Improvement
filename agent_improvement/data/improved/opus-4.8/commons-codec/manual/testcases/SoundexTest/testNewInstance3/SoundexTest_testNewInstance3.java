package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

/**
 * Verifies that a {@link Soundex} instance built from an explicit mapping string
 * behaves identically to the default US-English Soundex encoder.
 */
public class SoundexTest_testNewInstance3 extends AbstractStringEncoderTest<Soundex> {

    @Override
    protected Soundex createStringEncoder() {
        return new Soundex();
    }

    @Test
    void encodesWilliamsUsingExplicitUsEnglishMapping() {
        // Construct the encoder from the public US-English mapping string instead of
        // relying on the no-arg constructor's built-in mapping.
        final Soundex soundex = new Soundex(Soundex.US_ENGLISH_MAPPING_STRING);

        final String actualCode = soundex.soundex("Williams");

        assertEquals("W452", actualCode);
    }
}
