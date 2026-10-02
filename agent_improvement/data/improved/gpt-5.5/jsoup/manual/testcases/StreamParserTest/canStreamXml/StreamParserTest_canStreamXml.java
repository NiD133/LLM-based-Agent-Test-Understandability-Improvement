package org.jsoup.parser;

import org.jsoup.nodes.Element;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StreamParserTest_canStreamXml {
    private static final String XML_INPUT =
        "<outmost><DIV id=1>D1</DIV><div id=2>D2<p id=3><span>P One</p><p id=4>P Two</p></div><div id=5>D3<p id=6>P three</p>";

    private static final String EXPECTED_STREAMED_ELEMENTS =
        "DIV#1[D1]+;span[P One];p#3+;p#4[P Two];div#2[D2]+;p#6[P three];div#5[D3];outmost;#root;";

    @Test
    void canStreamXml() {
        try (StreamParser parser = new StreamParser(Parser.xmlParser()).parse(XML_INPUT, "")) {
            StringBuilder seenElements = new StringBuilder();

            parser.stream().forEachOrdered(element -> trackSeen(element, seenElements));

            assertEquals(EXPECTED_STREAMED_ELEMENTS, seenElements.toString());
        }
    }

    private static void trackSeen(Element element, StringBuilder actual) {
        actual.append(element.tagName());

        if (element.hasAttr("id")) {
            actual.append("#").append(element.id());
        }

        if (!element.ownText().isEmpty()) {
            actual.append("[").append(element.ownText()).append("]");
        }

        if (element.nextElementSibling() != null) {
            actual.append("+");
        }

        actual.append(";");
    }
}
