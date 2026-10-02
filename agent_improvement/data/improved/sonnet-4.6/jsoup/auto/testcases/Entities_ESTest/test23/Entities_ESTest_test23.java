package org.jsoup.nodes;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Entities_ESTest_test23 extends Entities_ESTest_scaffolding {

    /**
     * When the output charset is ASCII, Unicode characters outside the ASCII range must
     * be escaped as hex HTML entity references. Here, U+227B (SUCCEEDS symbol) in the
     * input is converted to &#x227b; because ASCII cannot encode it.
     */
    @Test(timeout = 4000)
    public void test_escapeNonAsciiCharacterAsHexEntity_whenCharsetIsAscii() throws Throwable {
        Document.OutputSettings outputSettings = new Document.OutputSettings();
        outputSettings.charset("ascii");

        // ≻ is the SUCCEEDS mathematical symbol (≻), not representable in ASCII
        String escaped = Entities.escape("ethiH_p3≻", outputSettings);

        assertEquals("ethiH_p3&#x227b;", escaped);
    }
}
