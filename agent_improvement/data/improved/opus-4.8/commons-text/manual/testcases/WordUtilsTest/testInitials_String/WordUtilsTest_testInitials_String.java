package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link WordUtils#initials(String)}, which returns the first character of
 * each whitespace-separated word, preserving the original case.
 */
public class WordUtilsTest_testInitials_String {

    @Test
    void testInitials_String() {
        // A null input is passed through as null.
        assertNull(WordUtils.initials(null));

        // Inputs with no words yield an empty result.
        assertEquals("", WordUtils.initials(""), "empty string has no initials");
        assertEquals("", WordUtils.initials("  "), "whitespace-only string has no initials");

        // A single word contributes its first character, with case preserved.
        assertEquals("I", WordUtils.initials("I"), "single upper-case letter");
        assertEquals("i", WordUtils.initials("i"), "single lower-case letter");

        // Each space-separated word contributes its initial.
        assertEquals("BJL", WordUtils.initials("Ben John Lee"), "one initial per word");

        // Leading, trailing and mixed whitespace (spaces, newlines, tabs) are all word separators.
        assertEquals("BJL", WordUtils.initials("   Ben \n   John\tLee\t"), "mixed whitespace separators");

        // A period is not a separator, so "J.Lee" counts as a single word.
        assertEquals("BJ", WordUtils.initials("Ben J.Lee"), "period does not split words");

        // Only whitespace splits words: the standalone "." is its own word here.
        assertEquals("BJ.L", WordUtils.initials(" Ben   John  . Lee"), "standalone period is its own word");

        // Digits count as ordinary word characters.
        assertEquals("iah1", WordUtils.initials("i am here 123"), "digits are treated as word characters");
    }
}
