package org.jsoup.nodes;

import org.jsoup.Jsoup;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class NodeIteratorTest_canWrap {
    private static final String HTML =
        "<div id=1><p>One<p>Two</div><div id=2><p>Three<p>Four</div>";

    private static final String EXPECTED_CONTENTS_AFTER_WRAP =
        "#root;html;head;body;div#outer;div#1;p;One;p;Two;div#2;p;Three;p;Four;";

    @Test
    void canWrap() {
        Document doc = Jsoup.parse(HTML);
        NodeIterator<Node> it = NodeIterator.from(doc);
        boolean sawInner = false;

        while (it.hasNext()) {
            Node node = it.next();

            if (node.attr("id").equals("1")) {
                node.wrap("<div id=outer>");
            }

            if (isTextNodeWithText(node, "One")) {
                sawInner = true;
            }
        }

        assertContents(doc, EXPECTED_CONTENTS_AFTER_WRAP);
        assertTrue(sawInner);
    }

    private static boolean isTextNodeWithText(Node node, String text) {
        return node instanceof TextNode && ((TextNode) node).text().equals(text);
    }

    private static void assertContents(Element el, String expected) {
        NodeIterator<Node> it = NodeIterator.from(el);
        assertIterates(it, expected);
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

            if (el.hasAttr("id")) {
                actual.append("#").append(el.id());
            }
        } else if (node instanceof TextNode) {
            actual.append(((TextNode) node).text());
        } else {
            actual.append(node.nodeName());
        }

        actual.append(";");
    }
}
