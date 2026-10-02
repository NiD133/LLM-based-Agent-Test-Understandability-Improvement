package org.jsoup.parser;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StreamParserTest_canRemoveFromDom {

    /**
     * While streaming through the parsed elements, an element can be removed from the DOM.
     * Here the middle {@code <div>DESTROY</div>} is dropped, leaving only the "One" and "Two" divs behind.
     */
    @Test
    void canRemoveFromDom() {
        String html = "<div>One</div><div>DESTROY</div><div>Two</div>";

        StreamParser parser = new StreamParser(Parser.htmlParser()).parse(html, "");
        parser.parse(html, "");

        // Stream over every element, removing the one whose text is "DESTROY".
        parser.stream().forEach(el -> {
            if (el.ownText().equals("DESTROY"))
                el.remove();
        });

        Document doc = parser.document();
        Elements divs = doc.select("div");
        assertEquals(2, divs.size());
        assertEquals("One Two", divs.text());
    }
}
