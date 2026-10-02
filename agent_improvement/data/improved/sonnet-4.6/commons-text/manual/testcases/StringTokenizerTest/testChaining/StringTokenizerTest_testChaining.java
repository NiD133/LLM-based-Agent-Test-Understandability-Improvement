package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.commons.lang3.ArrayUtils;
import org.junit.jupiter.api.Test;

public class StringTokenizerTest_testChaining {

    /**
     * Verifies that every fluent setter and reset method returns the same tokenizer instance,
     * enabling method chaining.
     */
    @Test
    void testChaining() {
        final StringTokenizer tok = new StringTokenizer();

        // reset methods
        assertEquals(tok, tok.reset());
        assertEquals(tok, tok.reset(""));
        assertEquals(tok, tok.reset(ArrayUtils.EMPTY_CHAR_ARRAY));

        // delimiter configuration
        assertEquals(tok, tok.setDelimiterChar(' '));
        assertEquals(tok, tok.setDelimiterString(" "));
        assertEquals(tok, tok.setDelimiterMatcher(null));

        // quote configuration
        assertEquals(tok, tok.setQuoteChar(' '));
        assertEquals(tok, tok.setQuoteMatcher(null));

        // ignored-character configuration
        assertEquals(tok, tok.setIgnoredChar(' '));
        assertEquals(tok, tok.setIgnoredMatcher(null));

        // trimmer configuration
        assertEquals(tok, tok.setTrimmerMatcher(null));

        // token-handling flags
        assertEquals(tok, tok.setEmptyTokenAsNull(false));
        assertEquals(tok, tok.setIgnoreEmptyTokens(false));
    }
}
