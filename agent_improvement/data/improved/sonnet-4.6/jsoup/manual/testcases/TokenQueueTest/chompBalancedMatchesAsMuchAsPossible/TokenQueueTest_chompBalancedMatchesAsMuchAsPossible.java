package org.jsoup.parser;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests that {@link TokenQueue#chompBalanced} consumes as much of a balanced
 * bracket expression as possible, including nested brackets, and stops at the
 * correctly matching close.
 */
public class TokenQueueTest_chompBalancedMatchesAsMuchAsPossible {

    /**
     * Given "unbalanced(something(or another)) else", after advancing past the
     * leading text up to the first '(', chompBalanced should return the full
     * inner content "something(or another)" — preserving the nested pair — and
     * leave " else" on the queue.
     */
    @Test
    public void chompBalancedMatchesAsMuchAsPossible() {
        TokenQueue tq = new TokenQueue("unbalanced(something(or another)) else");
        tq.consumeTo("(");
        String match = tq.chompBalanced('(', ')');
        assertEquals("something(or another)", match);
    }
}
