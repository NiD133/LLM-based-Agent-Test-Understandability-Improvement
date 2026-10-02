package org.jsoup.nodes;

import org.jsoup.Jsoup;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.io.IOException;
import static org.jsoup.integration.ParseTest.getFile;
import static org.jsoup.integration.ParseTest.getFileAsString;
import static org.junit.jupiter.api.Assertions.*;

public class PrinterTest_outline {

    @Test
    void outline() throws IOException {
        // outline mode, most everything gets indented
        File in = getFile("/printertests/input-1.html");
        Document doc = Jsoup.parse(in);
        doc.outputSettings().outline(true);
        String expected = getFileAsString(getFile("/printertests/outline-1.html"));
        String html = doc.html();
        assertEquals(expected, html);
        assertEquals(html, doc.outerHtml());
    }
}
