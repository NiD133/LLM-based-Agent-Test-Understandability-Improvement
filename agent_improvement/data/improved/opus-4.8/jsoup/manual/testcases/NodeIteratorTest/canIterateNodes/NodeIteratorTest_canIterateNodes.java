package org.jsoup.nodes;

import org.jsoup.Jsoup;
import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Verifies that {@link NodeIterator} walks a parsed document and visits every node
 * (elements, text, and the document root) exactly once, in document order.
 */
public class NodeIteratorTest_canIterateNodes {

    private final String html = "<div id=1><p>One<p>Two</div><div id=2><p>Three<p>Four</div>";

    @Test
    void canIterateNodes() {
        Document doc = Jsoup.parse(html);
        NodeIterator<Node> iterator = NodeIterator.from(doc);

        // Each node is rendered to a short token (see describe()) and joined with ';'.
        // The expected string lists every node of the document in document order.
        String expectedNodesInOrder =
            "#root;html;head;body;div#1;p;One;p;Two;div#2;p;Three;p;Four;";
        assertIteratesOver(iterator, expectedNodesInOrder);

        // After visiting the last node the iterator is exhausted and next() must fail.
        assertFalse(iterator.hasNext());
        assertThrows(NoSuchElementException.class, iterator::next);
    }

    /**
     * Drains the iterator and asserts that the visited nodes, rendered as tokens,
     * match {@code expected}. Also checks that every visited node is non-null and
     * that consecutive calls never return the same node instance.
     */
    private static <T extends Node> void assertIteratesOver(Iterator<T> iterator, String expected) {
        Node previousNode = null;
        StringBuilder visitedNodes = new StringBuilder();

        while (iterator.hasNext()) {
            Node node = iterator.next();
            assertNotNull(node);
            assertNotSame(previousNode, node);

            visitedNodes.append(describe(node)).append(";");
            previousNode = node;
        }

        assertEquals(expected, visitedNodes.toString());
    }

    /**
     * Renders a node to a compact token:
     * an element becomes its tag name (plus "#id" when it carries an id),
     * a text node becomes its text, and any other node becomes its node name.
     */
    private static String describe(Node node) {
        if (node instanceof Element) {
            Element element = (Element) node;
            String token = element.tagName();
            if (element.hasAttr("id"))
                token += "#" + element.id();
            return token;
        }
        if (node instanceof TextNode)
            return ((TextNode) node).text();
        return node.nodeName();
    }
}
