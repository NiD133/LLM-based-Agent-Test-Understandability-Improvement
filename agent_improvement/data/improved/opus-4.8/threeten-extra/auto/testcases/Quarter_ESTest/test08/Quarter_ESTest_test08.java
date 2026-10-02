package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Quarter_ESTest_test08 extends Quarter_ESTest_scaffolding {

    /**
     * Q3 (July to September) always spans 92 days, regardless of whether the
     * year is a leap year.
     */
    @Test(timeout = 4000)
    public void thirdQuarterLengthInLeapYearIs92Days() throws Throwable {
        boolean leapYear = true;

        int lengthInDays = Quarter.Q3.length(leapYear);

        assertEquals(92, lengthInDays);
    }
}
