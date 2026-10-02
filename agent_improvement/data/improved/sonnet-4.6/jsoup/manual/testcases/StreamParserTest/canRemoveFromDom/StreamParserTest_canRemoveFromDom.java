package org.jsoup.parser;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class StreamParserTest_canRemoveFromDom {

    @Test
    void canRemoveFromDom() {
        // Elements removed from the DOM during streaming should not appear in the completed document.
        String html = "<div>One</div><div>DESTROY</div><div>Two</div>";
        StreamParser parser = new StreamParser(Parser.htmlParser()).parse(html, "");
        parser.parse(html, "");
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
