package org.jsoup.nodes;

import org.jsoup.Jsoup;
import org.junit.jupiter.api.Test;
import java.util.Iterator;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests that {@link NodeIterator#hasNext()} is a pure (idempotent) operation:
 * calling it repeatedly without an intervening {@link Iterator#next()} call
 * must not advance the iterator or cause any node to be skipped.
 */
public class NodeIteratorTest_hasNextIsPure {

    /** Two sibling divs, each containing two paragraphs with text nodes. */
    static final String HTML = "<div id=1><p>One<p>Two</div><div id=2><p>Three<p>Four</div>";

    /**
     * Walks {@code it} to exhaustion, collecting a semicolon-delimited label
     * for every visited node, then asserts the result matches {@code expected}.
     * Also verifies that consecutive {@link Iterator#next()} calls never return
     * the same object reference.
     */
    static <T extends Node> void assertIterates(Iterator<T> it, String expected) {
        Node previous = null;
        StringBuilder actual = new StringBuilder();
        while (it.hasNext()) {
            Node node = it.next();
            assertNotNull(node);
            assertNotSame(previous, node);
            appendNodeLabel(node, actual);
            previous = node;
        }
        assertEquals(expected, actual.toString());
    }

    /** Creates a {@link NodeIterator} rooted at {@code el} and asserts full traversal matches {@code expected}. */
    static void assertContents(Element el, String expected) {
        NodeIterator<Node> it = NodeIterator.from(el);
        assertIterates(it, expected);
    }

    /**
     * Appends a short label for {@code node} to {@code sb}:
     * elements emit their tag name (with {@code #id} when present),
     * text nodes emit their text content, and all other nodes emit their node name.
     * A {@code ";"} delimiter is always appended at the end.
     */
    public static void appendNodeLabel(Node node, StringBuilder sb) {
        if (node instanceof Element) {
            Element el = (Element) node;
            sb.append(el.tagName());
            if (el.hasAttr("id"))
                sb.append("#").append(el.id());
        } else if (node instanceof TextNode) {
            sb.append(((TextNode) node).text());
        } else {
            sb.append(node.nodeName());
        }
        sb.append(";");
    }

    /**
     * Verifies that {@link NodeIterator#hasNext()} is idempotent: calling it
     * multiple times in a row, without any intervening {@link Iterator#next()}
     * call, must not advance the iterator or skip any nodes.
     */
    @Test
    void hasNextIsPure() {
        Document doc = Jsoup.parse(HTML);
        NodeIterator<Node> it = NodeIterator.from(doc);

        // Two back-to-back hasNext() calls — neither should consume a node.
        assertTrue(it.hasNext());
        assertTrue(it.hasNext());

        // Every node is still reachable; nothing was skipped by the repeated hasNext() calls above.
        assertIterates(it, "#root;html;head;body;div#1;p;One;p;Two;div#2;p;Three;p;Four;");

        assertFalse(it.hasNext());
    }
}
