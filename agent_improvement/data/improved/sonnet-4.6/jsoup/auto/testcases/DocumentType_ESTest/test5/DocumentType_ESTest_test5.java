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
public class DocumentType_ESTest_test5 extends DocumentType_ESTest_scaffolding {

    /**
     * Verifies that calling setPubSysKey(null) is a no-op and does not affect
     * the fixed node name "#doctype" returned by nodeName().
     */
    @Test(timeout = 4000)
    public void test_nodeNameIsAlwaysDoctype_afterSettingNullPubSysKey() throws Throwable {
        DocumentType documentType = new DocumentType(";o6Js56yej6]Elr1*-", ";o6Js56yej6]Elr1*-", ";o6Js56yej6]Elr1*-");

        // setPubSysKey(null) is a no-op: the implementation only sets the attribute when value != null
        documentType.setPubSysKey((String) null);

        assertEquals("#doctype", documentType.nodeName());
    }
}
