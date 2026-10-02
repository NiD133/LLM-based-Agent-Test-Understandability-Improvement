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
     * Serializing a DocumentType via outerHtmlHead should succeed, and the node's
     * name is always the fixed literal "#doctype" regardless of its contents.
     */
    @Test(timeout = 4000)
    public void nodeNameIsDoctypeAfterSerializingHead() throws Throwable {
        // Build a doctype with an arbitrary name, empty public ID and a system ID.
        DocumentType documentType = new DocumentType("jjaJtcOf~qy9r3Z]t:*", "", "<");
        documentType.setInternalSubset("value");

        // Serialize the doctype's opening markup into a writer-backed appendable.
        PipedOutputStream pipedOutputStream = new PipedOutputStream();
        MockPrintWriter printWriter = new MockPrintWriter(pipedOutputStream, true);
        QuietAppendable appendable = QuietAppendable.wrap(printWriter);
        Document.OutputSettings outputSettings = new Document.OutputSettings();
        documentType.outerHtmlHead(appendable, outputSettings);

        // The doctype node always reports the same fixed node name.
        assertEquals("#doctype", documentType.nodeName());
    }
}
