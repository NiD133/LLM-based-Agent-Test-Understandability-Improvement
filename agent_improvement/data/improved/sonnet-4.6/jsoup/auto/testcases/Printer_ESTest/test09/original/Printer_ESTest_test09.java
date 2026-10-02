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
public class Printer_ESTest_test09 extends Printer_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test09() throws Throwable {
        TextNode textNode0 = TextNode.createFromEncoded(" />");
        MockFileWriter mockFileWriter0 = new MockFileWriter(" />");
        QuietAppendable quietAppendable0 = QuietAppendable.wrap(mockFileWriter0);
        Printer.Pretty printer_Pretty0 = (Printer.Pretty) Printer.printerFor(textNode0, quietAppendable0);
        Document document0 = new Document(" />", " />");
        Element element0 = document0.body();
        // Undeclared exception!
        try {
            printer_Pretty0.addHead(element0, (-1));
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            //
            // width must be >= 0
            //
            verifyException("org.jsoup.helper.Validate", e);
        }
    }
}
