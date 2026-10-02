package org.jsoup.parser;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

public class StreamParserTest_canStopAndCompleteAndReuse {

    @Test
    void canStopAndCompleteAndReuse() throws IOException {
        // --- Phase 1: parse partially, consume first element, then stop ---
        StreamParser parser = new StreamParser(Parser.htmlParser());
        String html = "<p>One<p>Two";
        parser.parse(html, "");

        Element firstParagraph = parser.expectFirst("p");
        assertEquals("One", firstParagraph.text());

        parser.stop(); // halt streaming mid-parse; no further elements will be emitted

        // --- Phase 2: verify iterator is exhausted after stop ---
        Iterator<Element> iterator = parser.iterator();
        assertFalse(iterator.hasNext(), "Iterator should report no elements after stop()");
        assertThrows(NoSuchElementException.class, iterator::next,
            "Calling next() on an exhausted iterator should throw NoSuchElementException");

        // selectNext() also returns null because the parser is stopped
        Element nextParagraph = parser.selectNext("p");
        assertNull(nextParagraph, "selectNext() should return null when parser is stopped");

        // --- Phase 3: complete() finishes the parse despite the earlier stop ---
        Document completedDoc = parser.complete();
        Elements paragraphs = completedDoc.select("p");
        assertEquals(2, paragraphs.size(), "Both <p> elements should be present in the completed document");
        assertEquals("One", paragraphs.get(0).text());
        assertEquals("Two", paragraphs.get(1).text());

        // --- Phase 4: reuse the same parser instance with new input ---
        parser.parse("<div>DIV", "");
        Element div = parser.expectFirst("div");
        assertEquals("DIV", div.text(), "Reused parser should parse new input independently");
    }
}
