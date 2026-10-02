package org.jsoup.nodes;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class DocumentType_ESTest_test5 extends DocumentType_ESTest_scaffolding {

    /**
     * Passing a null pub/sys key to setPubSysKey should be a no-op and must not
     * affect the doctype's node name, which always stays "#doctype".
     */
    @Test(timeout = 4000)
    public void setPubSysKeyWithNullLeavesNodeNameUnchanged() throws Throwable {
        String sharedValue = ";o6Js56yej6]Elr1*-";
        DocumentType documentType = new DocumentType(sharedValue, sharedValue, sharedValue);

        documentType.setPubSysKey((String) null);

        assertEquals("#doctype", documentType.nodeName());
    }
}
