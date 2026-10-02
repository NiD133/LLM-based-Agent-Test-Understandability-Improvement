package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.apache.commons.text.matcher.StringMatcherFactory;
import org.junit.jupiter.api.Test;

public class StringTokenizerTest_testBasicTrimmed1 {

    /**
     * Tokenizes "a: b :  " on the ':' delimiter while trimming whitespace
     * around each token. With empty tokens kept (not ignored) and returned as
     * null, the trailing empty segment after the last ':' becomes null.
     */
    @Test
    void testBasicTrimmed1() {
        final String input = "a: b :  ";
        final StringTokenizer tokenizer = new StringTokenizer(input, ':');
        tokenizer.setTrimmerMatcher(StringMatcherFactory.INSTANCE.trimMatcher());
        tokenizer.setIgnoreEmptyTokens(false);
        tokenizer.setEmptyTokenAsNull(true);

        assertEquals("a", tokenizer.next());
        assertEquals("b", tokenizer.next());
        assertNull(tokenizer.next(), "trailing whitespace-only token should be null");
        assertFalse(tokenizer.hasNext());
    }
}
