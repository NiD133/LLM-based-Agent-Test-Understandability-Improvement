package org.jsoup.nodes;

import org.jsoup.Jsoup;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Verifies that {@link NodeIterator} tolerates structural changes made to the tree mid-iteration.
 * Specifically, calling {@link Node#wrap(String)} on a node while iterating should not break the
 * traversal: every node (including the newly inserted wrapper) is still visited exactly once.
 */
public class NodeIteratorTest_canWrap {

    /** Two sibling divs, each holding two paragraphs. Div #1 will be wrapped during iteration. */
    private final String html = "<div id=1><p>One<p>Two</div><div id=2><p>Three<p>Four</div>";

    @Test
    void canWrap() {
        Document doc = Jsoup.parse(html);

        // Iterate the whole document. When we reach the div with id="1", wrap it in a new
        // <div id=outer>. The iterator must absorb this structural change and keep going.
        NodeIterator<Node> it = NodeIterator.from(doc);
        boolean sawTextNodeOne = false;
        while (it.hasNext()) {
            Node node = it.next();

            if (node.attr("id").equals("1")) {
                node.wrap("<div id=outer>");
            }

            // Confirm we still reach the inner text node "One" after the wrap happened.
            if (node instanceof TextNode && ((TextNode) node).text().equals("One")) {
                sawTextNodeOne = true;
            }
        }

        // A fresh iteration of the (now modified) document must visit every node in document order,
        // including the inserted div#outer surrounding div#1.
        String expectedTraversal =
            "#root;html;head;body;div#outer;div#1;p;One;p;Two;div#2;p;Three;p;Four;";
        assertDocumentIteratesAs(doc, expectedTraversal);

        assertTrue(sawTextNodeOne, "The text node \"One\" should have been visited during iteration");
    }

    /**
     * Iterates {@code element} and all of its descendants, asserting that the visited nodes,
     * rendered as a semicolon-separated string, equal {@code expected}.
     */
    private static void assertDocumentIteratesAs(Element element, String expected) {
        NodeIterator<Node> it = NodeIterator.from(element);
        assertIteratesAs(it, expected);
    }

    /**
     * Walks every node produced by {@code it}, asserting basic iterator contracts along the way
     * (no null nodes, no node returned twice in a row), and checks that the rendered sequence of
     * nodes equals {@code expected}.
     */
    private static <T extends Node> void assertIteratesAs(Iterator<T> it, String expected) {
        Node previous = null;
        StringBuilder visited = new StringBuilder();
        while (it.hasNext()) {
            Node node = it.next();
            assertNotNull(node);
            assertNotSame(previous, node);
            appendNodeLabel(node, visited);
            previous = node;
        }
        assertEquals(expected, visited.toString());
    }

    /**
     * Appends a short, human-readable label for {@code node} to {@code out}, followed by ';'.
     * Elements render as their tag name (with "#id" appended when they carry an id attribute),
     * text nodes render as their text, and any other node renders as its node name.
     */
    private static void appendNodeLabel(Node node, StringBuilder out) {
        if (node instanceof Element) {
            Element el = (Element) node;
            out.append(el.tagName());
            if (el.hasAttr("id")) {
                out.append("#").append(el.id());
            }
        } else if (node instanceof TextNode) {
            out.append(((TextNode) node).text());
        } else {
            out.append(node.nodeName());
        }
        out.append(";");
    }
}
