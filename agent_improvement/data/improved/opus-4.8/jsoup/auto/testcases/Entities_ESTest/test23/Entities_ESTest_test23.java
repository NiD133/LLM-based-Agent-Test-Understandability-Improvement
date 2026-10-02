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
     * When escaping against an ASCII charset, characters outside the ASCII range
     * that have no named HTML entity are emitted as numeric hexadecimal character
     * references. Here the non-ASCII '≻' (SUCCEEDS) becomes "&#x227b;", while
     * the surrounding ASCII text is left untouched.
     */
    @Test(timeout = 4000)
    public void escapeNonAsciiCharAgainstAsciiCharsetEmitsHexReference() throws Throwable {
        Document.OutputSettings asciiOutputSettings = new Document.OutputSettings();
        asciiOutputSettings.charset("ascii");

        String escaped = Entities.escape("ethiH_p3≻", asciiOutputSettings);

        assertEquals("ethiH_p3&#x227b;", escaped);
    }
}
