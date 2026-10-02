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
public class Printer_ESTest_test02 extends Printer_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test02() throws Throwable {
        CDataNode cDataNode0 = new CDataNode("");
        CDataNode cDataNode1 = cDataNode0.clone();
        StringBuilder stringBuilder0 = new StringBuilder((CharSequence) "");
        QuietAppendable quietAppendable0 = QuietAppendable.wrap(stringBuilder0);
        Printer.Outline printer_Outline0 = new Printer.Outline(cDataNode1, quietAppendable0, (Document.OutputSettings) null);
        boolean boolean0 = printer_Outline0.shouldIndent(cDataNode0);
        assertFalse(boolean0);
    }
}
