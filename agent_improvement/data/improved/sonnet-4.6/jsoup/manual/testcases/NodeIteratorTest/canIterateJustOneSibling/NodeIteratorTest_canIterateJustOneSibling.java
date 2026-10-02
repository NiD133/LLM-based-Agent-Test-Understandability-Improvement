package org.jsoup.nodes;

import org.jsoup.Jsoup;
import org.junit.jupiter.api.Test;
import java.util.Iterator;
import java.util.NoSuchElementException;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests that a NodeIterator starting at a node with siblings only visits
 * that node and its own descendants — not the node's siblings.
 */
public class NodeIteratorTest_canIterateJustOneSibling {

    // HTML with two sibling divs, each containing two sibling paragraphs
    String html = "<div id=1><p>One<p>Two</div><div id=2><p>Three<p>Four</div>";

    /**
     * Walks the iterator to completion, building a semicolon-delimited string
     * of visited node names/text, and asserts it equals {@code expected}.
     * Also verifies that each node is non-null and distinct from its predecessor.
     */
    static <T extends Node> void assertIterates(Iterator<T> it, String expected) {
        Node previous = null;
        StringBuilder actual = new StringBuilder();
        while (it.hasNext()) {
            Node node = it.next();
            assertNotNull(node);
            assertNotSame(previous, node);
            trackSeen(node, actual);
            previous = node;
        }
        assertEquals(expected, actual.toString());
    }

    /** Appends a short representation of {@code node} to {@code actual}. */
    public static void trackSeen(Node node, StringBuilder actual) {
        if (node instanceof Element) {
            Element el = (Element) node;
            actual.append(el.tagName());
            if (el.hasAttr("id"))
                actual.append("#").append(el.id());
        } else if (node instanceof TextNode) {
            actual.append(((TextNode) node).text());
        } else {
            actual.append(node.nodeName());
        }
        actual.append(";");
    }

    @Test
    void canIterateJustOneSibling() {
        Document doc = Jsoup.parse(html);

        // Select the second <p> inside div#1 — it has a sibling (<p>One) and
        // a parent (<div id=1>) that itself has siblings, but the iterator
        // should stay within p2's own subtree.
        Element p2 = doc.expectFirst("p:contains(Two)");
        assertEquals("Two", p2.text());

        // A general-node iterator rooted at p2 must visit p2 and its text child only.
        NodeIterator<Node> nodeIterator = NodeIterator.from(p2);
        assertIterates(nodeIterator, "p;Two;");

        // An Element-typed iterator rooted at p2 must yield p2 as the sole element
        // and then report no further elements.
        NodeIterator<Element> elementIterator = new NodeIterator<>(p2, Element.class);
        Element firstElement = elementIterator.next();
        assertSame(p2, firstElement);
        assertFalse(elementIterator.hasNext());
    }
}
