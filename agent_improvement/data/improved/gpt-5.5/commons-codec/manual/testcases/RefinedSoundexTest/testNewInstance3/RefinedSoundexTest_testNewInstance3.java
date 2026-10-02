package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class RefinedSoundexTest_testNewInstance3 {

    private static final String WORD_TO_ENCODE = "dogs";
    private static final String EXPECTED_US_ENGLISH_SOUNDEX = "D6043";

    protected RefinedSoundex createStringEncoder() {
        return new RefinedSoundex();
    }

    @Test
    void testNewInstance3() {
        final RefinedSoundex refinedSoundex = new RefinedSoundex(RefinedSoundex.US_ENGLISH_MAPPING_STRING);

        assertEquals(EXPECTED_US_ENGLISH_SOUNDEX, refinedSoundex.soundex(WORD_TO_ENCODE));
    }
}
