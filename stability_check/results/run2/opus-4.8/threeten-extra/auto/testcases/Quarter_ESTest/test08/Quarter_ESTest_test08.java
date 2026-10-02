package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Quarter_ESTest_test08 extends Quarter_ESTest_scaffolding {

    /**
     * The third quarter (July to September) always spans 92 days,
     * regardless of whether the year is a leap year.
     */
    @Test(timeout = 4000)
    public void thirdQuarterLengthIsAlwaysNinetyTwoDays() throws Throwable {
        Quarter thirdQuarter = Quarter.Q3;

        int lengthInLeapYear = thirdQuarter.length(true);

        assertEquals(92, lengthInLeapYear);
    }
}
