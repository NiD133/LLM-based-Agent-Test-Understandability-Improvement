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

    /**
     * Verifies that outerHtmlHead serializes a DOCTYPE node (including its internal subset)
     * without error, and that the node name is always "#doctype".
     */
    @Test(timeout = 4000)
    public void test0() throws Throwable {
        // Arrange: create a DOCTYPE with a name, an empty public ID, and a non-empty system ID
        DocumentType docType = new DocumentType("jjaJtcOf~qy9r3Z]t:*", "", "<");
        docType.setInternalSubset("value");

        // Build a QuietAppendable backed by a MockPrintWriter so that
        // outerHtmlHead can write without throwing checked IOException
        PipedOutputStream pipedOutputStream = new PipedOutputStream();
        MockPrintWriter mockPrintWriter = new MockPrintWriter(pipedOutputStream, true);
        QuietAppendable appendable = QuietAppendable.wrap(mockPrintWriter);
        Document.OutputSettings outputSettings = new Document.OutputSettings();

        // Act: serialize the DOCTYPE head into the appendable
        docType.outerHtmlHead(appendable, outputSettings);

        // Assert: the node name of any DocumentType is always "#doctype"
        assertEquals("#doctype", docType.nodeName());
    }
}
