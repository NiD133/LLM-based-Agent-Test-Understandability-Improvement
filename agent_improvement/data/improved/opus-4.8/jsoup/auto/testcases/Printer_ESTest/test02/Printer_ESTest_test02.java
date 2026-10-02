package org.jsoup.nodes;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.jsoup.internal.QuietAppendable;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Printer_ESTest_test02 extends Printer_ESTest_scaffolding {

    /**
     * The Outline printer's shouldIndent should return false for a blank CDATA node:
     * an empty CDATA value counts as blank text, so it is never indented.
     */
    @Test(timeout = 4000)
    public void shouldIndentReturnsFalseForBlankCDataNode() throws Throwable {
        CDataNode blankCData = new CDataNode("");
        CDataNode rootNode = blankCData.clone();

        StringBuilder output = new StringBuilder("");
        QuietAppendable accumulator = QuietAppendable.wrap(output);

        Printer.Outline outlinePrinter =
            new Printer.Outline(rootNode, accumulator, (Document.OutputSettings) null);

        boolean shouldIndent = outlinePrinter.shouldIndent(blankCData);

        assertFalse(shouldIndent);
    }
}
