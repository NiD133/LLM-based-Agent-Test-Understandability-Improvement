package org.jsoup.nodes;

import org.jsoup.Jsoup;
import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class NodeIteratorTest_canIterateNodes {
    private static final String HTML_WITH_TWO_DIVS =
        "<div id=1><p>One<p>Two</div><div id=2><p>Three<p>Four</div>";
    private static final String EXPECTED_DOCUMENT_ORDER =
        "#root;html;head;body;div#1;p;One;p;Two;div#2;p;Three;p;Four;";

    @Test
    void canIterateNodes() {
        Document doc = Jsoup.parse(HTML_WITH_TWO_DIVS);
        NodeIterator<Node> iterator = NodeIterator.from(doc);

        assertIteratesInDocumentOrder(iterator, EXPECTED_DOCUMENT_ORDER);
        assertIteratorIsExhausted(iterator);
    }

    private static void assertIteratorIsExhausted(NodeIterator<Node> iterator) {
        assertFalse(iterator.hasNext());

        boolean threw = false;
        try {
            iterator.next();
        } catch (NoSuchElementException e) {
            threw = true;
        }
        assertTrue(threw);
    }

    private static <T extends Node> void assertIteratesInDocumentOrder(Iterator<T> iterator, String expectedOrder) {
        Node previous = null;
        StringBuilder actualOrder = new StringBuilder();

        while (iterator.hasNext()) {
            Node node = iterator.next();

            assertNotNull(node);
            assertNotSame(previous, node);
            appendNodeDescription(node, actualOrder);
            previous = node;
        }

        assertEquals(expectedOrder, actualOrder.toString());
    }

    private static void appendNodeDescription(Node node, StringBuilder actualOrder) {
        if (node instanceof Element) {
            Element element = (Element) node;
            actualOrder.append(element.tagName());
            if (element.hasAttr("id")) {
                actualOrder.append("#").append(element.id());
            }
        } else if (node instanceof TextNode) {
            actualOrder.append(((TextNode) node).text());
        } else {
            actualOrder.append(node.nodeName());
        }

        actualOrder.append(";");
    }
}
