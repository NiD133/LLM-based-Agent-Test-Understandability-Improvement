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

    // Verifies that Printer.Outline.traverse() on a document with appended text
    // returns the same document element it was given.
    @Test(timeout = 4000)
    public void test_outlineTraverseReturnsOriginalDocument() throws Throwable {
        Parser xmlParser = Parser.xmlParser();
        Document document = xmlParser.parseInput("http://www.w3.org/2000/svg", "http://www.w3.org/XML/1998/namespace");

        MockFileWriter fileWriter = new MockFileWriter("http://www.w3.org/1998/Math/MathML");
        QuietAppendable appendable = QuietAppendable.wrap(fileWriter);
        Document.OutputSettings outputSettings = new Document.OutputSettings();

        Printer.Outline outlinePrinter = new Printer.Outline(document, appendable, outputSettings);

        document.appendText("http://www.w3.org/XML/1998/namespace");

        Element traversalResult = document.traverse(outlinePrinter);

        assertSame(document, traversalResult);
    }
}
