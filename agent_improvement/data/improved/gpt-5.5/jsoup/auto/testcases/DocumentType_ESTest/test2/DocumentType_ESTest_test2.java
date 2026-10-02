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
public class DocumentType_ESTest_test2 extends DocumentType_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test2() throws Throwable {
        String name = "lZw";
        String publicId = "^SC.U|GG-9]}~JM";
        String systemId = "^SC.U|GG-9]}~JM";
        String expectedOuterHtml = "<!DOCTYPE lZw PUBLIC \"^SC.U|GG-9]}~JM\" \"^SC.U|GG-9]}~JM\">";

        DocumentType documentType = new DocumentType(name, publicId, systemId);
        String outerHtml = documentType.outerHtml();

        assertEquals(expectedOuterHtml, outerHtml);
        assertEquals("#doctype", documentType.nodeName());
    }
}
