package org.jsoup.nodes;

import org.jsoup.Jsoup;
import org.junit.jupiter.api.Test;
import java.util.Iterator;
import java.util.NoSuchElementException;
import static org.junit.jupiter.api.Assertions.*;

public class NodeIteratorTest_canFilterForElements {

    // Two divs (id=1, id=2), each containing two <p> elements with text
    String html = "<div id=1><p>One<p>Two</div><div id=2><p>Three<p>Four</div>";

    static <T extends Node> void assertIterates(Iterator<T> it, String expected) {
        Node previous = null;
        StringBuilder actual = new StringBuilder();
        while (it.hasNext()) {
            Node node = it.next();
            assertNotNull(node);
            assertNotSame(previous, node);
            appendNodeLabel(node, actual);
            previous = node;
        }
        assertEquals(expected, actual.toString());
    }

    static void assertContents(Element el, String expected) {
        NodeIterator<Node> it = NodeIterator.from(el);
        assertIterates(it, expected);
    }

    /**
     * Appends a short human-readable label for {@code node} to {@code out}:
     * <ul>
     *   <li>Elements: {@code tagName} or {@code tagName#id} when an id attribute is present</li>
     *   <li>TextNodes: the text content</li>
     *   <li>All other nodes: the node name</li>
     * </ul>
     * A semicolon delimiter is always appended after the label.
     */
    public static void appendNodeLabel(Node node, StringBuilder out) {
        if (node instanceof Element) {
            Element el = (Element) node;
            out.append(el.tagName());
            if (el.hasAttr("id"))
                out.append("#").append(el.id());
        } else if (node instanceof TextNode)
            out.append(((TextNode) node).text());
        else
            out.append(node.nodeName());
        out.append(";");
    }

    @Test
    void canFilterForElements() {
        // NodeIterator<Element> visits only Element nodes, skipping text nodes and other node types.
        Document doc = Jsoup.parse(html);
        NodeIterator<Element> it = new NodeIterator<>(doc, Element.class);

        // Collect a label for each visited element to verify traversal order and type filtering
        StringBuilder visitedElements = new StringBuilder();
        while (it.hasNext()) {
            Element el = it.next();
            assertNotNull(el);
            appendNodeLabel(el, visitedElements);
        }

        // Expected traversal (document order, elements only — no text nodes):
        // #root → html → head → body → div#1 → p → p → div#2 → p → p
        assertEquals("#root;html;head;body;div#1;p;p;div#2;p;p;", visitedElements.toString());
    }
}
