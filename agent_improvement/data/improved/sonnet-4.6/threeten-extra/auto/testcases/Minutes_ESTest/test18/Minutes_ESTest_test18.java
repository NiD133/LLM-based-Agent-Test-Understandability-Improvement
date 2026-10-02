package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Minutes_ESTest_test18 extends Minutes_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test_minutesZero_isNeitherNegativeNorPositive() throws Throwable {
        Minutes zero = Minutes.ZERO;
        assertFalse("Minutes.ZERO should not be negative", zero.isNegative());
        assertFalse("Minutes.ZERO should not be positive", zero.isPositive());
    }
}
