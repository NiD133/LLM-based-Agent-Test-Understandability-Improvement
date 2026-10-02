package org.apache.commons.io;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class IOCase_ESTest_test19 extends IOCase_ESTest_scaffolding {

    /**
     * Verifies that checkEndsWith returns false when both the string and the
     * suffix are null, regardless of case-sensitivity mode.
     */
    @Test(timeout = 4000)
    public void test_checkEndsWith_returnsFalse_whenBothStrAndEndAreNull() throws Throwable {
        boolean result = IOCase.SENSITIVE.checkEndsWith(null, null);
        assertFalse(result);
    }
}
