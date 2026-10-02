package org.jsoup.nodes;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class DocumentType_ESTest_test2 extends DocumentType_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test_outerHtml_withPublicAndSystemId_includesPUBLICKeyword() throws Throwable {
        // A DocumentType with both publicId and systemId renders with the PUBLIC keyword
        DocumentType docType = new DocumentType("lZw", "^SC.U|GG-9]}~JM", "^SC.U|GG-9]}~JM");

        String outerHtml = docType.outerHtml();

        assertEquals("<!DOCTYPE lZw PUBLIC \"^SC.U|GG-9]}~JM\" \"^SC.U|GG-9]}~JM\">", outerHtml);
        assertEquals("#doctype", docType.nodeName());
    }
}
