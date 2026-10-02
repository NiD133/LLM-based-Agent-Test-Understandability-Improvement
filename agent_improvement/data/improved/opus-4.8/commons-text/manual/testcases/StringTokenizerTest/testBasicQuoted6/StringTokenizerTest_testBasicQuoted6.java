package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.apache.commons.text.matcher.StringMatcherFactory;
import org.junit.jupiter.api.Test;

public class StringTokenizerTest_testBasicQuoted6 {

    /**
     * Verifies that a quoted section is unwrapped while a delimiter that appears
     * inside the quotes is treated as ordinary text rather than a separator.
     *
     * <p>For the input {@code a:'b'"c':d} split on {@code ':'} with the standard
     * quote matcher:</p>
     * <ul>
     *   <li>{@code a} is the first token, ending at the first {@code ':'} delimiter.</li>
     *   <li>The second token spans {@code 'b'"c':d}: the quotes around
     *       {@code b"c':d} are stripped, and the {@code ':'} inside the quoted
     *       region is kept verbatim, yielding {@code b"c:d}.</li>
     * </ul>
     */
    @Test
    void quotedSectionPreservesDelimiterAndUnwrapsQuotes() {
        final String input = "a:'b'\"c':d";

        final StringTokenizer tokenizer = new StringTokenizer(input, ':');
        tokenizer.setQuoteMatcher(StringMatcherFactory.INSTANCE.quoteMatcher());

        assertEquals("a", tokenizer.next());
        assertEquals("b\"c:d", tokenizer.next());
        assertFalse(tokenizer.hasNext());
    }
}
