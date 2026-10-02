package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class SoundexTest_testEncodeBatch3 {

    private final Soundex stringEncoder = createStringEncoder();

    protected Soundex createStringEncoder() {
        return new Soundex();
    }

    private Soundex getStringEncoder() {
        return stringEncoder;
    }

    /**
     * Examples from http://www.archives.gov/research_room/genealogy/census/soundex.html
     */
    @Test
    void testEncodeBatch3() {
        final String[][] archiveExamples = {
            { "W252", "Washington" },
            { "L000", "Lee" },
            { "G362", "Gutierrez" },
            { "P236", "Pfister" },
            { "J250", "Jackson" },
            { "T522", "Tymczak" },
            // For VanDeusen: D-250 (D, 2 for the S, 5 for the N, 0 added) is also possible.
            { "V532", "VanDeusen" }
        };

        for (final String[] example : archiveExamples) {
            assertEquals(example[0], getStringEncoder().encode(example[1]));
        }
    }
}
