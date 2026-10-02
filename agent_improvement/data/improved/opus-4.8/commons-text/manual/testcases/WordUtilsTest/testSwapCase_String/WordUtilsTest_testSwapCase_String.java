package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

/**
 * Tests for {@link WordUtils#swapCase(String)}.
 *
 * <p>{@code swapCase} flips the case of every letter using a word-based algorithm:
 * upper/title case becomes lower case, while lower case becomes upper case — except
 * a lower case letter at the start of a word (i.e. after whitespace) becomes title case.</p>
 */
public class WordUtilsTest_testSwapCase_String {

    @Test
    void testSwapCase_String() {
        // A null input is returned unchanged (as null).
        assertNull(WordUtils.swapCase(null));

        // Strings without letters are returned unchanged.
        assertEquals("", WordUtils.swapCase(""));
        assertEquals("  ", WordUtils.swapCase("  "));

        // A single letter simply flips between the two cases.
        assertEquals("i", WordUtils.swapCase("I"));
        assertEquals("I", WordUtils.swapCase("i"));

        // Mixed-case sentences: each letter's case is swapped; digits/spaces are untouched.
        assertEquals("I AM HERE 123", WordUtils.swapCase("i am here 123"));
        assertEquals("i aM hERE 123", WordUtils.swapCase("I Am Here 123"));
        assertEquals("I AM here 123", WordUtils.swapCase("i am HERE 123"));
        assertEquals("i am here 123", WordUtils.swapCase("I AM HERE 123"));

        // A Unicode title-case character (LATIN CAPITAL LETTER N WITH SMALL LETTER J,
        // U+01C8) is treated as upper case and swapped to its lower-case form (U+01C9).
        final String titleCaseInput = "This String contains a TitleCase character: ǈ";
        final String expectedSwapped = "tHIS sTRING CONTAINS A tITLEcASE CHARACTER: ǉ";
        assertEquals(expectedSwapped, WordUtils.swapCase(titleCaseInput));
    }
}
