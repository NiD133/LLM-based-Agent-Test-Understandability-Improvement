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

    /**
     * Verifies that a TextNode keeps its parent after a Pretty printer writes it out.
     *
     * The text node is reparented into a Document, then printed via Printer.Pretty.addText.
     * Printing must not detach the node, so it should still report having a parent.
     */
    @Test(timeout = 4000)
    public void test07() throws Throwable {
        // Build a text node and an output sink (a mock file writer wrapped as an appendable).
        TextNode textNode = TextNode.createFromEncoded(" />");
        MockFileWriter outputWriter = new MockFileWriter(" />");
        QuietAppendable output = QuietAppendable.wrap(outputWriter);

        // printerFor returns a Pretty printer because the text node's output settings enable pretty print.
        Printer.Pretty prettyPrinter = (Printer.Pretty) Printer.printerFor(textNode, output);

        // Attach the text node to a document so it has a parent.
        Document document = new Document(" />", " />");
        document.reparentChild(textNode);
        document.head();

        // Print the text node; this must not remove it from its parent.
        prettyPrinter.addText(textNode, -1, -1);

        assertTrue(textNode.hasParent());
    }
}
