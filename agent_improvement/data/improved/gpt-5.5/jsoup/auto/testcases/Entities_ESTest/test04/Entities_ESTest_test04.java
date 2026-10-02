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
public class Entities_ESTest_test04 extends Entities_ESTest_scaffolding {

    private static final String TEXT_REQUIRING_XML_ESCAPES = "K'?wQt&";
    private static final String EXPECTED_XML_ESCAPED_TEXT = "K&#x27;?wQt&amp;";

    @Test(timeout = 4000)
    public void test04() throws Throwable {
        Document.OutputSettings outputSettings = new Document.OutputSettings();
        Document.OutputSettings xmlOutputSettings = outputSettings.syntax(Document.OutputSettings.Syntax.xml);

        String escapedText = Entities.escape(TEXT_REQUIRING_XML_ESCAPES, xmlOutputSettings);

        assertEquals(EXPECTED_XML_ESCAPED_TEXT, escapedText);
    }
}
