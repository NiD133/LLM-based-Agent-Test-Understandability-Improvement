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
        String doctypeName = "jjaJtcOf~qy9r3Z]t:*";
        String publicId = "";
        String systemId = "<";

        DocumentType documentType = new DocumentType(doctypeName, publicId, systemId);

        String actualSystemId = documentType.systemId();
        assertEquals(systemId, actualSystemId);
        assertEquals("#doctype", documentType.nodeName());
    }
}
