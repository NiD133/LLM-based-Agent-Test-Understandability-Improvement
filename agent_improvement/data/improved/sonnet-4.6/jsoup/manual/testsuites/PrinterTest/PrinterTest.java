package org.jsoup.nodes;

import org.jsoup.Jsoup;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.jsoup.integration.ParseTest.getFile;
import static org.jsoup.integration.ParseTest.getFileAsString;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for HTML/XML serialization of elements, covering the Pretty Printer,
 * Outline mode, and passthrough (non-pretty) output.
 */
public class PrinterTest {

    @Test
    void pretty() throws IOException {
        File inputFile = getFile("/printertests/input-1.html");
        Document doc = Jsoup.parse(inputFile);

        String expected = getFileAsString(getFile("/printertests/pretty-1.html"));
        String html = doc.html();

        assertEquals(expected, html);
        assertEquals(html, doc.outerHtml()); // html() and outerHtml() must be equivalent for a Document
    }

    @Test
    void passthru() throws IOException {
        // With pretty-printing disabled, the output should be nearly identical to the input,
        // apart from a few parse normalizations (e.g., doctype casing, pre elements).
        File inputFile = getFile("/printertests/input-1.html");
        Document doc = Jsoup.parse(inputFile);
        doc.outputSettings().prettyPrint(false);

        String expected = getFileAsString(getFile("/printertests/passthru-1.html"));
        String html = doc.html();

        assertEquals(expected, html);
        assertEquals(html, doc.outerHtml());
    }

    @Test
    void outline() throws IOException {
        // Outline mode indents almost every node, including inline elements.
        File inputFile = getFile("/printertests/input-1.html");
        Document doc = Jsoup.parse(inputFile);
        doc.outputSettings().outline(true);

        String expected = getFileAsString(getFile("/printertests/outline-1.html"));
        String html = doc.html();

        assertEquals(expected, html);
        assertEquals(html, doc.outerHtml());
    }

    @Test
    void sequentialTextNodesDontCollapse() {
        // The pretty printer must not collapse (trim leading/trailing whitespace from) sequential
        // text nodes. This situation doesn't arise from parsing but can occur after DOM manipulation.
        Document doc = Jsoup.parse("<div><div></div>Hello</div>");
        Element div = doc.expectFirst("div");
        TextNode helloNode = (TextNode) div.childNode(1);
        helloNode.after(" there."); // insert a sibling text node immediately after "Hello"

        // Each text node retains its own content before rendering
        assertEquals("Hello", helloNode.getWholeText());
        assertEquals(" there.", ((TextNode) helloNode.nextSibling()).getWholeText());

        // When rendered, the two sibling text nodes appear concatenated
        assertEquals("Hello there.", div.text());
        assertEquals("<div></div>\nHello there.", div.html());
        assertEquals("<div>\n <div></div>\n Hello there.\n</div>", div.outerHtml());
    }

    @Test
    void sequentialTextNodesCollapseAdjacentWhitespace() {
        // https://github.com/jhy/jsoup/pull/2349
        // The pretty printer should collapse whitespace between sequential text nodes into a single
        // space, even when intermediate blank or empty text nodes are present.
        Document doc = Jsoup.parseBodyFragment("Before <span> </span> After");
        doc.expectFirst("span")
                .after(new TextNode("")).after(new TextNode("")).after(new TextNode(" ")).after(new TextNode(""))
                .remove();

        assertEquals(6, doc.body().textNodes().size()); // whitespace is not yet collapsed before printing
        assertEquals("Before After", doc.body().html());
    }

    @Test
    void dontCollapseTextAfterNonElements() {
        // Text that follows a non-element node (such as a comment) should not be collapsed with preceding text.
        Document doc = Jsoup.parse("<div><div></div>Hello <!-- -_- --> there</div>");
        Element body = doc.body();

        assertEquals("Hello there", body.text());
        assertEquals("<div>\n <div></div>\n Hello <!-- -_- -->\n  there\n</div>", body.html());
    }

    @Test
    void spaceAfterSpanInBlock() {
        // Spaces surrounding inline elements inside a block element should be preserved (collapsed to one),
        // while leading/trailing spaces on the block itself are trimmed.
        Document doc = Jsoup.parse("<div> <span>Span</span> \n Text  <span>Follow</span></div> <p> <span>Span</span>  Text <span>Follow</span> </p>");
        Element body = doc.body();

        assertEquals("Span Text Follow Span Text Follow", body.text());
        assertEquals("<div>\n <span>Span</span> Text <span>Follow</span>\n</div>\n<p><span>Span</span> Text <span>Follow</span></p>", body.html());
    }
}
