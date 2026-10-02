package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class RefinedSoundexTest_testGetMappingCodeNonLetter {

    private static final char NON_LETTER = '#';
    private static final char NO_MAPPING_CODE = 0;

    private final RefinedSoundex stringEncoder = new RefinedSoundex();

    @Test
    void testGetMappingCodeNonLetter() {
        final char mappingCode = getStringEncoder().getMappingCode(NON_LETTER);

        assertEquals(NO_MAPPING_CODE, mappingCode, "Non-letter characters should not have a mapping code");
    }

    private RefinedSoundex getStringEncoder() {
        return stringEncoder;
    }
}
