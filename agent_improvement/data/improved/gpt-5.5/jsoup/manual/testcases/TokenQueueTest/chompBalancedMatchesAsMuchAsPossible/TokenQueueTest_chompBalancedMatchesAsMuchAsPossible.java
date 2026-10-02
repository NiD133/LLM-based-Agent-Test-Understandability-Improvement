package org.jsoup.parser;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TokenQueueTest_chompBalancedMatchesAsMuchAsPossible {

    @Test
    public void chompBalancedMatchesAsMuchAsPossible() {
        TokenQueue queue = new TokenQueue("unbalanced(something(or another)) else");

        queue.consumeTo("(");
        String balancedText = queue.chompBalanced('(', ')');

        assertEquals("something(or another)", balancedText);
    }
}
