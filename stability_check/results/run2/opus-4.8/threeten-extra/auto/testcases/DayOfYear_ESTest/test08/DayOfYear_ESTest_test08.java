package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class DayOfYear_ESTest_test08 extends DayOfYear_ESTest_scaffolding {

    /**
     * Day 366 is only valid in a leap year. The year -488 is a leap year
     * (divisible by 4 and not by 100), so isValidYear should return true.
     */
    @Test(timeout = 4000)
    public void day366IsValidInLeapYear() throws Throwable {
        DayOfYear lastDayOfYear = DayOfYear.of(366);

        boolean validInLeapYear = lastDayOfYear.isValidYear(-488);

        assertEquals(366, lastDayOfYear.getValue());
        assertTrue(validInLeapYear);
    }
}
