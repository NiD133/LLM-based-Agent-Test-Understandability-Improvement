package org.jsoup.parser;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TokenQueueTest_consumeCssIdentifier_invalidButSupportedForBackwardsCompatibility {

    private static String parseCssIdentifier(String cssIdentifier) {
        TokenQueue queue = new TokenQueue(cssIdentifier);
        return queue.consumeCssIdentifier();
    }

    private static void assertParsedCssIdentifierEquals(String expected, String cssIdentifier) {
        assertEquals(expected, parseCssIdentifier(cssIdentifier));
    }

    // Some of jsoup's tests depend on accepting these invalid CSS identifiers.
    @Test
    public void consumeCssIdentifier_invalidButSupportedForBackwardsCompatibility() {
        assertParsedCssIdentifierEquals("1", "1");
        assertParsedCssIdentifierEquals("-", "-");
        assertParsedCssIdentifierEquals("-1", "-1");
    }
}
