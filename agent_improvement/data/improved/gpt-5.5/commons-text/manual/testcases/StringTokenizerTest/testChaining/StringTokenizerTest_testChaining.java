package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.commons.lang3.ArrayUtils;
import org.junit.jupiter.api.Test;

public class StringTokenizerTest_testChaining {

    private static void assertReturnsSameTokenizer(final StringTokenizer expectedTokenizer,
            final StringTokenizer actualTokenizer) {
        assertEquals(expectedTokenizer, actualTokenizer);
    }

    @Test
    void testChaining() {
        final StringTokenizer tokenizer = new StringTokenizer();

        assertReturnsSameTokenizer(tokenizer, tokenizer.reset());
        assertReturnsSameTokenizer(tokenizer, tokenizer.reset(""));
        assertReturnsSameTokenizer(tokenizer, tokenizer.reset(ArrayUtils.EMPTY_CHAR_ARRAY));
        assertReturnsSameTokenizer(tokenizer, tokenizer.setDelimiterChar(' '));
        assertReturnsSameTokenizer(tokenizer, tokenizer.setDelimiterString(" "));
        assertReturnsSameTokenizer(tokenizer, tokenizer.setDelimiterMatcher(null));
        assertReturnsSameTokenizer(tokenizer, tokenizer.setQuoteChar(' '));
        assertReturnsSameTokenizer(tokenizer, tokenizer.setQuoteMatcher(null));
        assertReturnsSameTokenizer(tokenizer, tokenizer.setIgnoredChar(' '));
        assertReturnsSameTokenizer(tokenizer, tokenizer.setIgnoredMatcher(null));
        assertReturnsSameTokenizer(tokenizer, tokenizer.setTrimmerMatcher(null));
        assertReturnsSameTokenizer(tokenizer, tokenizer.setEmptyTokenAsNull(false));
        assertReturnsSameTokenizer(tokenizer, tokenizer.setIgnoreEmptyTokens(false));
    }
}
