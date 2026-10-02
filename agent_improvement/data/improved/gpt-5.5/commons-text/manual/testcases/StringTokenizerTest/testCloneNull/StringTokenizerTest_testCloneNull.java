package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

public class StringTokenizerTest_testCloneNull {

    @Test
    void testCloneNull() {
        final StringTokenizer tokenizer = new StringTokenizer((char[]) null);

        assertNull(tokenizer.nextToken());
        tokenizer.reset();
        assertNull(tokenizer.nextToken());

        final StringTokenizer clonedTokenizer = (StringTokenizer) tokenizer.clone();

        tokenizer.reset();
        assertNull(tokenizer.nextToken());
        assertNull(clonedTokenizer.nextToken());
    }
}
