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
public class DocumentType_ESTest_test8 extends DocumentType_ESTest_scaffolding {

    private static final String DOCTYPE_NAME = "lZw";
    private static final String PUBLIC_AND_SYSTEM_ID = "^SC.U|GG-9]}~JM";
    private static final String DOCUMENT_TYPE_NODE_NAME = "#doctype";

    @Test(timeout = 4000)
    public void test8() throws Throwable {
        DocumentType documentType = new DocumentType(
                DOCTYPE_NAME,
                PUBLIC_AND_SYSTEM_ID,
                PUBLIC_AND_SYSTEM_ID);

        String actualName = documentType.name();

        assertEquals(DOCTYPE_NAME, actualName);
        assertEquals(DOCUMENT_TYPE_NODE_NAME, documentType.nodeName());
    }
}
