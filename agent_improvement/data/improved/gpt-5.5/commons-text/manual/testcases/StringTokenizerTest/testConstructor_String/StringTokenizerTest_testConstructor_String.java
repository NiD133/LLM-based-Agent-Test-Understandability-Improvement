package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

public class StringTokenizerTest_testConstructor_String {

    private static final String TWO_WORD_INPUT = "a b";
    private static final String FIRST_TOKEN = "a";
    private static final String SECOND_TOKEN = "b";

    private void assertHasNoMoreTokens(final StringTokenizer tokenizer) {
        assertFalse(tokenizer.hasNext());
    }

    @Test
    void testConstructor_String() {
        StringTokenizer tokenizer = new StringTokenizer(TWO_WORD_INPUT);
        assertEquals(FIRST_TOKEN, tokenizer.next());
        assertEquals(SECOND_TOKEN, tokenizer.next());
        assertHasNoMoreTokens(tokenizer);

        tokenizer = new StringTokenizer("");
        assertHasNoMoreTokens(tokenizer);

        tokenizer = new StringTokenizer((String) null);
        assertHasNoMoreTokens(tokenizer);
    }
}
