package org.jsoup.nodes;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.jsoup.parser.Parser;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Printer_ESTest_test15 extends Printer_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test15() throws Throwable {
        // Parse a body fragment so the content lands inside <body>
        Document document = Parser.parseBodyFragment("S*NpiP", "S*NpiP");

        // Prepend text directly on the document node, placing it before <html>
        document.prependText("S*NpiP");

        // The printer should emit the document-level text first, then the full HTML tree
        String renderedHtml = document.toString();
        assertEquals("S*NpiP\n<html>\n <head></head>\n <body>S*NpiP</body>\n</html>", renderedHtml);
    }
}
