package org.jsoup.nodes;

import org.jsoup.Jsoup;
import org.junit.jupiter.api.Test;
import java.util.Iterator;
import static org.junit.jupiter.api.Assertions.*;

public class NodeIteratorTest_canRemoveViaNode {

    /**
     * Walks the iterator to completion, appending a short marker for every node it yields, and
     * asserts that the concatenated markers equal {@code expected}. Along the way it checks that
     * the iterator never returns null and never hands back the same instance twice in a row.
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

    /** Asserts that a fresh iteration over {@code el}'s tree visits exactly {@code expected}. */
    static void assertContents(Element el, String expected) {
        NodeIterator<Node> it = NodeIterator.from(el);
        assertIterates(it, expected);
    }

    /**
     * Appends a {@code ;}-terminated marker describing {@code node}:
     * an element as {@code tagName} (plus {@code #id} when it has an id), a text node as its text,
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
    void canRemoveViaNode() {
        String html = "<div id=out1><div id=1><p>One<p>Two</div><div id=2><p>Three<p>Four</div></div><div id=out2>Out2";
        Document doc = Jsoup.parse(html);

        // First pass: remove the element with id=1 the moment the iterator reaches it.
        // The node is still reported by this pass (it was already returned by next()), but the
        // iterator must recover and continue with its former siblings rather than its now-detached children.
        StringBuilder seenFirstPass = new StringBuilder();
        NodeIterator<Node> it = NodeIterator.from(doc);
        while (it.hasNext()) {
            Node node = it.next();
            if (node.attr("id").equals("1"))
                node.remove();
            trackSeen(node, seenFirstPass);
        }
        assertEquals("#root;html;head;body;div#out1;div#1;div#2;p;Three;p;Four;div#out2;Out2;", seenFirstPass.toString());
        // A subsequent fresh iteration no longer sees div#1 (or its children One/Two).
        assertContents(doc, "#root;html;head;body;div#out1;div#2;p;Three;p;Four;div#out2;Out2;");

        // Second pass: now remove the element with id=2 as it is reached.
        StringBuilder seenSecondPass = new StringBuilder();
        it = NodeIterator.from(doc);
        while (it.hasNext()) {
            Node node = it.next();
            if (node.attr("id").equals("2"))
                node.remove();
            trackSeen(node, seenSecondPass);
        }
        assertEquals("#root;html;head;body;div#out1;div#2;div#out2;Out2;", seenSecondPass.toString());
        // div#2 (and its children Three/Four) are gone on the next fresh iteration.
        assertContents(doc, "#root;html;head;body;div#out1;div#out2;Out2;");
    }
}
