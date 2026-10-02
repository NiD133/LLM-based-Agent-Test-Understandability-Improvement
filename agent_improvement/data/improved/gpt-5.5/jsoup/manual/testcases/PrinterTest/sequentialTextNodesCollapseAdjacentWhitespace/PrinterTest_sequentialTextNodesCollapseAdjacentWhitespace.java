package org.jsoup.nodes;

import org.jsoup.Jsoup;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PrinterTest_sequentialTextNodesCollapseAdjacentWhitespace {

    @Test
    void sequentialTextNodesCollapseAdjacentWhitespace() {
        // https://github.com/jhy/jsoup/pull/2349
        Document doc = Jsoup.parseBodyFragment("Before <span> </span> After");
        Element whitespaceMarker = doc.expectFirst("span");

        whitespaceMarker
            .after(new TextNode(""))
            .after(new TextNode(""))
            .after(new TextNode(" "))
            .after(new TextNode(""))
            .remove();

        assertEquals(6, doc.body().textNodes().size());
        assertEquals("Before After", doc.body().html());
    }
}
