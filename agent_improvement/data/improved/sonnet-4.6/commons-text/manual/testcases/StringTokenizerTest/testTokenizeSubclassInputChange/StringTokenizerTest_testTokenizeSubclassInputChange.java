package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

public class StringTokenizerTest_testTokenizeSubclassInputChange {

    /**
     * Verifies that a subclass can override {@code tokenize()} to substitute a completely different
     * char array, offset, and count from what was originally passed to the constructor.
     * <p>
     * "w x y z".toCharArray(), offset=2, count=5 covers the sub-range "x y z", whose
     * space-delimited tokens are "x", "y", "z" — regardless of the constructor argument "a b c d e".
     */
    @Test
    void testTokenizeSubclassInputChange() {
        final StringTokenizer tkn = new StringTokenizer("a b c d e") {

            @Override
            protected List<String> tokenize(final char[] chars, final int offset, final int count) {
                return super.tokenize("w x y z".toCharArray(), 2, 5);
            }
        };
        assertEquals("x", tkn.next());
        assertEquals("y", tkn.next());
    }
}
