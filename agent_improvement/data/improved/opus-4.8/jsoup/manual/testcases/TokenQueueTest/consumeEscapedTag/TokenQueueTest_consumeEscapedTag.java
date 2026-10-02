package org.jsoup.parser;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TokenQueueTest_consumeEscapedTag {

    /**
     * An element selector may contain CSS-escaped special characters (a backslash followed by the literal
     * character). {@link TokenQueue#consumeElementSelector()} should strip the escaping backslash and return the
     * literal character as part of the tag name.
     *
     * <p>The queue below holds four space-separated, escaped selectors. Note the Java-source escaping: {@code "\\\\"}
     * is the two characters {@code \\}, and {@code "\\."} is the two characters {@code \.}, so the queue effectively
     * contains: {@code p\\p p\.p p\:p p\!p}.</p>
     */
    @Test
    public void consumeEscapedTag() {
        TokenQueue q = new TokenQueue("p\\\\p p\\.p p\\:p p\\!p");

        // Each selector unescapes to a tag name containing the previously escaped character;
        // consumeWhitespace() then skips the single space before the next selector.
        assertEquals("p\\p", q.consumeElementSelector());
        assertTrue(q.consumeWhitespace());

        assertEquals("p.p", q.consumeElementSelector());
        assertTrue(q.consumeWhitespace());

        assertEquals("p:p", q.consumeElementSelector());
        assertTrue(q.consumeWhitespace());

        assertEquals("p!p", q.consumeElementSelector());

        // The whole queue has now been consumed.
        assertTrue(q.isEmpty());
    }
}
