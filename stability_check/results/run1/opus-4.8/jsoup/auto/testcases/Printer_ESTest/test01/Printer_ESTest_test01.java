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
public class Printer_ESTest_test01 extends Printer_ESTest_scaffolding {

    /**
     * Verifies that traversing a Document with a Printer.Outline visitor returns
     * the very same Document instance that was traversed (Node.traverse returns
     * the node it was called on).
     */
    @Test(timeout = 4000)
    public void traverseWithOutlinePrinterReturnsSameDocument() throws Throwable {
        // Parse some input as XML to obtain a Document to traverse.
        Parser xmlParser = Parser.xmlParser();
        Document document = xmlParser.parseInput(
            "http://www.w3.org/2000/svg",
            "http://www.w3.org/XML/1998/namespace");

        // Wrap a file writer so the printer has somewhere to emit output.
        MockFileWriter fileWriter = new MockFileWriter("http://www.w3.org/1998/Math/MathML");
        QuietAppendable output = QuietAppendable.wrap(fileWriter);

        // Build the Outline printer that will visit each node of the document.
        Document.OutputSettings outputSettings = new Document.OutputSettings();
        Printer.Outline outlinePrinter = new Printer.Outline(document, output, outputSettings);

        // Give the document a text node so the printer has content to visit.
        document.appendText("http://www.w3.org/XML/1998/namespace");

        // traverse returns the node it started from - here, the document itself.
        Element traversed = document.traverse(outlinePrinter);

        assertSame(document, traversed);
    }
}
