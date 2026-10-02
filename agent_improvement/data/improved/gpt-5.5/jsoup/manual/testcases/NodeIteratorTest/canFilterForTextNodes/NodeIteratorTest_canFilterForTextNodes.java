package org.jsoup.nodes;

import org.jsoup.Jsoup;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;

public class NodeIteratorTest_canFilterForTextNodes {
    private static final String HTML =
        "<div id=1><p>One<p>Two</div><div id=2><p>Three<p>Four</div>";
    private static final String EXPECTED_TEXT_NODES = "One;Two;Three;Four;";
    private static final String EXPECTED_ALL_NODES =
        "#root;html;head;body;div#1;p;One;p;Two;div#2;p;Three;p;Four;";

    @Test
    void canFilterForTextNodes() {
        Document doc = Jsoup.parse(HTML);
        NodeIterator<TextNode> textNodes = new NodeIterator<>(doc, TextNode.class);

        StringBuilder seen = new StringBuilder();
        while (textNodes.hasNext()) {
            TextNode text = textNodes.next();
            assertNotNull(text);
            appendNodeDescription(text, seen);
        }

        assertEquals(EXPECTED_TEXT_NODES, seen.toString());
        assertContents(doc, EXPECTED_ALL_NODES);
    }

    private static void assertContents(Element element, String expected) {
        NodeIterator<Node> iterator = NodeIterator.from(element);
        assertIterates(iterator, expected);
    }

    private static <T extends Node> void assertIterates(Iterator<T> iterator, String expected) {
        Node previous = null;
        StringBuilder actual = new StringBuilder();

        while (iterator.hasNext()) {
            Node node = iterator.next();
            assertNotNull(node);
            assertNotSame(previous, node);
            appendNodeDescription(node, actual);
            previous = node;
        }

        assertEquals(expected, actual.toString());
    }

    private static void appendNodeDescription(Node node, StringBuilder actual) {
        if (node instanceof Element) {
            appendElementDescription((Element) node, actual);
        } else if (node instanceof TextNode) {
            actual.append(((TextNode) node).text());
        } else {
            actual.append(node.nodeName());
        }
        actual.append(";");
    }

    private static void appendElementDescription(Element element, StringBuilder actual) {
        actual.append(element.tagName());
        if (element.hasAttr("id")) {
            actual.append("#").append(element.id());
        }
    }
}
