package org.jsoup.nodes;

import org.jsoup.Jsoup;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;

/**
 * Verifies that a {@link NodeIterator} can be reset with {@link NodeIterator#restart(Node)}
 * and then traverse a different subtree, behaving as if freshly constructed.
 */
public class NodeIteratorTest_canRestart {

    /** Two sibling {@code div}s, each holding two paragraphs, used as the document under test. */
    private final String html = "<div id=1><p>One<p>Two</div><div id=2><p>Three<p>Four</div>";

    @Test
    void canRestart() {
        Document doc = Jsoup.parse(html);
        NodeIterator<Node> iterator = NodeIterator.from(doc);

        // A fresh iterator walks the entire document in document order.
        assertIterates(iterator, "#root;html;head;body;div#1;p;One;p;Two;div#2;p;Three;p;Four;");

        // After restarting from the second div, the same iterator walks only that subtree.
        iterator.restart(doc.expectFirst("div#2"));
        assertIterates(iterator, "div#2;p;Three;p;Four;");
    }

    /**
     * Drains the iterator and asserts that the semicolon-separated summary of the visited
     * nodes matches {@code expectedTrail}. Also checks that every emitted node is non-null
     * and distinct from the one before it.
     */
    private static <T extends Node> void assertIterates(Iterator<T> iterator, String expectedTrail) {
        Node previousNode = null;
        StringBuilder visitedTrail = new StringBuilder();

        while (iterator.hasNext()) {
            Node currentNode = iterator.next();
            assertNotNull(currentNode);
            assertNotSame(previousNode, currentNode);
            appendNodeSummary(currentNode, visitedTrail);
            previousNode = currentNode;
        }

        assertEquals(expectedTrail, visitedTrail.toString());
    }

    /**
     * Appends a short, human-readable summary of {@code node} to {@code trail}, followed by a
     * {@code ";"} separator:
     * <ul>
     *   <li>Elements are rendered as their tag name, plus {@code #id} when an id is present.</li>
     *   <li>Text nodes are rendered as their text.</li>
     *   <li>All other nodes are rendered by their node name.</li>
     * </ul>
     */
    private static void appendNodeSummary(Node node, StringBuilder trail) {
        if (node instanceof Element) {
            Element element = (Element) node;
            trail.append(element.tagName());
            if (element.hasAttr("id"))
                trail.append("#").append(element.id());
        } else if (node instanceof TextNode) {
            trail.append(((TextNode) node).text());
        } else {
            trail.append(node.nodeName());
        }
        trail.append(";");
    }
}
