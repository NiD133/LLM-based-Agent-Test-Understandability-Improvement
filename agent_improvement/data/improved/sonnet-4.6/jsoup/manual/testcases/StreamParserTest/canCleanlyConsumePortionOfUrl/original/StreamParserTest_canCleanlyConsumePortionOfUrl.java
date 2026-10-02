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

public class StreamParserTest_canCleanlyConsumePortionOfUrl {

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
    void canCleanlyConsumePortionOfUrl() throws IOException {
        // test that we can get just the head section of large.html, and only read the minimum required from the URL
        // 280 K
        String url = TestServer.origin().file.url("/htmltests/large.html");
        AtomicReference<Float> seenPercent = new AtomicReference<>(0.0f);
        StreamParser parserRef;
        Connection con = Jsoup.connect(url).onResponseProgress((processed, total, percent, response) -> {
            //System.out.println("Processed: " + processed + " Total: " + total + " Percent: " + percent);
            seenPercent.set(percent);
        });
        Connection.Response response = con.execute();
        try (StreamParser parser = response.streamParser()) {
            parserRef = parser;
            // get the head section
            Element head = parser.selectFirst("head");
            Element title = head.expectFirst("title");
            assertEquals("Large HTML", title.text());
        }
        // now that we've left the try, the stream parser and the response bodystream should be closed
        assertTrue(isClosed(parserRef));
        // test that we didn't read all of the stream
        assertTrue(seenPercent.get() > 0.0f);
        assertTrue(seenPercent.get() < 100.0f);
        // not sure of a good way to assert the bufferedInputReader buf (as held by ConstrainableInputStream in Response.BodyStream) is null. But it is via StreamParser.close.
    }
}
