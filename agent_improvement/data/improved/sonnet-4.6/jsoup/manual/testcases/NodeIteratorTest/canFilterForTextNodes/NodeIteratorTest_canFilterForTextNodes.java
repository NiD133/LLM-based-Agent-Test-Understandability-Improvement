package org.jsoup.nodes;

import org.jsoup.Jsoup;
import org.junit.jupiter.api.Test;
import java.util.Iterator;
import java.util.NoSuchElementException;
import static org.junit.jupiter.api.Assertions.*;

public class NodeIteratorTest_canFilterForTextNodes {

    // Two divs, each containing two paragraphs with text nodes: "One", "Two", "Three", "Four"
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

    @Test
    void canFilterForTextNodes() {
        Document doc = Jsoup.parse(html);

        // Passing TextNode.class filters the iterator so it skips Element nodes and yields only text nodes
        NodeIterator<TextNode> textNodeIterator = new NodeIterator<>(doc, TextNode.class);

        StringBuilder collectedText = new StringBuilder();
        while (textNodeIterator.hasNext()) {
            TextNode text = textNodeIterator.next();
            assertNotNull(text);
            trackSeen(text, collectedText);
        }

        // Only the four text nodes should be visited; element nodes are excluded by the type filter
        assertEquals("One;Two;Three;Four;", collectedText.toString());

        // Confirm that the full document tree (elements and text nodes) is still intact after filtered traversal
        assertContents(doc, "#root;html;head;body;div#1;p;One;p;Two;div#2;p;Three;p;Four;");
    }
}
