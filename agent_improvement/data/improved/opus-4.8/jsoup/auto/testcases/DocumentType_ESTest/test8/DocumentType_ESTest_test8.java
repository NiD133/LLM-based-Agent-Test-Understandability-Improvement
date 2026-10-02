package org.jsoup.nodes;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class DocumentType_ESTest_test8 extends DocumentType_ESTest_scaffolding {

    /**
     * A DocumentType created with a name should expose that name via name(),
     * while nodeName() always returns the fixed "#doctype" value.
     */
    @Test(timeout = 4000)
    public void nameReturnsConstructorNameAndNodeNameIsDoctype() throws Throwable {
        String doctypeName = "lZw";
        String publicId = "^SC.U|GG-9]}~JM";
        String systemId = "^SC.U|GG-9]}~JM";

        DocumentType documentType = new DocumentType(doctypeName, publicId, systemId);

        assertEquals("lZw", documentType.name());
        assertEquals("#doctype", documentType.nodeName());
    }
}
