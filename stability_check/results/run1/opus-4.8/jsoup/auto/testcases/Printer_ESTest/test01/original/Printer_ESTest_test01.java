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

    @Test(timeout = 4000)
    public void test01() throws Throwable {
        Parser parser0 = Parser.xmlParser();
        Document document0 = parser0.parseInput("http://www.w3.org/2000/svg", "http://www.w3.org/XML/1998/namespace");
        MockFileWriter mockFileWriter0 = new MockFileWriter("http://www.w3.org/1998/Math/MathML");
        QuietAppendable quietAppendable0 = QuietAppendable.wrap(mockFileWriter0);
        Document.OutputSettings document_OutputSettings0 = new Document.OutputSettings();
        Printer.Outline printer_Outline0 = new Printer.Outline(document0, quietAppendable0, document_OutputSettings0);
        document0.appendText("http://www.w3.org/XML/1998/namespace");
        Element element0 = document0.traverse(printer_Outline0);
        assertSame(document0, element0);
    }
}
