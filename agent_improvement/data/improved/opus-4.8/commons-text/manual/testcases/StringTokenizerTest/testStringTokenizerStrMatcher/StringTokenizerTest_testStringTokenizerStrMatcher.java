package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.commons.text.matcher.StringMatcherFactory;
import org.junit.jupiter.api.Test;

public class StringTokenizerTest_testStringTokenizerStrMatcher {

    /**
     * Verifies that the {@code (char[], StringMatcher)} constructor splits the
     * input on every occurrence of the matched delimiter.
     *
     * <p>Here the input {@code a,c} is tokenized with a comma matcher, so the
     * delimiter is consumed and the tokenizer yields the two surrounding
     * fields {@code "a"} and {@code "c"}.</p>
     */
    @Test
    void testStringTokenizerStrMatcher() {
        final char[] input = {'a', ',', 'c'};

        final StringTokenizer tokenizer =
                new StringTokenizer(input, StringMatcherFactory.INSTANCE.commaMatcher());

        assertEquals("a", tokenizer.next());
        assertEquals("c", tokenizer.next());
    }
}
