package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class RandomStringGeneratorTest_testNoLoneSurrogates {

    @Test
    void testNoLoneSurrogates() {
        final int length = 5000;
        final String str = RandomStringGenerator.builder().get().generate(length);
        assertNoLoneSurrogates(str);
    }

    /**
     * Verifies that no surrogate character appears without its required pair.
     *
     * In UTF-16, supplementary characters (code points above U+FFFF) are encoded
     * as a high surrogate followed immediately by a low surrogate. A "lone surrogate"
     * is a high or low surrogate that is missing its required partner adjacent to it.
     * The generator must never produce lone surrogates.
     */
    private static void assertNoLoneSurrogates(final String str) {
        char previousChar = str.charAt(0);
        for (int i = 1; i < str.length(); i++) {
            final char currentChar = str.charAt(i);

            if (Character.isLowSurrogate(currentChar)) {
                assertTrue(Character.isHighSurrogate(previousChar),
                    "Low surrogate at index " + i + " must be preceded by a high surrogate");
            }
            if (Character.isHighSurrogate(previousChar)) {
                assertTrue(Character.isLowSurrogate(currentChar),
                    "High surrogate at index " + (i - 1) + " must be followed by a low surrogate");
            }
            if (Character.isHighSurrogate(currentChar)) {
                assertTrue(i + 1 < str.length(),
                    "High surrogate at index " + i + " must not be the last character in the string");
            }

            previousChar = currentChar;
        }
    }
}
