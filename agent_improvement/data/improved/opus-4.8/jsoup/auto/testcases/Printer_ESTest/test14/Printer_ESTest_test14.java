package org.jsoup.nodes;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.io.MockFileWriter;
import org.jsoup.internal.QuietAppendable;
import org.jsoup.parser.Parser;
import org.jsoup.parser.Tag;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Printer_ESTest_test14 extends Printer_ESTest_scaffolding {

    /**
     * Parsing loose markup (stray inline <b> tags and trailing text outside the
     * <html> structure) should normalise it into a well-formed document, and the
     * Printer's pretty-print pass should then re-indent that document into the
     * canonical, two-space-indented HTML layout.
     */
    @Test(timeout = 4000)
    public void prettyPrintsNormalisedDocument() throws Throwable {
        String looseMarkup = "<b></b><b></b>\n<html>\n <head></head>\n <body></body>\n</html>\nb";

        Document parsedDocument = Parser.parse(looseMarkup, "");
        String prettyPrintedHtml = parsedDocument.toString();

        String expectedHtml =
            "<html>\n" +
            " <head></head>\n" +
            " <body>\n" +
            "  <b></b><b></b> b\n" +
            " </body>\n" +
            "</html>";
        assertEquals(expectedHtml, prettyPrintedHtml);
    }
}
