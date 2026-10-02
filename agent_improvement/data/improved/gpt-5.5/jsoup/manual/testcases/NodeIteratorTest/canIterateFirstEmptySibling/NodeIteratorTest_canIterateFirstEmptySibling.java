package org.jsoup.nodes;

import org.jsoup.Jsoup;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

public class NodeIteratorTest_canIterateFirstEmptySibling {
    private static final String EMPTY_FIRST_SIBLING_HTML =
            "<div><p id=1></p><p id=2>.</p><p id=3>..</p>";

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
    void canIterateFirstEmptySibling() {
        Document doc = Jsoup.parse(EMPTY_FIRST_SIBLING_HTML);
        Element firstParagraph = doc.expectFirst("p#1");

        assertEquals("", firstParagraph.ownText());

        NodeIterator<Node> iterator = NodeIterator.from(firstParagraph);
        assertTrue(iterator.hasNext());

        Node iteratedNode = iterator.next();
        assertSame(firstParagraph, iteratedNode);
        assertFalse(iterator.hasNext());
    }
}
