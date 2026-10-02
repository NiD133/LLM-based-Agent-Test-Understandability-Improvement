package org.jsoup.nodes;

import org.jsoup.Jsoup;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Verifies that the pretty-printer normalises whitespace correctly when inline elements
 * (e.g. {@code <span>}) appear inside block-level containers (e.g. {@code <div>}, {@code <p>}).
 *
 * <p>Specifically, extra spaces and newlines that surround a {@code <span>} in the raw HTML
 * source must be collapsed to a single space in both the plain-text view and the
 * pretty-printed HTML output.
 */
public class PrinterTest_spaceAfterSpanInBlock {

    /**
     * Raw HTML that intentionally contains irregular whitespace around inline elements
     * so we can verify it is normalised on output.
     *
     * <ul>
     *   <li>The {@code <div>} has a leading space, a newline, and double spaces.</li>
     *   <li>The {@code <p>} has leading/trailing spaces and double spaces.</li>
     * </ul>
     */
    private static final String INPUT_HTML =
        "<div> <span>Span</span> \n Text  <span>Follow</span></div>"
        + " <p> <span>Span</span>  Text <span>Follow</span> </p>";

    /** All whitespace normalised to single spaces; block boundaries produce a single space too. */
    private static final String EXPECTED_TEXT = "Span Text Follow Span Text Follow";

    /**
     * Pretty-printed form:
     * <ul>
     *   <li>{@code <div>} gets a newline + one-space indent before its inline content.</li>
     *   <li>{@code <p>} keeps its inline content on one line (no leading indent).</li>
     *   <li>Redundant spaces between the span and surrounding text are collapsed.</li>
     * </ul>
     */
    private static final String EXPECTED_HTML =
        "<div>\n"
        + " <span>Span</span> Text <span>Follow</span>\n"
        + "</div>\n"
        + "<p><span>Span</span> Text <span>Follow</span></p>";

    @Test
    void spaceAfterSpanInBlock() {
        Document doc = Jsoup.parse(INPUT_HTML);
        Element body = doc.body();

        assertEquals(EXPECTED_TEXT, body.text(),
            "Plain-text extraction should collapse all whitespace to single spaces");
        assertEquals(EXPECTED_HTML, body.html(),
            "Pretty-printed HTML should normalise spaces around inline spans inside block elements");
    }
}
