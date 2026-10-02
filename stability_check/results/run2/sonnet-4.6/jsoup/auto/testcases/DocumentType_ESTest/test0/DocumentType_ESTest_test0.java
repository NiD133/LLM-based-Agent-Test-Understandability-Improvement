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
     * Verifies that a DocumentType with an internal subset renders its outer HTML head without error,
     * and that nodeName() always returns the fixed constant "#doctype".
     */
    @Test(timeout = 4000)
    public void test_outerHtmlHead_withInternalSubset_nodeNameIsDoctype() throws Throwable {
        // Construct a doctype with a non-empty name and a non-empty system ID ("<") but no public ID.
        DocumentType docType = new DocumentType("jjaJtcOf~qy9r3Z]t:*", "", "<");
        docType.setInternalSubset("value");

        // Wrap a PipedOutputStream in a MockPrintWriter so outerHtmlHead has somewhere to write.
        PipedOutputStream pipedOutputStream = new PipedOutputStream();
        MockPrintWriter printWriter = new MockPrintWriter(pipedOutputStream, true);
        QuietAppendable outputBuffer = QuietAppendable.wrap(printWriter);

        // Render the doctype opening tag into the buffer using default output settings.
        Document.OutputSettings outputSettings = new Document.OutputSettings();
        docType.outerHtmlHead(outputBuffer, outputSettings);

        // nodeName() must always return the fixed "#doctype" constant regardless of the name attribute.
        assertEquals("#doctype", docType.nodeName());
    }
}
