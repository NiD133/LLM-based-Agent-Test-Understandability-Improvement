package org.jsoup.parser;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StreamParserTest_canRemoveFromDom {
    private static final String HtmlWithRemovableElement =
        "<div>One</div><div>DESTROY</div><div>Two</div>";
    private static final String EmptyBaseUri = "";
    private static final String TextToRemove = "DESTROY";

    @Test
    void canRemoveFromDom() {
        StreamParser parser = new StreamParser(Parser.htmlParser()).parse(HtmlWithRemovableElement, EmptyBaseUri);
        parser.parse(HtmlWithRemovableElement, EmptyBaseUri);

        parser.stream().forEach(element -> {
            if (element.ownText().equals(TextToRemove)) {
                element.remove();
            }
        });

        Document document = parser.document();
        Elements divs = document.select("div");

        assertEquals(2, divs.size());
        assertEquals("One Two", divs.text());
    }
}
