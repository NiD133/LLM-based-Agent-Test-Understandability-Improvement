package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

public class StringTokenizerTest_testGetContent {

    private static final String CONTENT = "a   b c \"d e\" f ";

    @Test
    void testGetContent() {
        StringTokenizer tokenizer = new StringTokenizer(CONTENT);
        assertEquals(CONTENT, tokenizer.getContent());

        tokenizer = new StringTokenizer(CONTENT.toCharArray());
        assertEquals(CONTENT, tokenizer.getContent());

        tokenizer = new StringTokenizer();
        assertNull(tokenizer.getContent());
    }
}
