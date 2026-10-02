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
     * Serializing a DocumentType (via outerHtmlHead) should not change the node's
     * name, which is always "#doctype" regardless of the doctype's content.
     */
    @Test(timeout = 4000)
    public void serializingDoctypeKeepsNodeNameAsDoctype() throws Throwable {
        // Given a doctype with a name, an empty public ID, a system ID, and an internal subset.
        DocumentType documentType = new DocumentType("jjaJtcOf~qy9r3Z]t:*", "", "<");
        documentType.setInternalSubset("value");

        // And a sink that the doctype can write its serialized form into.
        PipedOutputStream outputStream = new PipedOutputStream();
        MockPrintWriter writer = new MockPrintWriter(outputStream, true);
        QuietAppendable appendable = QuietAppendable.wrap(writer);
        Document.OutputSettings outputSettings = new Document.OutputSettings();

        // When the doctype writes its opening HTML.
        documentType.outerHtmlHead(appendable, outputSettings);

        // Then the node name is unaffected by serialization.
        assertEquals("#doctype", documentType.nodeName());
    }
}
