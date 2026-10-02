package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class StringTokenizerTest_testStringTokenizerStringMatcher {

    @Test
    void testStringTokenizerStringMatcher() {
        final char[] input = { 'a', 'b', 'c', 'd' };
        final String delimiter = "bc";
        final StringTokenizer tokenizer = new StringTokenizer(input, delimiter);

        assertEquals("a", tokenizer.next());
        assertEquals("d", tokenizer.next());
    }
}
