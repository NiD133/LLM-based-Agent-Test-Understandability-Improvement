package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.apache.commons.text.matcher.StringMatcherFactory;
import org.junit.jupiter.api.Test;

public class StringTokenizerTest_testBasicTrimmed2 {

    /**
     * Tokenizes {@code "a:  b  :"} on the {@code ':'} delimiter while trimming a
     * literal two-space sequence from each token.
     *
     * <p>The three raw tokens are {@code "a"}, {@code "  b  "} and {@code ""}.
     * After trimming the {@code "  "} matcher they become {@code "a"}, {@code "b"}
     * and {@code ""}. Because empty tokens are kept (not ignored) but reported as
     * {@code null}, the resulting sequence is {@code "a"}, {@code "b"}, {@code null}.</p>
     */
    @Test
    void testBasicTrimmed2() {
        final String input = "a:  b  :";
        final StringTokenizer tokenizer = new StringTokenizer(input, ':');
        tokenizer.setTrimmerMatcher(StringMatcherFactory.INSTANCE.stringMatcher("  "));
        tokenizer.setIgnoreEmptyTokens(false);
        tokenizer.setEmptyTokenAsNull(true);

        assertEquals("a", tokenizer.next());
        assertEquals("b", tokenizer.next());
        assertNull(tokenizer.next(), "trailing empty token should be reported as null");
        assertFalse(tokenizer.hasNext(), "no tokens should remain after the last one");
    }
}
