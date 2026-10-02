package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class RefinedSoundexTest_testEncode {

    private static final String[][] PANGRAM_ENCODINGS = {
            { "T60", "The" },
            { "Q503", "quick" },
            { "B1908", "brown" },
            { "F205", "fox" },
            { "J408106", "jumped" },
            { "O0209", "over" },
            { "T60", "the" },
            { "L7050", "lazy" },
            { "D6043", "dogs" }
    };

    private final RefinedSoundex stringEncoder = createStringEncoder();

    protected RefinedSoundex createStringEncoder() {
        return new RefinedSoundex();
    }

    @Test
    void testEncode() {
        assertEncoding("T6036084", "testing");
        assertEncoding("T6036084", "TESTING");

        for (final String[] encoding : PANGRAM_ENCODINGS) {
            assertEncoding(encoding[0], encoding[1]);
        }

        // Testing CODEC-56
        assertEquals("D6043", RefinedSoundex.US_ENGLISH.encode("dogs"));
    }

    private void assertEncoding(final String expectedEncoding, final String input) {
        assertEquals(expectedEncoding, getStringEncoder().encode(input));
    }

    private RefinedSoundex getStringEncoder() {
        return stringEncoder;
    }
}
