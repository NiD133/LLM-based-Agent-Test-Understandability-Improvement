package org.jsoup.parser;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StreamParserTest_canRemoveWithIterator {
    private static final String HTML_WITH_REMOVABLE_DIV =
        "<div>One</div><div>DESTROY</div><div>Two</div>";
    private static final String TEXT_TO_REMOVE = "DESTROY";

    @Test
    void canRemoveWithIterator() {
        StreamParser parser = streamParserFor(HTML_WITH_REMOVABLE_DIV);

        removeElementsMatchingOwnText(parser, TEXT_TO_REMOVE);

        Elements remainingDivs = parsedDivs(parser);
        assertEquals(2, remainingDivs.size());
        assertEquals("One Two", remainingDivs.text());
    }

    private static StreamParser streamParserFor(String html) {
        StreamParser parser = new StreamParser(Parser.htmlParser()).parse(html, "");
        parser.parse(html, "");
        return parser;
    }

    private static void removeElementsMatchingOwnText(StreamParser parser, String textToRemove) {
        Iterator<Element> iterator = parser.iterator();
        while (iterator.hasNext()) {
            Element element = iterator.next();
            if (element.ownText().equals(textToRemove)) {
                iterator.remove();
            }
        }
    }

    private static Elements parsedDivs(StreamParser parser) {
        Document document = parser.document();
        return document.select("div");
    }
}
