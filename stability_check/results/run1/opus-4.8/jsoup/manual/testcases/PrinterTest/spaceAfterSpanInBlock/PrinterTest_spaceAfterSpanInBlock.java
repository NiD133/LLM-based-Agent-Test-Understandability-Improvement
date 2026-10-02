package org.jsoup.nodes;

import org.jsoup.Jsoup;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Verifies how jsoup's pretty printer handles whitespace around inline {@code <span>} elements
 * that sit inside block-level elements ({@code <div>} and {@code <p>}).
 *
 * <p>The key expectations are:
 * <ul>
 *   <li>{@code text()} collapses all surrounding whitespace into single spaces.</li>
 *   <li>{@code html()} preserves exactly one space between adjacent inline content, while still
 *       indenting the block-level elements.</li>
 * </ul>
 */
public class PrinterTest_spaceAfterSpanInBlock {

    @Test
    void spaceAfterSpanInBlock() {
        // Two block elements, each containing inline <span>s separated by irregular whitespace
        // (extra spaces and a newline) that the pretty printer is expected to normalise.
        String htmlWithIrregularWhitespace =
            "<div> <span>Span</span> \n Text  <span>Follow</span></div>"
                + " <p> <span>Span</span>  Text <span>Follow</span> </p>";

        Document doc = Jsoup.parse(htmlWithIrregularWhitespace);
        Element body = doc.body();

        // text() should collapse the messy whitespace into single spaces across both blocks.
        String expectedText = "Span Text Follow Span Text Follow";
        assertEquals(expectedText, body.text());

        // html() should keep exactly one space between inline neighbours while indenting the blocks.
        String expectedHtml =
            "<div>\n <span>Span</span> Text <span>Follow</span>\n</div>\n"
                + "<p><span>Span</span> Text <span>Follow</span></p>";
        assertEquals(expectedHtml, body.html());
    }
}
