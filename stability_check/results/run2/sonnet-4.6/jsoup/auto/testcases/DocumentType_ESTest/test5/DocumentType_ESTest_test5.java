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

    @Test(timeout = 4000)
    public void setPubSysKeyWithNull_doesNotChangeNodeName() throws Throwable {
        // Setting pubSysKey to null is a no-op; nodeName() must still return "#doctype"
        DocumentType documentType = new DocumentType(";o6Js56yej6]Elr1*-", ";o6Js56yej6]Elr1*-", ";o6Js56yej6]Elr1*-");
        documentType.setPubSysKey((String) null);
        assertEquals("#doctype", documentType.nodeName());
    }
}
