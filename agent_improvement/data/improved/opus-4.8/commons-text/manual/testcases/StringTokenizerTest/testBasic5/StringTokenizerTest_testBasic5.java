package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link StringTokenizer} splits on a delimiter character while
 * honoring a quote character: a quote opened mid-token suppresses the delimiter
 * until the quote is balanced.
 */
public class StringTokenizerTest_testBasic5 {

    @Test
    void splitsOnDelimiterWhileRespectingQuoteCharacter() {
        final String input = "a:b':c";
        final char delimiter = ':';
        final char quote = '\'';

        final StringTokenizer tokenizer = new StringTokenizer(input, delimiter, quote);

        assertEquals("a", tokenizer.next());
        assertEquals("b'", tokenizer.next());
        assertEquals("c", tokenizer.next());
        assertFalse(tokenizer.hasNext());
    }
}
