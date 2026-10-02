package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

/**
 * Tests {@link RefinedSoundex#getMappingCode(char)} for non-letter input.
 */
public class RefinedSoundexTest_testGetMappingCodeNonLetter extends AbstractStringEncoderTest<RefinedSoundex> {

    /** Code returned by getMappingCode when the character has no Soundex mapping. */
    private static final char NO_MAPPING_CODE = 0;

    @Override
    protected RefinedSoundex createStringEncoder() {
        return new RefinedSoundex();
    }

    /**
     * A non-letter character (such as '#') has no Soundex mapping, so
     * getMappingCode should return the "no mapping" code of 0.
     */
    @Test
    void testGetMappingCodeNonLetter() {
        final char nonLetter = '#';

        final char mappingCode = getStringEncoder().getMappingCode(nonLetter);

        assertEquals(NO_MAPPING_CODE, mappingCode,
                "A non-letter character should map to code 0");
    }
}
