package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

public class StringTokenizerTest_testGetContent {

    @Test
    void testGetContent() {
        final String input = "a   b c \"d e\" f ";

        StringTokenizer tokFromString = new StringTokenizer(input);
        assertEquals(input, tokFromString.getContent());

        StringTokenizer tokFromChars = new StringTokenizer(input.toCharArray());
        assertEquals(input, tokFromChars.getContent());

        StringTokenizer tokEmpty = new StringTokenizer();
        assertNull(tokEmpty.getContent());
    }
}
