package org.jsoup.parser;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TokenQueueTest_unescape {
    @Test
    public void unescape() {
        String escapedText = "one \\( \\) \\\\";
        String unescapedText = TokenQueue.unescape(escapedText);

        assertEquals("one ( ) \\", unescapedText);
    }
}
