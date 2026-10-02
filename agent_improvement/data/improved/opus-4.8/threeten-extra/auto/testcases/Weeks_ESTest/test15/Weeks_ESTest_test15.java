package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Weeks_ESTest_test15 extends Weeks_ESTest_scaffolding {

    /**
     * Verifies that a zero-week amount is considered neither positive nor negative.
     */
    @Test(timeout = 4000)
    public void zeroWeeks_isNeitherPositiveNorNegative() throws Throwable {
        Weeks zeroWeeks = Weeks.ZERO;

        assertFalse("zero weeks should not be positive", zeroWeeks.isPositive());
        assertFalse("zero weeks should not be negative", zeroWeeks.isNegative());
    }
}
