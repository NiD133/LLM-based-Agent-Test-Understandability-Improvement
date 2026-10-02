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
public class DocumentType_ESTest_test7 extends DocumentType_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test7() throws Throwable {
        DocumentType documentType0 = new DocumentType("", "", "");
        String string0 = documentType0.publicId();
        assertEquals("#doctype", documentType0.nodeName());
        assertEquals("", string0);
    }
}
