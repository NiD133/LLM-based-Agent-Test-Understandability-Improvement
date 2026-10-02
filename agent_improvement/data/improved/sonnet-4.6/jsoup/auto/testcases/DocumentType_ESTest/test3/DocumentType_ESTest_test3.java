package org.jsoup.nodes;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.jsoup.internal.QuietAppendable;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class DocumentType_ESTest_test3 extends DocumentType_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test_outerHtmlHead_withEmptyIds_andXmlSyntax_producesUpperCaseDoctype() throws Throwable {
        // A DocumentType with all-empty identifiers renders as bare "<!DOCTYPE>" in XML mode
        DocumentType docType = new DocumentType("", "", "");

        StringBuilder output = new StringBuilder(0);
        QuietAppendable appendable = QuietAppendable.wrap(output);

        Document.OutputSettings outputSettings = new Document.OutputSettings()
                .syntax(Document.OutputSettings.Syntax.xml);

        docType.outerHtmlHead(appendable, outputSettings);

        assertEquals("<!DOCTYPE>", output.toString());
    }
}
