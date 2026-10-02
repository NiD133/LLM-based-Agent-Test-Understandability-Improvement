package org.jsoup.parser;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for {@link TokenQueue#consumeCssIdentifier()} when the identifier contains CSS escape sequences.
 * Escape sequences use a leading backslash to include characters that would otherwise have special meaning
 * (e.g. {@code \.} for a literal dot, {@code \\} for a literal backslash).
 */
public class TokenQueueTest_consumeEscapedId {

    /**
     * Verifies that {@code consumeCssIdentifier()} correctly decodes backslash escape sequences:
     * <ul>
     *   <li>{@code i\.d} → {@code i.d} — a backslash followed by {@code .} produces a literal dot</li>
     *   <li>{@code i\\d} → {@code i\d} — a double backslash produces a literal backslash</li>
     * </ul>
     * The two identifiers are separated by whitespace, which is consumed by {@code consumeWhitespace()}.
     */
    @Test
    public void consumeEscapedId() {
        // Java string "i\\.d i\\\\d" represents the CSS input:  i\.d i\\d
        TokenQueue q = new TokenQueue("i\\.d i\\\\d");

        // "i\.d"  →  the \. escape decodes to a literal dot, yielding "i.d"
        assertEquals("i.d", q.consumeCssIdentifier());

        // whitespace between the two identifiers must be consumed
        assertTrue(q.consumeWhitespace());

        // "i\\d"  →  the \\ escape decodes to a literal backslash, yielding "i\d"
        assertEquals("i\\d", q.consumeCssIdentifier());

        // the entire input has been consumed
        assertTrue(q.isEmpty());
    }
}
