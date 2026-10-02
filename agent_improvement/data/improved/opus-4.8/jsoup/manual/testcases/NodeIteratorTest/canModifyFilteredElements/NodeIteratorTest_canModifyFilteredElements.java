package org.jsoup.nodes;

import org.jsoup.Jsoup;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;

/**
 * Verifies that a {@link NodeIterator} filtered to a single node type can be used to mutate the
 * elements it visits while the traversal is still in progress, without corrupting the iteration.
 */
public class NodeIteratorTest_canModifyFilteredElements {

    /** Two sibling divs, each containing two paragraphs, giving a small but nested tree to walk. */
    private static final String HTML =
        "<div id=1><p>One<p>Two</div><div id=2><p>Three<p>Four</div>";

    /**
     * Walks the iterator to completion and asserts that the nodes it yields, encoded by
     * {@link #describeNode}, match {@code expectedTrail}. Also checks the iterator never returns
     * null and never returns the same node twice in a row.
     */
    private static <T extends Node> void assertIterates(Iterator<T> it, String expectedTrail) {
        Node previous = null;
        StringBuilder actualTrail = new StringBuilder();
        while (it.hasNext()) {
            Node node = it.next();
            assertNotNull(node);
            assertNotSame(previous, node);
            describeNode(node, actualTrail);
            previous = node;
        }
        assertEquals(expectedTrail, actualTrail.toString());
    }

    /** Iterates every node under {@code el} and asserts the resulting trail matches {@code expectedTrail}. */
    private static void assertContents(Element el, String expectedTrail) {
        NodeIterator<Node> it = NodeIterator.from(el);
        assertIterates(it, expectedTrail);
    }

    /**
     * Appends a short, human-readable description of {@code node} to {@code trail}, followed by ';'.
     * Elements are shown as their tag name (plus "#id" when they carry an id), text nodes as their
     * text, and anything else as its node name.
     */
    private static void describeNode(Node node, StringBuilder trail) {
        if (node instanceof Element) {
            Element el = (Element) node;
            trail.append(el.tagName());
            if (el.hasAttr("id"))
                trail.append("#").append(el.id());
        } else if (node instanceof TextNode) {
            trail.append(((TextNode) node).text());
        } else {
            trail.append(node.nodeName());
        }
        trail.append(";");
    }

    @Test
    void canModifyFilteredElements() {
        Document doc = Jsoup.parse(HTML);

        // Iterate only Elements, appending "++" to the text of every element that has its own text.
        NodeIterator<Element> it = new NodeIterator<>(doc, Element.class);
        StringBuilder visitedElements = new StringBuilder();
        while (it.hasNext()) {
            Element el = it.next();
            if (!el.ownText().isEmpty())
                el.text(el.ownText() + "++");
            describeNode(el, visitedElements);
        }

        // The element-only traversal visits each element exactly once, in document order.
        assertEquals("#root;html;head;body;div#1;p;p;div#2;p;p;", visitedElements.toString());

        // A fresh full traversal confirms the in-place text edits stuck on the underlying document.
        assertContents(doc, "#root;html;head;body;div#1;p;One++;p;Two++;div#2;p;Three++;p;Four++;");
    }
}
