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
        // parse /printertests/input-1.html, check formatted same as pretty-1.html
        File in = getFile("/printertests/input-1.html");
        Document doc = Jsoup.parse(in);
        String expected = getFileAsString(getFile("/printertests/pretty-1.html"));
        String html = doc.html();
        assertEquals(expected, html);
        assertEquals(html, doc.outerHtml());
    }
}
