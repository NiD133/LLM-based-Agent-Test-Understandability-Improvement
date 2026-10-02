package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link StringTokenizer} treats every character of a delimiter
 * {@code String} as an individual delimiter when splitting a {@code char[]} input.
 */
public class StringTokenizerTest_testStringTokenizerStringMatcher {

    @Test
    void testStringTokenizerStringMatcher() {
        // Input "abcd"; the delimiter String "bc" makes both 'b' and 'c' delimiters.
        final char[] input = { 'a', 'b', 'c', 'd' };
        final String delimiters = "bc";

        final StringTokenizer tokenizer = new StringTokenizer(input, delimiters);

        // 'b' and 'c' are consumed as delimiters, leaving only the surrounding tokens.
        assertEquals("a", tokenizer.next());
        assertEquals("d", tokenizer.next());
    }
}
