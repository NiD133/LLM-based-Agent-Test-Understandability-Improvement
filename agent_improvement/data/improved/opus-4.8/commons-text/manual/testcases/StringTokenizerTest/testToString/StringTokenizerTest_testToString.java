package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class StringTokenizerTest_testToString {

    @Test
    void testToString() {
        final StringTokenizer tokenizer = new StringTokenizer("a b c d e");

        // Before the input is consumed, toString() reports that no tokenizing has happened yet.
        assertEquals("StringTokenizer[not tokenized yet]", tokenizer.toString());

        // Reading a token triggers tokenizing, so toString() now lists all tokens.
        tokenizer.next();
        assertEquals("StringTokenizer[a, b, c, d, e]", tokenizer.toString());
    }
}
