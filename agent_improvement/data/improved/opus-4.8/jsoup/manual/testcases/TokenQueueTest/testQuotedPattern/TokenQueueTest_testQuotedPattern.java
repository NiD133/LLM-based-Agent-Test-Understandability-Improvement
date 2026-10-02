package org.jsoup.parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.Test;

import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TokenQueueTest_testQuotedPattern {

    /**
     * A {@code :matches(...)} selector treats its argument as a regular expression. When the pattern needs to contain
     * regex metacharacters literally (such as {@code )}, {@code (}, or {@code 1)}), {@link Pattern#quote(String)} wraps
     * them in {@code \Q...\E}. This test verifies that jsoup parses such quoted patterns correctly and matches the
     * intended element text.
     */
    @Test
    public void testQuotedPattern() {
        Document doc = Jsoup.parse("<div>\\) foo1</div><div>( foo2</div><div>1) foo3</div>");

        assertMatchesQuotedPattern(doc, "\\)", "\\) foo1");
        assertMatchesQuotedPattern(doc, "(", "( foo2");
        assertMatchesQuotedPattern(doc, "1)", "1) foo3");
    }

    /**
     * Selects the first {@code <div>} whose text matches the given literal (quoted) pattern, and asserts that its text
     * node equals the expected value.
     */
    private static void assertMatchesQuotedPattern(Document doc, String literalPattern, String expectedText) {
        String selector = "div:matches(" + Pattern.quote(literalPattern) + ")";
        String actualText = doc.select(selector).get(0).childNode(0).toString();
        assertEquals(expectedText, actualText);
    }
}
