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

/**
 * Verifies the progressive, on-demand selection API of {@link StreamParser}:
 * {@code expectFirst}, {@code selectFirst}, {@code expectNext} and {@code selectNext}.
 * <p>
 * The parser reads its input lazily, only advancing far enough to satisfy each query,
 * while exposing the partially-built {@link Document} along the way.
 */
public class StreamParserTest_select {

    @Test
    void select() throws IOException {
        String html = "<title>One</title><p id=1>P One</p><p id=2>P Two</p>";
        StreamParser parser = new StreamParser(Parser.htmlParser()).parse(html, "");

        // expectFirst parses only as far as needed to find the <title>.
        Element title = parser.expectFirst("title");
        assertEquals("One", title.text());

        // The element belongs to the still-incomplete document being built.
        Document partialDoc = title.ownerDocument();
        assertNotNull(partialDoc);
        assertSame(partialDoc, parser.document());

        // Because the <title> was emitted when parsing reached the head of the first <p>,
        // exactly one (still text-less) <p> exists in the partial document so far.
        Elements ps = partialDoc.select("p");
        assertEquals(1, ps.size());
        assertEquals("", ps.get(0).text());

        // selectFirst re-runs the query against what is already parsed: same <title> element.
        Element titleAgain = parser.selectFirst("title");
        assertSame(title, titleAgain);

        // expectNext resumes parsing from the current position to the next matching <p>.
        Element firstP = parser.expectNext("p");
        assertEquals("P One", firstP.text());

        Element secondP = parser.expectNext("p");
        assertEquals("P Two", secondP.text());

        // No more <p> elements remain, so selectNext returns null.
        Element noMoreP = parser.selectNext("p");
        assertNull(noMoreP);
    }
}
