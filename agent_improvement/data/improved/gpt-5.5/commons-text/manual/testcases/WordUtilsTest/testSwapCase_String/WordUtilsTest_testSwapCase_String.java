package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

public class WordUtilsTest_testSwapCase_String {

    private static final String TITLE_CASE_INPUT = "This String contains a TitleCase character: \u01C8";
    private static final String TITLE_CASE_EXPECTED = "tHIS sTRING CONTAINS A tITLEcASE CHARACTER: \u01C9";

    @Test
    void testSwapCase_String() {
        assertNull(WordUtils.swapCase(null));

        assertSwapCase("", "");
        assertSwapCase("  ", "  ");
        assertSingleLetterCaseIsSwapped();
        assertSentenceCaseIsSwapped();
        assertSwapCase(TITLE_CASE_EXPECTED, TITLE_CASE_INPUT);
    }

    private static void assertSingleLetterCaseIsSwapped() {
        assertSwapCase("i", "I");
        assertSwapCase("I", "i");
    }

    private static void assertSentenceCaseIsSwapped() {
        assertSwapCase("I AM HERE 123", "i am here 123");
        assertSwapCase("i aM hERE 123", "I Am Here 123");
        assertSwapCase("I AM here 123", "i am HERE 123");
        assertSwapCase("i am here 123", "I AM HERE 123");
    }

    private static void assertSwapCase(final String expected, final String input) {
        assertEquals(expected, WordUtils.swapCase(input));
    }
}
