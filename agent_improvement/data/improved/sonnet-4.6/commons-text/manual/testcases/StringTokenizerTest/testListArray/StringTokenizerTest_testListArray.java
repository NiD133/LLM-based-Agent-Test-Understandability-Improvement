package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.Test;

public class StringTokenizerTest_testListArray {

    private static final String CSV_SIMPLE_FIXTURE = "A,b,c";

    private static final String TSV_SIMPLE_FIXTURE = "A\tb\tc";

    private void checkClone(final StringTokenizer tokenizer) {
        assertNotSame(StringTokenizer.getCSVInstance(), tokenizer);
        assertNotSame(StringTokenizer.getTSVInstance(), tokenizer);
    }

    private void testCSV(final String data) {
        testXSVAbc(StringTokenizer.getCSVInstance(data));
        testXSVAbc(StringTokenizer.getCSVInstance(data.toCharArray()));
    }

    void testEmpty(final StringTokenizer tokenizer) {
        checkClone(tokenizer);
        assertFalse(tokenizer.hasNext());
        assertFalse(tokenizer.hasPrevious());
        assertNull(tokenizer.nextToken());
        assertEquals(0, tokenizer.size());
        assertThrows(NoSuchElementException.class, tokenizer::next);
    }

    void testXSVAbc(final StringTokenizer tokenizer) {
        checkClone(tokenizer);
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
    void testListArray() {
        final String input = "a  b c";
        final StringTokenizer tokenizer = new StringTokenizer(input);

        // Both getTokenArray() and getTokenList() should return the same tokens
        final String[] tokenArray = tokenizer.getTokenArray();
        final List<String> tokenList = tokenizer.getTokenList();
        assertEquals(Arrays.asList(tokenArray), tokenList);
        assertEquals(3, tokenList.size());

        // The returned list is an independent copy — mutations do not affect the tokenizer
        tokenList.set(0, "z");
        tokenList.remove(1);
        tokenList.set(1, "y");
        tokenList.add("x");
        assertEquals(Arrays.asList("z", "y", "x"), tokenList);

        // The tokenizer's internal state is unchanged after modifying the returned list
        assertEquals(Arrays.asList(tokenArray), tokenizer.getTokenList());
        assertEquals("a", tokenizer.next());
        assertEquals("b", tokenizer.next());
        assertEquals("c", tokenizer.next());
    }
}
