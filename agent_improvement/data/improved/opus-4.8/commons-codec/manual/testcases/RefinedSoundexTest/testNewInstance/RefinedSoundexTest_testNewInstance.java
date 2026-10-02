package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

/**
 * Verifies that a {@link RefinedSoundex} created with its no-argument
 * constructor encodes words using the default US English mapping.
 */
public class RefinedSoundexTest_testNewInstance extends AbstractStringEncoderTest<RefinedSoundex> {

    @Override
    protected RefinedSoundex createStringEncoder() {
        return new RefinedSoundex();
    }

    @Test
    void defaultInstanceEncodesWordWithUsEnglishMapping() {
        final RefinedSoundex defaultEncoder = new RefinedSoundex();

        final String actualCode = defaultEncoder.soundex("dogs");

        assertEquals("D6043", actualCode,
                "Default RefinedSoundex should encode \"dogs\" using the US English mapping");
    }
}
