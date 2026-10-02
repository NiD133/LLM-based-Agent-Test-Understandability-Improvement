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
import org.junit.jupiter.api.Test;

public class StrTokenizerTest_testConstructor_charArray_char {

    private static final String CSV_SIMPLE_FIXTURE = "A,b,c";

    private static final String TSV_SIMPLE_FIXTURE = "A\tb\tc";

    private void checkClone(final StrTokenizer tokenizer) {
        assertNotSame(StrTokenizer.getCSVInstance(), tokenizer);
        assertNotSame(StrTokenizer.getTSVInstance(), tokenizer);
    }

    private void testCSV(final String data) {
        testXSVAbc(StrTokenizer.getCSVInstance(data));
        testXSVAbc(StrTokenizer.getCSVInstance(data.toCharArray()));
    }

    void testEmpty(final StrTokenizer tokenizer) {
        checkClone(tokenizer);
        assertFalse(tokenizer.hasNext());
        assertFalse(tokenizer.hasPrevious());
        assertNull(tokenizer.nextToken());
        assertEquals(0, tokenizer.size());
        assertThrows(NoSuchElementException.class, tokenizer::next);
    }

    void testXSVAbc(final StrTokenizer tokenizer) {
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
    void testConstructor_charArray_char() {
        StrTokenizer tok = new StrTokenizer("a b".toCharArray(), ' ');
        assertEquals(1, tok.getDelimiterMatcher().isMatch(" ".toCharArray(), 0, 0, 1));
        assertEquals("a", tok.next());
        assertEquals("b", tok.next());
        assertFalse(tok.hasNext());
        tok = new StrTokenizer(ArrayUtils.EMPTY_CHAR_ARRAY, ' ');
        assertFalse(tok.hasNext());
        tok = new StrTokenizer((char[]) null, ' ');
        assertFalse(tok.hasNext());
    }
}
