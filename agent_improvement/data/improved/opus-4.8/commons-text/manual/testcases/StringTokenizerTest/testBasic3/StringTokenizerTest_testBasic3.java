package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

/**
 * Verifies the default tokenization behaviour of {@link StringTokenizer}.
 * <p>
 * By default a {@code StringTokenizer} splits on whitespace-style delimiters
 * (space, tab, newline and form feed) and ignores empty tokens. Characters that
 * are <em>not</em> delimiters - such as the SOH control character ({@code })
 * - are treated as ordinary token content.
 */
public class StringTokenizerTest_testBasic3 {

    /** Start Of Heading (SOH) control char; not a delimiter, so it stays inside a token. */
    private static final char SOH = '';

    /** Form feed control char; one of the default delimiters. */
    private static final char FORM_FEED = '\f';

    @Test
    void testBasic3() {
        // Layout: "a" <space><newline> "b" <SOH> <form-feed> "c"
        // The run of space+newline collapses to a single split (empty tokens are ignored),
        // SOH is kept as content, and the form feed separates "b<SOH>" from "c".
        final String input = "a \nb" + SOH + FORM_FEED + "c";
        final StringTokenizer tokenizer = new StringTokenizer(input);

        assertEquals("a", tokenizer.next());
        assertEquals("b" + SOH, tokenizer.next());
        assertEquals("c", tokenizer.next());
        assertFalse(tokenizer.hasNext());
    }
}
