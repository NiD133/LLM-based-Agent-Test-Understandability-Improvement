package org.jsoup.parser;

import org.jsoup.Jsoup;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TokenQueueTest_testNestedQuotes {

    private static final String EXPECTED_CSS_SELECTOR = "#identifier";

    private static void validateNestedQuotes(String html, String selector) {
        assertEquals(EXPECTED_CSS_SELECTOR, Jsoup.parse(html).select(selector).first().cssSelector());
    }

    @Test
    public void testNestedQuotes() {
        validateNestedQuotes(
            "<html><body><a id=\"identifier\" onclick=\"func('arg')\" /></body></html>",
            "a[onclick*=\"('arg\"]"
        );
        validateNestedQuotes(
            "<html><body><a id=\"identifier\" onclick=func('arg') /></body></html>",
            "a[onclick*=\"('arg\"]"
        );
        validateNestedQuotes(
            "<html><body><a id=\"identifier\" onclick='func(\"arg\")' /></body></html>",
            "a[onclick*='(\"arg']"
        );
        validateNestedQuotes(
            "<html><body><a id=\"identifier\" onclick=func(\"arg\") /></body></html>",
            "a[onclick*='(\"arg']"
        );
    }
}
