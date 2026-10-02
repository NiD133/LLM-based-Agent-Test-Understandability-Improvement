package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

public class StringTokenizerTest_testBasicQuoted3 {

    /**
     * Verifies that a doubled quote inside a quoted section is collapsed into a
     * single literal quote, so {@code 'b''c'} is read as the token {@code b'c}.
     */
    @Test
    void testBasicQuoted3() {
        // Input: a colon-delimited string whose second field is quoted with '.
        // The "''" inside the quotes is an escaped (literal) single quote.
        final String input = "a:'b''c'";
        final char delimiter = ':';
        final char quote = '\'';

        final StringTokenizer tokenizer = new StringTokenizer(input, delimiter, quote);
        tokenizer.setIgnoreEmptyTokens(false);
        tokenizer.setEmptyTokenAsNull(true);

        assertEquals("a", tokenizer.next(), "first field, unquoted");
        assertEquals("b'c", tokenizer.next(), "second field: doubled quote becomes a single literal quote");
        assertFalse(tokenizer.hasNext(), "no tokens remain after the two fields");
    }
}
