package org.jsoup.parser;

import org.jsoup.nodes.Document;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Verifies that once {@link StreamParser#complete()} has consumed the whole input,
 * the StreamParser releases its underlying reader (i.e. it is closed).
 */
public class StreamParserTest_closedOnComplete {

    private static final String SAMPLE_HTML = "<div>One</div><div><p>Two</div>";

    /** Builds a StreamParser primed with {@link #SAMPLE_HTML}, ready to be consumed. */
    private static StreamParser newParserForSampleHtml() {
        StreamParser parser = new StreamParser(Parser.htmlParser()).parse(SAMPLE_HTML, "");
        // Re-supply the same input; parse() resets the parser, mirroring the original test setup.
        parser.parse(SAMPLE_HTML, "");
        return parser;
    }

    /**
     * Reports whether the parser's reader has been released. A {@code null} reader on the
     * tree builder means the input has been fully read and the parser is closed.
     */
    private static boolean isClosed(StreamParser parser) {
        CharacterReader reader = parser.document().parser().getTreeBuilder().reader;
        return reader == null;
    }

    @Test
    void closedOnComplete() throws IOException {
        StreamParser parser = newParserForSampleHtml();

        Document doc = parser.complete();

        assertTrue(isClosed(parser), "parser should be closed once the input is fully read");
    }
}
