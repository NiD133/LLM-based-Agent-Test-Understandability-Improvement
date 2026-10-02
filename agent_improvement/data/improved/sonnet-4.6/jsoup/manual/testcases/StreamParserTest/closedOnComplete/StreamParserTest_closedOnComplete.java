package org.jsoup.parser;

import org.jsoup.nodes.Document;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests that a {@link StreamParser} automatically closes its underlying reader
 * when parsing is completed via {@link StreamParser#complete()}.
 */
public class StreamParserTest_closedOnComplete {

    /**
     * Creates a StreamParser initialized with a basic HTML snippet.
     * Calls parse() twice to exercise the reset-and-reinitialize path:
     * the second call closes the first reader and starts fresh from the same input.
     */
    static StreamParser basic() {
        String html = "<div>One</div><div><p>Two</div>";
        StreamParser parser = new StreamParser(Parser.htmlParser()).parse(html, "");
        parser.parse(html, "");
        return parser;
    }

    /**
     * Returns true if the StreamParser has released its underlying CharacterReader,
     * indicating that the parser has been closed and its resources freed.
     */
    static boolean isClosed(StreamParser streamer) {
        return getReader(streamer) == null;
    }

    /**
     * Accesses the internal CharacterReader via the document's parser and tree builder.
     * A null value signals that the parser has been closed and the reader released.
     */
    private static CharacterReader getReader(StreamParser streamer) {
        return streamer.document().parser().getTreeBuilder().reader;
    }

    /**
     * Verifies that calling {@link StreamParser#complete()} causes the parser to
     * automatically close its underlying reader, releasing all held resources.
     */
    @Test
    void closedOnComplete() throws IOException {
        StreamParser streamer = basic();
        Document doc = streamer.complete();
        assertTrue(isClosed(streamer));
    }
}
