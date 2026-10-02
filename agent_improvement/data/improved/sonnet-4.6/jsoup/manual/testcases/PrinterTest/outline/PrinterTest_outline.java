package org.jsoup.nodes;

import org.jsoup.Jsoup;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.io.IOException;
import static org.jsoup.integration.ParseTest.getFile;
import static org.jsoup.integration.ParseTest.getFileAsString;
import static org.junit.jupiter.api.Assertions.*;

public class PrinterTest_outline {

    /**
     * Verifies that outline mode causes every element to be indented on its own line,
     * and that doc.html() and doc.outerHtml() produce identical output.
     */
    @Test
    void outline() throws IOException {
        // Parse the HTML input fixture
        File inputFile = getFile("/printertests/input-1.html");
        Document doc = Jsoup.parse(inputFile);

        // Enable outline mode so that every node is placed on its own indented line
        doc.outputSettings().outline(true);

        // Load the expected outline-formatted HTML for comparison
        String expected = getFileAsString(getFile("/printertests/outline-1.html"));

        // Render the document in outline mode
        String html = doc.html();

        // The rendered HTML must match the expected outline output
        assertEquals(expected, html);

        // doc.html() and doc.outerHtml() must be identical for a Document node
        assertEquals(html, doc.outerHtml());
    }
}
