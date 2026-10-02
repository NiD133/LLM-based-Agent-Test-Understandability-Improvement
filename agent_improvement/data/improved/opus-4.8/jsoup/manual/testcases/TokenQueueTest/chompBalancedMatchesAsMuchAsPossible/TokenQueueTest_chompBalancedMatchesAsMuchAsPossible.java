package org.jsoup.parser;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TokenQueueTest_chompBalancedMatchesAsMuchAsPossible {

    /**
     * chompBalanced should match the outermost balanced pair, greedily including any nested
     * pairs in between, and leave the remaining text on the queue.
     */
    @Test
    public void chompBalancedMatchesAsMuchAsPossible() {
        // Arrange: queue positioned just before the first opening parenthesis.
        TokenQueue tq = new TokenQueue("unbalanced(something(or another)) else");
        tq.consumeTo("(");

        // Act: pull the balanced "(...)" group off the queue.
        String match = tq.chompBalanced('(', ')');

        // Assert: the full nested content between the outer parentheses is returned.
        assertEquals("something(or another)", match);
    }
}
