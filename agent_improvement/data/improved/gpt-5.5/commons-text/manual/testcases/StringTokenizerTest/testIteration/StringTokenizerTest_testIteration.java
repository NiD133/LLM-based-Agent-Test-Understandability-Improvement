package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.NoSuchElementException;

import org.junit.jupiter.api.Test;

public class StringTokenizerTest_testIteration {

    @Test
    void testIteration() {
        final StringTokenizer tokenizer = new StringTokenizer("a b c");

        assertAtStart(tokenizer);
        assertNextToken(tokenizer, "a");
        assertUnsupportedIteratorMutations(tokenizer);

        assertCanMoveBothDirections(tokenizer);
        assertNextToken(tokenizer, "b");

        assertCanMoveBothDirections(tokenizer);
        assertNextToken(tokenizer, "c");

        assertAtEnd(tokenizer);
        assertThrows(NoSuchElementException.class, tokenizer::next);
        assertAtEnd(tokenizer);
    }

    private void assertAtStart(final StringTokenizer tokenizer) {
        assertFalse(tokenizer.hasPrevious());
        assertThrows(NoSuchElementException.class, tokenizer::previous);
        assertTrue(tokenizer.hasNext());
    }

    private void assertUnsupportedIteratorMutations(final StringTokenizer tokenizer) {
        assertThrows(UnsupportedOperationException.class, tokenizer::remove);
        assertThrows(UnsupportedOperationException.class, () -> tokenizer.set("x"));
        assertThrows(UnsupportedOperationException.class, () -> tokenizer.add("y"));
    }

    private void assertCanMoveBothDirections(final StringTokenizer tokenizer) {
        assertTrue(tokenizer.hasPrevious());
        assertTrue(tokenizer.hasNext());
    }

    private void assertAtEnd(final StringTokenizer tokenizer) {
        assertTrue(tokenizer.hasPrevious());
        assertFalse(tokenizer.hasNext());
    }

    private void assertNextToken(final StringTokenizer tokenizer, final String expectedToken) {
        assertEquals(expectedToken, tokenizer.next());
    }
}
