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
     * In XHTML escape mode, the non-breaking space (U+00A0) must be encoded as the
     * numeric hex reference {@code &#xa0;} rather than the named entity {@code &nbsp;},
     * because XHTML only defines a minimal set of named entities (lt, gt, amp, quot).
     */
    @Test(timeout = 4000)
    public void test07() throws Throwable {
        // Configure output settings to use the restricted XHTML escape mode
        Document.OutputSettings outputSettings = new Document.OutputSettings();
        outputSettings.escapeMode(Entities.EscapeMode.xhtml);

        // "yen" followed by a non-breaking space character (U+00A0)
        String inputWithNonBreakingSpace = "yen\u00A0";

        String escaped = Entities.escape(inputWithNonBreakingSpace, outputSettings);

        // In XHTML mode U+00A0 has no named entity, so it is rendered as the hex numeric reference &#xa0;
        assertEquals("yen&#xa0;", escaped);
    }
}
