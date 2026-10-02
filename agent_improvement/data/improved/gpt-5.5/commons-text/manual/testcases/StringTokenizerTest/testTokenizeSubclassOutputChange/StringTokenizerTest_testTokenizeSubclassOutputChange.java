package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

public class StringTokenizerTest_testTokenizeSubclassOutputChange {

    @Test
    void testTokenizeSubclassOutputChange() {
        final StringTokenizer tokenizer = new StringTokenizer("a b c") {

            @Override
            protected List<String> tokenize(final char[] chars, final int offset, final int count) {
                final List<String> tokens = super.tokenize(chars, offset, count);
                Collections.reverse(tokens);
                return tokens;
            }
        };

        assertEquals("c", tokenizer.next());
        assertEquals("b", tokenizer.next());
        assertEquals("a", tokenizer.next());
    }
}
