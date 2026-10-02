package org.jsoup.nodes;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Entities_ESTest_test04 extends Entities_ESTest_scaffolding {

    // In XML/XHTML mode the apostrophe is escaped as &#x27; and ampersand as &amp;
    @Test(timeout = 4000)
    public void test04() throws Throwable {
        Document.OutputSettings outputSettings = new Document.OutputSettings();
        Document.OutputSettings.Syntax xmlSyntax = Document.OutputSettings.Syntax.xml;
        Document.OutputSettings xmlOutputSettings = outputSettings.syntax(xmlSyntax);

        String escaped = Entities.escape("K'?wQt&", xmlOutputSettings);
        assertEquals("K&#x27;?wQt&amp;", escaped);
    }
}
