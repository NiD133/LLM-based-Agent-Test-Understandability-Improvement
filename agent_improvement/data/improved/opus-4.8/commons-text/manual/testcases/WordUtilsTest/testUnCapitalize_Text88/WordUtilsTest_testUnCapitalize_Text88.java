package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link WordUtils#uncapitalize(String, char...)} when an empty delimiter
 * array is supplied.
 *
 * <p>With no delimiters defined, the whole input is treated as a single word, so
 * only the very first character is uncapitalized and the rest is left untouched.</p>
 */
public class WordUtilsTest_testUnCapitalize_Text88 {

    @Test
    void uncapitalizeWithNoDelimitersOnlyLowercasesFirstCharacter() {
        final String input = "I am fine now";
        final char[] noDelimiters = new char[] {};

        final String result = WordUtils.uncapitalize(input, noDelimiters);

        assertEquals("i am fine now", result);
    }
}
