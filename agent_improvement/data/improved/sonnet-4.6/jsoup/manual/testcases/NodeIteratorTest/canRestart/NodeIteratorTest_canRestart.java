package org.jsoup.nodes;

import org.jsoup.Jsoup;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

public class NodeIteratorTest_canRestart {

    // Two sibling divs, each with two paragraphs:  <div id=1><p>One<p>Two</div><div id=2><p>Three<p>Four</div>
    private final String html = "<div id=1><p>One<p>Two</div><div id=2><p>Three<p>Four</div>";

    /**
     * Walks the iterator to exhaustion and asserts that the sequence of visited
     * nodes matches {@code expected}. Each node contributes one semicolon-delimited
     * token (tag[#id] for elements, text content for text nodes).
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

    /** Appends a short token representing {@code node} to {@code actual}. */
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
    @DisplayName("restart() resets the iterator to a new subtree root mid-traversal")
    void canRestart() {
        // Arrange: parse the full document and create an iterator at the document root
        Document doc = Jsoup.parse(html);
        NodeIterator<Node> it = NodeIterator.from(doc);

        // Act + Assert: first pass covers the entire document tree
        String fullDocumentNodes = "#root;html;head;body;div#1;p;One;p;Two;div#2;p;Three;p;Four;";
        assertIterates(it, fullDocumentNodes);

        // Act: restart at div#2 — the iterator now treats that element as the new root
        it.restart(doc.expectFirst("div#2"));

        // Assert: second pass is confined to div#2 and its descendants only
        String div2SubtreeNodes = "div#2;p;Three;p;Four;";
        assertIterates(it, div2SubtreeNodes);
    }
}
