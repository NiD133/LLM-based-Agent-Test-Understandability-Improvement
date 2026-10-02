package org.jsoup.nodes;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Printer_ESTest_test13 extends Printer_ESTest_scaffolding {

    /**
     * A shell document (html > head + body) pretty-prints with each structural
     * element on its own indented line. Content appended to the document itself
     * lands after the closing </html> tag: an appended <br> element and a "br"
     * text node both render without surrounding whitespace.
     */
    @Test(timeout = 4000)
    public void appendedContentRendersAfterHtmlElement() throws Throwable {
        Document shellDocument = Document.createShell("br");
        shellDocument.append("br");      // append a <br> element node
        shellDocument.appendText("br");  // append a "br" text node

        String renderedHtml = shellDocument.toString();

        String expectedHtml =
            "<html>\n"
            + " <head></head>\n"
            + " <body></body>\n"
            + "</html>\n"
            + "brbr";
        assertEquals(expectedHtml, renderedHtml);
    }
}
