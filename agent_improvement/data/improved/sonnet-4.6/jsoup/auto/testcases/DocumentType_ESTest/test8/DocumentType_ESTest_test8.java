package org.jsoup.nodes;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class DocumentType_ESTest_test8 extends DocumentType_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test8() throws Throwable {
        // Create a DocumentType with a specific name, publicId, and systemId
        DocumentType documentType = new DocumentType("lZw", "^SC.U|GG-9]}~JM", "^SC.U|GG-9]}~JM");

        // Verify that name() returns the name provided at construction time
        String actualName = documentType.name();
        assertEquals("DocumentType.name() should return the name set at construction", "lZw", actualName);

        // Verify that nodeName() always returns the fixed doctype node name
        assertEquals("DocumentType.nodeName() should always return '#doctype'", "#doctype", documentType.nodeName());
    }
}
