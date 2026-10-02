package org.jsoup.nodes;

import org.jsoup.Jsoup;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.io.IOException;
import static org.jsoup.integration.ParseTest.getFile;
import static org.jsoup.integration.ParseTest.getFileAsString;
import static org.junit.jupiter.api.Assertions.*;

public class PrinterTest_passthru {

    @Test
    void passthru() throws IOException {
        // disable pretty, should be almost 1:1 of input (other than a couple parse normalizations; doctype, pre)
        File in = getFile("/printertests/input-1.html");
        Document doc = Jsoup.parse(in);
        doc.outputSettings().prettyPrint(false);
        String expected = getFileAsString(getFile("/printertests/passthru-1.html"));
        String html = doc.html();
        assertEquals(expected, html);
        assertEquals(html, doc.outerHtml());
    }
}
