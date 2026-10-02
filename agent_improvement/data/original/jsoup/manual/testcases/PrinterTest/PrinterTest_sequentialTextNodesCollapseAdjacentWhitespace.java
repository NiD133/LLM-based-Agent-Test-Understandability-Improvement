package org.jsoup.nodes;

import org.jsoup.Jsoup;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.io.IOException;
import static org.jsoup.integration.ParseTest.getFile;
import static org.jsoup.integration.ParseTest.getFileAsString;
import static org.junit.jupiter.api.Assertions.*;

public class PrinterTest_sequentialTextNodesCollapseAdjacentWhitespace {

    @Test
    void sequentialTextNodesCollapseAdjacentWhitespace() {
        // https://github.com/jhy/jsoup/pull/2349
        // Tests that the pretty printer collapses whitespace between sequential text nodes into a single space.
        // This must also work with intermediate empty and blank text nodes.
        Document doc = Jsoup.parseBodyFragment("Before <span> </span> After");
        doc.expectFirst("span").after(new TextNode("")).after(new TextNode("")).after(new TextNode(" ")).after(new TextNode("")).remove();
        // no collapse before printing
        assertEquals(6, doc.body().textNodes().size());
        assertEquals("Before After", doc.body().html());
    }
}
