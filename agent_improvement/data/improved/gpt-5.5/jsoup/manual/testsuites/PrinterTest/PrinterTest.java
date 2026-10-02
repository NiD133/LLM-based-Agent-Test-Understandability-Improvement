package org.jsoup.nodes;

import org.jsoup.Jsoup;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.jsoup.integration.ParseTest.getFile;
import static org.jsoup.integration.ParseTest.getFileAsString;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests HTML and XML serialization through the pretty printer, outline mode,
 * and the base printer.
 */
public class PrinterTest {
    private static final String INPUT_FIXTURE = "/printertests/input-1.html";
    private static final String PRETTY_FIXTURE = "/printertests/pretty-1.html";
    private static final String PASSTHRU_FIXTURE = "/printertests/passthru-1.html";
    private static final String OUTLINE_FIXTURE = "/printertests/outline-1.html";

    @Test void pretty() throws IOException {
        Document doc = parseInputFixture();

        assertDocumentHtmlMatchesFixture(doc, PRETTY_FIXTURE);
    }

    @Test void passthru() throws IOException {
        Document doc = parseInputFixture();
        doc.outputSettings().prettyPrint(false);

        assertDocumentHtmlMatchesFixture(doc, PASSTHRU_FIXTURE);
    }

    @Test void outline() throws IOException {
        Document doc = parseInputFixture();
        doc.outputSettings().outline(true);

        assertDocumentHtmlMatchesFixture(doc, OUTLINE_FIXTURE);
    }

    @Test void sequentialTextNodesDontCollapse() {
        Document doc = Jsoup.parse("<div><div></div>Hello</div>");
        Element div = doc.expectFirst("div");
        TextNode hello = (TextNode) div.childNode(1);
        hello.after(" there.");

        assertEquals("Hello", hello.getWholeText());
        assertEquals(" there.", ((TextNode) hello.nextSibling()).getWholeText());
        assertEquals("Hello there.", div.text());
        assertEquals("<div></div>\nHello there.", div.html());
        assertEquals("<div>\n <div></div>\n Hello there.\n</div>", div.outerHtml());
    }

    @Test void sequentialTextNodesCollapseAdjacentWhitespace() {
        Document doc = Jsoup.parseBodyFragment("Before <span> </span> After");
        Element span = doc.expectFirst("span");

        span.after(new TextNode(""))
                .after(new TextNode(""))
                .after(new TextNode(" "))
                .after(new TextNode(""))
                .remove();

        assertEquals(6, doc.body().textNodes().size());
        assertEquals("Before After", doc.body().html());
    }

    @Test void dontCollapseTextAfterNonElements() {
        Document doc = Jsoup.parse("<div><div></div>Hello <!-- -_- --> there</div>");
        Element body = doc.body();

        assertEquals("Hello there", body.text());
        assertEquals("<div>\n <div></div>\n Hello <!-- -_- -->\n  there\n</div>", body.html());
    }

    @Test void spaceAfterSpanInBlock() {
        Document doc = Jsoup.parse("<div> <span>Span</span> \n Text  <span>Follow</span></div> <p> <span>Span</span>  Text <span>Follow</span> </p>");
        Element body = doc.body();

        assertEquals("Span Text Follow Span Text Follow", body.text());
        assertEquals("<div>\n <span>Span</span> Text <span>Follow</span>\n</div>\n<p><span>Span</span> Text <span>Follow</span></p>", body.html());
    }

    private static Document parseInputFixture() throws IOException {
        File in = getFile(INPUT_FIXTURE);
        return Jsoup.parse(in);
    }

    private static void assertDocumentHtmlMatchesFixture(Document doc, String fixturePath) throws IOException {
        String expected = getFileAsString(getFile(fixturePath));
        String html = doc.html();
        assertEquals(expected, html);
        assertEquals(html, doc.outerHtml());
    }
}
