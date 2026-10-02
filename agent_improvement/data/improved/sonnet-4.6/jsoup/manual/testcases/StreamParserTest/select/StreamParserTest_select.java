package org.jsoup.parser;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for {@link StreamParser}'s CSS-selector-based traversal methods:
 * {@code selectFirst}, {@code expectFirst}, {@code selectNext}, and {@code expectNext}.
 */
public class StreamParserTest_select {

    @Test
    void select() throws IOException {
        // HTML with a title and two paragraphs; each element has a distinct id for easy tracking
        String html = "<title>One</title><p id=1>P One</p><p id=2>P Two</p>";
        StreamParser parser = new StreamParser(Parser.htmlParser()).parse(html, "");

        // --- Phase 1: expectFirst suspends the parse right after <title> is complete ---
        // The parser has NOT yet consumed the full document at this point.
        Element title = parser.expectFirst("title");
        assertEquals("One", title.text());

        // The partial document is available; only one <p> exists and it has no text yet
        // because the parser stopped before reading the paragraph's content.
        Document partialDoc = title.ownerDocument();
        assertNotNull(partialDoc);
        Elements ps = partialDoc.select("p");
        assertEquals(1, ps.size());
        assertEquals("", ps.get(0).text());

        // document() returns the same in-progress Document instance
        assertSame(partialDoc, parser.document());

        // --- Phase 2: selectFirst on an already-parsed element returns it from the DOM cache ---
        // The title was already emitted, so selectFirst should return the same object without
        // advancing the parse further.
        Element title2 = parser.selectFirst("title");
        assertSame(title2, title);

        // --- Phase 3: expectNext advances the parse to find subsequent matching elements ---
        Element p1 = parser.expectNext("p");
        assertEquals("P One", p1.text());

        Element p2 = parser.expectNext("p");
        assertEquals("P Two", p2.text());

        // --- Phase 4: selectNext returns null when no more matching elements remain ---
        Element pNone = parser.selectNext("p");
        assertNull(pNone);
    }
}
