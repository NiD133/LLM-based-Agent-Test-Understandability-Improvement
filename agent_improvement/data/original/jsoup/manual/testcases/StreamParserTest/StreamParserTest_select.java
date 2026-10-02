package org.jsoup.parser;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.helper.DataUtil;
import org.jsoup.integration.ParseTest;
import org.jsoup.integration.TestServer;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicReference;
import static org.junit.jupiter.api.Assertions.*;

public class StreamParserTest_select {

    static void trackSeen(Element el, StringBuilder actual) {
        actual.append(el.tagName());
        if (el.hasAttr("id"))
            actual.append("#").append(el.id());
        if (!el.ownText().isEmpty())
            actual.append("[").append(el.ownText()).append("]");
        if (el.nextElementSibling() != null)
            actual.append("+");
        actual.append(";");
    }

    static StreamParser basic() {
        String html = "<div>One</div><div><p>Two</div>";
        StreamParser parser = new StreamParser(Parser.htmlParser()).parse(html, "");
        parser.parse(html, "");
        return parser;
    }

    static boolean isClosed(StreamParser streamer) {
        // a bit of a back door in!
        return getReader(streamer) == null;
    }

    private static CharacterReader getReader(StreamParser streamer) {
        return streamer.document().parser().getTreeBuilder().reader;
    }

    @Test
    void select() throws IOException {
        String html = "<title>One</title><p id=1>P One</p><p id=2>P Two</p>";
        StreamParser parser = new StreamParser(Parser.htmlParser()).parse(html, "");
        Element title = parser.expectFirst("title");
        assertEquals("One", title.text());
        Document partialDoc = title.ownerDocument();
        assertNotNull(partialDoc);
        // at this point, we should have one P with no text - as title was emitted on P head
        Elements ps = partialDoc.select("p");
        assertEquals(1, ps.size());
        assertEquals("", ps.get(0).text());
        assertSame(partialDoc, parser.document());
        Element title2 = parser.selectFirst("title");
        assertSame(title2, title);
        Element p1 = parser.expectNext("p");
        assertEquals("P One", p1.text());
        Element p2 = parser.expectNext("p");
        assertEquals("P Two", p2.text());
        Element pNone = parser.selectNext("p");
        assertNull(pNone);
    }
}
