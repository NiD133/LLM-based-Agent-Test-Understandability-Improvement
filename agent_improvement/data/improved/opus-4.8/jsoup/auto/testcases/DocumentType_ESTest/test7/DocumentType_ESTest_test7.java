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
     * A DocumentType created with an empty public ID should report an empty
     * public ID back, and always expose the fixed node name "#doctype".
     */
    @Test(timeout = 4000)
    public void publicIdIsEmptyWhenConstructedEmpty() throws Throwable {
        DocumentType doctype = new DocumentType("", "", "");

        String publicId = doctype.publicId();

        assertEquals("", publicId);
        assertEquals("#doctype", doctype.nodeName());
    }
}
