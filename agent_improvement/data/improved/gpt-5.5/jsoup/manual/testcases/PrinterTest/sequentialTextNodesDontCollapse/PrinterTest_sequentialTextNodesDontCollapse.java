package org.jsoup.nodes;

import org.jsoup.Jsoup;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PrinterTest_sequentialTextNodesDontCollapse {

    @Test
    void sequentialTextNodesDontCollapse() {
        Document document = Jsoup.parse("<div><div></div>Hello</div>");
        Element container = document.expectFirst("div");
        TextNode firstTextNode = (TextNode) container.childNode(1);

        firstTextNode.after(" there.");
        TextNode insertedTextNode = (TextNode) firstTextNode.nextSibling();

        assertEquals("Hello", firstTextNode.getWholeText());
        assertEquals(" there.", insertedTextNode.getWholeText());
        assertEquals("Hello there.", container.text());
        assertEquals("<div></div>\nHello there.", container.html());
        assertEquals("<div>\n <div></div>\n Hello there.\n</div>", container.outerHtml());
    }
}
