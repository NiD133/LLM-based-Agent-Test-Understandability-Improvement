package org.jsoup.nodes;

import org.jsoup.Jsoup;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.jsoup.integration.ParseTest.getFile;
import static org.jsoup.integration.ParseTest.getFileAsString;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PrinterTest_outline {
    private static final String INPUT_FIXTURE = "/printertests/input-1.html";
    private static final String OUTLINE_FIXTURE = "/printertests/outline-1.html";

    @Test
    void outline() throws IOException {
        File inputFile = getFile(INPUT_FIXTURE);
        Document document = Jsoup.parse(inputFile);

        document.outputSettings().outline(true);

        String expectedOutlineHtml = getFileAsString(getFile(OUTLINE_FIXTURE));
        String actualOutlineHtml = document.html();

        assertEquals(expectedOutlineHtml, actualOutlineHtml);
        assertEquals(actualOutlineHtml, document.outerHtml());
    }
}
