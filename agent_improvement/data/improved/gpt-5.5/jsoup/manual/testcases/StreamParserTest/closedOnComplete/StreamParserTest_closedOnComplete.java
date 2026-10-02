package org.jsoup.parser;

import org.jsoup.nodes.Document;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class StreamParserTest_closedOnComplete {
    private static final String HTML = "<div>One</div><div><p>Two</div>";
    private static final String BASE_URI = "";

    private static StreamParser parserWithInput() {
        StreamParser parser = new StreamParser(Parser.htmlParser()).parse(HTML, BASE_URI);
        parser.parse(HTML, BASE_URI);
        return parser;
    }

    private static boolean backingReaderIsClosed(StreamParser parser) {
        return backingReader(parser) == null;
    }

    private static CharacterReader backingReader(StreamParser parser) {
        return parser.document().parser().getTreeBuilder().reader;
    }

    @Test
    void closedOnComplete() throws IOException {
        StreamParser parser = parserWithInput();

        Document doc = parser.complete();

        assertTrue(backingReaderIsClosed(parser));
    }
}
