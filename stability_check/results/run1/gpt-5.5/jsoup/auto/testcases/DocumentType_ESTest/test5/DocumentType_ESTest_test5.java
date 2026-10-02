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
public class DocumentType_ESTest_test5 extends DocumentType_ESTest_scaffolding {

    private static final String DOCTYPE_VALUE = ";o6Js56yej6]Elr1*-";
    private static final String DOCTYPE_NODE_NAME = "#doctype";

    @Test(timeout = 4000)
    public void test5() throws Throwable {
        DocumentType documentType = new DocumentType(DOCTYPE_VALUE, DOCTYPE_VALUE, DOCTYPE_VALUE);

        documentType.setPubSysKey((String) null);

        assertEquals(DOCTYPE_NODE_NAME, documentType.nodeName());
    }
}
