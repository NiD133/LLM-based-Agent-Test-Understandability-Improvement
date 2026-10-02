package org.jsoup.parser;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StreamParserTest_canRemoveWithIterator {

    /**
     * While streaming the parse via the iterator, an element can be dropped from the resulting Document by calling
     * {@link Iterator#remove()}. Here the middle div (text "DESTROY") is removed, leaving only the first and last divs.
     */
    @Test
    void canRemoveWithIterator() {
        String html = "<div>One</div><div>DESTROY</div><div>Two</div>";
        StreamParser parser = new StreamParser(Parser.htmlParser()).parse(html, "");
        parser.parse(html, "");

        // Walk every emitted element and remove the one whose own text is "DESTROY".
        Iterator<Element> it = parser.iterator();
        while (it.hasNext()) {
            Element el = it.next();
            if (el.ownText().equals("DESTROY"))
                it.remove();
        }

        // The removed div should be gone, leaving "One" and "Two".
        Document doc = parser.document();
        Elements divs = doc.select("div");
        assertEquals(2, divs.size());
        assertEquals("One Two", divs.text());
    }
}
