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
public class Printer_ESTest_test00 extends Printer_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test_printerFor_xmlDocument_returnsNonNullPrinter() throws Throwable {
        // Parse an SVG namespace URI as content using an XML parser
        Parser xmlParser = Parser.xmlParser();
        Document document = xmlParser.parseInput("http://www.w3.org/2000/svg", "http://www.w3.org/XML/1998/namespace");

        // Wrap a MockFileWriter in a QuietAppendable to use as the output sink
        MockFileWriter fileWriter = new MockFileWriter("http://www.w3.org/1998/Math/MathML");
        QuietAppendable outputSink = QuietAppendable.wrap(fileWriter);

        // printerFor selects the appropriate Printer subtype based on the document's OutputSettings
        Printer printer = Printer.printerFor(document, outputSink);

        assertNotNull(printer);
    }
}
