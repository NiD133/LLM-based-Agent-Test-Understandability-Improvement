package org.jsoup.nodes;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Entities_ESTest_test02 extends Entities_ESTest_scaffolding {

    /**
     * Escaping with XML syntax should convert the reserved characters {@code <} and {@code >}
     * into their named entities ({@code &lt;} and {@code &gt;}), while leaving other characters
     * (including the newline, slashes and brackets) untouched.
     */
    @Test(timeout = 4000)
    public void escapeWithXmlSyntaxEscapesAngleBrackets() throws Throwable {
        Document.OutputSettings xmlOutputSettings = new Document.OutputSettings();
        xmlOutputSettings.syntax(Document.OutputSettings.Syntax.xml);

        String escaped = Entities.escape("e\n//]<]>", xmlOutputSettings);

        assertEquals("e\n//]&lt;]&gt;", escaped);
    }
}
