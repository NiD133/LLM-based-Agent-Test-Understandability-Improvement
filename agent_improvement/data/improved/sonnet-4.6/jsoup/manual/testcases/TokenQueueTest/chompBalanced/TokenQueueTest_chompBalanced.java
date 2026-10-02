package org.jsoup.parser;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for {@link TokenQueue#chompBalanced(char, char)}.
 *
 * <p>{@code chompBalanced} consumes and returns the content between a matched pair of open/close
 * delimiters, correctly handling nested occurrences of the same delimiter pair.
 */
public class TokenQueueTest_chompBalanced {

    /**
     * Given the queue {@code ":contains(one (two) three) four"}:
     * <ol>
     *   <li>{@code consumeTo("(")} advances past {@code ":contains"} and stops before the first {@code (}.</li>
     *   <li>{@code chompBalanced('(', ')')} consumes the balanced {@code (…)} block, returning its
     *       inner content — including the nested {@code (two)} — without the outer parentheses.</li>
     *   <li>{@code remainder()} returns whatever text follows the closing {@code )}.</li>
     * </ol>
     */
    @Test
    public void chompBalanced() {
        TokenQueue tq = new TokenQueue(":contains(one (two) three) four");

        String pre = tq.consumeTo("(");
        String guts = tq.chompBalanced('(', ')');
        String remainder = tq.remainder();

        assertEquals(":contains", pre);
        assertEquals("one (two) three", guts);
        assertEquals(" four", remainder);
    }
}
