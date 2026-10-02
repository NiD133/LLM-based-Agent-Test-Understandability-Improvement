package org.jsoup.nodes;

import org.junit.Test;
import static org.junit.Assert.*;
import java.io.PipedOutputStream;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.io.MockPrintWriter;
import org.jsoup.internal.QuietAppendable;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class DocumentType_ESTest_test6 extends DocumentType_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test6() throws Throwable {
        DocumentType documentType0 = new DocumentType("jjaJtcOf~qy9r3Z]t:*", "", "<");
        String string0 = documentType0.systemId();
        assertEquals("<", string0);
        assertEquals("#doctype", documentType0.nodeName());
    }
}
