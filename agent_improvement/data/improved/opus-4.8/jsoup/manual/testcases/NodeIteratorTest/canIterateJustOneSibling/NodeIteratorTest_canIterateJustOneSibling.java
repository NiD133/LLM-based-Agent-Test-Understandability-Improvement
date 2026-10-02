package org.jsoup.nodes;

import org.jsoup.Jsoup;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;

/**
 * Verifies that a {@link NodeIterator} started on a single element with no children visits exactly
 * that element (and its text), without straying into following siblings in the wider document.
 */
public class NodeIteratorTest_canIterateJustOneSibling {

    private static final String HTML =
        "<div id=1><p>One<p>Two</div><div id=2><p>Three<p>Four</div>";

    /**
     * Walks the iterator to completion and asserts that the sequence of visited nodes, rendered by
     * {@link #describeNode}, matches {@code expectedSequence}. Also checks the iterator never
     * returns null and never emits the same node instance twice in a row.
     */
    private static <T extends Node> void assertIterates(Iterator<T> iterator, String expectedSequence) {
        Node previousNode = null;
        StringBuilder visitedSequence = new StringBuilder();
        while (iterator.hasNext()) {
            Node node = iterator.next();
            assertNotNull(node);
            assertNotSame(previousNode, node);
            describeNode(node, visitedSequence);
            previousNode = node;
        }
        assertEquals(expectedSequence, visitedSequence.toString());
    }

    /**
     * Appends a short, semicolon-terminated description of {@code node} to {@code out}:
     * an element as its tag name (plus {@code #id} when present), a text node as its text,
     * and anything else as its node name.
     */
    private static void describeNode(Node node, StringBuilder out) {
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

    @Test
    void canIterateJustOneSibling() {
        Document doc = Jsoup.parse(HTML);

        // Start from the second paragraph ("Two"); it has no element siblings to descend into.
        Element paragraphTwo = doc.expectFirst("p:contains(Two)");
        assertEquals("Two", paragraphTwo.text());

        // A node-typed iterator visits the paragraph and its single text child, then stops.
        NodeIterator<Node> nodeIterator = NodeIterator.from(paragraphTwo);
        assertIterates(nodeIterator, "p;Two;");

        // An element-typed iterator visits only the paragraph itself, skipping the text node.
        NodeIterator<Element> elementIterator = new NodeIterator<>(paragraphTwo, Element.class);
        Element firstElement = elementIterator.next();
        assertSame(paragraphTwo, firstElement);
        assertFalse(elementIterator.hasNext());
    }
}
