package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class DayOfYear_ESTest_test08 extends DayOfYear_ESTest_scaffolding {

    /**
     * Day 366 only exists in leap years, so isValidYear returns true exactly
     * when the supplied year is a leap year. The year -488 is divisible by 4
     * (and not by 100), making it a leap year, so the day-of-year is valid.
     */
    @Test(timeout = 4000)
    public void day366_isValidForLeapYear() throws Throwable {
        DayOfYear lastDayOfLeapYear = DayOfYear.of(366);

        boolean validForLeapYear = lastDayOfLeapYear.isValidYear(-488);

        assertEquals("day-of-year value should be preserved", 366, lastDayOfLeapYear.getValue());
        assertTrue("day 366 must be valid in the leap year -488", validForLeapYear);
    }
}
