package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link StringTokenizer#getCSVInstance} trims surrounding whitespace
 * before splitting, so that padded CSV input still yields the clean tokens "A", "b", "c".
 */
public class StringTokenizerTest_testCSVSimpleNeedsTrim {

    /** The clean CSV payload whose tokens are always "A", "b", "c". */
    private static final String CSV_PAYLOAD = "A,b,c";

    /**
     * A freshly created CSV instance must be a distinct object from the shared
     * CSV and TSV prototypes (i.e. each call hands back an independent clone).
     */
    private void assertIsIndependentClone(final StringTokenizer tokenizer) {
        assertNotSame(StringTokenizer.getCSVInstance(), tokenizer);
        assertNotSame(StringTokenizer.getTSVInstance(), tokenizer);
    }

    /**
     * Parses the given CSV input both as a String and as a char[] and checks that
     * each form produces the expected "A", "b", "c" tokens.
     */
    private void assertParsesToAbc(final String input) {
        assertAbcTokensWithFullIteration(StringTokenizer.getCSVInstance(input));
        assertAbcTokensWithFullIteration(StringTokenizer.getCSVInstance(input.toCharArray()));
    }

    /**
     * Walks the tokenizer forward through "A", "b", "c" and then back again,
     * asserting the cursor indices and returned tokens at every step.
     */
    private void assertAbcTokensWithFullIteration(final StringTokenizer tokenizer) {
        assertIsIndependentClone(tokenizer);

        // Cursor starts before the first token.
        assertEquals(-1, tokenizer.previousIndex());
        assertEquals(0, tokenizer.nextIndex());
        assertNull(tokenizer.previousToken());

        // Forward pass: A, b, c.
        assertEquals("A", tokenizer.nextToken());
        assertEquals(1, tokenizer.nextIndex());
        assertEquals("b", tokenizer.nextToken());
        assertEquals(2, tokenizer.nextIndex());
        assertEquals("c", tokenizer.nextToken());
        assertEquals(3, tokenizer.nextIndex());

        // Past the end: no more tokens, cursor unchanged.
        assertNull(tokenizer.nextToken());
        assertEquals(3, tokenizer.nextIndex());

        // Backward pass: c, b, A.
        assertEquals("c", tokenizer.previousToken());
        assertEquals(2, tokenizer.nextIndex());
        assertEquals("b", tokenizer.previousToken());
        assertEquals(1, tokenizer.nextIndex());
        assertEquals("A", tokenizer.previousToken());
        assertEquals(0, tokenizer.nextIndex());

        // Before the start: no more tokens, cursor back at the beginning.
        assertNull(tokenizer.previousToken());
        assertEquals(0, tokenizer.nextIndex());
        assertEquals(-1, tokenizer.previousIndex());

        assertEquals(3, tokenizer.size());
    }

    @Test
    void testCSVSimpleNeedsTrim() {
        // Leading spaces should be trimmed away.
        assertParsesToAbc("   " + CSV_PAYLOAD);
        // Leading mixed whitespace (spaces, newlines, tabs) should be trimmed away.
        assertParsesToAbc("   \n\t  " + CSV_PAYLOAD);
        // Both leading and trailing whitespace should be trimmed away.
        assertParsesToAbc("   \n  " + CSV_PAYLOAD + "\n\n\r");
    }
}
