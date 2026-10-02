package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class StringTokenizerTest_testToString {

    @Test
    void testToString() {
        final StringTokenizer tkn = new StringTokenizer("a b c d e");

        // Before any iteration, tokenization has not yet occurred
        assertEquals("StringTokenizer[not tokenized yet]", tkn.toString());

        // Calling next() triggers tokenization; toString now lists all tokens
        tkn.next();
        assertEquals("StringTokenizer[a, b, c, d, e]", tkn.toString());
    }
}
