package org.jsoup.nodes;

import org.jsoup.Jsoup;
import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

public class NodeIteratorTest {
    // Two sibling divs, each containing two paragraphs.
    String html = "<div id=1><p>One<p>Two</div><div id=2><p>Three<p>Four</div>";

    // The full document tree of `html`, in iteration (document) order. Reused across several tests.
    private static final String FULL_TREE = "#root;html;head;body;div#1;p;One;p;Two;div#2;p;Three;p;Four;";

    // A deeper tree (two outer divs, one wrapping the standard structure) used by the structural-mutation tests.
    private static final String NESTED_HTML =
        "<div id=out1><div id=1><p>One<p>Two</div><div id=2><p>Three<p>Four</div></div><div id=out2>Out2";

    @Test void canIterateNodes() {
        Document doc = Jsoup.parse(html);
        NodeIterator<Node> it = NodeIterator.from(doc);
        assertIterates(it, FULL_TREE);
        assertFalse(it.hasNext());

        // Calling next() once exhausted must throw.
        assertThrows(NoSuchElementException.class, it::next);
    }

    @Test void hasNextIsPure() {
        Document doc = Jsoup.parse(html);
        NodeIterator<Node> it = NodeIterator.from(doc);
        // Repeated hasNext() calls must not advance the iterator.
        assertTrue(it.hasNext());
        assertTrue(it.hasNext());
        assertIterates(it, FULL_TREE);
        assertFalse(it.hasNext());
    }

    @Test void iterateSubTree() {
        Document doc = Jsoup.parse(html);

        Element div1 = doc.expectFirst("div#1");
        NodeIterator<Node> it = NodeIterator.from(div1);
        assertIterates(it, "div#1;p;One;p;Two;");
        assertFalse(it.hasNext());

        Element div2 = doc.expectFirst("div#2");
        NodeIterator<Node> it2 = NodeIterator.from(div2);
        assertIterates(it2, "div#2;p;Three;p;Four;");
        assertFalse(it2.hasNext());
    }

    @Test void canRestart() {
        Document doc = Jsoup.parse(html);

        NodeIterator<Node> it = NodeIterator.from(doc);
        assertIterates(it, FULL_TREE);

        // Restarting from a new root re-uses the iterator as if freshly constructed.
        it.restart(doc.expectFirst("div#2"));
        assertIterates(it, "div#2;p;Three;p;Four;");
    }

    @Test void canIterateJustOneSibling() {
        Document doc = Jsoup.parse(html);
        Element p2 = doc.expectFirst("p:contains(Two)");
        assertEquals("Two", p2.text());

        NodeIterator<Node> it = NodeIterator.from(p2);
        assertIterates(it, "p;Two;");

        // Filtering for Element yields just the starting <p> itself.
        NodeIterator<Element> elIt = new NodeIterator<>(p2, Element.class);
        Element found = elIt.next();
        assertSame(p2, found);
        assertFalse(elIt.hasNext());
    }

    @Test void canIterateFirstEmptySibling() {
        Document doc = Jsoup.parse("<div><p id=1></p><p id=2>.</p><p id=3>..</p>");
        Element p1 = doc.expectFirst("p#1");
        assertEquals("", p1.ownText());

        // An empty starting node still yields exactly itself.
        NodeIterator<Node> it = NodeIterator.from(p1);
        assertTrue(it.hasNext());
        Node node = it.next();
        assertSame(p1, node);
        assertFalse(it.hasNext());
    }

    @Test void canRemoveViaIterator() {
        Document doc = Jsoup.parse(NESTED_HTML);

        // Remove div#1 via the iterator's remove(); its children are skipped, traversal continues.
        NodeIterator<Node> it = NodeIterator.from(doc);
        String seen = iterateRemovingById(it, "1");
        assertEquals("#root;html;head;body;div#out1;div#1;div#2;p;Three;p;Four;div#out2;Out2;", seen);
        assertContents(doc, "#root;html;head;body;div#out1;div#2;p;Three;p;Four;div#out2;Out2;");

        // Now remove div#2 from the already-modified tree.
        it = NodeIterator.from(doc);
        seen = iterateRemovingById(it, "2");
        assertEquals("#root;html;head;body;div#out1;div#2;div#out2;Out2;", seen);
        assertContents(doc, "#root;html;head;body;div#out1;div#out2;Out2;");
    }

