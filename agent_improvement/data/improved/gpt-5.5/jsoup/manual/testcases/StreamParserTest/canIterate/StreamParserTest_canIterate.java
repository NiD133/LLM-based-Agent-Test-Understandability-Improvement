package org.jsoup.parser;

import org.jsoup.nodes.Element;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StreamParserTest_canIterate {
    private static final String HTML =
        "<title>Test</title></head><div id=1>D1</div><div id=2>D2<p id=3><span>P One</p><p id=4>P Two</p></div><div id=5>D3<p id=6>P three</p>";

    private static final String EXPECTED_EMISSION_ORDER =
        "title[Test];head+;div#1[D1]+;span[P One];p#3+;p#4[P Two];div#2[D2]+;p#6[P three];div#5[D3];body;html;#root;";

    @Test
    void canIterate() {
        StreamParser parser = new StreamParser(Parser.htmlParser()).parse(HTML, "");
        Iterator<Element> iterator = parser.iterator();

        StringBuilder seenElements = new StringBuilder();
        while (iterator.hasNext()) {
            recordEmittedElement(iterator.next(), seenElements);
        }

        assertEquals(EXPECTED_EMISSION_ORDER, seenElements.toString());
    }

    private static void recordEmittedElement(Element element, StringBuilder actual) {
        actual.append(element.tagName());
        if (element.hasAttr("id"))
            actual.append("#").append(element.id());
        if (!element.ownText().isEmpty())
            actual.append("[").append(element.ownText()).append("]");
        if (element.nextElementSibling() != null)
            actual.append("+");
        actual.append(";");
    }
}
