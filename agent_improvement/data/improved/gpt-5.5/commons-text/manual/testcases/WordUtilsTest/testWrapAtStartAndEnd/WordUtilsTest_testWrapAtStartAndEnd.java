package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class WordUtilsTest_testWrapAtStartAndEnd {

    @Test
    void testWrapAtStartAndEnd() {
        final String textWithWrapPointsAtBothEnds = "nabcdefabcdefn";
        final int wrapLength = 2;
        final String newLine = "\n";
        final boolean wrapLongWords = false;
        final String wrapOnLetterN = "(?=n)";

        assertEquals(
                "\nabcdefabcdef\n",
                WordUtils.wrap(textWithWrapPointsAtBothEnds, wrapLength, newLine, wrapLongWords, wrapOnLetterN));
    }
}
