package org.jsoup.parser;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests that {@link TokenQueue#consumeElementSelector()} correctly unescapes
 * backslash-escaped CSS special characters when reading element selectors.
 *
 * <p>In CSS selector syntax a backslash followed by a special character (e.g. {@code \.})
 * means "treat the special character literally". The selector consumer must strip the
 * escape prefix and return the bare character in the result.</p>
 */
public class TokenQueueTest_consumeEscapedTag {

    /**
     * Verifies that a queue containing four space-separated element selectors, each with a
     * different backslash-escaped special character, is consumed correctly.
     *
     * <p>Input queue (Java string literal):  {@code "p\\\\p p\\.p p\\:p p\\!p"}
     * <br>Actual characters in the queue:    {@code p\\p p\.p p\:p p\!p}
     *
     * <p>Each selector uses a CSS escape sequence:
     * <ul>
     *   <li>{@code p\\p}  — escaped backslash  → {@code p\p}</li>
     *   <li>{@code p\.p}  — escaped dot         → {@code p.p}</li>
     *   <li>{@code p\:p}  — escaped colon        → {@code p:p}</li>
     *   <li>{@code p\!p}  — escaped exclamation  → {@code p!p}</li>
     * </ul>
     *
     * <p>Between selectors, whitespace must be consumed (and the method must return
     * {@code true} to indicate that at least one whitespace character was seen).
     * After the last selector the queue must be empty.
     */
    @Test
    public void consumeEscapedTag() {
        // Queue content (actual chars): p\\p p\.p p\:p p\!p
        TokenQueue q = new TokenQueue("p\\\\p p\\.p p\\:p p\\!p");

        // "p\\p"  → backslash is itself escaped, so the result is a literal backslash
        assertEquals("p\\p", q.consumeElementSelector());

        // Separator between selectors must be present and consumed
        assertTrue(q.consumeWhitespace());

        // "p\.p"  → dot is a CSS special char; the escape strips the backslash, leaving "."
        assertEquals("p.p", q.consumeElementSelector());

        assertTrue(q.consumeWhitespace());

        // "p\:p"  → colon is a CSS pseudo-class marker; escaped here so it's treated literally
        assertEquals("p:p", q.consumeElementSelector());

        assertTrue(q.consumeWhitespace());

        // "p\!p"  → exclamation mark is not a valid bare selector character; escape makes it literal
        assertEquals("p!p", q.consumeElementSelector());

        // All input has been consumed
        assertTrue(q.isEmpty());
    }
}
