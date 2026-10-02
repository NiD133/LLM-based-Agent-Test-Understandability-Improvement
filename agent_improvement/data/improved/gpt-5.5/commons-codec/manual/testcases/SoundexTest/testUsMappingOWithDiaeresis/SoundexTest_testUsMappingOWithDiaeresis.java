package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class SoundexTest_testUsMappingOWithDiaeresis {

    private static final String LOWERCASE_O = "o";
    private static final String LOWERCASE_O_WITH_DIAERESIS = "\u00f6";

    private Soundex getStringEncoder() {
        return new Soundex();
    }

    /**
     * Fancy characters are not mapped by the default US mapping.
     *
     * https://issues.apache.org/jira/browse/CODEC-30
     */
    @Test
    void testUsMappingOWithDiaeresis() {
        assertEquals("O000", getStringEncoder().encode(LOWERCASE_O));

        if (Character.isLetter('\u00f6')) {
            assertThrows(IllegalArgumentException.class, () -> getStringEncoder().encode(LOWERCASE_O_WITH_DIAERESIS));
        } else {
            assertEquals("", getStringEncoder().encode(LOWERCASE_O_WITH_DIAERESIS));
        }
    }
}
