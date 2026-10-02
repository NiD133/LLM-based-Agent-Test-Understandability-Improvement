package org.jsoup.nodes;

import org.jsoup.Jsoup;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PrinterTest_spaceAfterSpanInBlock {

    /**
     * Verifies that when inline {@code <span>} elements are surrounded by whitespace inside block
     * elements ({@code <div>}, {@code <p>}), the pretty printer collapses the surrounding whitespace
     * to a single space rather than dropping it or preserving the original line breaks and runs of
     * spaces.
     */
    @Test
    void spaceAfterSpanInBlock() {
        // Input mixes irregular spacing and newlines around inline <span>s inside two block elements.
        String inputHtml =
            "<div> <span>Span</span> \n Text  <span>Follow</span></div>" +
            " <p> <span>Span</span>  Text <span>Follow</span> </p>";
        Document doc = Jsoup.parse(inputHtml);
        Element body = doc.body();

        // The extracted text normalises every run of whitespace down to single spaces.
        String expectedText = "Span Text Follow Span Text Follow";
        assertEquals(expectedText, body.text());

        // Pretty-printed HTML keeps exactly one space between inline content, indents the block
        // elements, and places each block on its own line.
        String expectedHtml =
            "<div>\n" +
            " <span>Span</span> Text <span>Follow</span>\n" +
            "</div>\n" +
            "<p><span>Span</span> Text <span>Follow</span></p>";
        assertEquals(expectedHtml, body.html());
    }
}
