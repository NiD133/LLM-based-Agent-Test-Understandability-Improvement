package org.jsoup.nodes;

import org.jsoup.Jsoup;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests that the pretty printer preserves whitespace between sequential text nodes
 * that were created by DOM manipulation (not parsing), specifically that leading
 * and trailing whitespace is not collapsed when multiple TextNodes are siblings.
 */
public class PrinterTest_sequentialTextNodesDontCollapse {

    @Test
    void sequentialTextNodesDontCollapse() {
        // Parsing produces a single TextNode, but after calling .after() we get two
        // sequential TextNode siblings: "Hello" and " there."
        // The pretty printer must not trim the leading space of the second node.
        Document doc = Jsoup.parse("<div><div></div>Hello</div>");
        Element div = doc.expectFirst("div");

        TextNode hello = (TextNode) div.childNode(1);
        hello.after(" there.");

        // Verify the two sibling TextNodes were created with the expected raw text
        TextNode thereSibling = (TextNode) hello.nextSibling();
        assertEquals("Hello", hello.getWholeText(),
            "First TextNode should contain the original text unchanged");
        assertEquals(" there.", thereSibling.getWholeText(),
            "Second TextNode should retain its leading space");

        // Verify the combined text and HTML rendering are correct
        assertEquals("Hello there.", div.text(),
            "div.text() should concatenate both text nodes");
        assertEquals("<div></div>\nHello there.", div.html(),
            "inner HTML should preserve the space between sequential text nodes");
        assertEquals("<div>\n <div></div>\n Hello there.\n</div>", div.outerHtml(),
            "outer HTML should indent correctly without collapsing the space");
    }
}
