package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

public class StringTokenizerTest_testTokenizeSubclassInputChange {

    @Test
    void testTokenizeSubclassInputChange() {
        final StringTokenizer tokenizer = new StringTokenizer("a b c d e") {

            @Override
            protected List<String> tokenize(final char[] chars, final int offset, final int count) {
                return super.tokenize("w x y z".toCharArray(), 2, 5);
            }
        };

        assertEquals("x", tokenizer.next());
        assertEquals("y", tokenizer.next());
    }
}
