package org.jsoup.parser;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class StreamParserTest_closedOnTryWithResources {

    /** Creates a StreamParser that has been initialized with simple HTML but not yet consumed. */
    static StreamParser basic() {
        String html = "<div>One</div><div><p>Two</div>";
        StreamParser parser = new StreamParser(Parser.htmlParser()).parse(html, "");
        parser.parse(html, "");
        return parser;
    }

    /**
     * Checks whether the StreamParser has been closed by inspecting its internal CharacterReader.
     * When {@link StreamParser#close()} is called, the TreeBuilder releases the reader (sets it to null),
     * so a null reader indicates a closed parser.
     */
    static boolean isClosed(StreamParser streamer) {
        return getReader(streamer) == null;
    }

    /**
     * Accesses the internal CharacterReader held by the parser's TreeBuilder.
     * This uses package-private access as a white-box probe to detect the closed state.
     */
    private static CharacterReader getReader(StreamParser streamer) {
        return streamer.document().parser().getTreeBuilder().reader;
    }

    /**
     * Verifies that StreamParser implements AutoCloseable correctly:
     * the parser must remain open inside the try-with-resources block and be closed automatically
     * upon exiting it.
     */
    @Test
    void closedOnTryWithResources() {
        // Hold a reference outside the try block so we can inspect state after close
        StreamParser copy;
        try (StreamParser streamer = basic()) {
            copy = streamer;
            assertFalse(isClosed(copy), "StreamParser should be open while inside the try-with-resources block");
        }
        assertTrue(isClosed(copy), "StreamParser should be closed after exiting the try-with-resources block");
    }
}
