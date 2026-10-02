package org.jsoup.parser;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TokenQueueTest_consumeEscapedTag {
    private static final String ESCAPED_TAG_SEQUENCE = "p\\\\p p\\.p p\\:p p\\!p";

    @Test
    public void consumeEscapedTag() {
        TokenQueue queue = new TokenQueue(ESCAPED_TAG_SEQUENCE);

        assertEquals("p\\p", queue.consumeElementSelector());
        assertTrue(queue.consumeWhitespace());

        assertEquals("p.p", queue.consumeElementSelector());
        assertTrue(queue.consumeWhitespace());

        assertEquals("p:p", queue.consumeElementSelector());
        assertTrue(queue.consumeWhitespace());

        assertEquals("p!p", queue.consumeElementSelector());
        assertTrue(queue.isEmpty());
    }
}
