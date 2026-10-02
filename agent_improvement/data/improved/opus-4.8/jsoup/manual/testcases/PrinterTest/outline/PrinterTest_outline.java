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
     * Verifies that when outline mode is enabled, the document is rendered with
     * (almost) every node indented, matching the expected outline-formatted HTML.
     */
    @Test
    void outline() throws IOException {
        // Parse the sample input document.
        File inputFile = getFile("/printertests/input-1.html");
        Document doc = Jsoup.parse(inputFile);

        // Enable outline mode, which indents nearly every node.
        doc.outputSettings().outline(true);

        // The pre-rendered reference output for outline mode.
        String expectedHtml = getFileAsString(getFile("/printertests/outline-1.html"));

        // Render the document and confirm it matches the reference output.
        String renderedHtml = doc.html();
        assertEquals(expectedHtml, renderedHtml);

        // html() and outerHtml() should produce identical output for the document.
        assertEquals(renderedHtml, doc.outerHtml());
    }
}
