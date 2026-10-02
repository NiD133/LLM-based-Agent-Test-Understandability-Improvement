package org.jsoup.nodes;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class DocumentType_ESTest_test4 extends DocumentType_ESTest_scaffolding {

    /**
     * A DocumentType's node name is always the fixed literal "#doctype",
     * regardless of its name/publicId/systemId or any later pubSysKey change.
     */
    @Test(timeout = 4000)
    public void nodeNameIsAlwaysDoctype() throws Throwable {
        DocumentType doctype = new DocumentType("lZw", "^SC.U|GG-9]}~JM", "^SC.U|GG-9]}~JM");
        doctype.setPubSysKey("^SC.U|GG-9]}~JM");

        assertEquals("#doctype", doctype.nodeName());
    }
}
