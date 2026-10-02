package org.jsoup.parser;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TokenQueueTest_consumeCssIdentifierWithEmptyInput {

    /**
     * consumeCssIdentifier() must throw when called on an empty queue,
     * because a CSS identifier requires at least one character.
     */
    @Test
    void consumeCssIdentifierWithEmptyInput() {
        TokenQueue emptyQueue = new TokenQueue("");
        Exception exception = assertThrows(IllegalArgumentException.class, emptyQueue::consumeCssIdentifier);
        assertEquals("CSS identifier expected, but end of input found", exception.getMessage());
    }
}
