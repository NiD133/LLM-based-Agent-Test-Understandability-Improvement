package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Weeks_ESTest_test15 extends Weeks_ESTest_scaffolding {

    /**
     * Weeks.ZERO should be neither positive nor negative.
     * isPositive() returns true only for amounts > 0, so zero must return false.
     * isNegative() returns true only for amounts < 0, so zero must also return false.
     */
    @Test(timeout = 4000)
    public void weeksZeroIsNeitherPositiveNorNegative() throws Throwable {
        Weeks zeroWeeks = Weeks.ZERO;

        boolean isPositive = zeroWeeks.isPositive();
        assertFalse("Weeks.ZERO should not be positive", isPositive);
        assertFalse("Weeks.ZERO should not be negative", zeroWeeks.isNegative());
    }
}
