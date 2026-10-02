package org.jsoup.nodes;

import org.jsoup.Jsoup;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Verifies that when the pretty-printer emits an element, the text that follows a
 * non-element node (here, an HTML comment) is not collapsed onto the previous line.
 * The comment must keep its own indented line, and the trailing text ("there") must
 * be pushed onto the next line rather than being merged with "Hello".
 */
public class PrinterTest_dontCollapseTextAfterNonElements {

    @Test
    void dontCollapseTextAfterNonElements() {
        // A block div whose content mixes an element, text, a comment, and more text.
        String html = "<div><div></div>Hello <!-- -_- --> there</div>";
        Document doc = Jsoup.parse(html);
        Element body = doc.body();

        // text() ignores comments and collapses whitespace, yielding the plain words.
        assertEquals("Hello there", body.text());

        // html() pretty-prints: the comment and the text after it each get their own line.
        String expectedPrettyHtml =
                "<div>\n" +
                " <div></div>\n" +
                " Hello <!-- -_- -->\n" +
                "  there\n" +
                "</div>";
        assertEquals(expectedPrettyHtml, body.html());
    }
}
