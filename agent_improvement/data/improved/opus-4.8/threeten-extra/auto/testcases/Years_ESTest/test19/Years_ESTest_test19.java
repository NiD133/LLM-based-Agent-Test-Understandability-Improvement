package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Years_ESTest_test19 extends Years_ESTest_scaffolding {

    /**
     * Verifies that a zero-year amount is not considered negative
     * and reports an amount of zero.
     */
    @Test(timeout = 4000)
    public void zeroYearsIsNotNegativeAndHasZeroAmount() throws Throwable {
        Years zeroYears = Years.of(0);

        assertFalse("Zero years should not be negative", zeroYears.isNegative());
        assertEquals("Amount should be zero", 0, zeroYears.getAmount());
    }
}
