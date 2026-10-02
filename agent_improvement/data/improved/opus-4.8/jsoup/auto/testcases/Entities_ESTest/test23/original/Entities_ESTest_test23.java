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
public class Entities_ESTest_test23 extends Entities_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test23() throws Throwable {
        Document.OutputSettings document_OutputSettings0 = new Document.OutputSettings();
        document_OutputSettings0.charset("ascii");
        String string0 = Entities.escape("ethiH_p3\u227B", document_OutputSettings0);
        assertEquals("ethiH_p3&#x227b;", string0);
    }
}
