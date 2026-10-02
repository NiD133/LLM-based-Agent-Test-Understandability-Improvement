package org.jsoup.nodes;

import org.jsoup.Jsoup;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;

/**
 * Verifies that {@link NodeIterator} keeps producing correct nodes even when the
 * tree is mutated mid-traversal via {@link Node#replaceWith(Node)}.
 */
public class NodeIteratorTest_canReplace {

    /**
     * Walks every node the iterator yields and records a compact, semicolon-separated
     * signature of each one (see {@link #appendSignature}). Also asserts the iterator's
     * basic contract: it never returns null and never returns the same node twice in a row.
     */
    static <T extends Node> void assertIterates(Iterator<T> it, String expectedSignatures) {
        Node previous = null;
        StringBuilder signatures = new StringBuilder();
        while (it.hasNext()) {
            Node node = it.next();
            assertNotNull(node);
            assertNotSame(previous, node);
            appendSignature(node, signatures);
            previous = node;
        }
        assertEquals(expectedSignatures, signatures.toString());
    }

    /**
     * Iterates the full subtree rooted at {@code el} from scratch and asserts the
     * sequence of node signatures matches {@code expectedSignatures}.
     */
    static void assertContents(Element el, String expectedSignatures) {
        NodeIterator<Node> it = NodeIterator.from(el);
        assertIterates(it, expectedSignatures);
    }

    /**
     * Appends a short, human-readable signature for {@code node} to {@code out},
     * terminated by a ';'. Elements show their tag name (plus "#id" when they have an
     * id), text nodes show their text, and anything else shows its node name.
     */
    public static void appendSignature(Node node, StringBuilder out) {
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
    void canReplace() {
        String html = "<div id=out1><div id=1><p>One<p>Two</div><div id=2><p>Three<p>Four</div></div><div id=out2>Out2";
        Document doc = Jsoup.parse(html);

        // First pass: replace the element with id=1 (a <div> containing <p>One<p>Two)
        // with a fresh <span>Foo</span> while iterating.
        NodeIterator<Node> it = NodeIterator.from(doc);
        StringBuilder seen = new StringBuilder();
        while (it.hasNext()) {
            Node node = it.next();
            appendSignature(node, seen);
            if (node.attr("id").equals("1")) {
                node.replaceWith(new Element("span").text("Foo"));
            }
        }
        // We see div#1 (the node being replaced), then the replacement <span>Foo>, then
        // continue past it. We never see <p>One because div#1 was swapped out before
        // descending into it.
        assertEquals(
            "#root;html;head;body;div#out1;div#1;span;Foo;div#2;p;Three;p;Four;div#out2;Out2;",
            seen.toString());
        // A fresh traversal of the now-mutated tree confirms div#1's old children are gone.
        assertContents(doc,
            "#root;html;head;body;div#out1;span;Foo;div#2;p;Three;p;Four;div#out2;Out2;");

        // Second pass: replace the element with id=2 with a fresh <span>Bar</span>.
        it = NodeIterator.from(doc);
        seen = new StringBuilder();
        while (it.hasNext()) {
            Node node = it.next();
            appendSignature(node, seen);
            if (node.attr("id").equals("2")) {
                node.replaceWith(new Element("span").text("Bar"));
            }
        }
        assertEquals(
            "#root;html;head;body;div#out1;span;Foo;div#2;span;Bar;div#out2;Out2;",
            seen.toString());
        assertContents(doc,
            "#root;html;head;body;div#out1;span;Foo;span;Bar;div#out2;Out2;");
    }
}
