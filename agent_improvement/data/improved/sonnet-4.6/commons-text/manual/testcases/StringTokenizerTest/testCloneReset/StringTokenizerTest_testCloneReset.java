package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class StringTokenizerTest_testCloneReset {

    @Test
    void testCloneReset() {
        final char[] input = { 'a' };
        final StringTokenizer tokenizer = new StringTokenizer(input);

        // Verify basic tokenization and that reset() repositions the tokenizer
        assertEquals("a", tokenizer.nextToken());
        tokenizer.reset(input);
        assertEquals("a", tokenizer.nextToken());

        // Clone captures a snapshot of the current tokenizer state (including buffered content)
        final StringTokenizer clonedTokenizer = (StringTokenizer) tokenizer.clone();

        // Mutate the shared array and reset the original — clone must be unaffected
        input[0] = 'b';
        tokenizer.reset(input);
        assertEquals("b", tokenizer.nextToken(),   "original tokenizer sees mutated input after reset");
        assertEquals("a", clonedTokenizer.nextToken(), "clone retains its own copy of the pre-mutation content");
    }
}
