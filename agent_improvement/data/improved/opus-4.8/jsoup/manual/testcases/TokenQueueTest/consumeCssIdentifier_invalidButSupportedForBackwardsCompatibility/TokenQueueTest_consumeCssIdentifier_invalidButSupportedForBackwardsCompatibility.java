package org.jsoup.parser;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TokenQueueTest_consumeCssIdentifier_invalidButSupportedForBackwardsCompatibility {

    /** Parses {@code input} as a CSS identifier and returns the consumed value. */
    private static String consumeCssIdentifier(String input) {
        return new TokenQueue(input).consumeCssIdentifier();
    }

    /**
     * These inputs are not strictly valid CSS identifiers, but jsoup still accepts them
     * because some of jsoup's own tests rely on this lenient, backwards-compatible behaviour.
     */
    @Test
    public void consumesIdentifiersThatAreInvalidButSupportedForBackwardsCompatibility() {
        assertEquals("1", consumeCssIdentifier("1"));
        assertEquals("-", consumeCssIdentifier("-"));
        assertEquals("-1", consumeCssIdentifier("-1"));
    }
}
