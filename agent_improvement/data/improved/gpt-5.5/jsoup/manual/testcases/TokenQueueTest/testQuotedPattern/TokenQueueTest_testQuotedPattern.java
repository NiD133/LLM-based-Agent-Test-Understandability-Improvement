package org.jsoup.parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.Test;

import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TokenQueueTest_testQuotedPattern {
    private static final String HTML_WITH_PARENTHESIS_TEXT =
        "<div>\\) foo1</div><div>( foo2</div><div>1) foo3</div>";

    @Test
    public void testQuotedPattern() {
        final Document doc = Jsoup.parse(HTML_WITH_PARENTHESIS_TEXT);

        assertFirstMatchedDivText(doc, "\\) foo1", "\\)");
        assertFirstMatchedDivText(doc, "( foo2", "(");
        assertFirstMatchedDivText(doc, "1) foo3", "1)");
    }

    private static void assertFirstMatchedDivText(Document doc, String expectedText, String quotedPattern) {
        assertEquals(
            expectedText,
            doc.select("div:matches(" + Pattern.quote(quotedPattern) + ")").get(0).childNode(0).toString()
        );
    }
}
