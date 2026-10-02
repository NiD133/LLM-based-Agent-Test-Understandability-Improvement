package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.NoSuchElementException;

import org.junit.jupiter.api.Test;

public class StringTokenizerTest_testCSVSimple {

    private static final String CSV_SIMPLE_FIXTURE = "A,b,c";

    private void assertTokenizerIsFreshCsvOrTsvClone(final StringTokenizer tokenizer) {
        assertNotSame(StringTokenizer.getCSVInstance(), tokenizer);
        assertNotSame(StringTokenizer.getTSVInstance(), tokenizer);
    }

    private void assertCsvTokenizationFromStringAndChars(final String data) {
        assertIteratesOverAbcTokens(StringTokenizer.getCSVInstance(data));
        assertIteratesOverAbcTokens(StringTokenizer.getCSVInstance(data.toCharArray()));
    }

    void testEmpty(final StringTokenizer tokenizer) {
        assertTokenizerIsFreshCsvOrTsvClone(tokenizer);
        assertFalse(tokenizer.hasNext());
        assertFalse(tokenizer.hasPrevious());
        assertNull(tokenizer.nextToken());
        assertEquals(0, tokenizer.size());
        assertThrows(NoSuchElementException.class, tokenizer::next);
    }

    void assertIteratesOverAbcTokens(final StringTokenizer tokenizer) {
        assertTokenizerIsFreshCsvOrTsvClone(tokenizer);

        assertEquals(-1, tokenizer.previousIndex());
        assertEquals(0, tokenizer.nextIndex());
        assertNull(tokenizer.previousToken());

        assertEquals("A", tokenizer.nextToken());
        assertEquals(1, tokenizer.nextIndex());
        assertEquals("b", tokenizer.nextToken());
        assertEquals(2, tokenizer.nextIndex());
        assertEquals("c", tokenizer.nextToken());
        assertEquals(3, tokenizer.nextIndex());
        assertNull(tokenizer.nextToken());
        assertEquals(3, tokenizer.nextIndex());

        assertEquals("c", tokenizer.previousToken());
        assertEquals(2, tokenizer.nextIndex());
        assertEquals("b", tokenizer.previousToken());
        assertEquals(1, tokenizer.nextIndex());
        assertEquals("A", tokenizer.previousToken());
        assertEquals(0, tokenizer.nextIndex());
        assertNull(tokenizer.previousToken());

        assertEquals(0, tokenizer.nextIndex());
        assertEquals(-1, tokenizer.previousIndex());
        assertEquals(3, tokenizer.size());
    }

    @Test
    void testCSVSimple() {
        assertCsvTokenizationFromStringAndChars(CSV_SIMPLE_FIXTURE);
    }
}
