package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Quarter_ESTest_test23 extends Quarter_ESTest_scaffolding {

    /**
     * Verifies that {@link Quarter#ofMonth(int)} maps a month in the second
     * quarter (April, month 4) to {@link Quarter#Q2}.
     */
    @Test(timeout = 4000)
    public void ofMonthForAprilReturnsSecondQuarter() throws Throwable {
        Quarter quarterForApril = Quarter.ofMonth(4);

        assertEquals(Quarter.Q2, quarterForApril);
    }
}
