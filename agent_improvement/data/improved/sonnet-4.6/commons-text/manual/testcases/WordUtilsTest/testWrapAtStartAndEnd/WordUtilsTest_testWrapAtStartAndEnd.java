package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class WordUtilsTest_testWrapAtStartAndEnd {

    @Test
    void testWrapAtStartAndEnd() {
        // "(?=n)" is a zero-width lookahead that matches the position just before each 'n',
        // so the wrap points fall at the very start and end of the content, producing leading
        // and trailing newlines.
        String input    = "nabcdefabcdefn";
        String expected = "\nabcdefabcdef\n";
        assertEquals(expected, WordUtils.wrap(input, 2, "\n", false, "(?=n)"));
    }
}
