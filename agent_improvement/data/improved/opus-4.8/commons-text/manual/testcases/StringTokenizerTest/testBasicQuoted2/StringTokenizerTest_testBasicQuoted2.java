package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link StringTokenizer} parses a colon-delimited, single-quoted
 * input while preserving empty tokens and reporting them as {@code null}.
 */
public class StringTokenizerTest_testBasicQuoted2 {

    @Test
    void parsesQuotedTokensAndReportsTrailingEmptyAsNull() {
        // Input "a:'b':" with ':' as the delimiter and '\'' as the quote char.
        // The trailing ':' produces an empty final token.
        final String input = "a:'b':";
        final StringTokenizer tokenizer = new StringTokenizer(input, ':', '\'');

        // Keep empty tokens, but surface them as null rather than "".
        tokenizer.setIgnoreEmptyTokens(false);
        tokenizer.setEmptyTokenAsNull(true);

        assertEquals("a", tokenizer.next());          // plain token
        assertEquals("b", tokenizer.next());          // quotes are stripped
        assertNull(tokenizer.next());                 // trailing empty token -> null
        assertFalse(tokenizer.hasNext());             // no tokens remain
    }
}
