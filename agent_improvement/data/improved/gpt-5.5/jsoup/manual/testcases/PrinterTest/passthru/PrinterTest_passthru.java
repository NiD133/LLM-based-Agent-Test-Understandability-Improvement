package org.jsoup.nodes;

import org.jsoup.Jsoup;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.jsoup.integration.ParseTest.getFile;
import static org.jsoup.integration.ParseTest.getFileAsString;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PrinterTest_passthru {

    @Test
    void passthru() throws IOException {
        // With pretty printing disabled, output should preserve the input apart from parser normalizations.
        File inputFile = getFile("/printertests/input-1.html");
        Document document = Jsoup.parse(inputFile);
        document.outputSettings().prettyPrint(false);

        String expectedHtml = getFileAsString(getFile("/printertests/passthru-1.html"));
        String actualHtml = document.html();

        assertEquals(expectedHtml, actualHtml, "HTML output should match the passthrough fixture");
        assertEquals(actualHtml, document.outerHtml(), "html() and outerHtml() should produce the same output");
    }
}
