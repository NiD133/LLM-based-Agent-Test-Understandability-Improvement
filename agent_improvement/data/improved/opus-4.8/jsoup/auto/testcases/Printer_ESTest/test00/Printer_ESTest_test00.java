package org.jsoup.nodes;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.io.MockFileWriter;
import org.jsoup.internal.QuietAppendable;
import org.jsoup.parser.Parser;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Printer_ESTest_test00 extends Printer_ESTest_scaffolding {

    /**
     * Verifies that {@link Printer#printerFor(Node, QuietAppendable)} returns a
     * (non-null) Printer instance for a parsed XML document writing to an
     * appendable destination.
     */
    @Test(timeout = 4000)
    public void printerForXmlDocumentReturnsPrinter() throws Throwable {
        // Parse a small piece of input as XML to obtain a Document to print.
        Document xmlDocument =
                Parser.xmlParser().parseInput("http://www.w3.org/2000/svg",
                        "http://www.w3.org/XML/1998/namespace");

        // Wrap a file writer as the appendable destination the printer writes to.
        MockFileWriter writer = new MockFileWriter("http://www.w3.org/1998/Math/MathML");
        QuietAppendable destination = QuietAppendable.wrap(writer);

        // A printer should be created for the document/destination pair.
        Printer printer = Printer.printerFor(xmlDocument, destination);

        assertNotNull(printer);
    }
}
