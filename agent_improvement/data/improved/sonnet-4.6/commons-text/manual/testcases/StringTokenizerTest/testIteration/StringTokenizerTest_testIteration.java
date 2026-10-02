package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.text.matcher.StringMatcher;
import org.apache.commons.text.matcher.StringMatcherFactory;
import org.junit.jupiter.api.Test;

public class StringTokenizerTest_testIteration {

    private static final String CSV_SIMPLE_FIXTURE = "A,b,c";

    private static final String TSV_SIMPLE_FIXTURE = "A\tb\tc";

    /** Asserts that the given tokenizer is not the shared CSV or TSV singleton instance. */
    private void checkClone(final StringTokenizer tokenizer) {
        assertNotSame(StringTokenizer.getCSVInstance(), tokenizer);
        assertNotSame(StringTokenizer.getTSVInstance(), tokenizer);
    }

    /** Exercises forward/backward iteration on a CSV-formatted string tokenizer. */
    private void testCSV(final String data) {
        testXSVAbc(StringTokenizer.getCSVInstance(data));
        testXSVAbc(StringTokenizer.getCSVInstance(data.toCharArray()));
    }

    /** Asserts that an empty tokenizer has no tokens and throws on iteration attempts. */
    void testEmpty(final StringTokenizer tokenizer) {
        checkClone(tokenizer);
        assertFalse(tokenizer.hasNext());
        assertFalse(tokenizer.hasPrevious());
        assertNull(tokenizer.nextToken());
        assertEquals(0, tokenizer.size());
        assertThrows(NoSuchElementException.class, tokenizer::next);
    }

    /**
     * Exercises full bidirectional traversal of a three-token ("A", "b", "c") tokenizer,
     * verifying index positions and boundary behaviour at both ends.
     */
    void testXSVAbc(final StringTokenizer tokenizer) {
        checkClone(tokenizer);

        // Initial state: cursor is before the first token
        assertEquals(-1, tokenizer.previousIndex());
        assertEquals(0, tokenizer.nextIndex());
        assertNull(tokenizer.previousToken());

        // Forward pass: consume all three tokens and check the advancing cursor
        assertEquals("A", tokenizer.nextToken());
        assertEquals(1, tokenizer.nextIndex());
        assertEquals("b", tokenizer.nextToken());
        assertEquals(2, tokenizer.nextIndex());
        assertEquals("c", tokenizer.nextToken());
        assertEquals(3, tokenizer.nextIndex());

        // Past-the-end: nextToken returns null and the index stays at size
        assertNull(tokenizer.nextToken());
        assertEquals(3, tokenizer.nextIndex());

        // Backward pass: consume tokens in reverse and check the retreating cursor
        assertEquals("c", tokenizer.previousToken());
        assertEquals(2, tokenizer.nextIndex());
        assertEquals("b", tokenizer.previousToken());
        assertEquals(1, tokenizer.nextIndex());
        assertEquals("A", tokenizer.previousToken());
        assertEquals(0, tokenizer.nextIndex());

        // Before-the-start: previousToken returns null and the index stays at 0
        assertNull(tokenizer.previousToken());
        assertEquals(0, tokenizer.nextIndex());
        assertEquals(-1, tokenizer.previousIndex());

        assertEquals(3, tokenizer.size());
    }

    @Test
    void testIteration() {
        final StringTokenizer tkn = new StringTokenizer("a b c");

        // Initial state: cursor is before the first token, so no previous exists
        assertFalse(tkn.hasPrevious());
        assertThrows(NoSuchElementException.class, tkn::previous);

        // Advance to the first token; ListIterator mutation operations are unsupported
        assertTrue(tkn.hasNext());
        assertEquals("a", tkn.next());
        assertThrows(UnsupportedOperationException.class, tkn::remove);
        assertThrows(UnsupportedOperationException.class, () -> tkn.set("x"));
        assertThrows(UnsupportedOperationException.class, () -> tkn.add("y"));

        // After consuming "a" the cursor sits between "a" and "b": both directions available
        assertTrue(tkn.hasPrevious());
        assertTrue(tkn.hasNext());

        // Advance through the remaining tokens
        assertEquals("b", tkn.next());
        assertTrue(tkn.hasPrevious());
        assertTrue(tkn.hasNext());

        assertEquals("c", tkn.next());

        // Past-the-end state: previous exists but forward iteration is exhausted
        assertTrue(tkn.hasPrevious());
        assertFalse(tkn.hasNext());
        assertThrows(NoSuchElementException.class, tkn::next);

        // State is unchanged after the failed next() call
        assertTrue(tkn.hasPrevious());
        assertFalse(tkn.hasNext());
    }
}
