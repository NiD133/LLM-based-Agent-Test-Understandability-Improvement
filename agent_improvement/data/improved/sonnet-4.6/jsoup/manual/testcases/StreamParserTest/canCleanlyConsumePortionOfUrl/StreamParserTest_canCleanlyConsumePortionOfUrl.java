package org.jsoup.parser;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.integration.TestServer;
import org.jsoup.nodes.Element;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicReference;
import static org.junit.jupiter.api.Assertions.*;

public class StreamParserTest_canCleanlyConsumePortionOfUrl {

    private static CharacterReader getReader(StreamParser parser) {
        return parser.document().parser().getTreeBuilder().reader;
    }

    private static boolean isClosed(StreamParser parser) {
        return getReader(parser) == null;
    }

    @Test
    void canCleanlyConsumePortionOfUrl() throws IOException {
        // Verifies that a StreamParser reading from a URL (~280K HTML file) can stop early
        // after finding just the <head> section, and that closing the try-with-resources
        // also closes the underlying network stream before the whole response is consumed.
        String url = TestServer.origin().file.url("/htmltests/large.html");
        AtomicReference<Float> seenPercent = new AtomicReference<>(0.0f);

        Connection con = Jsoup.connect(url)
            .onResponseProgress((processed, total, percent, response) -> seenPercent.set(percent));
        Connection.Response response = con.execute();

        StreamParser parserRef;
        try (StreamParser parser = response.streamParser()) {
            parserRef = parser;
            Element head = parser.selectFirst("head");
            Element title = head.expectFirst("title");
            assertEquals("Large HTML", title.text());
        } // exiting the try block closes the StreamParser and the backing response body stream

        assertTrue(isClosed(parserRef), "StreamParser should be closed after the try-with-resources block exits");
        assertTrue(seenPercent.get() > 0.0f, "At least some of the response must have been read");
        assertTrue(seenPercent.get() < 100.0f, "Should not need to read the entire response just to find <head>");
    }
}
