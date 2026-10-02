package org.jsoup.parser;

import org.jsoup.Jsoup;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Verifies that jsoup's attribute selector parser handles quote characters that are nested inside
 * the selector's own quoting. Each scenario builds a tiny document whose {@code <a>} element has an
 * {@code onclick} attribute containing both single and double quotes, then selects that element with
 * an {@code [onclick*="..."]} selector whose match value itself contains the opposite quote char.
 *
 * In every case the parser must locate the single {@code <a id="identifier">} element, so the
 * resolved CSS selector of the match is always {@code #identifier}.
 */
public class TokenQueueTest_testNestedQuotes {

    /**
     * Parses {@code html}, applies {@code selector}, and asserts that the first match is the element
     * with {@code id="identifier"} (whose computed CSS selector is therefore {@code #identifier}).
     */
    private static void assertSelectsIdentifierElement(String html, String selector) {
        String matchedSelector = Jsoup.parse(html).select(selector).first().cssSelector();
        assertEquals("#identifier", matchedSelector);
    }

    @Test
    public void testNestedQuotes() {
        // Double-quoted attribute value containing single quotes; selector wrapped in double quotes
        // and matching a fragment that includes a single quote: func('arg
        assertSelectsIdentifierElement(
            "<html><body><a id=\"identifier\" onclick=\"func('arg')\" /></body></html>",
            "a[onclick*=\"('arg\"]");

        // Same selector, but the attribute value is unquoted in the source HTML.
        assertSelectsIdentifierElement(
            "<html><body><a id=\"identifier\" onclick=func('arg') /></body></html>",
            "a[onclick*=\"('arg\"]");

        // Single-quoted attribute value containing double quotes; selector wrapped in single quotes
        // and matching a fragment that includes a double quote: func("arg
        assertSelectsIdentifierElement(
            "<html><body><a id=\"identifier\" onclick='func(\"arg\")' /></body></html>",
            "a[onclick*='(\"arg']");

        // Same selector, but the attribute value is unquoted in the source HTML.
        assertSelectsIdentifierElement(
            "<html><body><a id=\"identifier\" onclick=func(\"arg\") /></body></html>",
            "a[onclick*='(\"arg']");
    }
}
