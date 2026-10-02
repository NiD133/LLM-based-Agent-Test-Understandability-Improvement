package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Hours_ESTest_test15 extends Hours_ESTest_scaffolding {

    /**
     * Verifies that the ZERO constant represents an amount of zero hours,
     * and therefore is not considered positive.
     */
    @Test(timeout = 4000)
    public void zeroHoursIsNotPositive() throws Throwable {
        Hours zeroHours = Hours.ZERO;

        assertFalse("zero hours must not be positive", zeroHours.isPositive());
        assertEquals("ZERO must hold an amount of 0", 0, zeroHours.getAmount());
    }
}
