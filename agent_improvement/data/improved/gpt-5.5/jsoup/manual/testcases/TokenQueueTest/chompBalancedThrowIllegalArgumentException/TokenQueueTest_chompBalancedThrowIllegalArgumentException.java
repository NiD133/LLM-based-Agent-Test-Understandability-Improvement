package org.jsoup.parser;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class TokenQueueTest_chompBalancedThrowIllegalArgumentException {
    private static final String UNBALANCED_QUEUE = "unbalanced(something(or another)) else";
    private static final String EXPECTED_ERROR_MESSAGE =
            "Did not find balanced marker at 'something(or another)) else'";

    @Test
    public void chompBalancedThrowIllegalArgumentException() {
        TokenQueue tokenQueue = new TokenQueue(UNBALANCED_QUEUE);
        tokenQueue.consumeTo("(");

        try {
            tokenQueue.chompBalanced('(', '+');
            fail("should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException expected) {
            assertEquals(EXPECTED_ERROR_MESSAGE, expected.getMessage());
        }
    }
}
