package org.jsoup.nodes;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.jsoup.parser.Parser;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Printer_ESTest_test15 extends Printer_ESTest_scaffolding {

    /**
     * Verifies that pretty-printing a document renders text prepended directly to the
     * document (i.e. before the &lt;html&gt; element) on its own line, ahead of the
     * generated html/head/body structure.
     */
    @Test(timeout = 4000)
    public void prependedDocumentTextIsPrintedBeforeHtmlElement() throws Throwable {
        String text = "S*NpiP";

        // parseBodyFragment wraps the fragment's content inside a standard html/head/body skeleton.
        Document document = Parser.parseBodyFragment(text, text);
        // prependText adds the text as the document's first child, before the <html> element.
        document.prependText(text);

        String prettyPrintedHtml = document.toString();

        String expectedHtml =
            "S*NpiP\n" +
            "<html>\n" +
            " <head></head>\n" +
            " <body>S*NpiP</body>\n" +
            "</html>";
        assertEquals(expectedHtml, prettyPrintedHtml);
    }
}
