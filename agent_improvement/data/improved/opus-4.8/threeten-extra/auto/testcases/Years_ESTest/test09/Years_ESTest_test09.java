package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Years_ESTest_test09 extends Years_ESTest_scaffolding {

    /**
     * Verifies that {@link Years#abs()} converts a negative amount to its
     * positive counterpart while leaving the original (immutable) instance
     * unchanged.
     */
    @Test(timeout = 4000)
    public void absReturnsPositiveAmountAndLeavesOriginalUnchanged() throws Throwable {
        Years negativeYears = Years.of(-395);

        Years absoluteYears = negativeYears.abs();

        assertEquals("original amount should remain negative", -395, negativeYears.getAmount());
        assertEquals("abs() should return the positive amount", 395, absoluteYears.getAmount());
    }
}
