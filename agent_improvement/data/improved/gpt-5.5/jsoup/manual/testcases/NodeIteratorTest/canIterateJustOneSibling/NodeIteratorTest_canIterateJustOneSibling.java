package org.jsoup.nodes;

import org.jsoup.Jsoup;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;

public class NodeIteratorTest_canIterateJustOneSibling {
    private static final String HTML_WITH_TWO_SIBLING_DIVS =
        "<div id=1><p>One<p>Two</div><div id=2><p>Three<p>Four</div>";

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
    void canIterateJustOneSibling() {
        Document doc = Jsoup.parse(HTML_WITH_TWO_SIBLING_DIVS);
        Element paragraphContainingTwo = doc.expectFirst("p:contains(Two)");

        assertEquals("Two", paragraphContainingTwo.text());

        NodeIterator<Node> nodeIterator = NodeIterator.from(paragraphContainingTwo);
        assertIterates(nodeIterator, "p;Two;");

        NodeIterator<Element> elementIterator = new NodeIterator<>(paragraphContainingTwo, Element.class);
        Element found = elementIterator.next();

        assertSame(paragraphContainingTwo, found);
        assertFalse(elementIterator.hasNext());
    }
}
