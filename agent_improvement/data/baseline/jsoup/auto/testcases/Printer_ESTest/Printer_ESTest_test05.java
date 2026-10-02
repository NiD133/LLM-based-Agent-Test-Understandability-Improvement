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
public class Printer_ESTest_test05 extends Printer_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test05() throws Throwable {
        CDataNode cDataNode0 = new CDataNode("&>kY=yo1");
        StringBuilder stringBuilder0 = new StringBuilder((CharSequence) "&>kY=yo1");
        QuietAppendable quietAppendable0 = QuietAppendable.wrap(stringBuilder0);
        Printer.Outline printer_Outline0 = new Printer.Outline(cDataNode0, quietAppendable0, (Document.OutputSettings) null);
        // Undeclared exception!
        try {
            printer_Outline0.addText(cDataNode0, 1024, 8);
            fail("Expecting exception: NullPointerException");
        } catch (NullPointerException e) {
            //
            // no message in exception (getMessage() returned null)
            //
            verifyException("org.jsoup.nodes.Entities", e);
        }
    }
}
