package org.jsoup.nodes;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Printer_ESTest_test10 extends Printer_ESTest_scaffolding {

    /**
     * Pretty-printing a shell document with a trailing top-level text node should
     * render the standard html/head/body skeleton followed by the appended text.
     */
    @Test(timeout = 4000)
    public void prettyPrintsShellDocumentWithTrailingText() throws Throwable {
        Document document = Document.createShell("br");
        document.tagName("br", "br");
        document.appendText("br");

        String html = document.toString();

        String expectedHtml = "<html>\n <head></head>\n <body></body>\n</html>\nbr";
        assertEquals(expectedHtml, html);
    }
}
