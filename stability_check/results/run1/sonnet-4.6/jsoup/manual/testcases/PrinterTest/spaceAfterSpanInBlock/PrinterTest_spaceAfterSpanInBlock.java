package org.jsoup.nodes;

import org.jsoup.Jsoup;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PrinterTest_spaceAfterSpanInBlock {

    // Input HTML with irregular whitespace around inline <span> elements inside block elements
    private static final String INPUT_HTML =
        "<div> <span>Span</span> \n Text  <span>Follow</span></div>" +
        " <p> <span>Span</span>  Text <span>Follow</span> </p>";

    // The pretty-printer should normalize whitespace: a single space between inline elements
    private static final String EXPECTED_HTML =
        "<div>\n <span>Span</span> Text <span>Follow</span>\n</div>\n" +
        "<p><span>Span</span> Text <span>Follow</span></p>";

    // text() collapses all whitespace to single spaces across both block elements
    private static final String EXPECTED_TEXT = "Span Text Follow Span Text Follow";

    @Test
    void spaceAfterSpanInBlock() {
        Document doc = Jsoup.parse(INPUT_HTML);
        Element body = doc.body();

        assertEquals(EXPECTED_TEXT, body.text());
        assertEquals(EXPECTED_HTML, body.html());
    }
}
