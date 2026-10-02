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

public class StrTokenizerTest_test6 {

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
    void test6() {
        final String input = "a;b; c;\"d;\"\"e\";f; ; ;";
        final StrTokenizer tok = new StrTokenizer(input);
        tok.setDelimiterChar(';');
        tok.setQuoteChar('"');
        tok.setIgnoredMatcher(StrMatcher.trimMatcher());
        tok.setIgnoreEmptyTokens(false);
        // tok.setTreatingEmptyAsNull(true);
        final String[] tokens = tok.getTokenArray();
        final String[] expected = { "a", "b", " c", "d;\"e", "f", null, null, null };
        int nextCount = 0;
        while (tok.hasNext()) {
            tok.next();
            nextCount++;
        }
        int prevCount = 0;
        while (tok.hasPrevious()) {
            tok.previous();
            prevCount++;
        }
        assertEquals(expected.length, tokens.length, Arrays.toString(tokens));
        assertEquals(nextCount, expected.length, "could not cycle through entire token list using the 'hasNext' and 'next' methods");
        assertEquals(prevCount, expected.length, "could not cycle through entire token list using the 'hasPrevious' and 'previous' methods");
    }
}
