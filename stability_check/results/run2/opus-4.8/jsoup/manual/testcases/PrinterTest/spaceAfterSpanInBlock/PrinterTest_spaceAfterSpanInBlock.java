package org.jsoup.nodes;

import org.jsoup.Jsoup;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Verifies how jsoup normalises whitespace around inline {@code <span>} elements that sit inside
 * block-level elements ({@code <div>} and {@code <p>}).
 *
 * <p>The source markup deliberately contains irregular spacing (extra spaces, newlines) between the
 * spans and the surrounding text. Two behaviours are checked:
 * <ul>
 *   <li>{@link Element#text()} collapses all runs of whitespace into single spaces.</li>
 *   <li>{@link Element#html()} pretty-prints the block, keeping exactly one space between an inline
 *       span and its neighbouring text.</li>
 * </ul>
 */
public class PrinterTest_spaceAfterSpanInBlock {

    /** Markup with irregular whitespace surrounding inline spans inside a div and a paragraph. */
    private static final String INPUT_HTML =
        "<div> <span>Span</span> \n Text  <span>Follow</span></div> "
            + "<p> <span>Span</span>  Text <span>Follow</span> </p>";

    /** Expected plain text: every whitespace run collapsed to a single space. */
    private static final String EXPECTED_TEXT = "Span Text Follow Span Text Follow";

    /** Expected pretty-printed HTML: single spaces preserved around each inline span. */
    private static final String EXPECTED_HTML =
        "<div>\n <span>Span</span> Text <span>Follow</span>\n</div>\n"
            + "<p><span>Span</span> Text <span>Follow</span></p>";

    @Test
    void spaceAfterSpanInBlock() {
        Element body = Jsoup.parse(INPUT_HTML).body();

        assertEquals(EXPECTED_TEXT, body.text());
        assertEquals(EXPECTED_HTML, body.html());
    }
}
