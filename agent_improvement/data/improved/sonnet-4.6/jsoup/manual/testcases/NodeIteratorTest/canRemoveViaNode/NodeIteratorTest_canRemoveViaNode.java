package org.jsoup.nodes;

import org.jsoup.Jsoup;
import org.junit.jupiter.api.Test;
import java.util.Iterator;
import java.util.NoSuchElementException;
import static org.junit.jupiter.api.Assertions.*;

public class NodeIteratorTest_canRemoveViaNode {

    String html = "<div id=1><p>One<p>Two</div><div id=2><p>Three<p>Four</div>";

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

    static void assertContents(Element el, String expected) {
        NodeIterator<Node> it = NodeIterator.from(el);
        assertIterates(it, expected);
    }

    public static void trackSeen(Node node, StringBuilder actual) {
        if (node instanceof Element) {
            Element el = (Element) node;
            actual.append(el.tagName());
            if (el.hasAttr("id"))
                actual.append("#").append(el.id());
        } else if (node instanceof TextNode)
            actual.append(((TextNode) node).text());
        else
            actual.append(node.nodeName());
        actual.append(";");
    }

    /**
     * Verifies that a node can be removed during iteration via {@link Node#remove()}.
     *
     * <p>Key behaviour under test: calling {@code node.remove()} inside the iteration loop removes
     * the node from the document tree. The removed node itself still appears in the "seen" log
     * (because {@code next()} already returned it), but its descendants are skipped — the iterator
     * detects the structural change and jumps past the removed subtree.
     */
    @Test
    void canRemoveViaNode() {
        // Arrange: a document with two outer divs; the first outer div contains two inner divs,
        // each holding two paragraphs.
        String html = "<div id=out1><div id=1><p>One<p>Two</div><div id=2><p>Three<p>Four</div></div><div id=out2>Out2";
        Document doc = Jsoup.parse(html);

        // --- First pass: remove div#1 mid-iteration ---
        // Act
        NodeIterator<Node> it = NodeIterator.from(doc);
        StringBuilder seen = new StringBuilder();
        while (it.hasNext()) {
            Node node = it.next();
            if (node.attr("id").equals("1"))
                node.remove();
            trackSeen(node, seen);
        }

        // Assert: div#1 appears in "seen" (it was already returned before removal),
        // but its children (p;One;p;Two) are absent because the iterator skipped them
        // after the structural change.
        assertEquals("#root;html;head;body;div#out1;div#1;div#2;p;Three;p;Four;div#out2;Out2;", seen.toString());
        // The document itself no longer contains div#1 or its descendants.
        assertContents(doc, "#root;html;head;body;div#out1;div#2;p;Three;p;Four;div#out2;Out2;");

        // --- Second pass: remove div#2 mid-iteration ---
        // Act
        it = NodeIterator.from(doc);
        seen = new StringBuilder();
        while (it.hasNext()) {
            Node node = it.next();
            if (node.attr("id").equals("2"))
                node.remove();
            trackSeen(node, seen);
        }

        // Assert: same pattern — div#2 appears in "seen" but its children (p;Three;p;Four) are skipped.
        assertEquals("#root;html;head;body;div#out1;div#2;div#out2;Out2;", seen.toString());
        // The document now contains neither div#1 nor div#2.
        assertContents(doc, "#root;html;head;body;div#out1;div#out2;Out2;");
    }
}
