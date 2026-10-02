package org.jsoup.nodes;

import org.jsoup.Jsoup;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PrinterTest_sequentialTextNodesCollapseAdjacentWhitespace {

    /**
     * Verifies that the pretty printer collapses the whitespace between sequential text nodes
     * into a single space, even when there are empty or blank text nodes mixed in between.
     *
     * @see <a href="https://github.com/jhy/jsoup/pull/2349">jsoup PR #2349</a>
     */
    @Test
    void sequentialTextNodesCollapseAdjacentWhitespace() {
        // Start with: "Before" + <span> </span> + "After", giving two text nodes around the span.
        Document doc = Jsoup.parseBodyFragment("Before <span> </span> After");
        Element span = doc.expectFirst("span");

        // Insert a run of empty/blank text nodes immediately after the span, then drop the span itself.
        // Each after(...) re-inserts directly after `span`, so the inserted order is the reverse of the
        // call order: "", " ", "", "" end up sitting between the "Before " and " After" text nodes.
        span.after(new TextNode(""));
        span.after(new TextNode(""));
        span.after(new TextNode(" "));
        span.after(new TextNode(""));
        span.remove();

        // Before printing, the body still holds all six separate text nodes (none have been merged yet).
        assertEquals(6, doc.body().textNodes().size());

        // Pretty printing collapses the adjacent whitespace across those nodes into a single space.
        assertEquals("Before After", doc.body().html());
    }
}
