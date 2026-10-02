package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CharSetUtils_ESTest_test12 extends CharSetUtils_ESTest_scaffolding {

    // The '<' character does not appear in "org.apache.commons.lang3.CharSetUtils",
    // so containsAny should return false even when the charset array has trailing nulls.
    @Test(timeout = 4000)
    public void test12() throws Throwable {
        String[] charsetPatterns = new String[6];
        charsetPatterns[0] = "org.apache.commons.lang3.CharSetUtils";
        boolean result = CharSetUtils.containsAny("<", charsetPatterns);
        assertFalse(result);
    }
}
