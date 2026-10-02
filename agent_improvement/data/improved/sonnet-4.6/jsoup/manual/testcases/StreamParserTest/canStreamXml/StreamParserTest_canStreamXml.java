package org.jsoup.parser;

import org.jsoup.nodes.Element;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StreamParserTest_canStreamXml {

    /**
     * Appends a compact descriptor for {@code el} to {@code seen}.
     * Format per element: {@code tagName[#id][ownText][+];}
     *   - {@code #id}      present when the element has an "id" attribute
     *   - {@code [text]}   present when the element has non-empty own text
     *   - {@code +}        present when the element had a next sibling at emission time
     *   - {@code ;}        always appended as a separator
     */
    static void trackSeen(Element el, StringBuilder seen) {
        seen.append(el.tagName());
        if (el.hasAttr("id"))
            seen.append("#").append(el.id());
        if (!el.ownText().isEmpty())
            seen.append("[").append(el.ownText()).append("]");
        if (el.nextElementSibling() != null)
            seen.append("+");
        seen.append(";");
    }

    @Test
    void canStreamXml() {
        // XML parser is case-sensitive, so tag names are preserved exactly as written (e.g. DIV stays DIV)
        String xml = "<outmost><DIV id=1>D1</DIV><div id=2>D2<p id=3><span>P One</p><p id=4>P Two</p></div><div id=5>D3<p id=6>P three</p>";

        try (StreamParser parser = new StreamParser(Parser.xmlParser()).parse(xml, "")) {
            StringBuilder seen = new StringBuilder();
            // Elements are emitted in document order as each element is closed (children before their parents)
            parser.stream().forEachOrdered(el -> trackSeen(el, seen));

            // Each entry: tagName[#id][ownText][+]; — the + confirms the element had a next sibling at emission time
            String expected = "DIV#1[D1]+;span[P One];p#3+;p#4[P Two];div#2[D2]+;p#6[P three];div#5[D3];outmost;#root;";
            assertEquals(expected, seen.toString());
        }
    }
}
