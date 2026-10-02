package org.jsoup.nodes;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Entities_ESTest_test13 extends Entities_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test_escapeNullInputReturnsEmptyString() throws Throwable {
        // Cast disambiguates the single-arg overload; null input should be handled gracefully
        String escaped = Entities.escape((String) null);
        assertEquals("", escaped);
    }
}
