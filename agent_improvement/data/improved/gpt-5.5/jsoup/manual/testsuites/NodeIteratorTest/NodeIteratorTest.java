package org.jsoup.nodes;

import org.jsoup.Jsoup;
import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

public class NodeIteratorTest {
    private static final String BASIC_HTML = "<div id=1><p>One<p>Two</div><div id=2><p>Three<p>Four</div>";
    private static final String MUTABLE_HTML =
        "<div id=out1><div id=1><p>One<p>Two</div><div id=2><p>Three<p>Four</div></div><div id=out2>Out2";

    private static final String WHOLE_DOCUMENT = "#root;html;head;body;div#1;p;One;p;Two;div#2;p;Three;p;Four;";
    private static final String FIRST_DIV = "div#1;p;One;p;Two;";
    private static final String SECOND_DIV = "div#2;p;Three;p;Four;";

    private static final String MUTABLE_DOCUMENT = "#root;html;head;body;div#out1;div#1;div#2;p;Three;p;Four;div#out2;Out2;";
    private static final String AFTER_FIRST_DIV_REMOVED = "#root;html;head;body;div#out1;div#2;p;Three;p;Four;div#out2;Out2;";
    private static final String AFTER_SECOND_DIV_SEEN = "#root;html;head;body;div#out1;div#2;div#out2;Out2;";
    private static final String AFTER_BOTH_DIVS_REMOVED = "#root;html;head;body;div#out1;div#out2;Out2;";

    @Test void canIterateNodes() {
        Document doc = parseBasicDocument();
        NodeIterator<Node> it = NodeIterator.from(doc);
        assertIterates(it, WHOLE_DOCUMENT);
        assertFalse(it.hasNext());

        boolean threw = false;
        try {
            it.next();
        } catch (NoSuchElementException e) {
            threw = true;
        }
        assertTrue(threw);
    }

    @Test void hasNextIsPure() {
        Document doc = parseBasicDocument();
        NodeIterator<Node> it = NodeIterator.from(doc);
        assertTrue(it.hasNext());
        assertTrue(it.hasNext());
        assertIterates(it, WHOLE_DOCUMENT);
        assertFalse(it.hasNext());
    }

    @Test void iterateSubTree() {
        Document doc = parseBasicDocument();

        Element div1 = doc.expectFirst("div#1");
        NodeIterator<Node> it = NodeIterator.from(div1);
        assertIterates(it, FIRST_DIV);
        assertFalse(it.hasNext());

        Element div2 = doc.expectFirst("div#2");
        NodeIterator<Node> it2 = NodeIterator.from(div2);
        assertIterates(it2, SECOND_DIV);
        assertFalse(it2.hasNext());
    }

    @Test void canRestart() {
        Document doc = parseBasicDocument();

        NodeIterator<Node> it = NodeIterator.from(doc);
        assertIterates(it, WHOLE_DOCUMENT);

        it.restart(doc.expectFirst("div#2"));
        assertIterates(it, SECOND_DIV);
    }

    @Test void canIterateJustOneSibling() {
        Document doc = parseBasicDocument();
        Element p2 = doc.expectFirst("p:contains(Two)");
        assertEquals("Two", p2.text());

        NodeIterator<Node> it = NodeIterator.from(p2);
        assertIterates(it, "p;Two;");

        NodeIterator<Element> elIt = new NodeIterator<>(p2, Element.class);
        Element found = elIt.next();
        assertSame(p2, found);
        assertFalse(elIt.hasNext());
    }

    @Test void canIterateFirstEmptySibling() {
        Document doc = Jsoup.parse("<div><p id=1></p><p id=2>.</p><p id=3>..</p>");
        Element p1 = doc.expectFirst("p#1");
        assertEquals("", p1.ownText());

        NodeIterator<Node> it = NodeIterator.from(p1);
        assertTrue(it.hasNext());
        Node node = it.next();
        assertSame(p1, node);
        assertFalse(it.hasNext());
    }

    @Test void canRemoveViaIterator() {
        Document doc = parseMutableDocument();

        assertRemovalPassUsingIterator(doc, "1", MUTABLE_DOCUMENT, AFTER_FIRST_DIV_REMOVED);
        assertRemovalPassUsingIterator(doc, "2", AFTER_SECOND_DIV_SEEN, AFTER_BOTH_DIVS_REMOVED);
    }

    @Test void canRemoveViaNode() {
        Document doc = parseMutableDocument();

        assertRemovalPassUsingNode(doc, "1", MUTABLE_DOCUMENT, AFTER_FIRST_DIV_REMOVED);
        assertRemovalPassUsingNode(doc, "2", AFTER_SECOND_DIV_SEEN, AFTER_BOTH_DIVS_REMOVED);
    }

    @Test void canReplace() {
        Document doc = parseMutableDocument();

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
        Document doc = parseBasicDocument();
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
        assertContents(doc, "#root;html;head;body;div#outer;div#1;p;One;p;Two;div#2;p;Three;p;Four;");
        assertTrue(sawInner);
    }

    @Test void canFilterForElements() {
        Document doc = parseBasicDocument();
        NodeIterator<Element> it = new NodeIterator<>(doc, Element.class);

        StringBuilder seen = new StringBuilder();
        while (it.hasNext()) {
            Element el = it.next();
            assertNotNull(el);
            trackSeen(el, seen);
        }

        assertEquals("#root;html;head;body;div#1;p;p;div#2;p;p;", seen.toString());
    }

    @Test void canFilterForTextNodes() {
        Document doc = parseBasicDocument();
        NodeIterator<TextNode> it = new NodeIterator<>(doc, TextNode.class);

        StringBuilder seen = new StringBuilder();
        while (it.hasNext()) {
            TextNode text = it.next();
            assertNotNull(text);
            trackSeen(text, seen);
        }

        assertEquals("One;Two;Three;Four;", seen.toString());
        assertContents(doc, WHOLE_DOCUMENT);
    }

    @Test void canModifyFilteredElements() {
        Document doc = parseBasicDocument();
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

    private static Document parseBasicDocument() {
        return Jsoup.parse(BASIC_HTML);
    }

    private static Document parseMutableDocument() {
        return Jsoup.parse(MUTABLE_HTML);
    }

    private static void assertRemovalPassUsingIterator(
        Document doc, String idToRemove, String expectedSeen, String expectedContents
    ) {
        NodeIterator<Node> it = NodeIterator.from(doc);
        StringBuilder seen = new StringBuilder();
        while (it.hasNext()) {
            Node node = it.next();
            if (node.attr("id").equals(idToRemove))
                it.remove();
            trackSeen(node, seen);
        }
        assertEquals(expectedSeen, seen.toString());
        assertContents(doc, expectedContents);
    }

    private static void assertRemovalPassUsingNode(
        Document doc, String idToRemove, String expectedSeen, String expectedContents
    ) {
        NodeIterator<Node> it = NodeIterator.from(doc);
        StringBuilder seen = new StringBuilder();
        while (it.hasNext()) {
            Node node = it.next();
            if (node.attr("id").equals(idToRemove))
                node.remove();
            trackSeen(node, seen);
        }
        assertEquals(expectedSeen, seen.toString());
        assertContents(doc, expectedContents);
    }

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

    static void assertContents(Element el, String expected) {
        NodeIterator<Node> it = NodeIterator.from(el);
        assertIterates(it, expected);
    }

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
