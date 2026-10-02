package org.jsoup.nodes;

import org.jsoup.Jsoup;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class NodeIteratorTest_canIterateFirstEmptySibling {

    /**
     * When the starting node is an empty element (no children) that has following siblings,
     * the iterator should yield only that start node and then stop — it must not wander into
     * the sibling subtrees.
     */
    @Test
    void canIterateFirstEmptySibling() {
        // First <p> is empty; the later siblings (#2, #3) carry text but must be ignored.
        Document doc = Jsoup.parse("<div><p id=1></p><p id=2>.</p><p id=3>..</p>");
        Element firstParagraph = doc.expectFirst("p#1");
        assertEquals("", firstParagraph.ownText(), "first paragraph should have no text");

        NodeIterator<Node> it = NodeIterator.from(firstParagraph);

        // The only node yielded is the start node itself.
        assertTrue(it.hasNext());
        assertSame(firstParagraph, it.next());

        // No descendants and no siblings are visited, so iteration ends immediately.
        assertFalse(it.hasNext());
    }
}
