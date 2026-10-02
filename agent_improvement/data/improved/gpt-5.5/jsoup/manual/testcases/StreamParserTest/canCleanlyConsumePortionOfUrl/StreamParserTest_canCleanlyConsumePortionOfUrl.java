package org.jsoup.parser;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.integration.TestServer;
import org.jsoup.nodes.Element;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StreamParserTest_canCleanlyConsumePortionOfUrl {

    @Test
    void canCleanlyConsumePortionOfUrl() throws IOException {
        String url = TestServer.origin().file.url("/htmltests/large.html");
        AtomicReference<Float> seenPercent = new AtomicReference<>(0.0f);

        Connection con = Jsoup.connect(url).onResponseProgress((processed, total, percent, response) -> {
            seenPercent.set(percent);
        });
        Connection.Response response = con.execute();

        StreamParser parserAfterClose;
        try (StreamParser parser = response.streamParser()) {
            parserAfterClose = parser;

            Element head = parser.selectFirst("head");
            Element title = head.expectFirst("title");
            assertEquals("Large HTML", title.text());
        }

        assertTrue(isClosed(parserAfterClose));
        assertTrue(seenPercent.get() > 0.0f);
        assertTrue(seenPercent.get() < 100.0f);
    }

    private static boolean isClosed(StreamParser streamer) {
        return getReader(streamer) == null;
    }

    private static CharacterReader getReader(StreamParser streamer) {
        return streamer.document().parser().getTreeBuilder().reader;
    }
}
