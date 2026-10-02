package org.jsoup.nodes;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Entities_ESTest_test00 extends Entities_ESTest_scaffolding {

    /**
     * Escaping an empty string should yield an empty string,
     * since there are no characters that need HTML escaping.
     */
    @Test(timeout = 4000)
    public void escapeEmptyStringReturnsEmptyString() throws Throwable {
        String escaped = Entities.escape("");

        assertEquals("", escaped);
    }
}
