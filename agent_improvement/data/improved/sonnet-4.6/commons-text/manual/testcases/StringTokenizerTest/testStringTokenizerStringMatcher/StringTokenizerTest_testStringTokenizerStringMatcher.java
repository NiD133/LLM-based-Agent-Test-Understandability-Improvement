package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class StringTokenizerTest_testStringTokenizerStringMatcher {

    @Test
    @DisplayName("StringTokenizer splits char array using a multi-char string delimiter")
    void testStringTokenizerStringMatcher() {
        // Input: "abcd" with "bc" as the delimiter → expected tokens are "a" and "d"
        final char[] input = {'a', 'b', 'c', 'd'};
        final String delimiter = "bc";

        final StringTokenizer tokens = new StringTokenizer(input, delimiter);

        assertEquals("a", tokens.next());
        assertEquals("d", tokens.next());
    }
}
