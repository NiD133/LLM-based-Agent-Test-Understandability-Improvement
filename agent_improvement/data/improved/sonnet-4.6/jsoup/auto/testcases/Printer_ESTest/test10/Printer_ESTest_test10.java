package org.jsoup.nodes;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Printer_ESTest_test10 extends Printer_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test10() throws Throwable {
        // Text appended directly to the Document node (not to body) appears after </html> in the output
        Document document = Document.createShell("br");
        document.tagName("br", "br");
        document.appendText("br");

        String htmlOutput = document.toString();

        assertEquals("<html>\n <head></head>\n <body></body>\n</html>\nbr", htmlOutput);
    }
}
