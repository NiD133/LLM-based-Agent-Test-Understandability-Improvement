package org.jsoup.nodes;

import org.jsoup.Jsoup;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Verifies that the pretty printer does NOT collapse (i.e. trim leading/trailing whitespace) when two text
 * nodes sit next to each other as siblings.
 *
 * <p>A normal parse never produces two adjacent text nodes, but DOM manipulation (such as inserting a node
 * after an existing text node) can. In that situation the printer must keep the whitespace between the two
 * fragments so the rendered text stays readable.</p>
 */
public class PrinterTest_sequentialTextNodesDontCollapse {

    @Test
    void sequentialTextNodesDontCollapse() {
        // Parse a <div> that contains an empty nested <div> followed by the text "Hello".
        // The text needs to be content that the pretty printer would normally indent.
        Document doc = Jsoup.parse("<div><div></div>Hello</div>");
        Element outerDiv = doc.expectFirst("div");

        // childNode(1) is the "Hello" text node (childNode(0) is the empty nested <div>).
        TextNode helloText = (TextNode) outerDiv.childNode(1);

        // Manually insert a second, adjacent text node right after "Hello".
        // This creates the sequential-text-node scenario that a parse alone cannot.
        helloText.after(" there.");

        // The two text nodes remain separate and retain their exact whitespace.
        assertEquals("Hello", helloText.getWholeText());
        TextNode insertedText = (TextNode) helloText.nextSibling();
        assertEquals(" there.", insertedText.getWholeText());

        // The combined, normalized text reads as a single sentence.
        assertEquals("Hello there.", outerDiv.text());

        // Pretty-printed HTML: the adjacent text nodes are joined without collapsing the space between them.
        assertEquals("<div></div>\nHello there.", outerDiv.html());
        assertEquals("<div>\n <div></div>\n Hello there.\n</div>", outerDiv.outerHtml());
    }
}
