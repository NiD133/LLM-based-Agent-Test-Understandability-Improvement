package org.jsoup.parser;

import org.jsoup.nodes.Element;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests that StreamParser suspends reading precisely at the matched element
 * boundary, leaving the remainder of the input unconsumed until the next
 * consuming operation is called.
 */
public class StreamParserTest_doesNotReadPastParse {

    /**
     * Returns the CharacterReader still attached to the ongoing parse, allowing
     * the test to inspect exactly what portion of the input remains unread.
     */
    private static CharacterReader getReader(StreamParser streamer) {
        return streamer.document().parser().getTreeBuilder().reader;
    }

    /**
     * Creates a StreamParser initialized with two sibling divs, where the second
     * div contains a nested paragraph: {@code <div>One</div><div><p>Two</div>}.
     *
     * <p>The first {@code parse()} call chains during construction; the second
     * resets and re-initializes the same instance, demonstrating that a
     * StreamParser is reusable.</p>
     */
    private static StreamParser createBasicStreamParser() {
        String html = "<div>One</div><div><p>Two</div>";
        StreamParser parser = new StreamParser(Parser.htmlParser()).parse(html, "");
        parser.parse(html, "");
        return parser;
    }

    /**
     * Verifies that after selecting the first {@code <div>}, the parser has read
     * just far enough to expose the next sibling {@code <div>} but has NOT yet
     * consumed the content inside that sibling ({@code <p>Two}).
     *
     * <p>Expected parse state after {@code expectFirst("div")}:
     * <ol>
     *   <li>The first {@code <div>One</div>} is fully parsed and returned.</li>
     *   <li>Its next sibling {@code <div>} is already present in the DOM
     *       (the parser peeked ahead to close the first element).</li>
     *   <li>That sibling {@code <div>} has no children yet — the {@code <p>} has
     *       not been parsed.</li>
     *   <li>The underlying CharacterReader is positioned exactly at
     *       {@code "<p>Two"}, the first unconsumed token.</li>
     * </ol>
     */
    @Test
    void doesNotReadPastParse() throws IOException {
        StreamParser streamer = createBasicStreamParser();

        // Advance the parser only until the first <div> is complete
        Element firstDiv = streamer.expectFirst("div");

        // The parser must have peeked far enough to know the sibling <div> exists,
        // but it should NOT have consumed anything inside that sibling yet
        Element siblingDiv = firstDiv.nextElementSibling();
        assertNotNull(siblingDiv,
            "A sibling <div> should already be visible after the first <div> is parsed");
        assertEquals("div", siblingDiv.tagName(),
            "The sibling element should be a <div>");
        assertEquals(0, siblingDiv.childNodeSize(),
            "The sibling <div> must have no children yet — the parser should not have read past the first <div>");

        // Confirm the reader is paused exactly at the start of the un-parsed sibling content
        assertTrue(getReader(streamer).matches("<p>Two"),
            "The CharacterReader should be positioned at '<p>Two', the first unread token inside the sibling <div>");
    }
}
