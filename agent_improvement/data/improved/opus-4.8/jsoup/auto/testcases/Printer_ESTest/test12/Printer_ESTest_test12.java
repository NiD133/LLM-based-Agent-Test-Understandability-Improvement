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
public class Printer_ESTest_test12 extends Printer_ESTest_scaffolding {

    /**
     * Traversing a node with a Printer.Pretty visitor should return the same
     * node that traversal was started from (Node.traverse returns its receiver).
     */
    @Test(timeout = 4000)
    public void traverseWithPrettyPrinterReturnsSameNode() throws Throwable {
        // Build a Pretty printer that writes into a file-backed appendable.
        TextNode printerRoot = TextNode.createFromEncoded("vSq8d/x nu<U@frER");
        MockFileWriter output = new MockFileWriter("vSq8d/x nu<U@frER");
        QuietAppendable appendable = QuietAppendable.wrap(output);
        Document.OutputSettings outputSettings = new Document.OutputSettings();
        Printer.Pretty prettyPrinter = new Printer.Pretty(printerRoot, appendable, outputSettings);

        // Traverse an (empty) CDATA node with the printer.
        CDataNode cDataNode = new CDataNode("");
        Node traversedNode = cDataNode.traverse(prettyPrinter);

        assertSame(traversedNode, cDataNode);
    }
}
