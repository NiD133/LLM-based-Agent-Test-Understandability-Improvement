package org.jsoup.parser;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TokenQueueTest_unescape {

    /**
     * Verifies that {@link TokenQueue#unescape} removes backslash escapes:
     * <ul>
     *   <li>\( → (</li>
     *   <li>\) → )</li>
     *   <li>\\ → \  (double-backslash collapses to a single backslash)</li>
     * </ul>
     */
    @Test
    public void unescape() {
        assertEquals("one ( ) \\", TokenQueue.unescape("one \\( \\) \\\\"));
    }
}
