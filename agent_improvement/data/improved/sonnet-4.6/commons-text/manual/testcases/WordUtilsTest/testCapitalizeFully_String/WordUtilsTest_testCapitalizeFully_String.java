package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;

public class WordUtilsTest_testCapitalizeFully_String {

    // All Unicode whitespace characters concatenated, used to test boundary behaviour with exotic whitespace
    private static final String WHITESPACE = IntStream.rangeClosed(Character.MIN_CODE_POINT, Character.MAX_CODE_POINT)
            .filter(Character::isWhitespace)
            .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
            .toString();

    @Test
    void testCapitalizeFully_String() {
        // Null and empty inputs pass through unchanged
        assertNull(WordUtils.capitalizeFully(null));
        assertEquals("", WordUtils.capitalizeFully(""));
        assertEquals("  ", WordUtils.capitalizeFully("  "));

        // Single character: both lowercase and uppercase map to uppercase
        assertEquals("I", WordUtils.capitalizeFully("I"));
        assertEquals("I", WordUtils.capitalizeFully("i"));

        // Multiple words: first letter of each word is uppercased, rest are lowercased
        assertEquals("I Am Here 123", WordUtils.capitalizeFully("i am here 123"));
        assertEquals("I Am Here 123", WordUtils.capitalizeFully("I Am Here 123"));
        assertEquals("I Am Here 123", WordUtils.capitalizeFully("i am HERE 123"));
        assertEquals("I Am Here 123", WordUtils.capitalizeFully("I AM HERE 123"));

        // Single word: only the first character is capitalised
        assertEquals("Alphabet", WordUtils.capitalizeFully("alphabet"));

        // Non-space whitespace characters (tab, newline) are treated as word delimiters
        assertEquals("A\tB\nC D", WordUtils.capitalizeFully("a\tb\nc d"));
        assertEquals("And \tBut \nCleat  Dome", WordUtils.capitalizeFully("and \tbut \ncleat  dome"));

        // All-whitespace strings are returned unchanged (no word characters to capitalise)
        assertEquals(WHITESPACE, WordUtils.capitalizeFully(WHITESPACE));

        // Words separated by the full range of Unicode whitespace are still capitalised correctly
        assertEquals("A" + WHITESPACE + "B", WordUtils.capitalizeFully("a" + WHITESPACE + "b"));
    }
}
