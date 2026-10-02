package org.apache.commons.io;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class IOCase_ESTest_test13 extends IOCase_ESTest_scaffolding {

    /**
     * Verifies that {@link IOCase#checkEquals(String, String)} returns false
     * when the two strings differ entirely, regardless of the SYSTEM
     * case-sensitivity rule.
     */
    @Test(timeout = 4000)
    public void checkEqualsReturnsFalseForDifferentStrings() throws Throwable {
        IOCase systemCase = IOCase.SYSTEM;

        boolean areEqual = systemCase.checkEquals("zmIl1;deJ|AOW", "^J# C/>cH!\"$");

        assertFalse(areEqual);
    }
}
