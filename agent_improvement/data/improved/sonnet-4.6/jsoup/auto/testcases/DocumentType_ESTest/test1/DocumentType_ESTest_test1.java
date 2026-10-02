package org.jsoup.nodes;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class DocumentType_ESTest_test1 extends DocumentType_ESTest_scaffolding {

    /**
     * A DocumentType with all-empty name, publicId, and systemId is treated as
     * an HTML5 doctype (no PUBLIC/SYSTEM identifiers), so serialisation uses the
     * lowercase "<!doctype>" form.
     */
    @Test(timeout = 4000)
    public void emptyDoctype_serialisesAsHtml5LowercaseDoctype() throws Throwable {
        DocumentType emptyDoctype = new DocumentType("", "", "");

        assertEquals("#doctype", emptyDoctype.nodeName());
        assertEquals("<!doctype>", emptyDoctype.toString());
    }
}
