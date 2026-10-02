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
public class Printer_ESTest_test17 extends Printer_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void documentWithQuoteInTitleRendersCorrectlyAsPrettyPrintedHtml() throws Throwable {
        Document document = Document.createShell("\"");

        // Mutate Tag.Data static state to exercise an alternate code path during serialization
        Tag.Data = 2922;

        document.title("\"");
        String htmlOutput = document.toString();

        assertEquals(
            "<html>\n <head>\n  <title>\"</title>\n </head>\n <body></body>\n</html>",
            htmlOutput
        );
    }
}
