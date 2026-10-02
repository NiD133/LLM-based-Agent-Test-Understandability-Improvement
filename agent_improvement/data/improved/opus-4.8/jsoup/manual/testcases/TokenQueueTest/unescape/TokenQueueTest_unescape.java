package org.jsoup.parser;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TokenQueueTest_unescape {

    /**
     * {@link TokenQueue#unescape(String)} removes a single backslash that escapes a character,
     * while a doubled backslash ("\\\\") collapses to one literal backslash.
     */
    @Test
    public void unescape() {
        // Input "one \( \) \\" -> the escapes before '(' and ')' are dropped,
        // and the escaped backslash becomes a single backslash.
        String input = "one \\( \\) \\\\";
        String expected = "one ( ) \\";

        assertEquals(expected, TokenQueue.unescape(input));
    }
}
