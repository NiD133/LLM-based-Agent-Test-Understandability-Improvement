package org.jsoup.nodes;

import org.junit.Test;
import static org.junit.Assert.*;
import java.io.BufferedOutputStream;
import java.io.PipedOutputStream;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.io.MockFileWriter;
import org.evosuite.runtime.mock.java.io.MockPrintStream;
import org.jsoup.internal.QuietAppendable;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Entities_ESTest_test02 extends Entities_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test02() throws Throwable {
        Document.OutputSettings outputSettings = new Document.OutputSettings();
        Document.OutputSettings.Syntax xmlSyntax = Document.OutputSettings.Syntax.xml;
        outputSettings.syntax(xmlSyntax);

        String inputContainingXmlMarkup = "e\n//]<]>";
        String escaped = Entities.escape(inputContainingXmlMarkup, outputSettings);

        assertEquals("e\n//]&lt;]&gt;", escaped);
    }
}
