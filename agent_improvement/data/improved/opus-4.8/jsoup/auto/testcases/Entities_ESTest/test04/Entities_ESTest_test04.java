package org.jsoup.nodes;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Entities_ESTest_test04 extends Entities_ESTest_scaffolding {

    /**
     * Verifies that {@link Entities#escape(String, Document.OutputSettings)} with XML
     * output syntax escapes both the apostrophe and the ampersand:
     * the apostrophe becomes the hex character reference {@code &#x27;} and the
     * ampersand becomes {@code &amp;}, while the remaining characters are left as-is.
     */
    @Test(timeout = 4000)
    public void escapeWithXmlSyntaxEscapesApostropheAndAmpersand() throws Throwable {
        Document.OutputSettings xmlOutputSettings =
            new Document.OutputSettings().syntax(Document.OutputSettings.Syntax.xml);

        String escaped = Entities.escape("K'?wQt&", xmlOutputSettings);

        assertEquals("K&#x27;?wQt&amp;", escaped);
    }
}
