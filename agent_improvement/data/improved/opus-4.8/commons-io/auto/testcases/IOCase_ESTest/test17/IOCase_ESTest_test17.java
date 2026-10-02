package org.apache.commons.io;

import static org.junit.Assert.assertFalse;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class IOCase_ESTest_test17 extends IOCase_ESTest_scaffolding {

    /**
     * Verifies that {@link IOCase#checkEndsWith(String, String)} returns false
     * when the suffix to compare against is null, regardless of the input string.
     */
    @Test(timeout = 4000)
    public void checkEndsWithNullSuffixReturnsFalse() throws Throwable {
        IOCase systemCase = IOCase.SYSTEM;

        boolean endsWith = systemCase.checkEndsWith("%pe;MQhIB_m,ru(g&", (String) null);

        assertFalse(endsWith);
    }
}
