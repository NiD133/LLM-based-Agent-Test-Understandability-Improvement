package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

public class StringTokenizerTest_testReset {

    @Test
    void testReset() {
        final StringTokenizer tokenizer = new StringTokenizer("a b c");

        assertConsumesAllTokens(tokenizer);

        tokenizer.reset();

        assertConsumesAllTokens(tokenizer);
    }

    private void assertConsumesAllTokens(final StringTokenizer tokenizer) {
        assertEquals("a", tokenizer.next());
        assertEquals("b", tokenizer.next());
        assertEquals("c", tokenizer.next());
        assertFalse(tokenizer.hasNext());
    }
}
