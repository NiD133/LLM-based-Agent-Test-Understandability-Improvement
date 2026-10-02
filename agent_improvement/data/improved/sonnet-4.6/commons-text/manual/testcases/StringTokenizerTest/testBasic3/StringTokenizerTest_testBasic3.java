package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

public class StringTokenizerTest_testBasic3 {

    @Test
    void testBasic3() {
        // Input: "a" + space + newline + "b" + U+0001 (SOH) + form-feed + "c"
        // Default delimiters are whitespace (space, \t, \n, \r, \f).
        // U+0001 (SOH) is NOT whitespace, so it is kept inside the second token.
        final String input = "a \nb\fc";
        final StringTokenizer tokenizer = new StringTokenizer(input);

        assertEquals("a",        tokenizer.next(), "token before space + newline delimiters");
        assertEquals("b",  tokenizer.next(), "token containing non-delimiter SOH control char");
        assertEquals("c",        tokenizer.next(), "token after form-feed delimiter");
        assertFalse(tokenizer.hasNext(), "no further tokens remain");
    }
}
