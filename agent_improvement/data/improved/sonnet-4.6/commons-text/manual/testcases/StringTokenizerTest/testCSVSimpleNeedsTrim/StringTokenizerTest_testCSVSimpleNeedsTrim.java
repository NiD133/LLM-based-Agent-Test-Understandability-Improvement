package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.NoSuchElementException;

import org.junit.jupiter.api.Test;

public class StringTokenizerTest_testCSVSimpleNeedsTrim {

    private static final String CSV_SIMPLE_FIXTURE = "A,b,c";

    private static final String TSV_SIMPLE_FIXTURE = "A\tb\tc";

    /**
     * Verifies that the given tokenizer is a fresh instance — not the shared CSV or TSV prototype.
     */
    private void checkClone(final StringTokenizer tokenizer) {
        assertNotSame(StringTokenizer.getCSVInstance(), tokenizer);
        assertNotSame(StringTokenizer.getTSVInstance(), tokenizer);
    }

    /**
     * Tests CSV parsing on both a String and a char[] representation of the same input,
     * asserting that both forms tokenize to the three tokens "A", "b", "c".
     */
    private void testCSV(final String data) {
        assertTokenizerIteratesAbc(StringTokenizer.getCSVInstance(data));
        assertTokenizerIteratesAbc(StringTokenizer.getCSVInstance(data.toCharArray()));
    }

    /**
     * Exhaustively walks the tokenizer forward and backward, confirming it yields
     * exactly three tokens ("A", "b", "c") in the correct order with correct index tracking.
     */
    void assertTokenizerIteratesAbc(final StringTokenizer tokenizer) {
        checkClone(tokenizer);

        // Verify initial state: positioned before the first token
        assertEquals(-1, tokenizer.previousIndex());
        assertEquals(0, tokenizer.nextIndex());
        assertNull(tokenizer.previousToken());

        // Walk forward through all three tokens
        assertEquals("A", tokenizer.nextToken());
        assertEquals(1, tokenizer.nextIndex());
        assertEquals("b", tokenizer.nextToken());
        assertEquals(2, tokenizer.nextIndex());
        assertEquals("c", tokenizer.nextToken());
        assertEquals(3, tokenizer.nextIndex());

        // Verify end-of-sequence: nextToken() returns null and index stays at 3
        assertNull(tokenizer.nextToken());
        assertEquals(3, tokenizer.nextIndex());

        // Walk backward through all three tokens
        assertEquals("c", tokenizer.previousToken());
        assertEquals(2, tokenizer.nextIndex());
        assertEquals("b", tokenizer.previousToken());
        assertEquals(1, tokenizer.nextIndex());
        assertEquals("A", tokenizer.previousToken());
        assertEquals(0, tokenizer.nextIndex());

        // Verify start-of-sequence: previousToken() returns null and index stays at 0
        assertNull(tokenizer.previousToken());
        assertEquals(0, tokenizer.nextIndex());
        assertEquals(-1, tokenizer.previousIndex());

        assertEquals(3, tokenizer.size());
    }

    /**
     * Verifies that the CSV tokenizer correctly trims leading (and optionally trailing)
     * whitespace — including spaces, tabs, and newlines — before parsing.
     *
     * Three representative inputs are exercised:
     *   1. Leading spaces only
     *   2. Leading spaces, newline, and tab
     *   3. Leading spaces/newlines and trailing newlines/carriage-return
     *
     * In all cases the tokens "A", "b", "c" must be returned.
     */
    @Test
    void testCSVSimpleNeedsTrim() {
        String csvWithLeadingSpaces = "   " + CSV_SIMPLE_FIXTURE;
        String csvWithLeadingMixedWhitespace = "   \n\t  " + CSV_SIMPLE_FIXTURE;
        String csvWithLeadingAndTrailingWhitespace = "   \n  " + CSV_SIMPLE_FIXTURE + "\n\n\r";

        testCSV(csvWithLeadingSpaces);
        testCSV(csvWithLeadingMixedWhitespace);
        testCSV(csvWithLeadingAndTrailingWhitespace);
    }
}
