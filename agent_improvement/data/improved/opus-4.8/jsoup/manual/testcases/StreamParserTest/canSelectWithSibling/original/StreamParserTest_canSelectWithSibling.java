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

public class StreamParserTest_canSelectWithSibling {

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
    void canSelectWithSibling() throws IOException {
        StreamParser parser = basic();
        Element el = parser.expectNext("div:first-of-type");
        assertEquals("One", el.text());
        Element el2 = parser.selectNext("div:first-of-type");
        assertNull(el2);
    }
}
