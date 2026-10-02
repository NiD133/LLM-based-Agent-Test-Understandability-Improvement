package org.jsoup.nodes;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Entities_ESTest_test03 extends Entities_ESTest_scaffolding {

    /**
     * When escaping against an ASCII charset, characters outside the ASCII range
     * must be replaced by HTML entity references, while plain ASCII characters
     * pass through unchanged. Here the section sign (U+00A7, "§") is not
     * representable in ASCII, so it is escaped to its named entity "&sect;".
     */
    @Test(timeout = 4000)
    public void escapeReplacesNonAsciiCharWithNamedEntity() throws Throwable {
        Document.OutputSettings asciiOutput = new Document.OutputSettings().charset("ascii");

        String escaped = Entities.escape("kp1D(Cu%~vB8caHD§", asciiOutput);

        assertEquals("kp1D(Cu%~vB8caHD&sect;", escaped);
    }
}
