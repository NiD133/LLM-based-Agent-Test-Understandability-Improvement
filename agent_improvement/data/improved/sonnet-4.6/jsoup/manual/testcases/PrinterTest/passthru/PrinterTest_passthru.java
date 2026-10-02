package org.jsoup.nodes;

import org.jsoup.Jsoup;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.io.IOException;
import static org.jsoup.integration.ParseTest.getFile;
import static org.jsoup.integration.ParseTest.getFileAsString;
import static org.junit.jupiter.api.Assertions.*;

public class PrinterTest_passthru {

    /**
     * Verifies that disabling pretty-printing produces output that is nearly identical to the
     * original HTML input (pass-through mode). The only permitted deviations are parse-level
     * normalizations performed by the parser itself (e.g. doctype casing, whitespace inside
     * {@code <pre>} elements). The test also confirms that {@code html()} and {@code outerHtml()}
     * are consistent with each other in this mode.
     */
    @Test
    void passthruMode_disablesPrettyPrintAndPreservesInputFaithfully() throws IOException {
        // Arrange: parse the input HTML file and switch off pretty-printing
        File inputFile = getFile("/printertests/input-1.html");
        Document doc = Jsoup.parse(inputFile);
        doc.outputSettings().prettyPrint(false);

        String expectedHtml = getFileAsString(getFile("/printertests/passthru-1.html"));

        // Act: serialise the document
        String actualHtml = doc.html();

        // Assert: serialised output matches the expected pass-through file,
        //         and html() / outerHtml() are equivalent in non-pretty mode
        assertEquals(expectedHtml, actualHtml,
            "html() output should match the expected pass-through file");
        assertEquals(actualHtml, doc.outerHtml(),
            "outerHtml() should return the same result as html() when pretty-printing is disabled");
    }
}
