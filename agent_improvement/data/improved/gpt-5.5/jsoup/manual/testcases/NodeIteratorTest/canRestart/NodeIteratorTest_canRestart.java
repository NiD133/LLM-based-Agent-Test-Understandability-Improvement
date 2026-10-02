package org.jsoup.nodes;

import org.jsoup.Jsoup;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;

public class NodeIteratorTest_canRestart {
    private static final String TWO_DIVS_WITH_PARAGRAPHS =
        "<div id=1><p>One<p>Two</div><div id=2><p>Three<p>Four</div>";
    private static final String FULL_DOCUMENT_TRAVERSAL =
        "#root;html;head;body;div#1;p;One;p;Two;div#2;p;Three;p;Four;";
    private static final String SECOND_DIV_TRAVERSAL =
        "div#2;p;Three;p;Four;";

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
        } else if (node instanceof TextNode) {
            actual.append(((TextNode) node).text());
        } else {
            actual.append(node.nodeName());
        }
        actual.append(";");
    }

    @Test
    void canRestart() {
        Document doc = Jsoup.parse(TWO_DIVS_WITH_PARAGRAPHS);
        NodeIterator<Node> it = NodeIterator.from(doc);

        assertIterates(it, FULL_DOCUMENT_TRAVERSAL);

        it.restart(doc.expectFirst("div#2"));
        assertIterates(it, SECOND_DIV_TRAVERSAL);
    }
}
