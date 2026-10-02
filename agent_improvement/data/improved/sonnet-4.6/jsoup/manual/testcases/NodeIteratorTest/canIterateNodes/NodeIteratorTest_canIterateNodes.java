package org.jsoup.nodes;

import org.jsoup.Jsoup;
import org.junit.jupiter.api.Test;
import java.util.Iterator;
import java.util.NoSuchElementException;
import static org.junit.jupiter.api.Assertions.*;

public class NodeIteratorTest_canIterateNodes {

    String html = "<div id=1><p>One<p>Two</div><div id=2><p>Three<p>Four</div>";

    static <T extends Node> void assertIterates(Iterator<T> it, String expected) {
        Node previous = null;
        StringBuilder actual = new StringBuilder();
        while (it.hasNext()) {
            Node node = it.next();
            assertNotNull(node);
            assertNotSame(previous, node);
            appendNodeRepresentation(node, actual);
            previous = node;
        }
        assertEquals(expected, actual.toString());
    }

    static void assertContents(Element el, String expected) {
        NodeIterator<Node> it = NodeIterator.from(el);
        assertIterates(it, expected);
    }

    /** Appends a short textual representation of the node to {@code sb}: tag#id for elements, text for text nodes. */
    public static void appendNodeRepresentation(Node node, StringBuilder sb) {
        if (node instanceof Element) {
            Element el = (Element) node;
            sb.append(el.tagName());
            if (el.hasAttr("id"))
                sb.append("#").append(el.id());
        } else if (node instanceof TextNode)
            sb.append(((TextNode) node).text());
        else
            sb.append(node.nodeName());
        sb.append(";");
    }

    @Test
    void canIterateNodes() {
        Document doc = Jsoup.parse(html);
        NodeIterator<Node> it = NodeIterator.from(doc);

        // Verify the iterator visits every node in document order
        assertIterates(it, "#root;html;head;body;div#1;p;One;p;Two;div#2;p;Three;p;Four;");

        // After exhaustion, hasNext() returns false and next() throws
        assertFalse(it.hasNext());
        assertThrows(NoSuchElementException.class, it::next);
    }
}
