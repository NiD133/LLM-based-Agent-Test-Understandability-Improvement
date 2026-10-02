package org.jsoup.nodes;

import org.jsoup.Jsoup;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;

public class NodeIteratorTest_canRemoveViaIterator {
    private static final String REMOVABLE_NESTED_DIV_HTML =
        "<div id=out1><div id=1><p>One<p>Two</div><div id=2><p>Three<p>Four</div></div><div id=out2>Out2";

    private static final String SEEN_WHEN_FIRST_INNER_DIV_IS_REMOVED =
        "#root;html;head;body;div#out1;div#1;div#2;p;Three;p;Four;div#out2;Out2;";
    private static final String CONTENTS_AFTER_FIRST_INNER_DIV_IS_REMOVED =
        "#root;html;head;body;div#out1;div#2;p;Three;p;Four;div#out2;Out2;";

    private static final String SEEN_WHEN_SECOND_INNER_DIV_IS_REMOVED =
        "#root;html;head;body;div#out1;div#2;div#out2;Out2;";
    private static final String CONTENTS_AFTER_SECOND_INNER_DIV_IS_REMOVED =
        "#root;html;head;body;div#out1;div#out2;Out2;";

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
        } else if (node instanceof TextNode) {
            actual.append(((TextNode) node).text());
        } else {
            actual.append(node.nodeName());
        }
        actual.append(";");
    }

    @Test
    void canRemoveViaIterator() {
        Document doc = Jsoup.parse(REMOVABLE_NESTED_DIV_HTML);

        assertTraversalSeenWhileRemoving(doc, "1", SEEN_WHEN_FIRST_INNER_DIV_IS_REMOVED);
        assertContents(doc, CONTENTS_AFTER_FIRST_INNER_DIV_IS_REMOVED);

        assertTraversalSeenWhileRemoving(doc, "2", SEEN_WHEN_SECOND_INNER_DIV_IS_REMOVED);
        assertContents(doc, CONTENTS_AFTER_SECOND_INNER_DIV_IS_REMOVED);
    }

    private static void assertTraversalSeenWhileRemoving(Document doc, String idToRemove, String expectedSeen) {
        NodeIterator<Node> it = NodeIterator.from(doc);
        StringBuilder seen = new StringBuilder();

        while (it.hasNext()) {
            Node node = it.next();
            if (node.attr("id").equals(idToRemove))
                it.remove();
            trackSeen(node, seen);
        }

        assertEquals(expectedSeen, seen.toString());
    }
}
