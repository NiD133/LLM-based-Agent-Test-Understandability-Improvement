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

    private static final String SVG_NAMESPACE = "http://www.w3.org/2000/svg";
    private static final String XML_NAMESPACE = "http://www.w3.org/XML/1998/namespace";
    private static final String MATHML_NAMESPACE = "http://www.w3.org/1998/Math/MathML";

    @Test(timeout = 4000)
    public void test01() throws Throwable {
        Parser xmlParser = Parser.xmlParser();
        Document document = xmlParser.parseInput(SVG_NAMESPACE, XML_NAMESPACE);

        MockFileWriter fileWriter = new MockFileWriter(MATHML_NAMESPACE);
        QuietAppendable appendable = QuietAppendable.wrap(fileWriter);
        Document.OutputSettings outputSettings = new Document.OutputSettings();
        Printer.Outline outlinePrinter = new Printer.Outline(document, appendable, outputSettings);

        document.appendText(XML_NAMESPACE);
        Element traversedElement = document.traverse(outlinePrinter);

        assertSame(document, traversedElement);
    }
}
