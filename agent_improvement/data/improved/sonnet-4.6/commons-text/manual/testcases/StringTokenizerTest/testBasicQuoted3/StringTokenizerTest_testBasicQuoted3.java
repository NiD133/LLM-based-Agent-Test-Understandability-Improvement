package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

public class StringTokenizerTest_testBasicQuoted3 {

    @Test
    void testBasicQuoted3() {
        // Input uses ':' as delimiter and '\'' as quote char.
        // The quoted section 'b''c' contains an escaped quote (doubled ''),
        // which the tokenizer resolves to the literal character ', yielding token "b'c".
        final String input = "a:'b''c'";
        final StringTokenizer tok = new StringTokenizer(input, ':', '\'');
        tok.setIgnoreEmptyTokens(false);
        tok.setEmptyTokenAsNull(true);

        assertEquals("a", tok.next());
        assertEquals("b'c", tok.next());
        assertFalse(tok.hasNext());
    }
}
