package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link StringTokenizer} splits a string on a single
 * character delimiter and yields each token in order via {@code next()}.
 */
public class StringTokenizerTest_testBasicDelim1 {

    @Test
    void testBasicDelim1() {
        // Input "a:b:c" split on the ':' delimiter should yield "a", "b", "c".
        final StringTokenizer tokenizer = new StringTokenizer("a:b:c", ':');

        assertEquals("a", tokenizer.next());
        assertEquals("b", tokenizer.next());
        assertEquals("c", tokenizer.next());

        // No further tokens remain after the last element.
        assertFalse(tokenizer.hasNext());
    }
}
