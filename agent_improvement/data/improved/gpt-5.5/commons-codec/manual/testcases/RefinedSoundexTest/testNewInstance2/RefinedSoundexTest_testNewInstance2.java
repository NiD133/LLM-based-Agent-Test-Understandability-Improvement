package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class RefinedSoundexTest_testNewInstance2 {

    private static final String WORD_TO_ENCODE = "dogs";
    private static final String EXPECTED_REFINED_SOUNDEX_CODE = "D6043";

    @Test
    void testNewInstance2() {
        final char[] usEnglishMapping = RefinedSoundex.US_ENGLISH_MAPPING_STRING.toCharArray();
        final RefinedSoundex refinedSoundex = new RefinedSoundex(usEnglishMapping);

        assertEquals(EXPECTED_REFINED_SOUNDEX_CODE, refinedSoundex.soundex(WORD_TO_ENCODE));
    }
}
