package org.jsoup.parser;

import org.jsoup.nodes.Element;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class StreamParserTest_canReuse {

    /**
     * Appends a compact representation of {@code el} to {@code actual}:
     * {@code tagName[#id][ownText][+];} where {@code +} means the element has a next sibling.
     */
    static void trackSeen(Element el, StringBuilder actual) {
        actual.append(el.tagName());
        if (el.hasAttr("id"))
            actual.append("#").append(el.id());
        if (!el.ownText().isEmpty())
            actual.append("[").append(el.ownText()).append("]");
        if (el.nextElementSibling() != null)
            actual.append("+");
        actual.append(";");
    }

    @Test
    void canReuse() {
        StreamParser parser = new StreamParser(Parser.htmlParser());

        // --- First parse: two sibling paragraphs ---
        String html1 = "<p>One<p>Two";
        parser.parse(html1, "");
        StringBuilder firstParseResult = new StringBuilder();
        parser.stream().forEach(el -> trackSeen(el, firstParseResult));
        // Elements are emitted depth-first (children before parents).
        // "+" marks elements that have a next sibling at emit time.
        assertEquals("head+;p[One]+;p[Two];body;html;#root;", firstParseResult.toString());

        // --- Second parse (reuse the same parser): nested divs ---
        String html2 = "<div>Three<div>Four</div></div>";
        parser.parse(html2, "");
        StringBuilder secondParseResult = new StringBuilder();
        parser.stream().forEach(el -> trackSeen(el, secondParseResult));
        // Inner div[Four] is emitted before the outer div[Three] (depth-first order).
        assertEquals("head+;div[Four];div[Three];body;html;#root;", secondParseResult.toString());

        // --- Re-running stream() without a new parse: input is already exhausted ---
        StringBuilder afterExhaustedResult = new StringBuilder();
        parser.stream().forEach(el -> trackSeen(el, afterExhaustedResult));
        assertEquals("", afterExhaustedResult.toString());
    }
}
