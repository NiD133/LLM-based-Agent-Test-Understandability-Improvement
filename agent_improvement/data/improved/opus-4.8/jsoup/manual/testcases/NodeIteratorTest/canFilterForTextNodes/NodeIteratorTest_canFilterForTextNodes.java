package org.jsoup.nodes;

import org.jsoup.Jsoup;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;

public class NodeIteratorTest_canFilterForTextNodes {

    // Two sibling <div>s, each containing two <p> paragraphs with text.
    private static final String HTML =
        "<div id=1><p>One<p>Two</div><div id=2><p>Three<p>Four</div>";

    /**
     * Iterates every node and records what was visited, while asserting that the iterator never
     * yields null and never returns the same node twice in a row. The recorded trail is compared
     * against {@code expected}.
     */
    private static <T extends Node> void assertIterates(Iterator<T> it, String expected) {
        Node previous = null;
        StringBuilder visited = new StringBuilder();
        while (it.hasNext()) {
            Node node = it.next();
            assertNotNull(node);
            assertNotSame(previous, node);
            describeNode(node, visited);
            previous = node;
        }
        assertEquals(expected, visited.toString());
    }

    /** Iterates all nodes (no type filter) starting at {@code el} and checks the visited trail. */
    private static void assertContents(Element el, String expected) {
        NodeIterator<Node> it = NodeIterator.from(el);
        assertIterates(it, expected);
    }

    /**
     * Appends a short, semicolon-terminated description of {@code node} to {@code into}:
     * elements as their tag name (with {@code #id} when present), text nodes as their text,
     * and anything else as its node name.
     */
    private static void describeNode(Node node, StringBuilder into) {
        if (node instanceof Element) {
            Element el = (Element) node;
            into.append(el.tagName());
            if (el.hasAttr("id"))
                into.append("#").append(el.id());
        } else if (node instanceof TextNode) {
            into.append(((TextNode) node).text());
        } else {
            into.append(node.nodeName());
        }
        into.append(";");
    }

    @Test
    void canFilterForTextNodes() {
        Document doc = Jsoup.parse(HTML);

        // Filtering for TextNode.class should yield only the text nodes, in document order.
        NodeIterator<TextNode> textNodes = new NodeIterator<>(doc, TextNode.class);
        StringBuilder visitedText = new StringBuilder();
        while (textNodes.hasNext()) {
            TextNode text = textNodes.next();
            assertNotNull(text);
            describeNode(text, visitedText);
        }
        assertEquals("One;Two;Three;Four;", visitedText.toString());

        // An unfiltered iterator over the same document should visit every node in document order.
        assertContents(doc, "#root;html;head;body;div#1;p;One;p;Two;div#2;p;Three;p;Four;");
    }
}
