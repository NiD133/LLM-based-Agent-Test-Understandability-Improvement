package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.commons.text.matcher.StringMatcherFactory;
import org.junit.jupiter.api.Test;

public class StringTokenizerTest_testStringTokenizerStrMatcher {

    @Test
    void testStringTokenizerStrMatcher() {
        final char[] input = { 'a', ',', 'c' };
        final StringTokenizer tokenizer = new StringTokenizer(input, StringMatcherFactory.INSTANCE.commaMatcher());

        assertEquals("a", tokenizer.next());
        assertEquals("c", tokenizer.next());
    }
}
