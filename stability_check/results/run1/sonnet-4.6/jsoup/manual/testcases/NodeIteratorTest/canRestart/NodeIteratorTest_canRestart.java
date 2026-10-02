package org.jsoup.nodes;

import org.jsoup.Jsoup;
import org.junit.jupiter.api.Test;
import java.util.Iterator;
import static org.junit.jupiter.api.Assertions.*;

public class NodeIteratorTest_canRestart {

    // Two divs, each with two paragraphs: div#1(p"One", p"Two") and div#2(p"Three", p"Four")
    private final String html = "<div id=1><p>One<p>Two</div><div id=2><p>Three<p>Four</div>";

    /**
     * Walks the iterator to completion, building a semicolon-delimited string of node
     * identifiers, and asserts it matches {@code expected}.
     * Also verifies that each returned node is non-null and distinct from the previous one.
     */
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

    /**
     * Appends a short identifier for {@code node} to {@code sb}:
     * - Elements become "tagName" or "tagName#id" when an id attribute is present
     * - TextNodes become their text content
     * - All other nodes use {@link Node#nodeName()}
     */
    public static void trackSeen(Node node, StringBuilder sb) {
        if (node instanceof Element) {
            Element el = (Element) node;
            sb.append(el.tagName());
            if (el.hasAttr("id"))
                sb.append("#").append(el.id());
        } else if (node instanceof TextNode) {
            sb.append(((TextNode) node).text());
        } else {
            sb.append(node.nodeName());
        }
        sb.append(";");
    }

    @Test
    void canRestart() {
        Document doc = Jsoup.parse(html);

        // Full traversal: starts at the document root and visits every node in order
        NodeIterator<Node> it = NodeIterator.from(doc);
        assertIterates(it, "#root;html;head;body;div#1;p;One;p;Two;div#2;p;Three;p;Four;");

        // Restart mid-document: reusing the same iterator from div#2 should produce only its subtree
        it.restart(doc.expectFirst("div#2"));
        assertIterates(it, "div#2;p;Three;p;Four;");
    }
}
