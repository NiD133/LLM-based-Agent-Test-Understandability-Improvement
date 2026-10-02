package org.jsoup.nodes;

import org.jsoup.Jsoup;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.jsoup.integration.ParseTest.getFile;
import static org.jsoup.integration.ParseTest.getFileAsString;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PrinterTest_pretty {

    /**
     * Verifies that jsoup's pretty printer renders a known input document exactly as the
     * expected, pre-formatted reference file. Also checks that {@code html()} and
     * {@code outerHtml()} produce identical output for the parsed document.
     */
    @Test
    void pretty() throws IOException {
        // Parse the raw input document.
        File inputFile = getFile("/printertests/input-1.html");
        Document document = Jsoup.parse(inputFile);

        // Load the expected, already pretty-printed reference output.
        String expectedPrettyHtml = getFileAsString(getFile("/printertests/pretty-1.html"));

        // The parsed document should pretty-print to match the reference output.
        String actualHtml = document.html();
        assertEquals(expectedPrettyHtml, actualHtml);

        // html() and outerHtml() should yield the same result for this document.
        assertEquals(actualHtml, document.outerHtml());
    }
}
