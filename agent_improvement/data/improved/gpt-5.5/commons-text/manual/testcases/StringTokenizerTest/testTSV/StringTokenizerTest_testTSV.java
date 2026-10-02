package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

public class StringTokenizerTest_testTSV {

    private static final String THREE_COLUMN_TSV = "A\tb\tc";

    private void assertTokenizerIsClone(final StringTokenizer tokenizer) {
        assertNotSame(StringTokenizer.getCSVInstance(), tokenizer);
    }

    private void assertThreeColumnTokenizerNavigation(final StringTokenizer tokenizer) {
        assertTokenizerIsClone(tokenizer);

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
    void testTSV() {
        assertThreeColumnTokenizerNavigation(StringTokenizer.getTSVInstance(THREE_COLUMN_TSV));
        assertThreeColumnTokenizerNavigation(StringTokenizer.getTSVInstance(THREE_COLUMN_TSV.toCharArray()));
    }
}
