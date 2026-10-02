package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class SoundexTest_testEncodeBatch4 {

    private static final String[][] MYATT_SOUNDEX_EXAMPLES = {
        { "HOLMES", "H452" },
        { "ADOMOMI", "A355" },
        { "VONDERLEHR", "V536" },
        { "BALL", "B400" },
        { "SHAW", "S000" },
        { "JACKSON", "J250" },
        { "SCANLON", "S545" },
        { "SAINTJOHN", "S532" }
    };

    private final Soundex stringEncoder = createStringEncoder();

    protected Soundex createStringEncoder() {
        return new Soundex();
    }

    /**
     * Examples from: http://www.myatt.demon.co.uk/sxalg.htm
     */
    @Test
    void testEncodeBatch4() {
        for (final String[] example : MYATT_SOUNDEX_EXAMPLES) {
            assertSoundexEncoding(example[1], example[0]);
        }
    }

    private void assertSoundexEncoding(final String expectedEncoding, final String name) {
        assertEquals(expectedEncoding, getStringEncoder().encode(name));
    }

    private Soundex getStringEncoder() {
        return stringEncoder;
    }
}
