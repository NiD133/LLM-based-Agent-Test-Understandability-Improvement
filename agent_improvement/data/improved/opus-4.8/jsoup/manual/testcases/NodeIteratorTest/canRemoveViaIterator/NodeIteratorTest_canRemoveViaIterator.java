package org.jsoup.nodes;

import org.jsoup.Jsoup;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;

/**
 * Verifies that {@link NodeIterator#remove()} deletes the current node (and its subtree) from the
 * document while the iterator continues to traverse the remaining nodes correctly.
 */
public class NodeIteratorTest_canRemoveViaIterator {

    /**
     * Walks the whole document with a fresh iterator and confirms it visits exactly the nodes
     * described by {@code expectedTrace}.
     */
    static void assertContents(Element root, String expectedTrace) {
        NodeIterator<Node> it = NodeIterator.from(root);
        assertIterates(it, expectedTrace);
    }

    /**
     * Drains the iterator, asserting each emitted node is non-null and distinct from the one before
     * it, then checks that the concatenated trace of visited nodes matches {@code expectedTrace}.
     */
    static <T extends Node> void assertIterates(Iterator<T> it, String expectedTrace) {
        Node previous = null;
        StringBuilder actualTrace = new StringBuilder();

        while (it.hasNext()) {
            Node node = it.next();
            assertNotNull(node);
            assertNotSame(previous, node);
            appendTrace(node, actualTrace);
            previous = node;
        }

        assertEquals(expectedTrace, actualTrace.toString());
    }

    /**
     * Appends a human-readable token for {@code node} to {@code trace}, followed by a ';' separator:
     * elements are rendered as "tag" (plus "#id" when they carry an id), text nodes as their text,
     * and any other node as its node name.
     */
    static void appendTrace(Node node, StringBuilder trace) {
        if (node instanceof Element) {
            Element el = (Element) node;
            trace.append(el.tagName());
            if (el.hasAttr("id"))
                trace.append("#").append(el.id());
        } else if (node instanceof TextNode) {
            trace.append(((TextNode) node).text());
        } else {
            trace.append(node.nodeName());
        }
        trace.append(";");
    }

    @Test
    void canRemoveViaIterator() {
        String html = "<div id=out1><div id=1><p>One<p>Two</div><div id=2><p>Three<p>Four</div></div><div id=out2>Out2";
        Document doc = Jsoup.parse(html);

        // First pass: remove the element with id=1 (and its subtree) while iterating.
        // The trace still records div#1 because it is visited before remove() is called on it.
        String firstPassTrace = traverseRemovingId(doc, "1");
        assertEquals(
            "#root;html;head;body;div#out1;div#1;div#2;p;Three;p;Four;div#out2;Out2;",
            firstPassTrace);
        // A fresh traversal confirms div#1's subtree is gone from the document.
        assertContents(doc, "#root;html;head;body;div#out1;div#2;p;Three;p;Four;div#out2;Out2;");

        // Second pass: remove the element with id=2 (and its subtree) while iterating.
        String secondPassTrace = traverseRemovingId(doc, "2");
        assertEquals(
            "#root;html;head;body;div#out1;div#2;div#out2;Out2;",
            secondPassTrace);
        // A fresh traversal confirms div#2's subtree is gone too.
        assertContents(doc, "#root;html;head;body;div#out1;div#out2;Out2;");
    }

    /**
     * Iterates the whole document, calling {@link NodeIterator#remove()} on the node whose id equals
     * {@code idToRemove}, and returns the trace of every node visited during the pass.
     */
    private static String traverseRemovingId(Document doc, String idToRemove) {
        NodeIterator<Node> it = NodeIterator.from(doc);
        StringBuilder seen = new StringBuilder();

        while (it.hasNext()) {
            Node node = it.next();
            if (node.attr("id").equals(idToRemove))
                it.remove();
            appendTrace(node, seen);
        }

        return seen.toString();
    }
}
