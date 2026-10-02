package org.jsoup.parser;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Verifies that a {@link StreamParser} releases its underlying reader when used as a
 * try-with-resources resource: it stays open inside the block and is closed on exit.
 */
public class StreamParserTest_closedOnTryWithResources {

    /** Creates a StreamParser with some HTML staged for parsing. */
    static StreamParser basic() {
        String html = "<div>One</div><div><p>Two</div>";
        StreamParser parser = new StreamParser(Parser.htmlParser()).parse(html, "");
        parser.parse(html, "");
        return parser;
    }

    /**
     * Reports whether the parser has released its underlying reader.
     * A closed parser no longer holds a {@link CharacterReader} (a bit of a back door in!).
     */
    static boolean isClosed(StreamParser streamer) {
        CharacterReader reader = streamer.document().parser().getTreeBuilder().reader;
        return reader == null;
    }

    @Test
    void closedOnTryWithResources() {
        StreamParser copy;
        try (StreamParser streamer = basic()) {
            copy = streamer;
            // While inside the try block, the parser is still open.
            assertFalse(isClosed(copy));
        }
        // Leaving the try-with-resources block closes the parser.
        assertTrue(isClosed(copy));
    }
}
