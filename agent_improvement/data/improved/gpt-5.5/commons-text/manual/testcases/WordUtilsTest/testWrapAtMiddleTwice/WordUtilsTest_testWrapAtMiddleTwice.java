package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class WordUtilsTest_testWrapAtMiddleTwice {

    @Test
    void testWrapAtMiddleTwice() {
        final String textWithTwoAdjacentWrapPoints = "abcdefggabcdef";
        final int wrapLengthBeforeFirstWrapPoint = 2;
        final String lineSeparator = "\n";
        final boolean preserveLongWords = false;
        final String wrapBeforeEachG = "(?=g)";

        assertEquals(
                "abcdef\n\nabcdef",
                WordUtils.wrap(
                        textWithTwoAdjacentWrapPoints,
                        wrapLengthBeforeFirstWrapPoint,
                        lineSeparator,
                        preserveLongWords,
                        wrapBeforeEachG));
    }
}