    @Test void canRemoveViaNode() {
        Document doc = Jsoup.parse(NESTED_HTML);

        // Remove div#1 directly on the node (not via the iterator); traversal recovers and continues.
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

        // Now remove div#2 from the already-modified tree.
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

    @Test void canReplace() {
        Document doc = Jsoup.parse(NESTED_HTML);

        // Replace div#1 with <span>Foo</span>: we see the original div#1 (already emitted), then the
        // replacement span and its text, but NOT div#1's original <p>One children.
        NodeIterator<Node> it = NodeIterator.from(doc);
        StringBuilder seen = new StringBuilder();
        while (it.hasNext()) {
            Node node = it.next();
            trackSeen(node, seen);
            if (node.attr("id").equals("1")) {
                node.replaceWith(new Element("span").text("Foo"));
            }
        }
        assertEquals("#root;html;head;body;div#out1;div#1;span;Foo;div#2;p;Three;p;Four;div#out2;Out2;", seen.toString());
        assertContents(doc, "#root;html;head;body;div#out1;span;Foo;div#2;p;Three;p;Four;div#out2;Out2;");

        // Replace div#2 with <span>Bar</span> in the already-modified tree.
        it = NodeIterator.from(doc);
        seen = new StringBuilder();
        while (it.hasNext()) {
            Node node = it.next();
            trackSeen(node, seen);
            if (node.attr("id").equals("2")) {
                node.replaceWith(new Element("span").text("Bar"));
            }
        }
        assertEquals("#root;html;head;body;div#out1;span;Foo;div#2;span;Bar;div#out2;Out2;", seen.toString());
        assertContents(doc, "#root;html;head;body;div#out1;span;Foo;span;Bar;div#out2;Out2;");
    }

    @Test void canWrap() {
        Document doc = Jsoup.parse(html);
        NodeIterator<Node> it = NodeIterator.from(doc);
        boolean sawInner = false;
        while (it.hasNext()) {
            Node node = it.next();
            if (node.attr("id").equals("1")) {
                node.wrap("<div id=outer>");
            }
            if (node instanceof TextNode && ((TextNode) node).text().equals("One"))
                sawInner = true;
        }
        // Wrapping div#1 inserts an enclosing div#outer; iteration still descends into the wrapped content.
        assertContents(doc, "#root;html;head;body;div#outer;div#1;p;One;p;Two;div#2;p;Three;p;Four;");
        assertTrue(sawInner);
    }

    @Test void canFilterForElements() {
        Document doc = Jsoup.parse(html);
        NodeIterator<Element> it = new NodeIterator<>(doc, Element.class);

        StringBuilder seen = new StringBuilder();
        while (it.hasNext()) {
            Element el = it.next();
            assertNotNull(el);
            trackSeen(el, seen);
        }

        // Only Elements are emitted; text nodes are filtered out.
        assertEquals("#root;html;head;body;div#1;p;p;div#2;p;p;", seen.toString());
    }

    @Test void canFilterForTextNodes() {
        Document doc = Jsoup.parse(html);
        NodeIterator<TextNode> it = new NodeIterator<>(doc, TextNode.class);

        StringBuilder seen = new StringBuilder();
        while (it.hasNext()) {
            TextNode text = it.next();
            assertNotNull(text);
            trackSeen(text, seen);
        }

        // Only TextNodes are emitted; the underlying tree is left unchanged.
        assertEquals("One;Two;Three;Four;", seen.toString());
        assertContents(doc, FULL_TREE);
    }

    @Test void canModifyFilteredElements() {
        Document doc = Jsoup.parse(html);
        NodeIterator<Element> it = new NodeIterator<>(doc, Element.class);

        StringBuilder seen = new StringBuilder();
        while (it.hasNext()) {
            Element el = it.next();
            if (!el.ownText().isEmpty())
                el.text(el.ownText() + "++");
            trackSeen(el, seen);
        }

        // Editing emitted elements is reflected in the tree without disrupting iteration.
        assertEquals("#root;html;head;body;div#1;p;p;div#2;p;p;", seen.toString());
        assertContents(doc, "#root;html;head;body;div#1;p;One++;p;Two++;div#2;p;Three++;p;Four++;");
    }

    /**
     Iterates the whole tree, calling {@link NodeIterator#remove()} on any node whose {@code id} attribute
     equals {@code idToRemove}, and returns the {@code ;}-separated record of every node visited.
     */
    private static String iterateRemovingById(NodeIterator<Node> it, String idToRemove) {
        StringBuilder seen = new StringBuilder();
        while (it.hasNext()) {
            Node node = it.next();
            if (node.attr("id").equals(idToRemove))
                it.remove();
            trackSeen(node, seen);
        }
        return seen.toString();
    }

    /** Asserts that the iterator emits exactly the given {@code ;}-separated sequence of nodes, and no node is repeated. */
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

    /** Asserts that a fresh full iteration of {@code el} produces the given expected sequence. */
    static void assertContents(Element el, String expected) {
        NodeIterator<Node> it = NodeIterator.from(el);
        assertIterates(it, expected);
    }

    /** Appends a short, human-readable token for {@code node} (tag#id, text, or node name) followed by {@code ;}. */
    public static void trackSeen(Node node, StringBuilder actual) {
        if (node instanceof Element) {
            Element el = (Element) node;
            actual.append(el.tagName());
            if (el.hasAttr("id"))
                actual.append("#").append(el.id());
        }
        else if (node instanceof TextNode)
            actual.append(((TextNode) node).text());
        else
            actual.append(node.nodeName());
        actual.append(";");
    }

}
