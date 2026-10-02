package org.jsoup.nodes;

import org.jsoup.Jsoup;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;

public class NodeIteratorTest_canFilterForElements {
    private static final String HTML_WITH_NESTED_PARAGRAPHS =
        "<div id=1><p>One<p>Two</div><div id=2><p>Three<p>Four</div>";
    private static final String EXPECTED_ELEMENT_ORDER = "#root;html;head;body;div#1;p;p;div#2;p;p;";

    static <T extends Node> void assertIterates(Iterator<T> iterator, String expected) {
        Node previous = null;
        StringBuilder actual = new StringBuilder();

        while (iterator.hasNext()) {
            Node node = iterator.next();

            assertNotNull(node);
            assertNotSame(previous, node);
            trackSeen(node, actual);
            previous = node;
        }

        assertEquals(expected, actual.toString());
    }

    static void assertContents(Element element, String expected) {
        NodeIterator<Node> iterator = NodeIterator.from(element);
        assertIterates(iterator, expected);
    }

    public static void trackSeen(Node node, StringBuilder actual) {
        if (node instanceof Element) {
            Element element = (Element) node;
            actual.append(element.tagName());
            if (element.hasAttr("id")) {
                actual.append("#").append(element.id());
            }
        } else if (node instanceof TextNode) {
            actual.append(((TextNode) node).text());
        } else {
            actual.append(node.nodeName());
        }

        actual.append(";");
    }

    @Test
    void canFilterForElements() {
        Document document = Jsoup.parse(HTML_WITH_NESTED_PARAGRAPHS);
        NodeIterator<Element> elements = new NodeIterator<>(document, Element.class);
        StringBuilder seenElements = new StringBuilder();

        while (elements.hasNext()) {
            Element element = elements.next();

            assertNotNull(element);
            trackSeen(element, seenElements);
        }

        assertEquals(EXPECTED_ELEMENT_ORDER, seenElements.toString());
    }
}
