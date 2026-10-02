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
public class Entities_ESTest_test10 extends Entities_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test10() throws Throwable {
        MockPrintStream mockPrintStream0 = new MockPrintStream("[:9;Wd@P3x0sfFM/");
        QuietAppendable quietAppendable0 = QuietAppendable.wrap(mockPrintStream0);
        Document.OutputSettings document_OutputSettings0 = new Document.OutputSettings();
        Entities.escape(quietAppendable0, "Must be false", document_OutputSettings0, (-249532396));
        assertEquals(Entities.EscapeMode.base, document_OutputSettings0.escapeMode());
    }
}
