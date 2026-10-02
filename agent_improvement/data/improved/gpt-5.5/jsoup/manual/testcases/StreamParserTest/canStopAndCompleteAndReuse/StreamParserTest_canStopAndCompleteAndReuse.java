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
    private static final String BASE_URI = "";
    private static final String TWO_PARAGRAPHS = "<p>One<p>Two";
    private static final String DIV_FRAGMENT = "<div>DIV";

    @Test
    void canStopAndCompleteAndReuse() throws IOException {
        StreamParser parser = new StreamParser(Parser.htmlParser());

        parser.parse(TWO_PARAGRAPHS, BASE_URI);
        Element p = parser.expectFirst("p");
        assertEquals("One", p.text());

        parser.stop();
        Iterator<Element> it = parser.iterator();
        assertFalse(it.hasNext());
        assertThrows(NoSuchElementException.class, it::next);
        Element p2 = parser.selectNext("p");
        assertNull(p2);

        Document completed = parser.complete();
        Elements ps = completed.select("p");
        assertEquals(2, ps.size());
        assertEquals("One", ps.get(0).text());
        assertEquals("Two", ps.get(1).text());

        parser.parse(DIV_FRAGMENT, BASE_URI);
        Element div = parser.expectFirst("div");
        assertEquals("DIV", div.text());
    }
}
