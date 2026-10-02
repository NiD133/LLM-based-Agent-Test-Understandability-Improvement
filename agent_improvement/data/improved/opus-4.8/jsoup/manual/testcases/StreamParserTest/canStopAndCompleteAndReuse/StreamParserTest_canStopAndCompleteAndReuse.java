package org.jsoup.parser;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class StreamParserTest_canStopAndCompleteAndReuse {

    /**
     * A single StreamParser should support three things in sequence:
     * (1) parsing with an early stop, (2) completing the partial parse afterwards,
     * and (3) being reused for a brand-new input.
     */
    @Test
    void canStopAndCompleteAndReuse() throws IOException {
        StreamParser parser = new StreamParser(Parser.htmlParser());

        // (1) Begin a streaming parse and read just the first <p>, then stop early.
        parser.parse("<p>One<p>Two", "");
        Element firstP = parser.expectFirst("p");
        assertEquals("One", firstP.text());
        parser.stop();

        // After stopping, the iterator yields nothing and selectNext finds no further matches.
        Iterator<Element> it = parser.iterator();
        assertFalse(it.hasNext());
        assertThrows(NoSuchElementException.class, it::next);
        assertNull(parser.selectNext("p"));

        // (2) Completing finishes the parse of the remaining input; both <p>s are now present.
        Document completed = parser.complete();
        Elements paragraphs = completed.select("p");
        assertEquals(2, paragraphs.size());
        assertEquals("One", paragraphs.get(0).text());
        assertEquals("Two", paragraphs.get(1).text());

        // (3) The same parser can be reused for a fresh input.
        parser.parse("<div>DIV", "");
        Element div = parser.expectFirst("div");
        assertEquals("DIV", div.text());
    }
}
