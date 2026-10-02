package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Years_ESTest_test15 extends Years_ESTest_scaffolding {

    /**
     * Years.ZERO represents an amount of zero years, so it is neither
     * positive nor negative: isPositive() is false while isZero() is true.
     */
    @Test(timeout = 4000)
    public void zeroYearsIsNotPositiveButIsZero() throws Throwable {
        Years zeroYears = Years.ZERO;

        assertFalse("zero years must not be positive", zeroYears.isPositive());
        assertTrue("zero years must report as zero", zeroYears.isZero());
    }
}
