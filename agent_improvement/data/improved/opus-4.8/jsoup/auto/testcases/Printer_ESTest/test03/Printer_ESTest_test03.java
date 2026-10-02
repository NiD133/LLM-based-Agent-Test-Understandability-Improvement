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
public class Printer_ESTest_test03 extends Printer_ESTest_scaffolding {

    /**
     * Verifies that invoking {@link Printer.Outline#addTail} on a document parsed by the
     * XML parser does not affect the document's id, which stays empty.
     */
    @Test(timeout = 4000)
    public void addTailKeepsDocumentIdEmpty() throws Throwable {
        // Parse a document using the XML parser.
        Document document = Parser.xmlParser().parseInput(
                "http://www.w3.org/2000/svg",
                "http://www.w3.org/XML/1998/namespace");

        // Build an Outline printer that writes into a (mock) file writer.
        MockFileWriter fileWriter = new MockFileWriter("http://www.w3.org/1998/Math/MathML");
        QuietAppendable output = QuietAppendable.wrap(fileWriter);
        Document.OutputSettings outputSettings = new Document.OutputSettings();
        Printer.Outline outlinePrinter = new Printer.Outline(document, output, outputSettings);

        // Print the closing/tail markup for the document at an arbitrary depth.
        outlinePrinter.preserveWhitespace = true;
        outlinePrinter.addTail(document, 1643);

        // The document's id is unaffected and remains empty.
        assertEquals("", document.id());
    }
}
