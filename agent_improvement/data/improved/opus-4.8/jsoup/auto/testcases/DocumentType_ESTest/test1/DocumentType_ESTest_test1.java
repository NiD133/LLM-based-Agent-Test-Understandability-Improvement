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
     * A doctype created with empty name, public ID, and system ID should report
     * "#doctype" as its node name and serialize to the lowercase HTML5 form.
     */
    @Test(timeout = 4000)
    public void emptyDoctypeReportsNodeNameAndSerializesAsHtml5() throws Throwable {
        DocumentType emptyDoctype = new DocumentType("", "", "");

        String serialized = emptyDoctype.toString();

        assertEquals("#doctype", emptyDoctype.nodeName());
        assertEquals("<!doctype>", serialized);
    }
}
