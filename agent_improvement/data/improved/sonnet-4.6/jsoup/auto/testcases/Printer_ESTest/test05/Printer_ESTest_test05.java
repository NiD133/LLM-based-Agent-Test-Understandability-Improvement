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
     * Verifies that calling addText on a Printer.Outline with null OutputSettings throws
     * NullPointerException inside Entities.escape, since the settings object is required
     * to determine how characters should be escaped.
     */
    @Test(timeout = 4000)
    public void test05() throws Throwable {
        // A CDataNode doubles as the root node and the text node passed to addText
        CDataNode cdataNode = new CDataNode("&>kY=yo1");
        StringBuilder output = new StringBuilder((CharSequence) "&>kY=yo1");
        QuietAppendable appendable = QuietAppendable.wrap(output);

        // Construct an Outline printer with null OutputSettings
        Printer.Outline outlinePrinter = new Printer.Outline(cdataNode, appendable, (Document.OutputSettings) null);

        // addText delegates to Entities.escape(accum, text, settings, options);
        // with settings == null this must throw NullPointerException inside Entities
        try {
            outlinePrinter.addText(cdataNode, 1024, 8);
            fail("Expecting exception: NullPointerException");
        } catch (NullPointerException e) {
            verifyException("org.jsoup.nodes.Entities", e);
        }
    }
}
