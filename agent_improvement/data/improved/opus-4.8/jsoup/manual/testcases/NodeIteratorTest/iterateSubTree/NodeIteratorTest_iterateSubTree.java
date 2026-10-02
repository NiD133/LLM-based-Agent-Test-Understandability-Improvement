package org.jsoup.nodes;

import org.jsoup.Jsoup;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;

/**
 * Verifies that {@link NodeIterator} started from a non-root element walks only that element's
 * own subtree (the element itself plus all of its descendants), in document order.
 */
public class NodeIteratorTest_iterateSubTree {

    /** Two sibling divs, each containing two paragraphs. Used to confirm the iterator stays inside one div. */
    String html = "<div id=1><p>One<p>Two</div><div id=2><p>Three<p>Four</div>";

    /**
     * Drains the iterator and asserts that the nodes it visits, encoded as a string, match {@code expected}.
     * Along the way it also checks that every visited node is non-null and distinct from the one before it.
     */
    static <T extends Node> void assertIterates(Iterator<T> it, String expected) {
        Node previous = null;
        StringBuilder visitedNodes = new StringBuilder();
        while (it.hasNext()) {
            Node node = it.next();
            assertNotNull(node);
            assertNotSame(previous, node);
            appendNodeSignature(node, visitedNodes);
            previous = node;
        }
        assertEquals(expected, visitedNodes.toString());
    }

    /**
     * Encodes a single visited node into {@code out} as "value;", where the value is:
     * the tag name (plus "#id" when an id is present) for an element, the text for a text node,
     * or the node name otherwise.
     */
    public static void appendNodeSignature(Node node, StringBuilder out) {
        if (node instanceof Element) {
            Element el = (Element) node;
            out.append(el.tagName());
            if (el.hasAttr("id"))
                out.append("#").append(el.id());
        } else if (node instanceof TextNode) {
            out.append(((TextNode) node).text());
        } else {
            out.append(node.nodeName());
        }
        out.append(";");
    }

    @Test
    void iterateSubTree() {
        Document doc = Jsoup.parse(html);

        // Starting at the first div, the iterator should visit that div and its subtree only.
        Element div1 = doc.expectFirst("div#1");
        NodeIterator<Node> div1Iterator = NodeIterator.from(div1);
        assertIterates(div1Iterator, "div#1;p;One;p;Two;");
        assertFalse(div1Iterator.hasNext());

        // Starting at the second div should likewise stay within the second div's subtree.
        Element div2 = doc.expectFirst("div#2");
        NodeIterator<Node> div2Iterator = NodeIterator.from(div2);
        assertIterates(div2Iterator, "div#2;p;Three;p;Four;");
        assertFalse(div2Iterator.hasNext());
    }
}
