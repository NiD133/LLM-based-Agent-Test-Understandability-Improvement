package org.jsoup.nodes;

import org.jsoup.Jsoup;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

public class NodeIteratorTest_canRemoveViaIterator {

    /**
     * Walks every node produced by the iterator, appends a marker for each one to {@code actual}, and finally
     * checks that the whole traversal matches {@code expected}. Along the way it verifies that each node is
     * non-null and distinct from the previously emitted node.
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

    /** Iterates the subtree rooted at {@code el} and asserts the traversal matches {@code expected}. */
    static void assertContents(Element el, String expected) {
        NodeIterator<Node> it = NodeIterator.from(el);
        assertIterates(it, expected);
    }

    /**
     * Appends a human-readable marker for {@code node} to {@code actual}, followed by ";".
     * Elements are shown as their tag name (plus "#id" when they carry an id), text nodes as their text,
     * and any other node as its node name.
     */
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
    void canRemoveViaIterator() {
        String html = "<div id=out1><div id=1><p>One<p>Two</div><div id=2><p>Three<p>Four</div></div><div id=out2>Out2";
        Document doc = Jsoup.parse(html);

        // First pass: remove the element with id=1 while iterating.
        // It is still reported as "seen" for the step in which it is removed, but its descendants (the two
        // <p> paragraphs) are skipped because the subtree is detached mid-traversal.
        NodeIterator<Node> it = NodeIterator.from(doc);
        StringBuilder seen = new StringBuilder();
        while (it.hasNext()) {
            Node node = it.next();
            if (node.attr("id").equals("1"))
                it.remove();
            trackSeen(node, seen);
        }
        assertEquals("#root;html;head;body;div#out1;div#1;div#2;p;Three;p;Four;div#out2;Out2;", seen.toString());
        // Re-iterating the document confirms div#1 (and its children) are gone.
        assertContents(doc, "#root;html;head;body;div#out1;div#2;p;Three;p;Four;div#out2;Out2;");

        // Second pass: remove the element with id=2, which drops the remaining paragraphs too.
        it = NodeIterator.from(doc);
        seen = new StringBuilder();
        while (it.hasNext()) {
            Node node = it.next();
            if (node.attr("id").equals("2"))
                it.remove();
            trackSeen(node, seen);
        }
        assertEquals("#root;html;head;body;div#out1;div#2;div#out2;Out2;", seen.toString());
        assertContents(doc, "#root;html;head;body;div#out1;div#out2;Out2;");
    }
}
