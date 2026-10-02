package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link WordUtils#uncapitalize(String)}, which lowercases the first
 * character of every whitespace-separated word while leaving the remaining
 * characters of each word untouched.
 */
public class WordUtilsTest_testUncapitalize_String {

    /** Every Unicode whitespace code point, concatenated into a single String. */
    private static final String ALL_WHITESPACE =
        IntStream.rangeClosed(Character.MIN_CODE_POINT, Character.MAX_CODE_POINT)
            .filter(Character::isWhitespace)
            .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
            .toString();

    @Test
    void testUncapitalize_String() {
        // Null and "empty-ish" inputs are returned unchanged.
        assertNull(WordUtils.uncapitalize(null));
        assertEquals("", WordUtils.uncapitalize(""));
        assertEquals("  ", WordUtils.uncapitalize("  "));

        // A single leading letter is lowercased; an already-lowercase one is left as is.
        assertEquals("i", WordUtils.uncapitalize("I"));
        assertEquals("i", WordUtils.uncapitalize("i"));

        // Only the first letter of each word changes; the rest of the word is preserved.
        assertEquals("i am here 123", WordUtils.uncapitalize("i am here 123"));
        assertEquals("i am here 123", WordUtils.uncapitalize("I Am Here 123"));
        assertEquals("i am hERE 123", WordUtils.uncapitalize("i am HERE 123"));
        assertEquals("i aM hERE 123", WordUtils.uncapitalize("I AM HERE 123"));

        // Tabs and newlines also count as word separators.
        assertEquals("a\tb\nc d", WordUtils.uncapitalize("A\tB\nC D"));
        assertEquals("and \tbut \ncLEAT  dome", WordUtils.uncapitalize("And \tBut \nCLEAT  Dome"));

        // Sanity checks on the inverse operation across the full set of Unicode whitespace:
        // capitalizeFully treats every whitespace code point as a word boundary.
        assertEquals(ALL_WHITESPACE, WordUtils.capitalizeFully(ALL_WHITESPACE));
        assertEquals("A" + ALL_WHITESPACE + "B",
            WordUtils.capitalizeFully("a" + ALL_WHITESPACE + "b"));
    }
}
