package org.jsoup.parser;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TokenQueueTest_unescape_2 {

    /**
     * Verifies that an escaped backslash ({@code \\}) followed by an escaped special character ({@code \&})
     * is unescaped correctly: the doubled backslash becomes a single literal backslash, and the
     * backslash-escaped ampersand becomes a plain ampersand.
     *
     * <pre>
     * Input  (4 chars): \ \ \ &   →   represented in Java as "\\\\\\&"
     * Output (2 chars): \ &       →   represented in Java as "\\&"
     *
     * Processing steps:
     *   chars 1-2: \\ (escaped backslash) → outputs one literal backslash
     *   chars 3-4: \& (escaped ampersand) → outputs one ampersand (leading backslash is consumed)
     * </pre>
     */
    @Test
    public void unescape_2() {
        // Input literal string: \\\&  (two escape sequences: escaped-backslash + escaped-ampersand)
        String escapedInput = "\\\\\\&";

        // Expected literal string: \&  (a literal backslash followed by a literal ampersand)
        String expectedOutput = "\\&";

        assertEquals(expectedOutput, TokenQueue.unescape(escapedInput));
    }
}
