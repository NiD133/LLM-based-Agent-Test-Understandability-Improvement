package org.jsoup.parser;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StreamParserTest_closedOnTryWithResources {
    private static final String Html = "<div>One</div><div><p>Two</div>";
    private static final String BaseUri = "";

    private static StreamParser parserWithOpenReader() {
        StreamParser parser = new StreamParser(Parser.htmlParser()).parse(Html, BaseUri);
        parser.parse(Html, BaseUri);
        return parser;
    }

    private static boolean readerHasBeenReleased(StreamParser parser) {
        return currentReader(parser) == null;
    }

    private static CharacterReader currentReader(StreamParser parser) {
        return parser.document().parser().getTreeBuilder().reader;
    }

    @Test
    void closedOnTryWithResources() {
        StreamParser parserClosedByTryWithResources;

        try (StreamParser parser = parserWithOpenReader()) {
            parserClosedByTryWithResources = parser;
            assertFalse(readerHasBeenReleased(parserClosedByTryWithResources));
        }

        assertTrue(readerHasBeenReleased(parserClosedByTryWithResources));
    }
}
