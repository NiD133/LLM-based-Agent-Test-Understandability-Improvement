package org.jsoup.nodes;

import org.jsoup.Jsoup;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PrinterTest_spaceAfterSpanInBlock {

    // HTML with extra whitespace around spans inside block elements (div and p),
    // including leading/trailing spaces and newlines mixed with inline text
    private static final String HTML_WITH_SPANS_IN_BLOCKS =
        "<div> <span>Span</span> \n Text  <span>Follow</span></div>"
        + " <p> <span>Span</span>  Text <span>Follow</span> </p>";

    // Plain text: whitespace collapsed, each word separated by a single space
    private static final String EXPECTED_PLAIN_TEXT = "Span Text Follow Span Text Follow";

    // Pretty-printed HTML: leading/trailing blank text nodes trimmed, single space preserved
    // between adjacent inline elements (span + text + span) within each block
    private static final String EXPECTED_HTML =
        "<div>\n"
        + " <span>Span</span> Text <span>Follow</span>\n"
        + "</div>\n"
        + "<p><span>Span</span> Text <span>Follow</span></p>";

    @Test
    void spaceAfterSpanInBlock() {
        Document doc = Jsoup.parse(HTML_WITH_SPANS_IN_BLOCKS);
        Element body = doc.body();

        assertEquals(EXPECTED_PLAIN_TEXT, body.text());
        assertEquals(EXPECTED_HTML, body.html());
    }
}
