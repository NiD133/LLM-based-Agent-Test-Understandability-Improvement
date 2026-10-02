package org.jsoup.nodes;

import org.jsoup.Jsoup;
import org.junit.jupiter.api.Test;
import java.util.Iterator;
import java.util.NoSuchElementException;
import static org.junit.jupiter.api.Assertions.*;

public class NodeIteratorTest_iterateSubTree {

    // Two sibling divs, each containing two paragraphs with text nodes.
    // Used to verify that iteration stays within the requested subtree root.
    static final String TEST_HTML =
        "<div id=1><p>One<p>Two</div><div id=2><p>Three<p>Four</div>";

    /**
     * Drains the iterator, building a compact string of visited nodes, then
     * asserts that the result matches {@code expected}. Also verifies that
     * every returned node is non-null and that consecutive calls return
     * distinct node instances.
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
     * Convenience wrapper: creates a {@link NodeIterator} rooted at {@code el}
     * and delegates to {@link #assertIterates}.
     */
    static void assertContents(Element el, String expected) {
        NodeIterator<Node> it = NodeIterator.from(el);
        assertIterates(it, expected);
    }

    /**
     * Appends a short token for {@code node} to {@code actual}:
     * <ul>
     *   <li>Elements:   tagName (plus "#id" when an id attribute is present)</li>
     *   <li>TextNodes:  the text content</li>
     *   <li>Other nodes: the node name</li>
     * </ul>
     * Each token is terminated with a {@code ;} separator.
     */
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

    /**
     * Verifies that a {@link NodeIterator} rooted at a subtree element visits
     * only that subtree's nodes (the root element itself plus all descendants),
     * in document order, and that the iterator is exhausted immediately after.
     *
     * <p>The test exercises two independent subtrees to confirm that starting a
     * fresh iterator on a sibling div does not bleed into the previously
     * iterated div.
     */
    @Test
    void iterateSubTree() {
        Document doc = Jsoup.parse(TEST_HTML);

        // --- first subtree: div#1 with two paragraph children ---
        Element div1 = doc.expectFirst("div#1");
        NodeIterator<Node> div1Iterator = NodeIterator.from(div1);

        // Expected traversal order: root element, then each <p> with its text node
        assertIterates(div1Iterator, "div#1;p;One;p;Two;");
        assertFalse(div1Iterator.hasNext(), "iterator should be exhausted after full traversal");

        // --- second subtree: div#2, independent of the first ---
        Element div2 = doc.expectFirst("div#2");
        NodeIterator<Node> div2Iterator = NodeIterator.from(div2);

        assertIterates(div2Iterator, "div#2;p;Three;p;Four;");
        assertFalse(div2Iterator.hasNext(), "iterator should be exhausted after full traversal");
    }
}
