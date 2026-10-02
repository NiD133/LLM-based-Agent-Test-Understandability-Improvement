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
     * Verifies that traversing a Document with a Printer.Outline returns the same Document instance.
     * The Outline printer writes an indented representation of all nodes to the given appendable.
     */
    @Test(timeout = 4000)
    public void test01() throws Throwable {
        // Parse an XML document using the SVG namespace URL as input content
        Parser xmlParser = Parser.xmlParser();
        Document document = xmlParser.parseInput("http://www.w3.org/2000/svg", "http://www.w3.org/XML/1998/namespace");

        // Create an output destination backed by a mock file writer
        MockFileWriter fileWriter = new MockFileWriter("http://www.w3.org/1998/Math/MathML");
        QuietAppendable outputAppendable = QuietAppendable.wrap(fileWriter);

        // Use default output settings for the outline printer
        Document.OutputSettings outputSettings = new Document.OutputSettings();

        // Create an Outline printer that will write an indented node tree to the appendable
        Printer.Outline outlinePrinter = new Printer.Outline(document, outputAppendable, outputSettings);

        // Append a text node to the document before traversal
        document.appendText("http://www.w3.org/XML/1998/namespace");

        // Traverse the document with the outline printer; expect the same document to be returned
        Element result = document.traverse(outlinePrinter);
        assertSame(document, result);
    }
}
