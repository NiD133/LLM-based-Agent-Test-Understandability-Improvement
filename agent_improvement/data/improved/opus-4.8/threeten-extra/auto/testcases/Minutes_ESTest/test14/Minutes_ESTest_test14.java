package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Minutes_ESTest_test14 extends Minutes_ESTest_scaffolding {

    /**
     * Verifies that the zero-minutes amount is considered neither positive nor negative.
     */
    @Test(timeout = 4000)
    public void zeroMinutesIsNeitherPositiveNorNegative() throws Throwable {
        Minutes zeroMinutes = Minutes.ZERO;

        assertFalse("zero minutes should not be positive", zeroMinutes.isPositive());
        assertFalse("zero minutes should not be negative", zeroMinutes.isNegative());
    }
}
