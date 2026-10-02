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
public class Printer_ESTest_test03 extends Printer_ESTest_scaffolding {

    /**
     * Verifies that calling addTail on an Outline printer with preserveWhitespace enabled
     * does not alter the document's id. The document is parsed from an SVG URL using an
     * XML parser, and output is directed to a MockFileWriter targeting a MathML path.
     */
    @Test(timeout = 4000)
    public void test03() throws Throwable {
        // Parse an SVG URL as XML to produce a document with no id attribute
        Parser xmlParser = Parser.xmlParser();
        Document svgDocument = xmlParser.parseInput("http://www.w3.org/2000/svg", "http://www.w3.org/XML/1998/namespace");

        // Set up an output channel writing to a MathML file path
        MockFileWriter mathMlFileWriter = new MockFileWriter("http://www.w3.org/1998/Math/MathML");
        QuietAppendable outputAppendable = QuietAppendable.wrap(mathMlFileWriter);

        // Create an Outline printer with default output settings and whitespace preservation on
        Document.OutputSettings outputSettings = new Document.OutputSettings();
        Printer.Outline outlinePrinter = new Printer.Outline(svgDocument, outputAppendable, outputSettings);
        outlinePrinter.preserveWhitespace = true;

        // Calling addTail on the document node should not affect the document's id
        outlinePrinter.addTail(svgDocument, 1643);
        assertEquals("", svgDocument.id());
    }
}
