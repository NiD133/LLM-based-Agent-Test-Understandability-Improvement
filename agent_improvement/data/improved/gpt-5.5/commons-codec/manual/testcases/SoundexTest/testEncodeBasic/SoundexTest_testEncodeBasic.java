package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class SoundexTest_testEncodeBasic {

    private static final String[][] BASIC_SOUNDEX_ENCODINGS = {
        { "testing", "T235" },
        { "The", "T000" },
        { "quick", "Q200" },
        { "brown", "B650" },
        { "fox", "F200" },
        { "jumped", "J513" },
        { "over", "O160" },
        { "the", "T000" },
        { "lazy", "L200" },
        { "dogs", "D200" }
    };

    private final Soundex stringEncoder = createStringEncoder();

    protected Soundex createStringEncoder() {
        return new Soundex();
    }

    protected Soundex getStringEncoder() {
        return stringEncoder;
    }

    @Test
    void testEncodeBasic() {
        for (final String[] encoding : BASIC_SOUNDEX_ENCODINGS) {
            assertEquals(encoding[1], getStringEncoder().encode(encoding[0]));
        }
    }
}
