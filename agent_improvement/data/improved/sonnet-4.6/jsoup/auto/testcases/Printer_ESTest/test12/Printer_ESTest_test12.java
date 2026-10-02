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
     * Verifies that traversing an empty CDataNode through a Pretty printer returns
     * the same CDataNode instance (i.e., traverse returns the root node it was called on).
     */
    @Test(timeout = 4000)
    public void test_traverseEmptyCDataNode_returnsSameNode() throws Throwable {
        // Set up a Pretty printer rooted at a TextNode
        TextNode rootTextNode = TextNode.createFromEncoded("vSq8d/x nu<U@frER");
        MockFileWriter fileWriter = new MockFileWriter("vSq8d/x nu<U@frER");
        QuietAppendable output = QuietAppendable.wrap(fileWriter);
        Document.OutputSettings outputSettings = new Document.OutputSettings();
        Printer.Pretty prettyPrinter = new Printer.Pretty(rootTextNode, output, outputSettings);

        // Traverse an empty CDataNode using the Pretty printer
        CDataNode emptyCDataNode = new CDataNode("");
        Node traversalResult = emptyCDataNode.traverse(prettyPrinter);

        // The traversal must return the same node it was invoked on
        assertSame(traversalResult, emptyCDataNode);
    }
}
