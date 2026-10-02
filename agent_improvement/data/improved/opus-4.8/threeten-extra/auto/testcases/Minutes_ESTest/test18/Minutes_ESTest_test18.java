package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Minutes_ESTest_test18 extends Minutes_ESTest_scaffolding {

    /**
     * Minutes.ZERO represents a value of zero minutes, which is neither
     * negative nor positive.
     */
    @Test(timeout = 4000)
    public void zeroMinutesIsNeitherNegativeNorPositive() throws Throwable {
        Minutes zero = Minutes.ZERO;

        assertFalse("zero should not be negative", zero.isNegative());
        assertFalse("zero should not be positive", zero.isPositive());
    }
}
