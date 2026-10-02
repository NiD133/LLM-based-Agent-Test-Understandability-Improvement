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
public class Entities_ESTest_test03 extends Entities_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test03() throws Throwable {
        Document.OutputSettings document_OutputSettings0 = new Document.OutputSettings();
        Document.OutputSettings document_OutputSettings1 = document_OutputSettings0.charset("ascii");
        String string0 = Entities.escape("kp1D(Cu%~vB8caHD\u00A7", document_OutputSettings1);
        assertEquals("kp1D(Cu%~vB8caHD&sect;", string0);
    }
}
