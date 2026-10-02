package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.apache.commons.text.matcher.StringMatcherFactory;
import org.junit.jupiter.api.Test;

public class StringTokenizerTest_testBasicQuotedTrimmed1 {

    /**
     * Tokenizes a colon-delimited string with single-quote quoting, surrounding
     * whitespace trimming, and empty tokens reported as {@code null}.
     *
     * <p>Input {@code "a: 'b' :"} is split on {@code ':'} into three raw tokens:
     * {@code "a"}, {@code " 'b' "} and {@code ""}. After trimming and unquoting
     * these become {@code "a"}, {@code "b"} and an empty token, which is exposed
     * as {@code null}. After the third token the tokenizer is exhausted.</p>
     */
    @Test
    void testBasicQuotedTrimmed1() {
        final String input = "a: 'b' :";
        final StringTokenizer tokenizer = new StringTokenizer(input, ':', '\'');
        tokenizer.setTrimmerMatcher(StringMatcherFactory.INSTANCE.trimMatcher());
        tokenizer.setIgnoreEmptyTokens(false);
        tokenizer.setEmptyTokenAsNull(true);

        assertEquals("a", tokenizer.next());
        assertEquals("b", tokenizer.next());
        assertNull(tokenizer.next());
        assertFalse(tokenizer.hasNext());
    }
}
