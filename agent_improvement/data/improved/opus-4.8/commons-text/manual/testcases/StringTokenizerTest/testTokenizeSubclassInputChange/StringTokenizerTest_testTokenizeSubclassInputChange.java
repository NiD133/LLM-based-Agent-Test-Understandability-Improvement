package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

public class StringTokenizerTest_testTokenizeSubclassInputChange {

    /**
     * Verifies that a subclass can completely substitute the input that is tokenized
     * by overriding {@link StringTokenizer#tokenize(char[], int, int)}.
     *
     * <p>The tokenizer is constructed with the text {@code "a b c d e"}, but the overridden
     * {@code tokenize} ignores those arguments and instead tokenizes the slice of
     * {@code "w x y z"} starting at offset 2 with length 5, i.e. the substring {@code "x y z"}.
     * The expected tokens are therefore {@code "x"}, {@code "y"}, {@code "z"}.</p>
     */
    @Test
    void testTokenizeSubclassInputChange() {
        final StringTokenizer tokenizer = new StringTokenizer("a b c d e") {

            @Override
            protected List<String> tokenize(final char[] chars, final int offset, final int count) {
                // Ignore the constructor input; tokenize "x y z" (offset 2, length 5 of "w x y z") instead.
                return super.tokenize("w x y z".toCharArray(), 2, 5);
            }
        };

        assertEquals("x", tokenizer.next());
        assertEquals("y", tokenizer.next());
    }
}
