package org.jsoup.nodes;

import org.jsoup.Jsoup;
import org.junit.jupiter.api.Test;
import java.util.Iterator;
import java.util.NoSuchElementException;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests that {@link NodeIterator} supports removing a node during iteration via {@link NodeIterator#remove()}.
 * Removing a node causes its entire subtree to be detached, but the iterator continues visiting
 * any children of the removed node that it had already entered before visiting the remaining siblings.
 */
public class NodeIteratorTest_canRemoveViaIterator {

    // HTML used by the helper method assertContents; must be set before calling it.
    String html = "<div id=1><p>One<p>Two</div><div id=2><p>Three<p>Four</div>";

    /**
     * Walks the iterator to completion, appending each node's description to {@code actual},
     * and asserts that no node is null and that no node is returned twice in a row.
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
     * Creates a fresh {@link NodeIterator} rooted at {@code el}, iterates it fully,
     * and asserts the visited sequence matches {@code expected}.
     */
    static void assertContents(Element el, String expected) {
        NodeIterator<Node> it = NodeIterator.from(el);
        assertIterates(it, expected);
    }

    /**
     * Appends a short human-readable token for {@code node} to {@code actual}:
     * elements are represented as "tag" or "tag#id", text nodes as their text,
     * and all other nodes by their node name.
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
     * Verifies the remove-during-iteration contract of {@link NodeIterator}:
     * <ol>
     *   <li>When {@link NodeIterator#remove()} is called on a node, the node and its subtree
     *       are detached from the document.</li>
     *   <li>The iterator still visits any children of the removed node that it entered before
     *       the removal (because the iterator had already descended into that subtree), then
     *       continues with the next sibling of the removed node.</li>
     *   <li>Subsequent {@link #assertContents} calls confirm the document reflects the removal.</li>
     * </ol>
     */
    @Test
    void canRemoveViaIterator() {
        String html = "<div id=out1><div id=1><p>One<p>Two</div><div id=2><p>Three<p>Four</div></div><div id=out2>Out2";
        Document doc = Jsoup.parse(html);

        // --- Phase 1: remove div#1 while iterating ---
        // When we encounter div#1 we call remove(). The iterator had already yielded div#1 and
        // will continue into div#2 (and its children) before finishing. div#1's children (p, One,
        // p, Two) are skipped because remove() detaches the subtree before the iterator descends.
        NodeIterator<Node> it = NodeIterator.from(doc);
        StringBuilder visitedDuringFirstPass = new StringBuilder();
        while (it.hasNext()) {
            Node node = it.next();
            if (node.attr("id").equals("1"))
                it.remove();
            trackSeen(node, visitedDuringFirstPass);
        }
        // div#1 was visited (and removed), its children were not — iterator skipped them.
        assertEquals("#root;html;head;body;div#out1;div#1;div#2;p;Three;p;Four;div#out2;Out2;",
            visitedDuringFirstPass.toString());
        // The document no longer contains div#1 or its children.
        assertContents(doc, "#root;html;head;body;div#out1;div#2;p;Three;p;Four;div#out2;Out2;");

        // --- Phase 2: remove div#2 while iterating the already-modified document ---
        it = NodeIterator.from(doc);
        StringBuilder visitedDuringSecondPass = new StringBuilder();
        while (it.hasNext()) {
            Node node = it.next();
            if (node.attr("id").equals("2"))
                it.remove();
            trackSeen(node, visitedDuringSecondPass);
        }
        // div#2 was visited (and removed); its children (p, Three, p, Four) were skipped.
        assertEquals("#root;html;head;body;div#out1;div#2;div#out2;Out2;",
            visitedDuringSecondPass.toString());
        // The document now contains neither div#1 nor div#2.
        assertContents(doc, "#root;html;head;body;div#out1;div#out2;Out2;");
    }
}
