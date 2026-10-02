package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;

public class WordUtilsTest_testUncapitalize_String {

    // All Unicode whitespace characters concatenated into a single string, used to test boundary behavior
    private static final String WHITESPACE = IntStream.rangeClosed(Character.MIN_CODE_POINT, Character.MAX_CODE_POINT)
            .filter(Character::isWhitespace)
            .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
            .toString();

    @Test
    void testUncapitalize_String() {
        // Null and empty inputs are returned as-is
        assertNull(WordUtils.uncapitalize(null));
        assertEquals("", WordUtils.uncapitalize(""));
        assertEquals("  ", WordUtils.uncapitalize("  "));

        // Single character: uppercase is lowercased, lowercase stays lowercase
        assertEquals("i", WordUtils.uncapitalize("I"));
        assertEquals("i", WordUtils.uncapitalize("i"));

        // Multi-word strings: only the first character of each word is lowercased
        assertEquals("i am here 123", WordUtils.uncapitalize("i am here 123"));
        assertEquals("i am here 123", WordUtils.uncapitalize("I Am Here 123"));
        assertEquals("i am hERE 123", WordUtils.uncapitalize("i am HERE 123"));
        assertEquals("i aM hERE 123", WordUtils.uncapitalize("I AM HERE 123"));

        // Tab and newline characters act as word delimiters
        assertEquals("a\tb\nc d", WordUtils.uncapitalize("A\tB\nC D"));
        assertEquals("and \tbut \ncLEAT  dome", WordUtils.uncapitalize("And \tBut \nCLEAT  Dome"));

        // A string of all whitespace characters is unchanged by capitalizeFully
        assertEquals(WHITESPACE, WordUtils.capitalizeFully(WHITESPACE));
        // capitalizeFully capitalizes the first letter of each word separated by any whitespace
        assertEquals("A" + WHITESPACE + "B", WordUtils.capitalizeFully("a" + WHITESPACE + "b"));
    }
}
