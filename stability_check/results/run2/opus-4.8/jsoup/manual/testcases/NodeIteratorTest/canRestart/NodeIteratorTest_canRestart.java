package org.jsoup.nodes;

import org.jsoup.Jsoup;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;

/**
 * Verifies that a {@link NodeIterator} can be restarted from an arbitrary node and, once restarted,
 * traverses only that node and its descendants.
 */
public class NodeIteratorTest_canRestart {

    private final String html = "<div id=1><p>One<p>Two</div><div id=2><p>Three<p>Four</div>";

    /**
     * Drains the iterator and asserts that the nodes it visits, encoded via {@link #describeNode},
     * match the {@code expected} semicolon-separated sequence. Also checks that every visited node is
     * non-null and distinct from the one visited immediately before it.
     */
    static <T extends Node> void assertIterates(Iterator<T> iterator, String expectedSequence) {
        Node previousNode = null;
        StringBuilder visitedSequence = new StringBuilder();

        while (iterator.hasNext()) {
            Node currentNode = iterator.next();
            assertNotNull(currentNode);
            assertNotSame(previousNode, currentNode);
            describeNode(currentNode, visitedSequence);
            previousNode = currentNode;
        }

        assertEquals(expectedSequence, visitedSequence.toString());
    }

    /**
     * Appends a short, human-readable description of {@code node} to {@code sequence}, followed by a
     * ';' separator. Elements are shown as their tag name (with "#id" appended when an id is present),
     * text nodes as their text, and all other nodes as their node name.
     */
    public static void describeNode(Node node, StringBuilder sequence) {
        if (node instanceof Element) {
            Element element = (Element) node;
            sequence.append(element.tagName());
            if (element.hasAttr("id"))
                sequence.append("#").append(element.id());
        } else if (node instanceof TextNode) {
            sequence.append(((TextNode) node).text());
        } else {
            sequence.append(node.nodeName());
        }
        sequence.append(";");
    }

    @Test
    void canRestart() {
        Document doc = Jsoup.parse(html);
        NodeIterator<Node> iterator = NodeIterator.from(doc);

        // A fresh iterator visits the whole document in depth-first document order.
        assertIterates(iterator, "#root;html;head;body;div#1;p;One;p;Two;div#2;p;Three;p;Four;");

        // After restarting at the second div, iteration is confined to that subtree.
        iterator.restart(doc.expectFirst("div#2"));
        assertIterates(iterator, "div#2;p;Three;p;Four;");
    }
}
