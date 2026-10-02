package org.jsoup.parser;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Verifies that {@link TokenQueue#chompBalanced(char, char)} correctly extracts a balanced
 * {@code (...)} group while leaving escaped parentheses untouched, and that the surrounding
 * helpers ({@code consumeTo}, {@code remainder}, and {@code unescape}) behave as expected.
 */
public class TokenQueueTest_chompEscapedBalanced {

    @Test
    public void chompEscapedBalanced() {
        // Input contains a balanced "(...)" group whose body has escaped parens ("\(", "\)")
        // that must NOT be treated as group delimiters.
        TokenQueue queue = new TokenQueue(":contains(one (two) \\( \\) \\) three) four");

        String beforeGroup = queue.consumeTo("(");          // text up to the first "("
        String groupBody = queue.chompBalanced('(', ')');   // balanced contents, escapes preserved
        String afterGroup = queue.remainder();              // whatever is left on the queue

        assertEquals(":contains", beforeGroup);
        // chompBalanced keeps the backslash escapes intact (suitable for regexes).
        assertEquals("one (two) \\( \\) \\) three", groupBody);
        // unescape strips the backslashes (suitable for plain "contains" text matching).
        assertEquals("one (two) ( ) ) three", TokenQueue.unescape(groupBody));
        assertEquals(" four", afterGroup);
    }
}
