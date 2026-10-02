package org.jsoup.nodes;

import org.jsoup.Jsoup;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.io.IOException;
import static org.jsoup.integration.ParseTest.getFile;
import static org.jsoup.integration.ParseTest.getFileAsString;
import static org.junit.jupiter.api.Assertions.*;

public class PrinterTest_pretty {

    @Test
    void pretty() throws IOException {
        // Verify that the pretty-printer renders input-1.html identically to the
        // hand-crafted reference file pretty-1.html, and that html() and outerHtml()
        // are consistent with each other (both delegate to the same Printer.Pretty path).
        File inputFile = getFile("/printertests/input-1.html");
        Document doc = Jsoup.parse(inputFile);

        String expectedHtml = getFileAsString(getFile("/printertests/pretty-1.html"));
        String actualHtml = doc.html();

        assertEquals(expectedHtml, actualHtml,
            "doc.html() should match the reference pretty-printed output");
        assertEquals(actualHtml, doc.outerHtml(),
            "doc.html() and doc.outerHtml() should produce identical output for a Document");
    }
}
