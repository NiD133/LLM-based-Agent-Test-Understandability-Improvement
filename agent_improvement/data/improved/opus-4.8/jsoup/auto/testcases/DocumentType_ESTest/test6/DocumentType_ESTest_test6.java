package org.jsoup.nodes;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class DocumentType_ESTest_test6 extends DocumentType_ESTest_scaffolding {

    /**
     * Verifies that a DocumentType created with a given system ID reports that
     * same value back via systemId(), and that its node name is always "#doctype".
     */
    @Test(timeout = 4000)
    public void systemIdIsReturnedAndNodeNameIsDoctype() throws Throwable {
        String name = "jjaJtcOf~qy9r3Z]t:*";
        String publicId = "";
        String systemId = "<";
        DocumentType documentType = new DocumentType(name, publicId, systemId);

        assertEquals("<", documentType.systemId());
        assertEquals("#doctype", documentType.nodeName());
    }
}
