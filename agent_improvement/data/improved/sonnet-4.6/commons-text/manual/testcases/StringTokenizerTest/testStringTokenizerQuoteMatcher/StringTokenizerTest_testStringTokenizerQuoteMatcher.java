package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.commons.text.matcher.StringMatcherFactory;
import org.junit.jupiter.api.Test;

public class StringTokenizerTest_testStringTokenizerQuoteMatcher {

    /**
     * Input chars represent: 'ac'd
     * The single-quote matcher treats 'ac' as a quoted region, stripping the quotes,
     * and 'd' follows immediately outside the quotes — yielding a single token "acd".
     */
    @Test
    void testStringTokenizerQuoteMatcher() {
        // 'ac'd — single quotes are the quote characters, comma is the delimiter
        final char[] quotedInput = { '\'', 'a', 'c', '\'', 'd' };
        final StringTokenizer tokenizer = new StringTokenizer(
                quotedInput,
                StringMatcherFactory.INSTANCE.commaMatcher(),
                StringMatcherFactory.INSTANCE.quoteMatcher());

        assertEquals("acd", tokenizer.next());
    }
}
