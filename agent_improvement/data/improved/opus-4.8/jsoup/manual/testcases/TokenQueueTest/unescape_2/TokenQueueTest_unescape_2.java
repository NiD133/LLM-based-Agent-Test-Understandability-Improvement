package org.jsoup.parser;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TokenQueueTest_unescape_2 {

    /**
     * {@link TokenQueue#unescape(String)} collapses each escaped backslash ("\\") into a single
     * backslash, while leaving other escape sequences untouched.
     *
     * <p>The input below is three backslashes followed by an ampersand ({@code \\\&}):</p>
     * <ul>
     *   <li>the first two backslashes form an escaped backslash and collapse into one backslash,</li>
     *   <li>the remaining backslash and ampersand are passed through unchanged,</li>
     * </ul>
     * <p>so the result is a single backslash followed by an ampersand ({@code \&}).</p>
     */
    @Test
    public void unescape_2() {
        String escaped = "\\\\\\&";   // \\\& : three backslashes then an ampersand
        String expected = "\\&";       // \&   : one backslash then an ampersand

        assertEquals(expected, TokenQueue.unescape(escaped));
    }
}
