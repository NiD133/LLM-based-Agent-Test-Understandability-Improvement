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
     * Verifies that serializing a DocumentType (via outerHtmlHead) with an
     * internal subset set does not disturb the node's identity: its node name
     * must remain the fixed "#doctype" value.
     */
    @Test(timeout = 4000)
    public void nodeNameRemainsDoctypeAfterSerialization() throws Throwable {
        // Build a doctype with a name, an empty public ID and a system ID.
        DocumentType documentType = new DocumentType("jjaJtcOf~qy9r3Z]t:*", "", "<");
        documentType.setInternalSubset("value");

        // Wrap a writer so outerHtmlHead has an Appendable to serialize into.
        PipedOutputStream pipedOutputStream = new PipedOutputStream();
        MockPrintWriter mockPrintWriter = new MockPrintWriter(pipedOutputStream, true);
        QuietAppendable quietAppendable = QuietAppendable.wrap(mockPrintWriter);

        // Serialize the doctype's opening markup using default output settings.
        Document.OutputSettings outputSettings = new Document.OutputSettings();
        documentType.outerHtmlHead(quietAppendable, outputSettings);

        // The node name of a DocumentType is always "#doctype".
        assertEquals("#doctype", documentType.nodeName());
    }
}
