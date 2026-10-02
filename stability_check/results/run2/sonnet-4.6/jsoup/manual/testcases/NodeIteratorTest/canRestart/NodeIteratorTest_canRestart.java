package org.jsoup.nodes;

import org.jsoup.Jsoup;
import org.junit.jupiter.api.Test;
import java.util.Iterator;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests that a {@link NodeIterator} can be restarted mid-use via {@link NodeIterator#restart(Node)},
 * which repositions the iterator to a new starting node without allocating a new iterator.
 */
public class NodeIteratorTest_canRestart {

    // Document structure: two sibling divs, each containing two paragraphs
    String html = "<div id=1><p>One<p>Two</div><div id=2><p>Three<p>Four</div>";

    /**
     * Walks the iterator to exhaustion, recording each visited node in document order,
     * and asserts the collected traversal matches {@code expected}.
     *
     * Each visited node is appended to {@code actual} via {@link #trackSeen}:
     * elements are recorded as "tagName" (with "#id" when present),
     * text nodes as their trimmed text, and all others by their node name.
     */
    static <T extends Node> void assertIterates(Iterator<T> it, String expected) {
        Node previous = null;
        StringBuilder actual = new StringBuilder();
        while (it.hasNext()) {
            Node node = it.next();
            assertNotNull(node);
            assertNotSame(previous, node); // each call to next() must return a distinct node
            trackSeen(node, actual);
            previous = node;
        }
        assertEquals(expected, actual.toString());
    }

    /** Appends a short descriptor for {@code node} (followed by ";") to {@code actual}. */
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

    /**
     * Verifies that after fully iterating a document, calling {@code restart()} with a
     * descendant node repositions the iterator so it traverses only that subtree, as if
     * it were freshly created from that node.
     */
    @Test
    void canRestart() {
        Document doc = Jsoup.parse(html);

        // First pass: iterate the full document tree from the root
        NodeIterator<Node> it = NodeIterator.from(doc);
        assertIterates(it, "#root;html;head;body;div#1;p;One;p;Two;div#2;p;Three;p;Four;");

        // Restart the same iterator at "div#2"; it should now traverse only that subtree
        it.restart(doc.expectFirst("div#2"));
        assertIterates(it, "div#2;p;Three;p;Four;");
    }
}
