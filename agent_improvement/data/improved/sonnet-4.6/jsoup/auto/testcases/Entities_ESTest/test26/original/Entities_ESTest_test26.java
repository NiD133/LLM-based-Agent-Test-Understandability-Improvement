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
public class Entities_ESTest_test26 extends Entities_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test26() throws Throwable {
        MockFileWriter mockFileWriter0 = new MockFileWriter("e\nY]<]>");
        QuietAppendable quietAppendable0 = QuietAppendable.wrap(mockFileWriter0);
        Document.OutputSettings document_OutputSettings0 = new Document.OutputSettings();
        Entities.escape(quietAppendable0, "v{}++=\"D1mJ", document_OutputSettings0, 76);
        assertEquals(1, document_OutputSettings0.indentAmount());
    }
}
