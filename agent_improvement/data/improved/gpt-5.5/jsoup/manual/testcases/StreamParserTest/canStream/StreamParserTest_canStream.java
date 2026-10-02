package org.jsoup.parser;

import org.jsoup.nodes.Element;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StreamParserTest_canStream {
    private static final String HTML =
        "<title>Test</title></head>"
            + "<div id=1>D1</div>"
            + "<div id=2>D2<p id=3><span>P One</p><p id=4>P Two</p></div>"
            + "<div id=5>D3<p id=6>P three</p>";

    private static final String EXPECTED_STREAM_TRACE =
        "title[Test];"
            + "head+;"
            + "div#1[D1]+;"
            + "span[P One];"
            + "p#3+;"
            + "p#4[P Two];"
            + "div#2[D2]+;"
            + "p#6[P three];"
            + "div#5[D3];"
            + "body;"
            + "html;"
            + "#root;";

    @Test
    void canStream() {
        try (StreamParser parser = new StreamParser(Parser.htmlParser()).parse(HTML, "")) {
            StringBuilder streamedElements = new StringBuilder();

            parser.stream().forEachOrdered(element -> appendElementTrace(element, streamedElements));

            assertEquals(EXPECTED_STREAM_TRACE, streamedElements.toString());
        }
    }

    private static void appendElementTrace(Element element, StringBuilder trace) {
        trace.append(element.tagName());

        if (element.hasAttr("id")) {
            trace.append("#").append(element.id());
        }

        if (!element.ownText().isEmpty()) {
            trace.append("[").append(element.ownText()).append("]");
        }

        if (element.nextElementSibling() != null) {
            trace.append("+");
        }

        trace.append(";");
    }
}
