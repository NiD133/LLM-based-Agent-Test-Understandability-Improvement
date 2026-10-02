package org.jsoup.parser;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TokenQueueTest_chompEscapedBalanced {

    private static final String QUEUE_TEXT = ":contains(one (two) \\( \\) \\) three) four";
    private static final String BALANCED_CONTENT = "one (two) \\( \\) \\) three";
    private static final String UNESCAPED_BALANCED_CONTENT = "one (two) ( ) ) three";

    @Test
    public void chompEscapedBalanced() {
        TokenQueue tq = new TokenQueue(QUEUE_TEXT);

        String pre = tq.consumeTo("(");
        String guts = tq.chompBalanced('(', ')');
        String remainder = tq.remainder();

        assertEquals(":contains", pre);
        assertEquals(BALANCED_CONTENT, guts);
        assertEquals(UNESCAPED_BALANCED_CONTENT, TokenQueue.unescape(guts));
        assertEquals(" four", remainder);
    }
}
