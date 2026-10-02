package org.jsoup.nodes;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Entities_ESTest_test00 extends Entities_ESTest_scaffolding {

    // Escaping an empty string should produce an empty string (no characters to escape).
    @Test(timeout = 4000)
    public void escape_withEmptyString_returnsEmptyString() throws Throwable {
        String result = Entities.escape("");
        assertEquals("", result);
    }
}
