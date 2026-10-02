package org.jsoup.parser;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;

public class StreamParserTest_select {
    private static final String HTML = "<title>One</title><p id=1>P One</p><p id=2>P Two</p>";
    private static final String TITLE_SELECTOR = "title";
    private static final String PARAGRAPH_SELECTOR = "p";

    @Test
    void select() throws IOException {
        StreamParser parser = new StreamParser(Parser.htmlParser()).parse(HTML, "");

        Element title = parser.expectFirst(TITLE_SELECTOR);
        assertEquals("One", title.text());

        Document partialDocument = title.ownerDocument();
        assertNotNull(partialDocument);

        Elements parsedParagraphs = partialDocument.select(PARAGRAPH_SELECTOR);
        assertEquals(1, parsedParagraphs.size());
        assertEquals("", parsedParagraphs.get(0).text());
        assertSame(partialDocument, parser.document());

        Element sameTitleFromPartialDocument = parser.selectFirst(TITLE_SELECTOR);
        assertSame(sameTitleFromPartialDocument, title);

        Element firstParagraph = parser.expectNext(PARAGRAPH_SELECTOR);
        assertEquals("P One", firstParagraph.text());

        Element secondParagraph = parser.expectNext(PARAGRAPH_SELECTOR);
        assertEquals("P Two", secondParagraph.text());

        Element noRemainingParagraph = parser.selectNext(PARAGRAPH_SELECTOR);
        assertNull(noRemainingParagraph);
    }
}
