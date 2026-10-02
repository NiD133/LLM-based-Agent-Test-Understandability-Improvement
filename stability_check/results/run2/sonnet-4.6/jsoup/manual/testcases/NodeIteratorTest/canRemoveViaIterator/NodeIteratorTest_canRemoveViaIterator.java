package org.jsoup.nodes;

import org.jsoup.Jsoup;
import org.junit.jupiter.api.Test;
import java.util.Iterator;
import java.util.NoSuchElementException;
import static org.junit.jupiter.api.Assertions.*;

public class NodeIteratorTest_canRemoveViaIterator {

    String html = "<div id=1><p>One<p>Two</div><div id=2><p>Three<p>Four</div>";

    // Records a node's identity into `actual`: element tag (with optional id), text content, or node name.
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

    // Iterates through `it`, asserts each returned node is non-null and distinct from the previous one,
    // and verifies the concatenated representation matches `expected`.
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

    // Creates a fresh iterator over `el` and asserts the full traversal matches `expected`.
    static void assertContents(Element el, String expected) {
        NodeIterator<Node> it = NodeIterator.from(el);
        assertIterates(it, expected);
    }

    @Test
    void canRemoveViaIterator() {
        // HTML has two top-level divs (out1, out2). out1 contains two inner divs (id=1 and id=2).
        String html = "<div id=out1><div id=1><p>One<p>Two</div><div id=2><p>Three<p>Four</div></div><div id=out2>Out2";
        Document doc = Jsoup.parse(html);

        // --- Phase 1: remove div#1 while iterating ---
        // When a node is removed mid-iteration, the iterator still reports visiting that node
        // but skips its descendants. After the loop, div#1 and its children are gone from the doc.
        NodeIterator<Node> it = NodeIterator.from(doc);
        StringBuilder seen = new StringBuilder();
        while (it.hasNext()) {
            Node node = it.next();
            if (node.attr("id").equals("1"))
                it.remove();
            trackSeen(node, seen);
        }
        // div#1 was visited but its children (p>One, p>Two) were skipped after removal.
        assertEquals("#root;html;head;body;div#out1;div#1;div#2;p;Three;p;Four;div#out2;Out2;", seen.toString());
        // The document no longer contains div#1 or its children.
        assertContents(doc, "#root;html;head;body;div#out1;div#2;p;Three;p;Four;div#out2;Out2;");

        // --- Phase 2: remove div#2 while iterating the modified document ---
        // Repeat the same pattern: div#2 is visited but its children are skipped once removed.
        it = NodeIterator.from(doc);
        seen = new StringBuilder();
        while (it.hasNext()) {
            Node node = it.next();
            if (node.attr("id").equals("2"))
                it.remove();
            trackSeen(node, seen);
        }
        // div#2 was visited but its children (p>Three, p>Four) were skipped after removal.
        assertEquals("#root;html;head;body;div#out1;div#2;div#out2;Out2;", seen.toString());
        // The document now contains neither div#1 nor div#2.
        assertContents(doc, "#root;html;head;body;div#out1;div#out2;Out2;");
    }
}
