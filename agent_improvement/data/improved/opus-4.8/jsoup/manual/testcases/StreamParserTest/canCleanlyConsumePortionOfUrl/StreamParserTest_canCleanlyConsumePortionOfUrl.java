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

    /**
     * A StreamParser is considered closed once its underlying CharacterReader has been released.
     * We reach into the parser internals (a back door) to confirm the reader is gone.
     */
    private static boolean isClosed(StreamParser parser) {
        CharacterReader reader = parser.document().parser().getTreeBuilder().reader;
        return reader == null;
    }

    @Test
    void canCleanlyConsumePortionOfUrl() throws IOException {
        // large.html is ~280 KB. We only want the <head> section, so the parser should read
        // just enough of the streamed response to find it and then stop, rather than the whole body.
        String url = TestServer.origin().file.url("/htmltests/large.html");

        // Track the most recent download-progress percentage reported while reading the response.
        AtomicReference<Float> seenPercent = new AtomicReference<>(0.0f);
        Connection connection = Jsoup.connect(url)
            .onResponseProgress((processed, total, percent, response) -> seenPercent.set(percent));

        Connection.Response response = connection.execute();

        StreamParser parser;
        try (StreamParser streamParser = response.streamParser()) {
            parser = streamParser;

            // Parsing suspends as soon as <head> (and its <title>) are available.
            Element head = streamParser.selectFirst("head");
            Element title = head.expectFirst("title");
            assertEquals("Large HTML", title.text());
        }

        // Leaving the try-with-resources closes the StreamParser, which also closes the response body stream.
        assertTrue(isClosed(parser), "StreamParser should be closed after the try-with-resources block");

        // Confirm we stopped partway through: some of the stream was read, but not all of it.
        assertTrue(seenPercent.get() > 0.0f, "Expected to have read some of the stream");
        assertTrue(seenPercent.get() < 100.0f, "Expected to stop before reading the whole stream");
    }
}
