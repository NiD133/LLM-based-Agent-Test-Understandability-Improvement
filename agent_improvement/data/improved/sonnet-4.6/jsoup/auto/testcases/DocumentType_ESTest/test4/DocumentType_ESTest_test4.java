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
public class DocumentType_ESTest_test4 extends DocumentType_ESTest_scaffolding {

    /**
     * Verifies that nodeName() always returns "#doctype" regardless of what
     * pubSysKey is set to after construction.
     */
    @Test(timeout = 4000)
    public void test_nodeNameIsAlwaysDoctype_afterSettingPubSysKey() throws Throwable {
        // Construct a DocumentType with a name and identical public/system IDs
        String publicAndSystemId = "^SC.U|GG-9]}~JM";
        DocumentType docType = new DocumentType("lZw", publicAndSystemId, publicAndSystemId);

        // Explicitly override the pubSysKey attribute (normally set automatically to "PUBLIC" or "SYSTEM")
        docType.setPubSysKey(publicAndSystemId);

        // nodeName() must always be "#doctype" for any DocumentType node
        assertEquals("#doctype", docType.nodeName());
    }
}
