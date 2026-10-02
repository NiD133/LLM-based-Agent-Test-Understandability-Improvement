package org.jsoup.nodes;

import static org.junit.Assert.assertEquals;

import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class DocumentType_ESTest_test5 extends DocumentType_ESTest_scaffolding {

    /**
     * A DocumentType always reports "#doctype" as its node name, and passing a
     * null value to setPubSysKey is a no-op that leaves the node unchanged.
     */
    @Test(timeout = 4000)
    public void nodeNameIsDoctypeAfterSettingNullPubSysKey() throws Throwable {
        String sharedValue = ";o6Js56yej6]Elr1*-";
        DocumentType documentType = new DocumentType(sharedValue, sharedValue, sharedValue);

        documentType.setPubSysKey((String) null);

        assertEquals("#doctype", documentType.nodeName());
    }
}
