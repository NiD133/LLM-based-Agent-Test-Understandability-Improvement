package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class SoundexTest_testEncodeBatch2 {

    private static final String[][] GENEALOGY_EXAMPLES = {
        { "Allricht", "A462" },
        { "Eberhard", "E166" },
        { "Engebrethson", "E521" },
        { "Heimbach", "H512" },
        { "Hanselmann", "H524" },
        { "Hildebrand", "H431" },
        { "Kavanagh", "K152" },
        { "Lind", "L530" },
        { "Lukaschowsky", "L222" },
        { "McDonnell", "M235" },
        { "McGee", "M200" },
        { "Opnian", "O155" },
        { "Oppenheimer", "O155" },
        { "Riedemanas", "R355" },
        { "Zita", "Z300" },
        { "Zitzmeinn", "Z325" },
    };

    private final Soundex stringEncoder = createStringEncoder();

    protected Soundex createStringEncoder() {
        return new Soundex();
    }

    private Soundex getStringEncoder() {
        return stringEncoder;
    }

    /**
     * Examples from http://www.bradandkathy.com/genealogy/overviewofsoundex.html
     */
    @Test
    void testEncodeBatch2() {
        for (final String[] example : GENEALOGY_EXAMPLES) {
            assertSoundex(example[0], example[1]);
        }
    }

    private void assertSoundex(final String name, final String expectedEncoding) {
        assertEquals(expectedEncoding, getStringEncoder().encode(name));
    }
}
