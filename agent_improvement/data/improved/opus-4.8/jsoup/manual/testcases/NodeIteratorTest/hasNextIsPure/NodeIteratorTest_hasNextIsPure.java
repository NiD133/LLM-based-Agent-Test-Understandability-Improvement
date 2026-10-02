package org.jsoup.nodes;

import org.jsoup.Jsoup;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Verifies that {@link NodeIterator#hasNext()} is a pure, side-effect-free query:
 * calling it (repeatedly, or not at all) must not advance the iterator or change
 * which nodes {@link NodeIterator#next()} subsequently returns.
 */
public class NodeIteratorTest_hasNextIsPure {

    // Two sibling divs, each containing two paragraphs, so the document tree has
    // enough depth and breadth to exercise a full document-order traversal.
    private final String html = "<div id=1><p>One<p>Two</div><div id=2><p>Three<p>Four</div>";

    @Test
    void hasNextIsPure() {
        Document doc = Jsoup.parse(html);
        NodeIterator<Node> iterator = NodeIterator.from(doc);

        // Calling hasNext() many times before any next() must not consume the first node.
        assertTrue(iterator.hasNext());
        assertTrue(iterator.hasNext());

        // The very first next() still returns the root, proving the prior hasNext() calls
        // did not advance the iterator. The full walk visits every node in document order.
        String expectedDocumentOrder =
            "#root;html;head;body;div#1;p;One;p;Two;div#2;p;Three;p;Four;";
        assertIteratesInOrder(iterator, expectedDocumentOrder);

        // After the traversal is exhausted, hasNext() must report false.
        assertFalse(iterator.hasNext());
    }

    /**
     * Drains the iterator and asserts that the visited nodes, rendered as a
     * semicolon-separated summary, match {@code expected} exactly.
     */
    private static <T extends Node> void assertIteratesInOrder(Iterator<T> iterator, String expected) {
        Node previous = null;
        StringBuilder visited = new StringBuilder();
        while (iterator.hasNext()) {
            Node node = iterator.next();
            assertNotNull(node);
            assertNotSame(previous, node, "next() must not return the same node twice in a row");
            appendNodeSummary(node, visited);
            previous = node;
        }
        assertEquals(expected, visited.toString());
    }

    /**
     * Appends a short, human-readable summary of {@code node} to {@code out}:
     * elements as their tag name (plus {@code #id} when present), text nodes as
     * their text, and anything else as its node name. Each entry ends with ';'.
     */
    private static void appendNodeSummary(Node node, StringBuilder out) {
        if (node instanceof Element) {
            Element element = (Element) node;
            out.append(element.tagName());
            if (element.hasAttr("id"))
                out.append("#").append(element.id());
        } else if (node instanceof TextNode) {
            out.append(((TextNode) node).text());
        } else {
            out.append(node.nodeName());
        }
        out.append(";");
    }
}
