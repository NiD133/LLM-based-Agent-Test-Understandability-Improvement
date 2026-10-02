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
        CDataNode cdataRoot = new CDataNode("&>kY=yo1");
        StringBuilder outputBuffer = new StringBuilder((CharSequence) "&>kY=yo1");
        QuietAppendable appendableOutput = QuietAppendable.wrap(outputBuffer);
        Printer.Outline outlinePrinter = new Printer.Outline(cdataRoot, appendableOutput, (Document.OutputSettings) null);

        try {
            outlinePrinter.addText(cdataRoot, 1024, 8);
            fail("Expecting exception: NullPointerException");
        } catch (NullPointerException exception) {
            verifyException("org.jsoup.nodes.Entities", exception);
        }
    }
}
