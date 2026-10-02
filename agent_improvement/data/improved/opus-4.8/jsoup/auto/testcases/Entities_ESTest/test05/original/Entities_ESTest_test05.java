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
public class Entities_ESTest_test05 extends Entities_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test05() throws Throwable {
        Document.OutputSettings document_OutputSettings0 = new Document.OutputSettings();
        PipedOutputStream pipedOutputStream0 = new PipedOutputStream();
        BufferedOutputStream bufferedOutputStream0 = new BufferedOutputStream(pipedOutputStream0);
        MockPrintStream mockPrintStream0 = new MockPrintStream(bufferedOutputStream0, false);
        QuietAppendable quietAppendable0 = QuietAppendable.wrap(mockPrintStream0);
        Entities.escape(quietAppendable0, "20,HVe0[Tl'l>TR", document_OutputSettings0, (-1814));
        assertEquals(Document.OutputSettings.Syntax.html, document_OutputSettings0.syntax());
    }
}
