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
     * Verifies that content placed before the <html> tag (two <b> elements and
     * a text node "b") is correctly relocated into <body> during parsing, and
     * that the document is pretty-printed with proper indentation.
     */
    @Test(timeout = 4000)
    public void test_contentBeforeHtmlTagIsMovedIntoBody() throws Throwable {
        // Input has <b> elements and loose text appearing before the <html> root tag
        String inputHtml = "<b></b><b></b>\n<html>\n <head></head>\n <body></body>\n</html>\nb";
        Document document = Parser.parse(inputHtml, "");

        String prettyPrintedHtml = document.toString();

        // jsoup moves the out-of-place content into <body> and pretty-prints the tree
        String expectedHtml = "<html>\n <head></head>\n <body>\n  <b></b><b></b> b\n </body>\n</html>";
        assertEquals(expectedHtml, prettyPrintedHtml);
    }
}
