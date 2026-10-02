package org.jsoup.nodes;

import org.jsoup.Jsoup;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;

/**
 * Verifies that a {@link NodeIterator} can be restarted from a new node and then
 * resumes iterating in document order from that node onward.
 */
public class NodeIteratorTest_canRestart {

    private static final String HTML =
        "<div id=1><p>One<p>Two</div><div id=2><p>Three<p>Four</div>";

    @Test
    void canRestart() {
        Document doc = Jsoup.parse(HTML);
        NodeIterator<Node> it = NodeIterator.from(doc);

        // A fresh iterator walks the whole document, in document order.
        assertIteratesTo(it, "#root;html;head;body;div#1;p;One;p;Two;div#2;p;Three;p;Four;");

        // After restarting at the second div, the same iterator walks only that subtree.
        it.restart(doc.expectFirst("div#2"));
        assertIteratesTo(it, "div#2;p;Three;p;Four;");
    }

    /**
     * Drains the iterator and asserts that the nodes it produces, summarised one per node,
     * match the expected semicolon-separated string. Also checks that every node is non-null
     * and that no node is returned twice in a row.
     */
    private static <T extends Node> void assertIteratesTo(Iterator<T> it, String expected) {
        Node previous = null;
        StringBuilder seen = new StringBuilder();

        while (it.hasNext()) {
            Node node = it.next();
            assertNotNull(node);
            assertNotSame(previous, node);
            appendSummary(node, seen);
            previous = node;
        }

        assertEquals(expected, seen.toString());
    }

    /**
     * Appends a short, human-readable summary of {@code node} (followed by a ';') to {@code out}:
     * an element as its tag name (plus "#id" when it has an id), a text node as its text,
     * and any other node as its node name.
     */
    private static void appendSummary(Node node, StringBuilder out) {
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
}
