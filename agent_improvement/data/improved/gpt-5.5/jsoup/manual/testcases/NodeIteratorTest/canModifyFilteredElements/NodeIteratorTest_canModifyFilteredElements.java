package org.jsoup.nodes;

import org.jsoup.Jsoup;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;

public class NodeIteratorTest_canModifyFilteredElements {
    private static final String HTML =
        "<div id=1><p>One<p>Two</div><div id=2><p>Three<p>Four</div>";
    private static final String ELEMENTS_SEEN_IN_DOCUMENT_ORDER =
        "#root;html;head;body;div#1;p;p;div#2;p;p;";
    private static final String CONTENT_AFTER_TEXT_MUTATION =
        "#root;html;head;body;div#1;p;One++;p;Two++;div#2;p;Three++;p;Four++;";
    private static final String TEXT_SUFFIX = "++";

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

    @Test
    void canModifyFilteredElements() {
        Document doc = Jsoup.parse(HTML);
        NodeIterator<Element> it = new NodeIterator<>(doc, Element.class);
        StringBuilder seen = new StringBuilder();

        while (it.hasNext()) {
            Element el = it.next();

            if (!el.ownText().isEmpty()) {
                el.text(el.ownText() + TEXT_SUFFIX);
            }
            trackSeen(el, seen);
        }

        assertEquals(ELEMENTS_SEEN_IN_DOCUMENT_ORDER, seen.toString());
        assertContents(doc, CONTENT_AFTER_TEXT_MUTATION);
    }
}
