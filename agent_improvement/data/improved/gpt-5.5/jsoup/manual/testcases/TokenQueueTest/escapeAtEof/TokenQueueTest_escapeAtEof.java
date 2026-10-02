package org.jsoup.parser;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TokenQueueTest_escapeAtEof {

    @Test
    void escapeAtEof() {
        TokenQueue queue = new TokenQueue("Foo\\");

        String selector = queue.consumeElementSelector();

        assertEquals("Foo", selector);
    }
}
