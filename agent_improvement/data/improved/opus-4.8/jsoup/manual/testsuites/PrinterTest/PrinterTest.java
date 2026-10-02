package org.jsoup.nodes;

import org.jsoup.Jsoup;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.function.Consumer;

import static org.jsoup.integration.ParseTest.getFile;
import static org.jsoup.integration.ParseTest.getFileAsString;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 Test for the HTML / XML serialization of elements, including the Pretty Printer, Outline mode, and the base.
 */
public class PrinterTest {
    /** Shared parse input used by the fixture-comparison serialization tests. */
    private static final String INPUT_FILE = "/printertests/input-1.html";

    /**
     Parses {@link #INPUT_FILE}, applies the given output settings, and asserts that the serialized HTML matches the
     contents of the named fixture file. Also verifies that {@code html()} and {@code outerHtml()} agree.

     @param expectedFixture path of the fixture file holding the expected serialization
     @param configure       adjustment to apply to the document's output settings before serializing
     */
    private void assertSerializesTo(String expectedFixture, Consumer<Document.OutputSettings> configure)
            throws IOException {
        File in = getFile(INPUT_FILE);
        Document doc = Jsoup.parse(in);
        configure.accept(doc.outputSettings());

        String expected = getFileAsString(getFile(expectedFixture));
        String html = doc.html();
        assertEquals(expected, html);
        assertEquals(html, doc.outerHtml());
    }

    @Test void pretty() throws IOException {
        // pretty printing is on by default: formatted output should match pretty-1.html
        assertSerializesTo("/printertests/pretty-1.html", settings -> { /* default settings */ });
    }

    @Test void passthru() throws IOException {
        // disable pretty, should be almost 1:1 of input (other than a couple parse normalizations; doctype, pre)
        assertSerializesTo("/printertests/passthru-1.html", settings -> settings.prettyPrint(false));
    }

    @Test void outline() throws IOException {
        // outline mode, most everything gets indented
        assertSerializesTo("/printertests/outline-1.html", settings -> settings.outline(true));
    }

    @Test void sequentialTextNodesDontCollapse() {
        // tests that the pretty printer does not collapse (trim leading | trailing whitespace) when there are
        // sequential textnodes. That doesn't happen in a parse, but can when manipulated.
        Document doc = Jsoup.parse("<div><div></div>Hello</div>"); // needs to be text that would indent
        Element div = doc.expectFirst("div");
        TextNode hello = (TextNode) div.childNode(1);
        hello.after(" there.");

        // the two text nodes keep their own whitespace and are not merged
        assertEquals("Hello", hello.getWholeText());
        assertEquals(" there.", ((TextNode) hello.nextSibling()).getWholeText());

        assertEquals("Hello there.", div.text());
        assertEquals("<div></div>\nHello there.", div.html());
        assertEquals("<div>\n <div></div>\n Hello there.\n</div>", div.outerHtml());
    }

    @Test void sequentialTextNodesCollapseAdjacentWhitespace() {
        // https://github.com/jhy/jsoup/pull/2349
        // Tests that the pretty printer collapses whitespace between sequential text nodes into a single space.
        // This must also work with intermediate empty and blank text nodes.
        Document doc = Jsoup.parseBodyFragment("Before <span> </span> After");

        // replace the <span> with a run of empty and blank text nodes, leaving them un-collapsed in the tree
        doc.expectFirst("span")
                .after(new TextNode("")).after(new TextNode("")).after(new TextNode(" ")).after(new TextNode(""))
                .remove();

        assertEquals(6, doc.body().textNodes().size()); // no collapse before printing
        assertEquals("Before After", doc.body().html());
    }

    @Test void dontCollapseTextAfterNonElements() {
        Document doc = Jsoup.parse("<div><div></div>Hello <!-- -_- --> there</div>");
        Element body = doc.body();

        // the comment between "Hello" and "there" must not cause those words to be collapsed together
        assertEquals("Hello there", body.text());
        assertEquals("<div>\n <div></div>\n Hello <!-- -_- -->\n  there\n</div>", body.html());
    }

    @Test void spaceAfterSpanInBlock() {
        Document doc = Jsoup.parse("<div> <span>Span</span> \n Text  <span>Follow</span></div> <p> <span>Span</span>  Text <span>Follow</span> </p>");
        Element body = doc.body();

        // a single space is preserved after each inline <span> within the block
        assertEquals("Span Text Follow Span Text Follow", body.text());
        assertEquals("<div>\n <span>Span</span> Text <span>Follow</span>\n</div>\n<p><span>Span</span> Text <span>Follow</span></p>", body.html());
    }

}
