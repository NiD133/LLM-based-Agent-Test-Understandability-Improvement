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
    public void test_shouldIndent_returnsFalse_forBlankCDataNodeNotEqualToRoot() throws Throwable {
        // Arrange: create a blank CDataNode and a clone to use as the printer root
        CDataNode blankCDataNode = new CDataNode("");
        CDataNode rootNode = blankCDataNode.clone();

        // Wrap a StringBuilder as the output accumulator
        StringBuilder output = new StringBuilder((CharSequence) "");
        QuietAppendable accumulator = QuietAppendable.wrap(output);

        // Create an Outline printer whose root is the cloned node (null OutputSettings)
        Printer.Outline outlinePrinter = new Printer.Outline(rootNode, accumulator, (Document.OutputSettings) null);

        // Act: ask whether the blank CDataNode (not the root) should be indented
        boolean shouldIndent = outlinePrinter.shouldIndent(blankCDataNode);

        // Assert: blank CDataNodes are never indented
        assertFalse(shouldIndent);
    }
}
