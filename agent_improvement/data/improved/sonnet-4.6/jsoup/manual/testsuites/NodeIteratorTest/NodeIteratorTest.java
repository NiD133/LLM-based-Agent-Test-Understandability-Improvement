package org.jsoup.nodes;

import org.jsoup.Jsoup;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

public class NodeIteratorTest {
    // Two top-level divs, each containing two paragraphs
    private static final String HTML = "<div id=1><p>One<p>Two</div><div id=2><p>Three<p>Four</div>";

    @Test
    @DisplayName("NodeIterator.from() visits all descendants in document order and throws when exhausted")
    void canIterateNodes() {
        Document doc = Jsoup.parse(HTML);
        NodeIterator<Node> it = NodeIterator.from(doc);

        assertIterates(it, "#root;html;head;body;div#1;p;One;p;Two;div#2;p;Three;p;Four;");
        assertFalse(it.hasNext());
        assertThrows(NoSuchElementException.class, it::next,
                "calling next() on an exhausted iterator must throw NoSuchElementException");
    }

    @Test
    @DisplayName("hasNext() is idempotent — multiple calls must not advance the iterator")
    void hasNextIsPure() {
        Document doc = Jsoup.parse(HTML);
        NodeIterator<Node> it = NodeIterator.from(doc);

        // Repeated hasNext() calls before any next() call must not change the position
        assertTrue(it.hasNext());
        assertTrue(it.hasNext());

        assertIterates(it, "#root;html;head;body;div#1;p;One;p;Two;div#2;p;Three;p;Four;");
        assertFalse(it.hasNext());
    }

    @Test
    @DisplayName("NodeIterator.from(subElement) iterates only within the subtree of that element")
    void iterateSubTree() {
        Document doc = Jsoup.parse(HTML);

        Element div1 = doc.expectFirst("div#1");
        NodeIterator<Node> it1 = NodeIterator.from(div1);
        assertIterates(it1, "div#1;p;One;p;Two;");
        assertFalse(it1.hasNext());

        Element div2 = doc.expectFirst("div#2");
        NodeIterator<Node> it2 = NodeIterator.from(div2);
        assertIterates(it2, "div#2;p;Three;p;Four;");
        assertFalse(it2.hasNext());
    }

    @Test
    @DisplayName("restart() resets the iterator to a new root without allocating a new object")
    void canRestart() {
        Document doc = Jsoup.parse(HTML);
        NodeIterator<Node> it = NodeIterator.from(doc);

        // Fully consume the document, then restart from div#2 only
        assertIterates(it, "#root;html;head;body;div#1;p;One;p;Two;div#2;p;Three;p;Four;");

        it.restart(doc.expectFirst("div#2"));
        assertIterates(it, "div#2;p;Three;p;Four;");
    }

    @Test
    @DisplayName("Iterator starting at an element with no siblings visits only that element's subtree")
    void canIterateJustOneSibling() {
        Document doc = Jsoup.parse(HTML);
        Element p2 = doc.expectFirst("p:contains(Two)");
        assertEquals("Two", p2.text());

        // Generic Node iterator includes the TextNode child
        NodeIterator<Node> nodeIt = NodeIterator.from(p2);
        assertIterates(nodeIt, "p;Two;");

        // Element-typed iterator sees only the Element itself, not TextNode children
        NodeIterator<Element> elementIt = new NodeIterator<>(p2, Element.class);
        Element found = elementIt.next();
        assertSame(p2, found);
        assertFalse(elementIt.hasNext());
    }

    @Test
    @DisplayName("Iterator over an empty element (no children) yields exactly that element and stops")
    void canIterateFirstEmptySibling() {
        Document doc = Jsoup.parse("<div><p id=1></p><p id=2>.</p><p id=3>..</p>");
        Element p1 = doc.expectFirst("p#1");
        assertEquals("", p1.ownText());

        NodeIterator<Node> it = NodeIterator.from(p1);

        assertTrue(it.hasNext());
        Node first = it.next();
        assertSame(p1, first);
        assertFalse(it.hasNext());
    }

