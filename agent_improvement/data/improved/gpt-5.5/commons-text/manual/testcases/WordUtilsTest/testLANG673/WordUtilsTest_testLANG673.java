package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class WordUtilsTest_testLANG673 {

    private static final String TEXT_WITH_WORD_BOUNDARIES = "01 23 45 67 89";
    private static final int UPPER_LIMIT_BEYOND_TEXT = 40;
    private static final String NO_ABBREVIATION_SUFFIX = "";

    @Test
    void testLANG673() {
        assertEquals("01", WordUtils.abbreviate(TEXT_WITH_WORD_BOUNDARIES, 0, UPPER_LIMIT_BEYOND_TEXT, NO_ABBREVIATION_SUFFIX));
        assertEquals("01 23 45 67", WordUtils.abbreviate(TEXT_WITH_WORD_BOUNDARIES, 10, UPPER_LIMIT_BEYOND_TEXT, NO_ABBREVIATION_SUFFIX));
        assertEquals("01 23 45 67 89", WordUtils.abbreviate(TEXT_WITH_WORD_BOUNDARIES, 40, UPPER_LIMIT_BEYOND_TEXT, NO_ABBREVIATION_SUFFIX));
    }
}
