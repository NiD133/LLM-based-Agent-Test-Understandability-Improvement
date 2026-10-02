package org.jsoup.parser;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TokenQueueTest_consumeEscapedId {

    /**
     * Verifies that {@link TokenQueue#consumeCssIdentifier()} unescapes backslash escapes
     * within CSS identifiers, and that consecutive identifiers separated by whitespace are
     * consumed one at a time until the queue is empty.
     *
     * <p>Input {@code "i\.d i\\d"} contains two whitespace-separated identifiers:
     * <ul>
     *   <li>{@code i\.d} — an escaped dot, which unescapes to {@code i.d}</li>
     *   <li>{@code i\\d} — an escaped backslash, which unescapes to {@code i\d}</li>
     * </ul>
     */
    @Test
    public void consumeEscapedId() {
        // Java string literal "i\\.d i\\\\d" represents the raw text:  i\.d i\\d
        TokenQueue queue = new TokenQueue("i\\.d i\\\\d");

        // First identifier: the escaped dot unescapes to a literal dot.
        assertEquals("i.d", queue.consumeCssIdentifier());

        // The space between the two identifiers is consumed as whitespace.
        assertTrue(queue.consumeWhitespace());

        // Second identifier: the escaped backslash unescapes to a single backslash.
        assertEquals("i\\d", queue.consumeCssIdentifier());

        // All input has now been consumed.
        assertTrue(queue.isEmpty());
    }
}
