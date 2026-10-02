package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.commons.lang3.ArrayUtils;
import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link StringTokenizer}'s {@code reset} and {@code set*} methods support a fluent
 * (method-chaining) API by always returning the same tokenizer instance they were called on.
 */
public class StringTokenizerTest_testChaining {

    @Test
    void testChaining() {
        final StringTokenizer tokenizer = new StringTokenizer();

        // Every reset/setter call below must return the same tokenizer instance, so that calls can be
        // chained. We assert this by checking the returned value equals the original tokenizer.
        assertEquals(tokenizer, tokenizer.reset());
        assertEquals(tokenizer, tokenizer.reset(""));
        assertEquals(tokenizer, tokenizer.reset(ArrayUtils.EMPTY_CHAR_ARRAY));
        assertEquals(tokenizer, tokenizer.setDelimiterChar(' '));
        assertEquals(tokenizer, tokenizer.setDelimiterString(" "));
        assertEquals(tokenizer, tokenizer.setDelimiterMatcher(null));
        assertEquals(tokenizer, tokenizer.setQuoteChar(' '));
        assertEquals(tokenizer, tokenizer.setQuoteMatcher(null));
        assertEquals(tokenizer, tokenizer.setIgnoredChar(' '));
        assertEquals(tokenizer, tokenizer.setIgnoredMatcher(null));
        assertEquals(tokenizer, tokenizer.setTrimmerMatcher(null));
        assertEquals(tokenizer, tokenizer.setEmptyTokenAsNull(false));
        assertEquals(tokenizer, tokenizer.setIgnoreEmptyTokens(false));
    }
}
