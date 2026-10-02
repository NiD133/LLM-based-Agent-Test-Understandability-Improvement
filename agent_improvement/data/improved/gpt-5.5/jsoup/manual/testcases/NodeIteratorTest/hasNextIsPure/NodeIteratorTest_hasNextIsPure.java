package org.jsoup.nodes;

import org.jsoup.Jsoup;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class NodeIteratorTest_hasNextIsPure {
    private static final String HTML =
        "<div id=1><p>One<p>Two</div><div id=2><p>Three<p>Four</div>";

    private static final String EXPECTED_DOCUMENT_ORDER =
        "#root;html;head;body;div#1;p;One;p;Two;div#2;p;Three;p;Four;";

    @Test
    void hasNextIsPure() {
        Document doc = Jsoup.parse(HTML);
        NodeIterator<Node> it = NodeIterator.from(doc);

        assertTrue(it.hasNext());
        assertTrue(it.hasNext());
        assertIterates(it, EXPECTED_DOCUMENT_ORDER);
        assertFalse(it.hasNext());
    }

    private static <T extends Node> void assertIterates(Iterator<T> it, String expected) {
        Node previous = null;
        StringBuilder actual = new StringBuilder();

        while (it.hasNext()) {
            Node node = it.next();
            assertNotNull(node);
            assertNotSame(previous, node);

            appendNodeSummary(node, actual);
            previous = node;
        }

        assertEquals(expected, actual.toString());
    }

    private static void appendNodeSummary(Node node, StringBuilder actual) {
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
}
