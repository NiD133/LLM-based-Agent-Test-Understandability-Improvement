package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Days_ESTest_test15 extends Days_ESTest_scaffolding {

    /**
     * Verifies that the ZERO constant holds an amount of zero days
     * and is therefore not considered positive.
     */
    @Test(timeout = 4000)
    public void zeroDays_isNotPositive_andHasAmountZero() throws Throwable {
        Days zeroDays = Days.ZERO;

        assertFalse("ZERO must not be positive", zeroDays.isPositive());
        assertEquals("ZERO must hold an amount of 0 days", 0, zeroDays.getAmount());
    }
}
