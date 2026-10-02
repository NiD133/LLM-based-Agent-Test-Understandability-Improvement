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
        DocumentType doctype = new DocumentType("jjaJtcOf~qy9r3Z]t:*", "", "<");
        doctype.setInternalSubset("value");

        PipedOutputStream outputStream = new PipedOutputStream();
        MockPrintWriter writer = new MockPrintWriter(outputStream, true);
        QuietAppendable appendable = QuietAppendable.wrap(writer);
        Document.OutputSettings outputSettings = new Document.OutputSettings();

        doctype.outerHtmlHead(appendable, outputSettings);

        assertEquals("#doctype", doctype.nodeName());
    }
}
