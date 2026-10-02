package org.jsoup.nodes;

import org.jsoup.Jsoup;
import org.junit.jupiter.api.Test;
import java.util.Iterator;
import java.util.NoSuchElementException;
import static org.junit.jupiter.api.Assertions.*;

public class NodeIteratorTest_canWrap {

    // Two divs, each containing two paragraphs; div id=1 will be wrapped during iteration
    private static final String HTML = "<div id=1><p>One<p>Two</div><div id=2><p>Three<p>Four</div>";

    /**
     * Exhausts {@code it}, asserting each returned node is non-null and distinct from
     * the previous one, then asserts that the concatenated node-summaries equal {@code expected}.
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
     * Asserts that a fresh {@link NodeIterator#from} traversal of {@code el} produces
     * the node sequence described by {@code expected}.
     */
    static void assertContents(Element el, String expected) {
        NodeIterator<Node> it = NodeIterator.from(el);
        assertIterates(it, expected);
    }

    /**
     * Appends a short label for {@code node} to {@code actual}:
     * elements use "tagName" or "tagName#id"; text nodes use their text; others use nodeName.
     * A semicolon is appended after each label to act as a separator.
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
     * Verifies that {@link Node#wrap(String)} called on a node mid-iteration does not
     * disrupt the iterator: the iterator must continue into the wrapped element's
     * descendants, and the final document structure must reflect the new wrapper element.
     *
     * <p>Specifically, when div#1 is wrapped inside a new "div#outer" during traversal,
     * the iterator still visits the text node "One" that lives inside div#1, and the
     * completed document contains the expected "div#outer > div#1 > …" hierarchy.
     */
    @Test
    void canWrap() {
        Document doc = Jsoup.parse(HTML);
        NodeIterator<Node> it = NodeIterator.from(doc);

        // Flag set to true once the iterator reaches content inside the wrapped element
        boolean visitedContentInsideWrappedElement = false;

        while (it.hasNext()) {
            Node node = it.next();

            // Wrap div#1 in a new outer div when encountered during iteration
            if (node.attr("id").equals("1")) {
                node.wrap("<div id=outer>");
            }

            // Confirm the iterator still descends into div#1's children after the wrap
            if (node instanceof TextNode && ((TextNode) node).text().equals("One")) {
                visitedContentInsideWrappedElement = true;
            }
        }

        // The document should now show div#outer wrapping div#1 in the correct position
        assertContents(doc, "#root;html;head;body;div#outer;div#1;p;One;p;Two;div#2;p;Three;p;Four;");

        // The iterator must have visited the inner content of the wrapped element
        assertTrue(visitedContentInsideWrappedElement);
    }
}
