package org.jsoup.parser;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TokenQueueTest_escapeAtEof {

    @Test
    void escapeAtEof() {
        // A trailing backslash with nothing following it is an incomplete escape sequence.
        // consumeEscapedCssIdentifier silently drops it, so only "Foo" is returned.
        TokenQueue q = new TokenQueue("Foo\\");
        String s = q.consumeElementSelector();
        assertEquals("Foo", s);
    }
}
