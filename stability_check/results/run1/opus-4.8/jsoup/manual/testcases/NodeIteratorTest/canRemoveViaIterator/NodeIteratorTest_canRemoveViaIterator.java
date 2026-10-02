package org.jsoup.nodes;

import org.jsoup.Jsoup;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;

/**
 * Verifies that {@link NodeIterator#remove()} deletes the current node (and its subtree) from the
 * document while the traversal continues correctly across the structural change.
 */
public class NodeIteratorTest_canRemoveViaIterator {

    /**
     * Walks every node produced by the iterator, sanity-checks each one, records what was seen via
     * {@link #trackSeen}, and finally asserts that the recorded traversal matches {@code expected}.
     */
    static <T extends Node> void assertIterates(Iterator<T> it, String expected) {
        Node previous = null;
        StringBuilder actual = new StringBuilder();
        while (it.hasNext()) {
            Node node = it.next();
            assertNotNull(node);
            assertNotSame(previous, node, "Iterator must not return the same node twice in a row");
            trackSeen(node, actual);
            previous = node;
        }
        assertEquals(expected, actual.toString());
    }

    /**
     * Asserts that a fresh iteration over {@code el} and its descendants visits exactly {@code expected}.
     */
    static void assertContents(Element el, String expected) {
        NodeIterator<Node> it = NodeIterator.from(el);
        assertIterates(it, expected);
    }

    /**
     * Appends a short, human-readable label for {@code node} to {@code actual}, terminated by ';'.
     * Elements show their tag name (plus "#id" when present), text nodes show their text, and any
     * other node shows its node name.
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

    /**
     * Iterates over the whole document and removes every node whose {@code id} attribute equals
     * {@code idToRemove}, recording each visited node. Returns the traversal that was actually seen
     * (which still includes the removed node, since it is visited before removal takes effect).
     */
    private static String removeNodesWithId(Document doc, String idToRemove) {
        NodeIterator<Node> it = NodeIterator.from(doc);
        StringBuilder seen = new StringBuilder();
        while (it.hasNext()) {
            Node node = it.next();
            if (node.attr("id").equals(idToRemove))
                it.remove();
            trackSeen(node, seen);
        }
        return seen.toString();
    }

    @Test
    void canRemoveViaIterator() {
        String html = "<div id=out1><div id=1><p>One<p>Two</div><div id=2><p>Three<p>Four</div></div><div id=out2>Out2";
        Document doc = Jsoup.parse(html);

        // Remove the "div#1" subtree. It is still reported as seen because removal happens after the
        // node is returned by next(); its descendants (One/Two) are then skipped.
        String seenAfterRemovingId1 = removeNodesWithId(doc, "1");
        assertEquals("#root;html;head;body;div#out1;div#1;div#2;p;Three;p;Four;div#out2;Out2;", seenAfterRemovingId1);
        assertContents(doc, "#root;html;head;body;div#out1;div#2;p;Three;p;Four;div#out2;Out2;");

        // Now remove the "div#2" subtree from the already-modified document.
        String seenAfterRemovingId2 = removeNodesWithId(doc, "2");
        assertEquals("#root;html;head;body;div#out1;div#2;div#out2;Out2;", seenAfterRemovingId2);
        assertContents(doc, "#root;html;head;body;div#out1;div#out2;Out2;");
    }
}
