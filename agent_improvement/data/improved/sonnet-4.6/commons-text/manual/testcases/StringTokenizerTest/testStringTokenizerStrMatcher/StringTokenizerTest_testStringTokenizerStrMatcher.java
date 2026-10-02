package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.commons.text.matcher.StringMatcherFactory;
import org.junit.jupiter.api.Test;

public class StringTokenizerTest_testStringTokenizerStrMatcher {

    @Test
    void testStringTokenizerStrMatcher() {
        // Tokenize a char array using a StringMatcher (comma) as delimiter.
        // The comma is consumed as a delimiter, so only "a" and "c" are yielded.
        final char[] chars = { 'a', ',', 'c' };
        final StringTokenizer tokens = new StringTokenizer(chars, StringMatcherFactory.INSTANCE.commaMatcher());
        assertEquals("a", tokens.next());
        assertEquals("c", tokens.next());
    }
}
