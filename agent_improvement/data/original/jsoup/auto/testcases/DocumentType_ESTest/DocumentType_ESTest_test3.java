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
public class DocumentType_ESTest_test3 extends DocumentType_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test3() throws Throwable {
        DocumentType documentType0 = new DocumentType("", "", "");
        StringBuilder stringBuilder0 = new StringBuilder(0);
        QuietAppendable quietAppendable0 = QuietAppendable.wrap(stringBuilder0);
        Document.OutputSettings document_OutputSettings0 = new Document.OutputSettings();
        Document.OutputSettings.Syntax document_OutputSettings_Syntax0 = Document.OutputSettings.Syntax.xml;
        Document.OutputSettings document_OutputSettings1 = document_OutputSettings0.syntax(document_OutputSettings_Syntax0);
        documentType0.outerHtmlHead(quietAppendable0, document_OutputSettings1);
        assertEquals("<!DOCTYPE>", stringBuilder0.toString());
    }
}
