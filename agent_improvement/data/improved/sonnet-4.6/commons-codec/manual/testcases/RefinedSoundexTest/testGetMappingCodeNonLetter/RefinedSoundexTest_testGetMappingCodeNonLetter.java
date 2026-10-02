package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class RefinedSoundexTest_testGetMappingCodeNonLetter extends AbstractStringEncoderTest<RefinedSoundex> {

    @Override
    protected RefinedSoundex createStringEncoder() {
        return new RefinedSoundex();
    }

    /**
     * getMappingCode must return 0 (the "no mapping" sentinel) for any character
     * that is not a letter, because only A-Z have Soundex codes assigned.
     */
    @Test
    @DisplayName("getMappingCode returns 0 for non-letter characters (e.g. '#')")
    void testGetMappingCodeNonLetter() {
        final char NON_LETTER = '#';
        final char EXPECTED_NO_MAPPING = 0;

        char actualCode = getStringEncoder().getMappingCode(NON_LETTER);

        assertEquals(EXPECTED_NO_MAPPING, actualCode,
                "getMappingCode('#') should return 0 because '#' is not a letter and has no Soundex mapping");
    }
}
