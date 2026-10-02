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
     * Traversing a Document with a Printer.Outline visitor should visit every node
     * and return the same Document instance that traversal started from.
     */
    @Test(timeout = 4000)
    public void traverseWithOutlinePrinterReturnsSameDocument() throws Throwable {
        // Parse an arbitrary (non-empty) input into a Document using the XML parser.
        Parser xmlParser = Parser.xmlParser();
        Document document = xmlParser.parseInput(
                "http://www.w3.org/2000/svg",
                "http://www.w3.org/XML/1998/namespace");

        // Build an Outline printer that writes the rendered output to a file-backed appendable.
        MockFileWriter fileWriter = new MockFileWriter("http://www.w3.org/1998/Math/MathML");
        QuietAppendable output = QuietAppendable.wrap(fileWriter);
        Document.OutputSettings outputSettings = new Document.OutputSettings();
        Printer.Outline outlinePrinter = new Printer.Outline(document, output, outputSettings);

        // Add some text content so the printer has a child node to visit.
        document.appendText("http://www.w3.org/XML/1998/namespace");

        // traverse() returns the node it was invoked on, i.e. the original Document.
        Element traversedElement = document.traverse(outlinePrinter);

        assertSame(document, traversedElement);
    }
}
