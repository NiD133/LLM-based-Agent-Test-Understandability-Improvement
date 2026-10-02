package org.jsoup.nodes;

import org.jsoup.Jsoup;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;

public class NodeIteratorTest_canReplace {
    private static final String HTML_WITH_TWO_REPLACE_TARGETS =
        "<div id=out1><div id=1><p>One<p>Two</div><div id=2><p>Three<p>Four</div></div><div id=out2>Out2";

    private static final String SEEN_WHILE_REPLACING_FIRST_DIV =
        "#root;html;head;body;div#out1;div#1;span;Foo;div#2;p;Three;p;Four;div#out2;Out2;";
    private static final String CONTENTS_AFTER_REPLACING_FIRST_DIV =
        "#root;html;head;body;div#out1;span;Foo;div#2;p;Three;p;Four;div#out2;Out2;";

    private static final String SEEN_WHILE_REPLACING_SECOND_DIV =
        "#root;html;head;body;div#out1;span;Foo;div#2;span;Bar;div#out2;Out2;";
    private static final String CONTENTS_AFTER_REPLACING_SECOND_DIV =
        "#root;html;head;body;div#out1;span;Foo;span;Bar;div#out2;Out2;";

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
            if (el.hasAttr("id")) {
                actual.append("#").append(el.id());
            }
        } else if (node instanceof TextNode) {
            actual.append(((TextNode) node).text());
        } else {
            actual.append(node.nodeName());
        }
        actual.append(";");
    }

    @Test
    void canReplace() {
        Document doc = Jsoup.parse(HTML_WITH_TWO_REPLACE_TARGETS);

        String seenWhileReplacingFirstDiv = replaceMatchingNodeDuringIteration(doc, "1", "Foo");
        assertEquals(SEEN_WHILE_REPLACING_FIRST_DIV, seenWhileReplacingFirstDiv);
        assertContents(doc, CONTENTS_AFTER_REPLACING_FIRST_DIV);

        String seenWhileReplacingSecondDiv = replaceMatchingNodeDuringIteration(doc, "2", "Bar");
        assertEquals(SEEN_WHILE_REPLACING_SECOND_DIV, seenWhileReplacingSecondDiv);
        assertContents(doc, CONTENTS_AFTER_REPLACING_SECOND_DIV);
    }

    private static String replaceMatchingNodeDuringIteration(Document doc, String targetId, String replacementText) {
        NodeIterator<Node> it = NodeIterator.from(doc);
        StringBuilder seen = new StringBuilder();

        while (it.hasNext()) {
            Node node = it.next();
            trackSeen(node, seen);
            if (node.attr("id").equals(targetId)) {
                node.replaceWith(new Element("span").text(replacementText));
            }
        }

        return seen.toString();
    }
}
