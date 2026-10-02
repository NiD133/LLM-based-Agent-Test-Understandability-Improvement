package org.jsoup.nodes;

import org.jsoup.Jsoup;
import org.junit.jupiter.api.Test;
import java.util.Iterator;
import static org.junit.jupiter.api.Assertions.*;

public class NodeIteratorTest_canRemoveViaIterator {

    String html = "<div id=1><p>One<p>Two</div><div id=2><p>Three<p>Four</div>";

    /**
     * Walks the iterator to the end, verifying each node is non-null and distinct from the
     * previous one, then checks that the collected tag/text sequence matches {@code expected}.
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

    /** Iterates the full subtree of {@code el} and checks the traversal sequence. */
    static void assertContents(Element el, String expected) {
        NodeIterator<Node> it = NodeIterator.from(el);
        assertIterates(it, expected);
    }

    /**
     * Appends a short token for {@code node} to {@code actual}:
     * elements contribute "tagName" (plus "#id" when present), text nodes contribute their text,
     * and other nodes contribute their node name — each followed by ";".
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
     * Verifies that calling {@link NodeIterator#remove()} while iterating correctly removes the
     * current node (and its subtree) from the document and that subsequent iteration skips the
     * removed subtree.
     *
     * <p>The document structure is:
     * <pre>
     *   div#out1
     *     div#1  ->  p "One",  p "Two"
     *     div#2  ->  p "Three", p "Four"
     *   div#out2  ->  "Out2"
     * </pre>
     */
    @Test
    void canRemoveViaIterator() {
        String html = "<div id=out1><div id=1><p>One<p>Two</div><div id=2><p>Three<p>Four</div></div><div id=out2>Out2";
        Document doc = Jsoup.parse(html);

        // --- Phase 1: remove div#1 during iteration ---
        // When div#1 is removed the iterator must skip its children (p "One", p "Two")
        // but continue from div#2 onwards.
        NodeIterator<Node> it = NodeIterator.from(doc);
        StringBuilder seen = new StringBuilder();
        while (it.hasNext()) {
            Node node = it.next();
            if (node.attr("id").equals("1"))
                it.remove();
            trackSeen(node, seen);
        }
        // div#1 itself was visited, but its two <p> children were not (they were removed with it)
        assertEquals("#root;html;head;body;div#out1;div#1;div#2;p;Three;p;Four;div#out2;Out2;", seen.toString());
        // The document no longer contains div#1 or its descendants
        assertContents(doc, "#root;html;head;body;div#out1;div#2;p;Three;p;Four;div#out2;Out2;");

        // --- Phase 2: remove div#2 from the already-modified document ---
        // Same mechanics: div#2 itself is visited, its children are skipped after removal.
        it = NodeIterator.from(doc);
        seen = new StringBuilder();
        while (it.hasNext()) {
            Node node = it.next();
            if (node.attr("id").equals("2"))
                it.remove();
            trackSeen(node, seen);
        }
        // div#2 itself was visited, but its two <p> children were not
        assertEquals("#root;html;head;body;div#out1;div#2;div#out2;Out2;", seen.toString());
        // The document now contains only div#out1 (empty) and div#out2
        assertContents(doc, "#root;html;head;body;div#out1;div#out2;Out2;");
    }
}
