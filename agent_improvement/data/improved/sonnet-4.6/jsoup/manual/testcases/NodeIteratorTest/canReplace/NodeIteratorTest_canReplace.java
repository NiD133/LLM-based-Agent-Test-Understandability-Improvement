package org.jsoup.nodes;

import org.jsoup.Jsoup;
import org.junit.jupiter.api.Test;
import java.util.Iterator;
import java.util.NoSuchElementException;
import static org.junit.jupiter.api.Assertions.*;

public class NodeIteratorTest_canReplace {

    /**
     * Asserts that the iterator visits nodes in the exact order described by {@code expected}.
     * Each call to {@link #appendNodeLabel} appends a short identifier for each node,
     * so the concatenated result must equal {@code expected}.
     * Also verifies that each node is non-null and different from the previous one.
     */
    static <T extends Node> void assertIterates(Iterator<T> it, String expected) {
        Node lastSeen = null;
        StringBuilder visitLog = new StringBuilder();
        while (it.hasNext()) {
            Node node = it.next();
            assertNotNull(node);
            assertNotSame(lastSeen, node);
            appendNodeLabel(node, visitLog);
            lastSeen = node;
        }
        assertEquals(expected, visitLog.toString());
    }

    /**
     * Iterates the entire subtree rooted at {@code el} and asserts the traversal order
     * matches {@code expected}.
     */
    static void assertContents(Element el, String expected) {
        NodeIterator<Node> it = NodeIterator.from(el);
        assertIterates(it, expected);
    }

    /**
     * Appends a short human-readable label for {@code node} to {@code visitLog}, followed
     * by a semicolon delimiter.
     * Elements are labelled as "tagName" or "tagName#id"; TextNodes as their text content;
     * all other nodes as their node name.
     */
    public static void appendNodeLabel(Node node, StringBuilder visitLog) {
        if (node instanceof Element) {
            Element el = (Element) node;
            visitLog.append(el.tagName());
            if (el.hasAttr("id"))
                visitLog.append("#").append(el.id());
        } else if (node instanceof TextNode)
            visitLog.append(((TextNode) node).text());
        else
            visitLog.append(node.nodeName());
        visitLog.append(";");
    }

    @Test
    void canReplace() {
        String html = "<div id=out1><div id=1><p>One<p>Two</div><div id=2><p>Three<p>Four</div></div><div id=out2>Out2";
        Document doc = Jsoup.parse(html);

        // --- Scenario 1: replace div#1 with <span>Foo</span> during iteration ---
        // After replacement the iterator skips the former children of div#1 (<p>One, <p>Two)
        // but continues into the replacement node (<span>) and all remaining siblings.
        NodeIterator<Node> it = NodeIterator.from(doc);
        StringBuilder visitLog = new StringBuilder();
        while (it.hasNext()) {
            Node node = it.next();
            appendNodeLabel(node, visitLog);
            if (node.attr("id").equals("1")) {
                node.replaceWith(new Element("span").text("Foo"));
            }
        }
        // div#1 is recorded before replacement; its original children are skipped;
        // the replacement <span> and subsequent nodes are visited normally.
        assertEquals("#root;html;head;body;div#out1;div#1;span;Foo;div#2;p;Three;p;Four;div#out2;Out2;", visitLog.toString());
        assertContents(doc, "#root;html;head;body;div#out1;span;Foo;div#2;p;Three;p;Four;div#out2;Out2;");

        // --- Scenario 2: replace div#2 with <span>Bar</span> during a fresh iteration ---
        it = NodeIterator.from(doc);
        visitLog = new StringBuilder();
        while (it.hasNext()) {
            Node node = it.next();
            appendNodeLabel(node, visitLog);
            if (node.attr("id").equals("2")) {
                node.replaceWith(new Element("span").text("Bar"));
            }
        }
        assertEquals("#root;html;head;body;div#out1;span;Foo;div#2;span;Bar;div#out2;Out2;", visitLog.toString());
        assertContents(doc, "#root;html;head;body;div#out1;span;Foo;span;Bar;div#out2;Out2;");
    }
}
