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

    /**
     * A doctype with empty name, public ID, and system ID serializes as just
     * "<!DOCTYPE>" when written with XML syntax, since all of its parts are blank.
     */
    @Test(timeout = 4000)
    public void emptyDoctypeWithXmlSyntaxSerializesToBareDoctype() throws Throwable {
        DocumentType emptyDoctype = new DocumentType("", "", "");

        StringBuilder output = new StringBuilder();
        QuietAppendable appendable = QuietAppendable.wrap(output);

        Document.OutputSettings xmlSettings =
            new Document.OutputSettings().syntax(Document.OutputSettings.Syntax.xml);

        emptyDoctype.outerHtmlHead(appendable, xmlSettings);

        assertEquals("<!DOCTYPE>", output.toString());
    }
}
