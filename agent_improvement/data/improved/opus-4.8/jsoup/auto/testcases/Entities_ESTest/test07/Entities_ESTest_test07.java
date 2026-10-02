package org.jsoup.nodes;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Entities_ESTest_test07 extends Entities_ESTest_scaffolding {

    /**
     * In XHTML escape mode, a non-breaking space (U+00A0) must be emitted as the
     * numeric reference {@code &#xa0;} rather than the named {@code &nbsp;} entity,
     * while the surrounding plain text is left untouched.
     */
    @Test(timeout = 4000)
    public void escapeWritesNonBreakingSpaceAsNumericReferenceInXhtmlMode() throws Throwable {
        Document.OutputSettings outputSettings = new Document.OutputSettings();
        outputSettings.escapeMode(Entities.EscapeMode.xhtml);

        // " " is a non-breaking space appended to the text "yen".
        String textWithNonBreakingSpace = "yen ";
        String escaped = Entities.escape(textWithNonBreakingSpace, outputSettings);

        assertEquals("yen&#xa0;", escaped);
    }
}
