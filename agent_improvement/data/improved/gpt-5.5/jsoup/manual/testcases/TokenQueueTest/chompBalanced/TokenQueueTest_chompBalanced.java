package org.jsoup.parser;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TokenQueueTest_chompBalanced {
    private static final String QUEUE_WITH_NESTED_PARENTHESES = ":contains(one (two) three) four";

    @Test
    public void chompBalancedConsumesNestedContentAndLeavesRemainder() {
        TokenQueue tokenQueue = new TokenQueue(QUEUE_WITH_NESTED_PARENTHESES);

        String prefix = tokenQueue.consumeTo("(");
        String balancedContent = tokenQueue.chompBalanced('(', ')');
        String remainder = tokenQueue.remainder();

        assertEquals(":contains", prefix);
        assertEquals("one (two) three", balancedContent);
        assertEquals(" four", remainder);
    }
}