    @Test
    @DisplayName("it.remove() skips the removed subtree while visiting remaining siblings")
    void canRemoveViaIterator() {
        String html = "<div id=out1><div id=1><p>One<p>Two</div><div id=2><p>Three<p>Four</div></div><div id=out2>Out2";
        Document doc = Jsoup.parse(html);

        // Remove div#1: its children are skipped; div#2 subtree is visited normally
        NodeIterator<Node> it = NodeIterator.from(doc);
        StringBuilder seen = new StringBuilder();
        while (it.hasNext()) {
            Node node = it.next();
            if (node.attr("id").equals("1"))
                it.remove();
            trackSeen(node, seen);
        }
        assertEquals("#root;html;head;body;div#out1;div#1;div#2;p;Three;p;Four;div#out2;Out2;", seen.toString());
        assertContents(doc, "#root;html;head;body;div#out1;div#2;p;Three;p;Four;div#out2;Out2;");

        // Remove div#2 from the already-trimmed document
        it = NodeIterator.from(doc);
        seen = new StringBuilder();
        while (it.hasNext()) {
            Node node = it.next();
            if (node.attr("id").equals("2"))
                it.remove();
            trackSeen(node, seen);
        }
        assertEquals("#root;html;head;body;div#out1;div#2;div#out2;Out2;", seen.toString());
        assertContents(doc, "#root;html;head;body;div#out1;div#out2;Out2;");
    }

    @Test
    @DisplayName("node.remove() during traversal behaves identically to it.remove()")
    void canRemoveViaNode() {
        String html = "<div id=out1><div id=1><p>One<p>Two</div><div id=2><p>Three<p>Four</div></div><div id=out2>Out2";
        Document doc = Jsoup.parse(html);

        // Remove div#1 via node.remove(); the iterator should detect the structural change and adapt
        NodeIterator<Node> it = NodeIterator.from(doc);
        StringBuilder seen = new StringBuilder();
        while (it.hasNext()) {
            Node node = it.next();
            if (node.attr("id").equals("1"))
                node.remove();
            trackSeen(node, seen);
        }
        assertEquals("#root;html;head;body;div#out1;div#1;div#2;p;Three;p;Four;div#out2;Out2;", seen.toString());
        assertContents(doc, "#root;html;head;body;div#out1;div#2;p;Three;p;Four;div#out2;Out2;");

        // Remove div#2 from the already-trimmed document
        it = NodeIterator.from(doc);
        seen = new StringBuilder();
        while (it.hasNext()) {
            Node node = it.next();
            if (node.attr("id").equals("2"))
                node.remove();
            trackSeen(node, seen);
        }
        assertEquals("#root;html;head;body;div#out1;div#2;div#out2;Out2;", seen.toString());
        assertContents(doc, "#root;html;head;body;div#out1;div#out2;Out2;");
    }

    @Test
    @DisplayName("replaceWith() skips the replaced subtree and continues with the replacement node")
    void canReplace() {
        String html = "<div id=out1><div id=1><p>One<p>Two</div><div id=2><p>Three<p>Four</div></div><div id=out2>Out2";
        Document doc = Jsoup.parse(html);

        // Replace div#1 with <span>Foo</span>; div#1's children are not visited, but the span is
        NodeIterator<Node> it = NodeIterator.from(doc);
        StringBuilder seen = new StringBuilder();
        while (it.hasNext()) {
            Node node = it.next();
            trackSeen(node, seen);
            if (node.attr("id").equals("1"))
                node.replaceWith(new Element("span").text("Foo"));
        }
        assertEquals("#root;html;head;body;div#out1;div#1;span;Foo;div#2;p;Three;p;Four;div#out2;Out2;", seen.toString());
        assertContents(doc, "#root;html;head;body;div#out1;span;Foo;div#2;p;Three;p;Four;div#out2;Out2;");

        // Replace div#2 with <span>Bar</span>
        it = NodeIterator.from(doc);
        seen = new StringBuilder();
        while (it.hasNext()) {
            Node node = it.next();
            trackSeen(node, seen);
            if (node.attr("id").equals("2"))
                node.replaceWith(new Element("span").text("Bar"));
        }
        assertEquals("#root;html;head;body;div#out1;span;Foo;div#2;span;Bar;div#out2;Out2;", seen.toString());
        assertContents(doc, "#root;html;head;body;div#out1;span;Foo;span;Bar;div#out2;Out2;");
    }

