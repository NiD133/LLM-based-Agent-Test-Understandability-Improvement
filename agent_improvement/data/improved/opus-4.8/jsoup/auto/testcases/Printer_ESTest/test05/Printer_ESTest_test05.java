package org.jsoup.nodes;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.jsoup.internal.QuietAppendable;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Printer_ESTest_test05 extends Printer_ESTest_scaffolding {

    /**
     * Printer.addText() escapes the text node's value via Entities.escape(), which reads from the
     * Printer's OutputSettings. When the Outline printer is built with null settings, escaping
     * dereferences that null and Entities throws a NullPointerException.
     */
    @Test(timeout = 4000)
    public void test05() throws Throwable {
        CDataNode textNode = new CDataNode("&>kY=yo1");
        QuietAppendable output = QuietAppendable.wrap(new StringBuilder("&>kY=yo1"));

        // Outline printer with null OutputSettings.
        Printer.Outline outlinePrinter =
                new Printer.Outline(textNode, output, (Document.OutputSettings) null);

        try {
            outlinePrinter.addText(textNode, 1024, 8);
            fail("Expecting exception: NullPointerException");
        } catch (NullPointerException e) {
            // Thrown while escaping the text because OutputSettings is null.
            verifyException("org.jsoup.nodes.Entities", e);
        }
    }
}
