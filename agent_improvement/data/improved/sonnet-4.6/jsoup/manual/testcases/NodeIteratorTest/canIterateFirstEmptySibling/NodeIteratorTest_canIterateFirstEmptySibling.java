package org.jsoup.nodes;

import org.jsoup.Jsoup;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class NodeIteratorTest_canIterateFirstEmptySibling {

    @Test
    void canIterateFirstEmptySibling() {
        // Arrange: a document with three sibling paragraphs where the first is empty
        Document doc = Jsoup.parse("<div><p id=1></p><p id=2>.</p><p id=3>..</p>");
        Element emptyParagraph = doc.expectFirst("p#1");
        assertEquals("", emptyParagraph.ownText());

        // Act: create an iterator starting at the empty paragraph
        NodeIterator<Node> it = NodeIterator.from(emptyParagraph);

        // Assert: the iterator yields only the paragraph itself, then stops
        assertTrue(it.hasNext());
        Node firstNode = it.next();
        assertSame(emptyParagraph, firstNode);
        assertFalse(it.hasNext());
    }
}
