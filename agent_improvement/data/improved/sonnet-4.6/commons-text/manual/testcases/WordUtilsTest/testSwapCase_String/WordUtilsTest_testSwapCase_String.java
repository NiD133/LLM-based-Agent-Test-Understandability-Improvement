package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class WordUtilsTest_testSwapCase_String {

    @Test
    @DisplayName("swapCase returns null for null input and empty string for empty input")
    void testSwapCase_nullAndEmpty() {
        assertNull(WordUtils.swapCase(null));
        assertEquals("", WordUtils.swapCase(""));
    }

    @Test
    @DisplayName("swapCase preserves whitespace-only strings unchanged")
    void testSwapCase_whitespaceOnly() {
        assertEquals("  ", WordUtils.swapCase("  "));
    }

    @Test
    @DisplayName("swapCase inverts case of single characters")
    void testSwapCase_singleCharacter() {
        assertEquals("i", WordUtils.swapCase("I"));
        assertEquals("I", WordUtils.swapCase("i"));
    }

    @Test
    @DisplayName("swapCase applies word-based algorithm: lowercase at word start becomes title case, other lowercase becomes uppercase, uppercase becomes lowercase")
    void testSwapCase_multiWordStrings() {
        assertEquals("I AM HERE 123", WordUtils.swapCase("i am here 123"));
        assertEquals("i aM hERE 123", WordUtils.swapCase("I Am Here 123"));
        assertEquals("I AM here 123", WordUtils.swapCase("i am HERE 123"));
        assertEquals("i am here 123", WordUtils.swapCase("I AM HERE 123"));
    }

    @Test
    @DisplayName("swapCase handles Unicode title case characters by converting them to lowercase")
    void testSwapCase_unicodeTitleCase() {
        final String input = "This String contains a TitleCase character: ǈ";
        final String expected = "tHIS sTRING CONTAINS A tITLEcASE CHARACTER: ǉ";
        assertEquals(expected, WordUtils.swapCase(input));
    }
}
