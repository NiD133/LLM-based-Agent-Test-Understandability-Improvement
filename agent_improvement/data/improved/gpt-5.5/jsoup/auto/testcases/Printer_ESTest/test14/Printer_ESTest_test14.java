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

    @Test(timeout = 4000)
    public void test14() throws Throwable {
        String htmlWithInlineTagsAndTrailingText = "<b></b><b></b>\n<html>\n <head></head>\n <body></body>\n</html>\nb";
        String emptyBaseUri = "";
        String expectedPrettyPrintedDocument = "<html>\n <head></head>\n <body>\n  <b></b><b></b> b\n </body>\n</html>";

        Document parsedDocument = Parser.parse(htmlWithInlineTagsAndTrailingText, emptyBaseUri);
        String actualPrettyPrintedDocument = parsedDocument.toString();

        assertEquals(expectedPrettyPrintedDocument, actualPrettyPrintedDocument);
    }
}
