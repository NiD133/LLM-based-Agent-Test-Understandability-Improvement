package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.apache.commons.lang3.ArrayUtils;
import org.junit.jupiter.api.Test;

public class StringTokenizerTest_testConstructor_charArray {

    @Test
    void testConstructor_charArray() {
        StringTokenizer tokenizer = new StringTokenizer("a b".toCharArray());
        assertEquals("a", tokenizer.next());
        assertEquals("b", tokenizer.next());
        assertFalse(tokenizer.hasNext());

        tokenizer = new StringTokenizer(ArrayUtils.EMPTY_CHAR_ARRAY);
        assertFalse(tokenizer.hasNext());

        tokenizer = new StringTokenizer((char[]) null);
        assertFalse(tokenizer.hasNext());
    }
}
