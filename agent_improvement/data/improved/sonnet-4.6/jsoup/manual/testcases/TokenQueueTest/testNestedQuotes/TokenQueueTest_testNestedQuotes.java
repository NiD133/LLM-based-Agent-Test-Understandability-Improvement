package org.jsoup.parser;

import org.jsoup.Jsoup;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests that CSS attribute selectors with nested quotes (a quote of one type inside a
 * selector value delimited by the other type) correctly match elements in parsed HTML.
 */
public class TokenQueueTest_testNestedQuotes {

    /**
     * Parses {@code html}, selects using {@code selector}, and asserts the matched
     * element's CSS selector string is {@code "#identifier"}.
     */
    private static void validateNestedQuotes(String html, String selector) {
        assertEquals("#identifier", Jsoup.parse(html).select(selector).first().cssSelector());
    }

    @Test
    public void testNestedQuotes() {
        // Attribute value is double-quoted in HTML; selector uses double quotes containing a single quote
        validateNestedQuotes(
            "<html><body><a id=\"identifier\" onclick=\"func('arg')\" /></body></html>",
            "a[onclick*=\"('arg\"]"
        );

        // Attribute value is unquoted in HTML; selector uses double quotes containing a single quote
        validateNestedQuotes(
            "<html><body><a id=\"identifier\" onclick=func('arg') /></body></html>",
            "a[onclick*=\"('arg\"]"
        );

        // Attribute value is single-quoted in HTML; selector uses single quotes containing a double quote
        validateNestedQuotes(
            "<html><body><a id=\"identifier\" onclick='func(\"arg\")' /></body></html>",
            "a[onclick*='(\"arg']"
        );

        // Attribute value is unquoted in HTML; selector uses single quotes containing a double quote
        validateNestedQuotes(
            "<html><body><a id=\"identifier\" onclick=func(\"arg\") /></body></html>",
            "a[onclick*='(\"arg']"
        );
    }
}
