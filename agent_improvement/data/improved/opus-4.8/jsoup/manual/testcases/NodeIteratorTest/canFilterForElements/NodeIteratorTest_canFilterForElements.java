package org.jsoup.nodes;

import org.jsoup.Jsoup;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class NodeIteratorTest_canFilterForElements {

    // Two sibling <div>s, each containing two <p> elements; the surrounding html/head/body
    // wrappers are added by the parser.
    private static final String HTML = "<div id=1><p>One<p>Two</div><div id=2><p>Three<p>Four</div>";

    /**
     * Appends a short, readable description of {@code node} to {@code out}, followed by ';'.
     * Elements are rendered as their tag name (plus "#id" when they carry an id attribute),
     * text nodes as their text, and any other node as its node name.
     */
    private static void describeNode(Node node, StringBuilder out) {
        if (node instanceof Element) {
            Element el = (Element) node;
            out.append(el.tagName());
            if (el.hasAttr("id"))
                out.append("#").append(el.id());
        } else if (node instanceof TextNode) {
            out.append(((TextNode) node).text());
        } else {
            out.append(node.nodeName());
        }
        out.append(";");
    }

    @Test
    void canFilterForElements() {
        Document doc = Jsoup.parse(HTML);

        // Filtering for Element.class should yield only the elements, skipping text nodes.
        NodeIterator<Element> it = new NodeIterator<>(doc, Element.class);

        StringBuilder seen = new StringBuilder();
        while (it.hasNext()) {
            Element el = it.next();
            assertNotNull(el);
            describeNode(el, seen);
        }

        assertEquals("#root;html;head;body;div#1;p;p;div#2;p;p;", seen.toString());
    }
}
