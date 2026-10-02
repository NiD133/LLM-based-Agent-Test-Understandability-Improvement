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

    private static final String SVG_NAMESPACE_URI = "http://www.w3.org/2000/svg";
    private static final String XML_NAMESPACE_URI = "http://www.w3.org/XML/1998/namespace";
    private static final String MATHML_NAMESPACE_URI = "http://www.w3.org/1998/Math/MathML";

    @Test(timeout = 4000)
    public void test01() throws Throwable {
        Parser xmlParser = Parser.xmlParser();
        Document parsedDocument = xmlParser.parseInput(SVG_NAMESPACE_URI, XML_NAMESPACE_URI);
        MockFileWriter outputWriter = new MockFileWriter(MATHML_NAMESPACE_URI);
        QuietAppendable appendableOutput = QuietAppendable.wrap(outputWriter);
        Document.OutputSettings outputSettings = new Document.OutputSettings();
        Printer.Outline outlinePrinter = new Printer.Outline(parsedDocument, appendableOutput, outputSettings);

        parsedDocument.appendText(XML_NAMESPACE_URI);
        Element traversalResult = parsedDocument.traverse(outlinePrinter);

        assertSame(parsedDocument, traversalResult);
    }
}
