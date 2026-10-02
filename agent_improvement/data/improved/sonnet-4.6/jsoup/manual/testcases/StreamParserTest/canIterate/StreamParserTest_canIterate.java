package org.jsoup.parser;

import org.jsoup.nodes.Element;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StreamParserTest_canIterate {

    /**
     * Appends a concise representation of {@code el} to {@code actual}.
     * <p>Format per element: {@code tagName[#id][ownText][+];}
     * <ul>
     *   <li>{@code #id}   – present when the element has an {@code id} attribute</li>
     *   <li>{@code [text]} – present when the element has non-empty own (direct) text</li>
     *   <li>{@code +}     – present when the element had a next sibling at emission time</li>
     * </ul>
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
    void canIterate() {
        // iterator() is the same interface as stream(), just pull-based.
        // Elements are emitted depth-first (children before parents); "+" means the element
        // had a next sibling at the moment it was emitted by the parser.
        String html = "<title>Test</title></head>"
            + "<div id=1>D1</div>"
            + "<div id=2>D2<p id=3><span>P One</p><p id=4>P Two</p></div>"
            + "<div id=5>D3<p id=6>P three</p>";

        StreamParser parser = new StreamParser(Parser.htmlParser()).parse(html, "");

        StringBuilder seen = new StringBuilder();
        Iterator<Element> it = parser.iterator();
        while (it.hasNext()) {
            trackSeen(it.next(), seen);
        }

        assertEquals(
            "title[Test];head+;div#1[D1]+;span[P One];p#3+;p#4[P Two];div#2[D2]+;p#6[P three];div#5[D3];body;html;#root;",
            seen.toString()
        );
    }
}
