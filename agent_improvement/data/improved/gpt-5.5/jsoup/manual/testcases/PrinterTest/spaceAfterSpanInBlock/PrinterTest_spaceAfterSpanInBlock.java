package org.jsoup.nodes;

import org.jsoup.Jsoup;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PrinterTest_spaceAfterSpanInBlock {
    private static final String HTML_WITH_SPANS_IN_BLOCKS =
        "<div> <span>Span</span> \n Text  <span>Follow</span></div> " +
        "<p> <span>Span</span>  Text <span>Follow</span> </p>";

    private static final String EXPECTED_BODY_TEXT =
        "Span Text Follow Span Text Follow";

    private static final String EXPECTED_PRETTY_BODY_HTML =
        "<div>\n" +
        " <span>Span</span> Text <span>Follow</span>\n" +
        "</div>\n" +
        "<p><span>Span</span> Text <span>Follow</span></p>";

    @Test
    void spaceAfterSpanInBlock() {
        Document doc = Jsoup.parse(HTML_WITH_SPANS_IN_BLOCKS);
        Element body = doc.body();

        assertEquals(EXPECTED_BODY_TEXT, body.text());
        assertEquals(EXPECTED_PRETTY_BODY_HTML, body.html());
    }
}
