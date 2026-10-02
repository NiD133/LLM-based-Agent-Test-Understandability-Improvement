package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.commons.text.matcher.StringMatcherFactory;
import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link StringTokenizer} strips the quote characters matched by a
 * quote {@code StringMatcher} while leaving the rest of the input intact.
 */
public class StringTokenizerTest_testStringTokenizerQuoteMatcher {

    @Test
    void quoteMatcherStripsQuotesFromToken() {
        // Input: 'ac'd  -> the single-quote pairs are treated as quote markers
        // and removed, so the quoted "ac" merges with the trailing "d".
        final char[] input = { '\'', 'a', 'c', '\'', 'd' };

        final StringTokenizer tokenizer = new StringTokenizer(
                input,
                StringMatcherFactory.INSTANCE.commaMatcher(),
                StringMatcherFactory.INSTANCE.quoteMatcher());

        assertEquals("acd", tokenizer.next());
    }
}
