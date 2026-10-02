package org.jsoup.nodes;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class DocumentType_ESTest_test2 extends DocumentType_ESTest_scaffolding {

    /**
     * A DocumentType created with a name plus public and system IDs should serialize
     * as a full {@code <!DOCTYPE ... PUBLIC ...>} declaration, and report "#doctype"
     * as its node name.
     */
    @Test(timeout = 4000)
    public void outerHtmlIncludesNamePublicAndSystemIds() throws Throwable {
        String name = "lZw";
        String publicId = "^SC.U|GG-9]}~JM";
        String systemId = "^SC.U|GG-9]}~JM";

        DocumentType documentType = new DocumentType(name, publicId, systemId);

        String outerHtml = documentType.outerHtml();

        assertEquals(
            "<!DOCTYPE lZw PUBLIC \"^SC.U|GG-9]}~JM\" \"^SC.U|GG-9]}~JM\">",
            outerHtml);
        assertEquals("#doctype", documentType.nodeName());
    }
}
