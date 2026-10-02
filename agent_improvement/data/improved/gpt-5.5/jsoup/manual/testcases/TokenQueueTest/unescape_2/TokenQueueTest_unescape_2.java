package org.jsoup.parser;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TokenQueueTest_unescape_2 {
    @Test
    public void unescape_2() {
        String escapedBackslashBeforeAmpersand = "\\\\\\&";
        String expectedUnescapedText = "\\&";

        assertEquals(expectedUnescapedText, TokenQueue.unescape(escapedBackslashBeforeAmpersand));
    }
}
