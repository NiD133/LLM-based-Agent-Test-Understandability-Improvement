package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class SoundexTest_testUsMappingEWithAcute {

    private static final char E_WITH_ACUTE = '\u00e9';
    private static final String E_WITH_ACUTE_TEXT = "\u00e9";

    private final Soundex stringEncoder = createStringEncoder();

    protected Soundex createStringEncoder() {
        return new Soundex();
    }

    private Soundex getStringEncoder() {
        return stringEncoder;
    }

    /**
     * The default US mapping only supports unaccented A-Z characters.
     *
     * https://issues.apache.org/jira/browse/CODEC-30
     */
    @Test
    void testUsMappingEWithAcute() {
        assertEquals("E000", getStringEncoder().encode("e"));

        final boolean accentedEIsALetter = Character.isLetter(E_WITH_ACUTE);
        if (accentedEIsALetter) {
            assertThrows(IllegalArgumentException.class, () -> getStringEncoder().encode(E_WITH_ACUTE_TEXT));
        } else {
            assertEquals("", getStringEncoder().encode(E_WITH_ACUTE_TEXT));
        }
    }
}
