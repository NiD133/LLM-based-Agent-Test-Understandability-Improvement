package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.apache.commons.text.matcher.StringMatcherFactory;
import org.junit.jupiter.api.Test;

public class StringTokenizerTest_testBasicQuotedTrimmed1 {

    /**
     * Verifies that a tokenizer using ':' as delimiter and '\'' as quote char,
     * combined with a trim matcher, correctly parses "a: 'b' :" into three
     * tokens: "a", "b", and null (empty token at the trailing delimiter).
     *
     * Input breakdown:
     *   "a: 'b' :"
     *    ^  ^^^  ^
     *    |   |   trailing delimiter → empty → null (emptyTokenAsNull=true)
     *    |  single-quoted token → "b" (quotes stripped)
     *    first token "a" (whitespace trimmed)
     */
    @Test
    void testBasicQuotedTrimmed1() {
        final char delimiter = ':';
        final char quoteChar = '\'';
        final String input = "a: 'b' :";

        final StringTokenizer tokenizer = new StringTokenizer(input, delimiter, quoteChar);
        tokenizer.setTrimmerMatcher(StringMatcherFactory.INSTANCE.trimMatcher());
        tokenizer.setIgnoreEmptyTokens(false);
        tokenizer.setEmptyTokenAsNull(true);

        assertEquals("a", tokenizer.next(), "First token should be 'a' (trimmed)");
        assertEquals("b", tokenizer.next(), "Second token should be 'b' (unquoted and trimmed)");
        assertNull(tokenizer.next(), "Third token after trailing ':' should be null (empty → null)");
        assertFalse(tokenizer.hasNext(), "No more tokens after the three parsed tokens");
    }
}
