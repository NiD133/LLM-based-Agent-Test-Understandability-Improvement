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

    // Standard XML namespace URIs used as test input data
    private static final String SVG_NAMESPACE_URI    = "http://www.w3.org/2000/svg";
    private static final String XML_NAMESPACE_URI    = "http://www.w3.org/XML/1998/namespace";
    private static final String MATHML_NAMESPACE_URI = "http://www.w3.org/1998/Math/MathML";

    /**
     * Verifies that traversing an XML document with an Outline printer returns
     * the same document element, even when text has been appended after the
     * printer was constructed.
     */
    @Test(timeout = 4000)
    public void test01() throws Throwable {
        // Build a parsed XML document from SVG namespace content
        Parser xmlParser = Parser.xmlParser();
        Document document = xmlParser.parseInput(SVG_NAMESPACE_URI, XML_NAMESPACE_URI);

        // Wrap a file writer in a QuietAppendable to capture the printed output
        MockFileWriter fileWriter = new MockFileWriter(MATHML_NAMESPACE_URI);
        QuietAppendable output = QuietAppendable.wrap(fileWriter);

        // Create an Outline printer that will format the document with indentation
        Document.OutputSettings outputSettings = new Document.OutputSettings();
        Printer.Outline outlinePrinter = new Printer.Outline(document, output, outputSettings);

        // Mutate the document after the printer is created to confirm the printer
        // operates on the live document state at traversal time
        document.appendText(XML_NAMESPACE_URI);

        // Traverse the document tree; the result must be the root document itself
        Element traversalResult = document.traverse(outlinePrinter);
        assertSame(document, traversalResult);
    }
}
