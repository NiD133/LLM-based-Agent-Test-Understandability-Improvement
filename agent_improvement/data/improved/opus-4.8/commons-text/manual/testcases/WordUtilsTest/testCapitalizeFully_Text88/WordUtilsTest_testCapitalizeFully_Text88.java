package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Verifies {@link WordUtils#capitalizeFully(String, char...)} when an empty
 * delimiter array is supplied.
 */
public class WordUtilsTest_testCapitalizeFully_Text88 {

    /**
     * With no delimiter characters, the entire input is treated as a single
     * word: every letter is lower-cased and only the very first character is
     * capitalized, leaving the remaining words unchanged.
     */
    @Test
    void testCapitalizeFully_Text88() {
        final String input = "i am fine now";
        final char[] noDelimiters = new char[] {};

        final String result = WordUtils.capitalizeFully(input, noDelimiters);

        assertEquals("I am fine now", result);
    }
}
