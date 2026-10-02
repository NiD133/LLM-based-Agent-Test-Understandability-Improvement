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
    public void test_nodeNameIsDoctype_afterSerializingWithInternalSubset() throws Throwable {
        // DocumentType with a non-blank systemId triggers standard <!DOCTYPE ...> serialization
        DocumentType docType = new DocumentType("jjaJtcOf~qy9r3Z]t:*", "", "<");
        docType.setInternalSubset("value");

        // Wire up an output sink: PipedOutputStream → MockPrintWriter → QuietAppendable
        PipedOutputStream pipedOutputStream = new PipedOutputStream();
        MockPrintWriter printWriter = new MockPrintWriter(pipedOutputStream, true);
        QuietAppendable output = QuietAppendable.wrap(printWriter);

        Document.OutputSettings outputSettings = new Document.OutputSettings();
        docType.outerHtmlHead(output, outputSettings);

        assertEquals("#doctype", docType.nodeName());
    }
}
