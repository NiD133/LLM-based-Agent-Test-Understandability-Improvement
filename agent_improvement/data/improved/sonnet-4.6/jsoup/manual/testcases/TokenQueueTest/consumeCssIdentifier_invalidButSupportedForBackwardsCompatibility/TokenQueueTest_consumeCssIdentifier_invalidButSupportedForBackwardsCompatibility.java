package org.jsoup.parser;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests that {@link TokenQueue#consumeCssIdentifier()} accepts identifiers that are technically
 * invalid per the CSS specification but are kept working for backwards compatibility with existing
 * jsoup code that relies on this lenient behaviour.
 *
 * <p>Strictly speaking, a CSS ident-token must start with a letter, underscore, or a hyphen
 * followed by a letter/underscore. Bare digits ("1"), a lone hyphen ("-"), or a hyphen followed
 * directly by a digit ("-1") are therefore invalid. However, jsoup historically accepted them,
 * and some of its own tests depend on that fact, so the behaviour is preserved.
 */
public class TokenQueueTest_consumeCssIdentifier_invalidButSupportedForBackwardsCompatibility {

    /**
     * Convenience: create a {@link TokenQueue} from the given text and consume a single CSS identifier.
     */
    private static String parseCssIdentifier(String text) {
        TokenQueue q = new TokenQueue(text);
        return q.consumeCssIdentifier();
    }

    private void assertParsedCssIdentifierEquals(String expected, String cssIdentifier) {
        assertEquals(expected, parseCssIdentifier(cssIdentifier));
    }

    @Test
    public void consumeCssIdentifier_invalidButSupportedForBackwardsCompatibility() {
        // A bare digit at the start is not a valid CSS ident-start, but is accepted unchanged.
        assertParsedCssIdentifierEquals("1", "1");

        // A lone hyphen is not a valid CSS identifier on its own, but is accepted unchanged.
        assertParsedCssIdentifierEquals("-", "-");

        // A hyphen followed immediately by a digit is invalid (should be escaped as "-\31 "),
        // but is accepted unchanged for backwards compatibility.
        assertParsedCssIdentifierEquals("-1", "-1");
    }
}
