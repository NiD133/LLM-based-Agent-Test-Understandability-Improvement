package org.jsoup.parser;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TokenQueueTest_consumeEscapedId {
    @Test
    public void consumeEscapedId() {
        TokenQueue queue = new TokenQueue("i\\.d i\\\\d");

        assertEquals("i.d", queue.consumeCssIdentifier());
        assertTrue(queue.consumeWhitespace());
        assertEquals("i\\d", queue.consumeCssIdentifier());
        assertTrue(queue.isEmpty());
    }
}
