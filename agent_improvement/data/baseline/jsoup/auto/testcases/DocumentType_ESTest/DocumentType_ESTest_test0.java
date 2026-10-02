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
public class DocumentType_ESTest_test0 extends DocumentType_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test0() throws Throwable {
        DocumentType documentType0 = new DocumentType("jjaJtcOf~qy9r3Z]t:*", "", "<");
        documentType0.setInternalSubset("value");
        PipedOutputStream pipedOutputStream0 = new PipedOutputStream();
        MockPrintWriter mockPrintWriter0 = new MockPrintWriter(pipedOutputStream0, true);
        QuietAppendable quietAppendable0 = QuietAppendable.wrap(mockPrintWriter0);
        Document.OutputSettings document_OutputSettings0 = new Document.OutputSettings();
        documentType0.outerHtmlHead(quietAppendable0, document_OutputSettings0);
        assertEquals("#doctype", documentType0.nodeName());
    }
}
