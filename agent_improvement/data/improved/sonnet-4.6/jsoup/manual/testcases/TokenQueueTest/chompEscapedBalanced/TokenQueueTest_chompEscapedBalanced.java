package org.jsoup.parser;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests that {@link TokenQueue#chompBalanced} correctly handles backslash-escaped
 * parentheses: escaped parens must not affect the nesting depth counter, so the
 * method finds the true balanced closing paren and leaves the tail on the queue.
 */
public class TokenQueueTest_chompEscapedBalanced {

    @Test
    public void chompEscapedBalanced() {
        // Input string (Java literal): ":contains(one (two) \( \) \) three) four"
        // The \( and \) are escaped parens — they should be treated as plain characters,
        // not as depth-changing open/close tokens.
        TokenQueue tq = new TokenQueue(":contains(one (two) \\( \\) \\) three) four");

        // consumeTo stops just before the first unescaped '('
        String pre = tq.consumeTo("(");

        // chompBalanced consumes from '(' to its true matching ')', preserving escape sequences
        String guts = tq.chompBalanced('(', ')');

        // remainder() returns everything left after the closing ')'
        String remainder = tq.remainder();

        assertEquals(":contains", pre,
            "consumeTo('(') should capture the selector name before the opening paren");

        assertEquals("one (two) \\( \\) \\) three", guts,
            "chompBalanced should capture the balanced content with escape sequences intact");

        assertEquals("one (two) ( ) ) three", TokenQueue.unescape(guts),
            "unescape() should convert backslash-escaped parens to their literal characters");

        assertEquals(" four", remainder,
            "remainder() should contain the text that follows the closing paren");
    }
}
