package org.jsoup.parser;

import org.jsoup.nodes.Element;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Verifies that a single {@link StreamParser} instance can be reused across several
 * {@link StreamParser#parse(String, String)} calls, and that consuming the stream a
 * second time (without a fresh parse) yields nothing.
 */
public class StreamParserTest_canReuse {

    private static final String NO_BASE_URI = "";

    /**
     * Appends a compact, human-readable signature of the given element to {@code out},
     * so a whole stream of emitted elements can be asserted as one string.
     *
     * Format per element: {@code tag[#id][[ownText]][+];}
     * where {@code #id} is added when the element has an id, {@code [ownText]} when it
     * has direct text, and {@code +} when it has a following element sibling.
     */
    private static void appendSignature(Element el, StringBuilder out) {
        out.append(el.tagName());
        if (el.hasAttr("id"))
            out.append("#").append(el.id());
        if (!el.ownText().isEmpty())
            out.append("[").append(el.ownText()).append("]");
        if (el.nextElementSibling() != null)
            out.append("+");
        out.append(";");
    }

    /** Consumes the parser's current stream and returns the combined element signatures. */
    private static String collectSignatures(StreamParser parser) {
        StringBuilder signatures = new StringBuilder();
        parser.stream().forEach(el -> appendSignature(el, signatures));
        return signatures.toString();
    }

    @Test
    void canReuse() {
        StreamParser parser = new StreamParser(Parser.htmlParser());

        // First parse: the stream emits each element in document order as it is closed.
        parser.parse("<p>One<p>Two", NO_BASE_URI);
        assertEquals("head+;p[One]+;p[Two];body;html;#root;", collectSignatures(parser));

        // Reuse the same parser for a second, different input.
        parser.parse("<div>Three<div>Four</div></div>", NO_BASE_URI);
        assertEquals("head+;div[Four];div[Three];body;html;#root;", collectSignatures(parser));

        // Streaming again without a new parse() should yield nothing.
        assertEquals("", collectSignatures(parser));
    }
}
