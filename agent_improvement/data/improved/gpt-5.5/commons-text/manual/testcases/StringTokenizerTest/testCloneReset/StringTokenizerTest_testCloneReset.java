package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class StringTokenizerTest_testCloneReset {

    @Test
    void testCloneReset() {
        final char[] input = { 'a' };
        final StringTokenizer tokenizer = new StringTokenizer(input);

        assertEquals("a", tokenizer.nextToken());

        tokenizer.reset(input);
        assertEquals("a", tokenizer.nextToken());

        final StringTokenizer clonedTokenizer = (StringTokenizer) tokenizer.clone();

        input[0] = 'b';
        tokenizer.reset(input);

        assertEquals("b", tokenizer.nextToken());
        assertEquals("a", clonedTokenizer.nextToken());
    }
}
