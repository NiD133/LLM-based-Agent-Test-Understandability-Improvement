package org.jsoup.parser;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TokenQueueTest_chompBalancedThrowIllegalArgumentException {

    /**
     * chompBalanced should reject input whose opening marker is never balanced by its closer.
     * Here the queue is positioned at the first '(' and asked to find a balancing '+', which
     * does not exist, so the call must throw IllegalArgumentException describing the leftover text.
     */
    @Test
    public void chompBalancedThrowsWhenCloserNeverFound() {
        TokenQueue queue = new TokenQueue("unbalanced(something(or another)) else");
        queue.consumeTo("("); // advance to the first opening marker

        IllegalArgumentException thrown = assertThrows(
            IllegalArgumentException.class,
            () -> queue.chompBalanced('(', '+'));

        assertEquals(
            "Did not find balanced marker at 'something(or another)) else'",
            thrown.getMessage());
    }
}
