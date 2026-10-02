package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class RefinedSoundexTest_testNewInstance {

    private static final String WORD_ENCODED_WITH_DEFAULT_MAPPING = "dogs";
    private static final String EXPECTED_REFINED_SOUNDEX_CODE = "D6043";

    protected RefinedSoundex createStringEncoder() {
        return new RefinedSoundex();
    }

    @Test
    void testNewInstance() {
        assertEquals(
                EXPECTED_REFINED_SOUNDEX_CODE,
                new RefinedSoundex().soundex(WORD_ENCODED_WITH_DEFAULT_MAPPING));
    }
}
