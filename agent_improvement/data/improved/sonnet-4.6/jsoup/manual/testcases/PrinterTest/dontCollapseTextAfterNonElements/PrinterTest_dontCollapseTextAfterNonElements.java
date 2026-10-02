package org.jsoup.nodes;

import org.jsoup.Jsoup;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PrinterTest_dontCollapseTextAfterNonElements {

    // HTML where a text node ("there") follows a non-element node (HTML comment).
    // The pretty-printer must not collapse or drop that trailing text.
    private static final String HTML_TEXT_AFTER_COMMENT =
        "<div><div></div>Hello <!-- -_- --> there</div>";

    @Test
    void dontCollapseTextAfterNonElements() {
        Document doc = Jsoup.parse(HTML_TEXT_AFTER_COMMENT);
        Element body = doc.body();

        // Plain-text view strips comments and collapses whitespace: "Hello" and "there" join.
        assertEquals("Hello there", body.text());

        // Pretty-printed HTML must keep "there" on its own indented line rather than
        // merging it with "Hello" or losing it entirely after the comment node.
        String expectedHtml =
            "<div>\n" +
            " <div></div>\n" +
            " Hello <!-- -_- -->\n" +
            "  there\n" +
            "</div>";
        assertEquals(expectedHtml, body.html());
    }
}
