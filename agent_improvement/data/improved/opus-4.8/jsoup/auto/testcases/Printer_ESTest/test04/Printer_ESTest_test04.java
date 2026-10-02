package org.jsoup.nodes;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.jsoup.parser.Parser;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Printer_ESTest_test04 extends Printer_ESTest_scaffolding {

    /**
     * When a parsed document is printed with outline output enabled, the
     * Printer should emit the document as pretty-printed, indented HTML.
     */
    @Test(timeout = 4000)
    public void printsParsedBodyFragmentAsOutlinedHtml() throws Throwable {
        Document document = Parser.parseBodyFragment("S*NpiP", "S*NpiP");

        Document.OutputSettings outputSettings = new Document.OutputSettings().outline(true);
        document.outputSettings(outputSettings);

        String renderedHtml = document.toString();

        String expectedHtml = "<html>\n <head></head>\n <body>S*NpiP</body>\n</html>";
        assertEquals(expectedHtml, renderedHtml);
    }
}
