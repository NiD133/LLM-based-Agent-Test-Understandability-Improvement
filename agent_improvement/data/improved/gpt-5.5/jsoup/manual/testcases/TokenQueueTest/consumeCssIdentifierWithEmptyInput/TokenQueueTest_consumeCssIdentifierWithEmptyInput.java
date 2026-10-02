package org.jsoup.parser;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TokenQueueTest_consumeCssIdentifierWithEmptyInput {
    private static final String EMPTY_INPUT = "";
    private static final String EXPECTED_EMPTY_INPUT_ERROR =
            "CSS identifier expected, but end of input found";

    @Test
    void consumeCssIdentifierWithEmptyInput() {
        TokenQueue emptyQueue = new TokenQueue(EMPTY_INPUT);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                emptyQueue::consumeCssIdentifier
        );

        assertEquals(EXPECTED_EMPTY_INPUT_ERROR, exception.getMessage());
    }
}
