package org.jsoup.nodes;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Entities_ESTest_test25 extends Entities_ESTest_scaffolding {

    /**
     * Escaping a string that ends with a non-breaking space (U+00A0) should,
     * with the default HTML output settings, convert that character into the
     * named entity {@code &nbsp;} while leaving the surrounding text intact.
     */
    @Test(timeout = 4000)
    public void escapeConvertsNonBreakingSpaceToNamedEntity() throws Throwable {
        Document.OutputSettings defaultHtmlOutput = new Document.OutputSettings();
        String inputWithNonBreakingSpace = "yen "; // trailing char is the non-breaking space

        String escaped = Entities.escape(inputWithNonBreakingSpace, defaultHtmlOutput);

        assertEquals("yen&nbsp;", escaped);
    }
}
