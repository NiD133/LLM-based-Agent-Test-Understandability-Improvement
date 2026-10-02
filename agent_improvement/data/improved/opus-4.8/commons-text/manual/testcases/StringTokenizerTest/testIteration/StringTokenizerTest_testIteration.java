package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.NoSuchElementException;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link StringTokenizer} behaves as a forward/backward
 * {@code ListIterator} over the tokens of {@code "a b c"} (split on the
 * default whitespace delimiter), and that the mutating iterator operations
 * are not supported.
 */
public class StringTokenizerTest_testIteration {

    @Test
    void testIteration() {
        // Tokenizes "a b c" into the three tokens: "a", "b", "c".
        final StringTokenizer tokenizer = new StringTokenizer("a b c");

        // Positioned before the first token: nothing precedes the cursor.
        assertFalse(tokenizer.hasPrevious());
        assertThrows(NoSuchElementException.class, tokenizer::previous);

        // Read the first token "a".
        assertTrue(tokenizer.hasNext());
        assertEquals("a", tokenizer.next());

        // The iterator is read-only: structural modifications are rejected.
        assertThrows(UnsupportedOperationException.class, tokenizer::remove);
        assertThrows(UnsupportedOperationException.class, () -> tokenizer.set("x"));
        assertThrows(UnsupportedOperationException.class, () -> tokenizer.add("y"));

        // After "a": both a previous ("a") and a next ("b") token exist.
        assertTrue(tokenizer.hasPrevious());
        assertTrue(tokenizer.hasNext());
        assertEquals("b", tokenizer.next());

        // After "b": still surrounded by tokens, next is "c".
        assertTrue(tokenizer.hasPrevious());
        assertTrue(tokenizer.hasNext());
        assertEquals("c", tokenizer.next());

        // After the last token "c": a previous exists but no next remains.
        assertTrue(tokenizer.hasPrevious());
        assertFalse(tokenizer.hasNext());
        assertThrows(NoSuchElementException.class, tokenizer::next);

        // Position is unchanged by the failed next() call.
        assertTrue(tokenizer.hasPrevious());
        assertFalse(tokenizer.hasNext());
    }
}
