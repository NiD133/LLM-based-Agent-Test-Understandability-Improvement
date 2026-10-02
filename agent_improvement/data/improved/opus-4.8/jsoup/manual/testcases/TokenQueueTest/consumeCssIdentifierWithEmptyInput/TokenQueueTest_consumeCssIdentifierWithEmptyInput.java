package org.jsoup.parser;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TokenQueueTest_consumeCssIdentifierWithEmptyInput {

    @Test
    void consumeCssIdentifierOnEmptyQueueThrowsWithEndOfInputMessage() {
        TokenQueue emptyQueue = new TokenQueue("");

        IllegalArgumentException thrown = assertThrows(
            IllegalArgumentException.class,
            emptyQueue::consumeCssIdentifier
        );
        assertEquals("CSS identifier expected, but end of input found", thrown.getMessage());
    }
}
