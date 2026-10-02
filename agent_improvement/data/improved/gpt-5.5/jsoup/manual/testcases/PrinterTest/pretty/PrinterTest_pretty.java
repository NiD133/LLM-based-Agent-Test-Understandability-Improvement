package org.jsoup.nodes;

import org.jsoup.Jsoup;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.jsoup.integration.ParseTest.getFile;
import static org.jsoup.integration.ParseTest.getFileAsString;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PrinterTest_pretty {
    private static final String INPUT_FIXTURE = "/printertests/input-1.html";
    private static final String EXPECTED_PRETTY_FIXTURE = "/printertests/pretty-1.html";

    @Test
    void pretty() throws IOException {
        File inputFile = getFile(INPUT_FIXTURE);
        Document document = Jsoup.parse(inputFile);

        String expectedPrettyHtml = getFileAsString(getFile(EXPECTED_PRETTY_FIXTURE));
        String actualPrettyHtml = document.html();

        assertEquals(expectedPrettyHtml, actualPrettyHtml);
        assertEquals(actualPrettyHtml, document.outerHtml());
    }
}
