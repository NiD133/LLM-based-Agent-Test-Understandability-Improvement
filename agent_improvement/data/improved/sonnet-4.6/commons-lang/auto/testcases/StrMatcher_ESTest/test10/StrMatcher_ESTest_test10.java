package org.apache.commons.lang3.text;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StrMatcher_ESTest_test10 extends StrMatcher_ESTest_scaffolding {

    // NoMatcher is a package-private static inner class that always returns 0 (no match).
    // This test verifies that it can be directly instantiated without error.
    @Test(timeout = 4000)
    public void test_noMatcher_canBeInstantiated() throws Throwable {
        StrMatcher.NoMatcher noMatcher = new StrMatcher.NoMatcher();
    }
}
