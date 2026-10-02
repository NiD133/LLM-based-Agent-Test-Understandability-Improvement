package org.jsoup.nodes;

import org.jsoup.Jsoup;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;

public class NodeIteratorTest_canRemoveViaIterator {
    private static final String HTML_WITH_REMOVABLE_DIVS =
            "<div id=out1><div id=1><p>One<p>Two</div><div id=2><p>Three<p>Four</div></div><div id=out2>Out2";

    private static final String SEEN_WHILE_REMOVING_FIRST_DIV =
            "#root;html;head;body;div#out1;div#1;div#2;p;Three;p;Four;div#out2;Out2;";
    private static final String CONTENTS_AFTER_REMOVING_FIRST_DIV =
            "#root;html;head;body;div#out1;div#2;p;Three;p;Four;div#out2;Out2;";
    private static final String SEEN_WHILE_REMOVING_SECOND_DIV =
            "#root;html;head;body;div#out1;div#2;div#out2;Out2;";
    private static final String CONTENTS_AFTER_REMOVING_SECOND_DIV =
            "#root;html;head;body;div#out1;div#out2;Out2;";

    static <T extends Node> void assertIterates(Iterator<T> iterator, String expected) {
        Node previous = null;
        StringBuilder actual = new StringBuilder();

        while (iterator.hasNext()) {
            Node node = iterator.next();
            assertNotNull(node);
            assertNotSame(previous, node);
            trackSeen(node, actual);
            previous = node;
        }

        assertEquals(expected, actual.toString());
    }

    static void assertContents(Element element, String expected) {
        NodeIterator<Node> iterator = NodeIterator.from(element);
        assertIterates(iterator, expected);
    }

    public static void trackSeen(Node node, StringBuilder actual) {
        if (node instanceof Element) {
            Element element = (Element) node;
            actual.append(element.tagName());
            if (element.hasAttr("id")) {
                actual.append("#").append(element.id());
            }
        } else if (node instanceof TextNode) {
            actual.append(((TextNode) node).text());
        } else {
            actual.append(node.nodeName());
        }
        actual.append(";");
    }

    @Test
    void canRemoveViaIterator() {
        Document document = Jsoup.parse(HTML_WITH_REMOVABLE_DIVS);

        removeNodeWithIdAndAssertTraversal(document, "1", SEEN_WHILE_REMOVING_FIRST_DIV);
        assertContents(document, CONTENTS_AFTER_REMOVING_FIRST_DIV);

        removeNodeWithIdAndAssertTraversal(document, "2", SEEN_WHILE_REMOVING_SECOND_DIV);
        assertContents(document, CONTENTS_AFTER_REMOVING_SECOND_DIV);
    }

    private static void removeNodeWithIdAndAssertTraversal(Document document, String idToRemove, String expectedSeen) {
        NodeIterator<Node> iterator = NodeIterator.from(document);
        StringBuilder seen = new StringBuilder();

        while (iterator.hasNext()) {
            Node node = iterator.next();
            if (node.attr("id").equals(idToRemove)) {
                iterator.remove();
            }
            trackSeen(node, seen);
        }

        assertEquals(expectedSeen, seen.toString());
    }
}
