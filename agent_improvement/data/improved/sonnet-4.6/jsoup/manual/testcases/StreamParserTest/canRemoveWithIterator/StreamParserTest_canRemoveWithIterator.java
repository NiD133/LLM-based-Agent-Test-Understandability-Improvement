package org.jsoup.parser;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

public class StreamParserTest_canRemoveWithIterator {

    @Test
    void canRemoveWithIterator() {
        String html = "<div>One</div><div>DESTROY</div><div>Two</div>";
        StreamParser parser = new StreamParser(Parser.htmlParser()).parse(html, "");
        parser.parse(html, "");
        Iterator<Element> it = parser.iterator();
        while (it.hasNext()) {
            Element el = it.next();
            if (el.ownText().equals("DESTROY"))
                it.remove(); // remove the matched element via the iterator (delegates to Element.remove())
        }
        Document doc = parser.document();
        Elements divs = doc.select("div");
        assertEquals(2, divs.size());
        assertEquals("One Two", divs.text());
    }
}
