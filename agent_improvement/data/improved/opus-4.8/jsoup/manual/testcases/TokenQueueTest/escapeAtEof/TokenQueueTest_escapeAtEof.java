package org.jsoup.parser;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TokenQueueTest_escapeAtEof {

    /**
     * A trailing backslash with no following character is not a valid escape sequence, so
     * {@link TokenQueue#consumeElementSelector()} should ignore it and return only the
     * preceding identifier characters.
     */
    @Test
    void escapeAtEof() {
        TokenQueue queue = new TokenQueue("Foo\\");

        String elementSelector = queue.consumeElementSelector();

        // The dangling backslash is dropped; we are left with just "Foo".
        assertEquals("Foo", elementSelector);
    }
}
