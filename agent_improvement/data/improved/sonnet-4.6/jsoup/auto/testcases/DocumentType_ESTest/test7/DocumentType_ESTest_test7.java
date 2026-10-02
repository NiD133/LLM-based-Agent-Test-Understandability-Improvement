package org.jsoup.nodes;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class DocumentType_ESTest_test7 extends DocumentType_ESTest_scaffolding {

    /**
     * Verifies that a DocumentType created with empty strings has an empty publicId
     * and reports "#doctype" as its node name.
     */
    @Test(timeout = 4000)
    public void test_emptyDoctype_hasEmptyPublicIdAndCorrectNodeName() throws Throwable {
        DocumentType emptyDoctype = new DocumentType("", "", "");

        String publicId = emptyDoctype.publicId();

        assertEquals("Node name should be '#doctype' for any DocumentType node", "#doctype", emptyDoctype.nodeName());
        assertEquals("Public ID should be empty when constructed with an empty string", "", publicId);
    }
}
