package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

public class StringTokenizerTest_testBasicDelim2 {

    /**
     * When the delimiter does not appear in the input, the whole string is
     * returned as a single token and no further tokens are available.
     */
    @Test
    void testBasicDelim2() {
        final String input = "a:b:c";
        final char commaDelimiter = ',';

        final StringTokenizer tokenizer = new StringTokenizer(input, commaDelimiter);

        assertEquals("a:b:c", tokenizer.next());
        assertFalse(tokenizer.hasNext());
    }
}
