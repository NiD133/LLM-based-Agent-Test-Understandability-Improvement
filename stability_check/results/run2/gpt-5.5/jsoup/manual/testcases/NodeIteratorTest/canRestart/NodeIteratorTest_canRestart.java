package org.jsoup.nodes;

import org.jsoup.Jsoup;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;

public class NodeIteratorTest_canRestart {
    private static final String TWO_DIVS_HTML =
        "<div id=1><p>One<p>Two</div><div id=2><p>Three<p>Four</div>";
    private static final String WHOLE_DOCUMENT_TRAVERSAL =
        "#root;html;head;body;div#1;p;One;p;Two;div#2;p;Three;p;Four;";
    private static final String SECOND_DIV_TRAVERSAL =
        "div#2;p;Three;p;Four;";

    @Test
    void canRestart() {
        Document doc = Jsoup.parse(TWO_DIVS_HTML);
        NodeIterator<Node> it = NodeIterator.from(doc);

        assertIterates(it, WHOLE_DOCUMENT_TRAVERSAL);
        it.restart(doc.expectFirst("div#2"));
        assertIterates(it, SECOND_DIV_TRAVERSAL);
    }

    private static <T extends Node> void assertIterates(Iterator<T> iterator, String expectedTraversal) {
        Node previousNode = null;
        StringBuilder actualTraversal = new StringBuilder();

        while (iterator.hasNext()) {
            Node node = iterator.next();

            assertNotNull(node);
            assertNotSame(previousNode, node);
            appendTraversalLabel(node, actualTraversal);
            previousNode = node;
        }

        assertEquals(expectedTraversal, actualTraversal.toString());
    }

    private static void appendTraversalLabel(Node node, StringBuilder traversal) {
        if (node instanceof Element) {
            Element element = (Element) node;
            traversal.append(element.tagName());
            if (element.hasAttr("id")) {
                traversal.append("#").append(element.id());
            }
        } else if (node instanceof TextNode) {
            traversal.append(((TextNode) node).text());
        } else {
            traversal.append(node.nodeName());
        }

        traversal.append(";");
    }
}