    @Test
    @DisplayName("wrap() inserts an outer container; inner nodes remain reachable during the same traversal")
    void canWrap() {
        Document doc = Jsoup.parse(HTML);
        NodeIterator<Node> it = NodeIterator.from(doc);

        boolean sawInnerText = false;
        while (it.hasNext()) {
            Node node = it.next();
            if (node.attr("id").equals("1"))
                node.wrap("<div id=outer>");
            if (node instanceof TextNode && ((TextNode) node).text().equals("One"))
                sawInnerText = true;
        }

        assertContents(doc, "#root;html;head;body;div#outer;div#1;p;One;p;Two;div#2;p;Three;p;Four;");
        assertTrue(sawInnerText, "TextNode 'One' inside the wrapped div should still have been visited");
    }

    @Test
    @DisplayName("Typed iterator for Element.class yields only Element nodes, skipping all TextNodes")
    void canFilterForElements() {
        Document doc = Jsoup.parse(HTML);
        NodeIterator<Element> it = new NodeIterator<>(doc, Element.class);

        StringBuilder seen = new StringBuilder();
        while (it.hasNext()) {
            Element el = it.next();
            assertNotNull(el);
            trackSeen(el, seen);
        }

        assertEquals("#root;html;head;body;div#1;p;p;div#2;p;p;", seen.toString());
    }

    @Test
    @DisplayName("Typed iterator for TextNode.class yields only text content without disturbing tree structure")
    void canFilterForTextNodes() {
        Document doc = Jsoup.parse(HTML);
        NodeIterator<TextNode> it = new NodeIterator<>(doc, TextNode.class);

        StringBuilder seen = new StringBuilder();
        while (it.hasNext()) {
            TextNode text = it.next();
            assertNotNull(text);
            trackSeen(text, seen);
        }

        assertEquals("One;Two;Three;Four;", seen.toString());
        // Verify the tree structure was not disturbed by the read-only traversal
        assertContents(doc, "#root;html;head;body;div#1;p;One;p;Two;div#2;p;Three;p;Four;");
    }

    @Test
    @DisplayName("Elements can be modified in place via a typed Element iterator during traversal")
    void canModifyFilteredElements() {
        Document doc = Jsoup.parse(HTML);
        NodeIterator<Element> it = new NodeIterator<>(doc, Element.class);

        StringBuilder seen = new StringBuilder();
        while (it.hasNext()) {
            Element el = it.next();
            if (!el.ownText().isEmpty())
                el.text(el.ownText() + "++");
            trackSeen(el, seen);
        }

        assertEquals("#root;html;head;body;div#1;p;p;div#2;p;p;", seen.toString());
        assertContents(doc, "#root;html;head;body;div#1;p;One++;p;Two++;div#2;p;Three++;p;Four++;");
    }

    // --- Helper methods ---

    /**
     * Drains the iterator and verifies the visited nodes (in order) match {@code expected}.
     * Each node contributes one semicolon-terminated token via {@link #trackSeen}.
     * Also asserts each returned node is non-null and distinct from the previous one.
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
     * Asserts that a fresh {@link NodeIterator} rooted at {@code el} produces the sequence {@code expected}.
     */
    static void assertContents(Element el, String expected) {
        NodeIterator<Node> it = NodeIterator.from(el);
        assertIterates(it, expected);
    }

    /**
     * Appends a short descriptor for {@code node} to {@code actual}, followed by {@code ;}.
     * Elements use tagName (plus {@code #id} when present); TextNodes use their text; other nodes use nodeName().
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
}
