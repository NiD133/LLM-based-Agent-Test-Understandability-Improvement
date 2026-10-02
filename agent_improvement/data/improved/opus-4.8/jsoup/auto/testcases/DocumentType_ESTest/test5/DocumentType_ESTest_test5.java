package org.jsoup.nodes;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class DocumentType_ESTest_test5 extends DocumentType_ESTest_scaffolding {

    /**
     * Calling setPubSysKey with a null value is a no-op (the CUT ignores null),
     * and the doctype node still reports its fixed node name "#doctype".
     */
    @Test(timeout = 4000)
    public void setPubSysKeyWithNullKeepsDoctypeNodeName() throws Throwable {
        String doctypeText = ";o6Js56yej6]Elr1*-";
        DocumentType documentType = new DocumentType(doctypeText, doctypeText, doctypeText);

        documentType.setPubSysKey((String) null);

        assertEquals("#doctype", documentType.nodeName());
    }
}
