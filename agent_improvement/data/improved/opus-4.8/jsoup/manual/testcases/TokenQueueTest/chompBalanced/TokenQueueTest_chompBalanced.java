package org.jsoup.parser;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TokenQueueTest_chompBalanced {

    /**
     * chompBalanced pulls out the content between a balanced pair of markers, ignoring nested pairs.
     * Here the queue is ":contains(one (two) three) four":
     *   - consumeTo("(")  reads everything up to the first '(' -> ":contains"
     *   - chompBalanced('(', ')') reads the balanced content between the outer parentheses,
     *     keeping the nested "(two)" intact -> "one (two) three"
     *   - remainder() returns whatever is left on the queue -> " four"
     */
    @Test
    public void chompBalanced() {
        TokenQueue tq = new TokenQueue(":contains(one (two) three) four");

        String beforeParen = tq.consumeTo("(");
        String balancedContent = tq.chompBalanced('(', ')');
        String remainder = tq.remainder();

        assertEquals(":contains", beforeParen);
        assertEquals("one (two) three", balancedContent);
        assertEquals(" four", remainder);
    }
}
