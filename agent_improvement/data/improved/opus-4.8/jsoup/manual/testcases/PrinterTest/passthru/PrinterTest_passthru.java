package org.jsoup.nodes;

import org.jsoup.Jsoup;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.io.IOException;
import static org.jsoup.integration.ParseTest.getFile;
import static org.jsoup.integration.ParseTest.getFileAsString;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PrinterTest_passthru {

    /**
     * When pretty-printing is disabled, the serialized HTML should pass through almost
     * unchanged from the parsed input (apart from a couple of unavoidable parse
     * normalizations, e.g. for the doctype and {@code <pre>} blocks).
     */
    @Test
    void passthruPreservesInputWhenPrettyPrintDisabled() throws IOException {
        // Parse the source document and turn off pretty-printing so output mirrors the input.
        File inputFile = getFile("/printertests/input-1.html");
        Document doc = Jsoup.parse(inputFile);
        doc.outputSettings().prettyPrint(false);

        // The expected serialization is captured in a golden file.
        String expectedHtml = getFileAsString(getFile("/printertests/passthru-1.html"));

        String actualHtml = doc.html();

        // The serialized HTML matches the golden file...
        assertEquals(expectedHtml, actualHtml);
        // ...and html() and outerHtml() produce identical output for the document.
        assertEquals(actualHtml, doc.outerHtml());
    }
}
