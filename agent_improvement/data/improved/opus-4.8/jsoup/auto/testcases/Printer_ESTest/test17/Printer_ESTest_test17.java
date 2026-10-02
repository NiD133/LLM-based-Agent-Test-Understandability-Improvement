package org.jsoup.nodes;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.jsoup.parser.Tag;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Printer_ESTest_test17 extends Printer_ESTest_scaffolding {

    /**
     * Verifies that pretty-printing a freshly created HTML shell whose title is set
     * produces the expected indented document markup.
     */
    @Test(timeout = 4000)
    public void prettyPrintsShellWithTitle() throws Throwable {
        // Build a minimal HTML document shell (<html><head><title></title></head><body></body></html>).
        Document document = Document.createShell("\"");
        Tag.Data = 2922;
        document.title("\"");

        String rendered = document.toString();

        String expectedHtml =
            "<html>\n" +
            " <head>\n" +
            "  <title>\"</title>\n" +
            " </head>\n" +
            " <body></body>\n" +
            "</html>";
        assertEquals(expectedHtml, rendered);
    }
}
