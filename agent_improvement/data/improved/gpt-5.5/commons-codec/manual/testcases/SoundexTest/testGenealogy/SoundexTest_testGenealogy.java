package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class SoundexTest_testGenealogy {

    private static final Soundex GENEALOGY_SOUNDEX = Soundex.US_ENGLISH_GENEALOGY;

    private static final String[][] GENEALOGY_EXAMPLES = {
        { "Heggenburger", "H251" },
        { "Blackman", "B425" },
        { "Schmidt", "S530" },
        { "Lippmann", "L150" },
        { "Dodds", "D200" },
        { "Dhdds", "D200" },
        { "Dwdds", "D200" }
    };

    @Test
    void testGenealogy() {
        for (final String[] example : GENEALOGY_EXAMPLES) {
            assertGenealogyCode(example[0], example[1]);
        }
    }

    private void assertGenealogyCode(final String name, final String expectedCode) {
        assertEquals(expectedCode, GENEALOGY_SOUNDEX.encode(name));
    }
}
