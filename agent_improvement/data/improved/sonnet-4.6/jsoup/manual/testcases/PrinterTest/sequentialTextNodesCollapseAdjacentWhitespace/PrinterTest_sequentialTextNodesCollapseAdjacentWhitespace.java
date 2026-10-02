package org.jsoup.nodes;

import org.jsoup.Jsoup;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PrinterTest_sequentialTextNodesCollapseAdjacentWhitespace {

    @Test
    void sequentialTextNodesCollapseAdjacentWhitespace() {
        // https://github.com/jhy/jsoup/pull/2349
        // The pretty printer must collapse whitespace across sequential text nodes —
        // including empty and blank-only ones — into a single space between words.

        // Parse a fragment where a span carries the only whitespace between "Before" and "After".
        Document doc = Jsoup.parseBodyFragment("Before <span> </span> After");

        // Replace the span with a run of empty/blank text nodes, simulating the edge case.
        // Each .after() inserts its node immediately after the span, so the final sibling
        // order (left-to-right) is the reverse of insertion order:
        //   "Before " | "" | " " | "" | "" | " After"
        Element span = doc.expectFirst("span");
        span.after(new TextNode(""));   // 4th in DOM order after removal
        span.after(new TextNode(""));   // 3rd in DOM order after removal
        span.after(new TextNode(" "));  // 2nd in DOM order after removal — single blank space
        span.after(new TextNode(""));   // 1st in DOM order after removal
        span.remove();

        // Verify the raw DOM has 6 text nodes and has NOT been collapsed yet.
        // ("Before ", "", " ", "", "", " After")
        assertEquals(6, doc.body().textNodes().size());

        // The pretty printer must render the six text nodes as a single space between words.
        assertEquals("Before After", doc.body().html());
    }
}
