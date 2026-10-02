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
public class Printer_ESTest_test07 extends Printer_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test07() throws Throwable {
        TextNode textNode0 = TextNode.createFromEncoded(" />");
        MockFileWriter mockFileWriter0 = new MockFileWriter(" />");
        QuietAppendable quietAppendable0 = QuietAppendable.wrap(mockFileWriter0);
        Printer.Pretty printer_Pretty0 = (Printer.Pretty) Printer.printerFor(textNode0, quietAppendable0);
        Document document0 = new Document(" />", " />");
        document0.reparentChild(textNode0);
        document0.head();
        printer_Pretty0.addText(textNode0, (-1), (-1));
        assertTrue(textNode0.hasParent());
    }
}
