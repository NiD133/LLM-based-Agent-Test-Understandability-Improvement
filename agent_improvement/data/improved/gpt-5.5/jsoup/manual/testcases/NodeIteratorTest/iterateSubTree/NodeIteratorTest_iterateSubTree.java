package org.jsoup.nodes;

import org.jsoup.Jsoup;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;

public class NodeIteratorTest_iterateSubTree {
    private static final String html =
            "<div id=1><p>One<p>Two</div><div id=2><p>Three<p>Four</div>";

    private static final String FIRST_DIV_TRAVERSAL = "div#1;p;One;p;Two;";
    private static final String SECOND_DIV_TRAVERSAL = "div#2;p;Three;p;Four;";

    @Test
    void iterateSubTree() {
        Document doc = Jsoup.parse(html);

        Element div1 = doc.expectFirst("div#1");
        NodeIterator<Node> it = NodeIterator.from(div1);
        assertIterates(it, FIRST_DIV_TRAVERSAL);
        assertFalse(it.hasNext());

        Element div2 = doc.expectFirst("div#2");
        NodeIterator<Node> it2 = NodeIterator.from(div2);
        assertIterates(it2, SECOND_DIV_TRAVERSAL);
        assertFalse(it2.hasNext());
    }

    private static <T extends Node> void assertIterates(Iterator<T> it, String expected) {
        Node previous = null;
        StringBuilder actual = new StringBuilder();

        while (it.hasNext()) {
            Node node = it.next();

            assertNotNull(node);
            assertNotSame(previous, node);

            appendNodeDescription(node, actual);
            previous = node;
        }

        assertEquals(expected, actual.toString());
    }

    private static void appendNodeDescription(Node node, StringBuilder actual) {
        if (node instanceof Element) {
            Element el = (Element) node;
            actual.append(el.tagName());
            if (el.hasAttr("id"))
                actual.append("#").append(el.id());
        } else if (node instanceof TextNode) {
            actual.append(((TextNode) node).text());
        } else {
            actual.append(node.nodeName());
        }

        actual.append(";");
    }
}
