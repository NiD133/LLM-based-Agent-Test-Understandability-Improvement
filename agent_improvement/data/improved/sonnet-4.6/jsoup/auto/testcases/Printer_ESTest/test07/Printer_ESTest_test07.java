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

    @Test(timeout = 4000)
    public void test07() throws Throwable {
        // Create a text node from an encoded string and a Pretty printer backed by a mock file writer
        TextNode encodedTextNode = TextNode.createFromEncoded(" />");
        MockFileWriter fileWriter = new MockFileWriter(" />");
        QuietAppendable output = QuietAppendable.wrap(fileWriter);
        Printer.Pretty prettyPrinter = (Printer.Pretty) Printer.printerFor(encodedTextNode, output);

        // Reparent the text node under a new document so it has a parent node
        Document document = new Document(" />", " />");
        document.reparentChild(encodedTextNode);
        document.head();

        // addText with all-bits-set options and negative depth should not detach the node
        prettyPrinter.addText(encodedTextNode, (-1), (-1));
        assertTrue(encodedTextNode.hasParent());
    }
}
