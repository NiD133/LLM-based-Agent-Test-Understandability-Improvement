package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

public class StringTokenizerTest_testTokenizeSubclassOutputChange {

    /**
     * Verifies that a subclass can change the tokenizer's output by overriding
     * {@link StringTokenizer#tokenize(char[], int, int)}.
     *
     * <p>Here the override reverses the token list produced by the superclass,
     * so iterating "a b c" yields the tokens in reverse order: c, b, a.</p>
     */
    @Test
    void testTokenizeSubclassOutputChange() {
        final StringTokenizer reverseOrderTokenizer = new StringTokenizer("a b c") {
            @Override
            protected List<String> tokenize(final char[] chars, final int offset, final int count) {
                final List<String> tokens = super.tokenize(chars, offset, count);
                Collections.reverse(tokens);
                return tokens;
            }
        };

        assertEquals("c", reverseOrderTokenizer.next());
        assertEquals("b", reverseOrderTokenizer.next());
        assertEquals("a", reverseOrderTokenizer.next());
    }
}
