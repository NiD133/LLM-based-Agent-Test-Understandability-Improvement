package org.jsoup.parser;

import org.jsoup.nodes.Element;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StreamParserTest_emitsOnlyOnce {
    private static final String EMPTY_BASE_URI = "";
    private static final String EXPECTED_EMITTED_ELEMENTS = "head+;a[Link];body;html;#root;";

    static void trackSeen(Element el, StringBuilder actual) {
        actual.append(el.tagName());

        if (el.hasAttr("id")) {
            actual.append("#").append(el.id());
        }

        if (!el.ownText().isEmpty()) {
            actual.append("[").append(el.ownText()).append("]");
        }

        if (el.nextElementSibling() != null) {
            actual.append("+");
        }

        actual.append(";");
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "<html><body><a>Link</a></body></html>",
        "<html><body><a>Link</a>",
        "<a>Link</a></body></html>",
        "<a>Link</a>",
        "<a>Link",
        "<a>Link</body>"
    })
    void emitsOnlyOnce(String html) {
        try (StreamParser parser = new StreamParser(Parser.htmlParser()).parse(html, EMPTY_BASE_URI)) {
            StringBuilder seen = new StringBuilder();

            parser.stream().forEach(el -> trackSeen(el, seen));

            assertEquals(EXPECTED_EMITTED_ELEMENTS, seen.toString());
        }
    }
}
