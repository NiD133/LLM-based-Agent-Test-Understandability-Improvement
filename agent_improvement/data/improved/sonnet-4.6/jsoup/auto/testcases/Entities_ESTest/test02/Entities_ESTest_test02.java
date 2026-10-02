package org.jsoup.nodes;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Entities_ESTest_test02 extends Entities_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test02() throws Throwable {
        // Configure output settings to use XML syntax, which escapes < and > as &lt; and &gt;
        Document.OutputSettings xmlOutputSettings = new Document.OutputSettings();
        Document.OutputSettings.Syntax xmlSyntax = Document.OutputSettings.Syntax.xml;
        xmlOutputSettings.syntax(xmlSyntax);

        String escapedResult = Entities.escape("e\n//]<]>", xmlOutputSettings);

        assertEquals("e\n//]&lt;]&gt;", escapedResult);
    }
}
