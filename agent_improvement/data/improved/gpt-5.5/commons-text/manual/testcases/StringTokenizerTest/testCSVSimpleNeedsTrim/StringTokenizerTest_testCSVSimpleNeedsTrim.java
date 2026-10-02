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
    private static final String[] EXPECTED_TOKENS = {"A", "b", "c"};

    private void assertCsvPrototypeWasCloned(final StringTokenizer tokenizer) {
        assertNotSame(StringTokenizer.getCSVInstance(), tokenizer);
        assertNotSame(StringTokenizer.getTSVInstance(), tokenizer);
    }

    private void assertSimpleCsvTokens(final String input) {
        assertTokenizerTraversesExpectedTokens(StringTokenizer.getCSVInstance(input));
        assertTokenizerTraversesExpectedTokens(StringTokenizer.getCSVInstance(input.toCharArray()));
    }

    void testEmpty(final StringTokenizer tokenizer) {
        assertCsvPrototypeWasCloned(tokenizer);
        assertFalse(tokenizer.hasNext());
        assertFalse(tokenizer.hasPrevious());
        assertNull(tokenizer.nextToken());
        assertEquals(0, tokenizer.size());
        assertThrows(NoSuchElementException.class, tokenizer::next);
    }

    void assertTokenizerTraversesExpectedTokens(final StringTokenizer tokenizer) {
        assertCsvPrototypeWasCloned(tokenizer);

        assertEquals(-1, tokenizer.previousIndex());
        assertEquals(0, tokenizer.nextIndex());
        assertNull(tokenizer.previousToken());

        assertEquals(EXPECTED_TOKENS[0], tokenizer.nextToken());
        assertEquals(1, tokenizer.nextIndex());
        assertEquals(EXPECTED_TOKENS[1], tokenizer.nextToken());
        assertEquals(2, tokenizer.nextIndex());
        assertEquals(EXPECTED_TOKENS[2], tokenizer.nextToken());
        assertEquals(3, tokenizer.nextIndex());
        assertNull(tokenizer.nextToken());
        assertEquals(3, tokenizer.nextIndex());

        assertEquals(EXPECTED_TOKENS[2], tokenizer.previousToken());
        assertEquals(2, tokenizer.nextIndex());
        assertEquals(EXPECTED_TOKENS[1], tokenizer.previousToken());
        assertEquals(1, tokenizer.nextIndex());
        assertEquals(EXPECTED_TOKENS[0], tokenizer.previousToken());
        assertEquals(0, tokenizer.nextIndex());
        assertNull(tokenizer.previousToken());

        assertEquals(0, tokenizer.nextIndex());
        assertEquals(-1, tokenizer.previousIndex());
        assertEquals(EXPECTED_TOKENS.length, tokenizer.size());
    }

    @Test
    void testCSVSimpleNeedsTrim() {
        assertSimpleCsvTokens("   " + CSV_SIMPLE_FIXTURE);
        assertSimpleCsvTokens("   \n\t  " + CSV_SIMPLE_FIXTURE);
        assertSimpleCsvTokens("   \n  " + CSV_SIMPLE_FIXTURE + "\n\n\r");
    }
}
