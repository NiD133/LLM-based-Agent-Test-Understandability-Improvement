package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class SoundexTest_testUsEnglishStatic {

    private static final String NAME_REQUIRING_US_ENGLISH_HW_HANDLING = "Williams";
    private static final String WILLIAMS_US_ENGLISH_SOUNDEX = "W452";

    /**
     * Verifies the static US English encoder behavior covered by CODEC-54 and CODEC-56.
     */
    @Test
    void testUsEnglishStatic() {
        final String soundexCode = Soundex.US_ENGLISH.soundex(NAME_REQUIRING_US_ENGLISH_HW_HANDLING);

        assertEquals(WILLIAMS_US_ENGLISH_SOUNDEX, soundexCode);
    }
}
