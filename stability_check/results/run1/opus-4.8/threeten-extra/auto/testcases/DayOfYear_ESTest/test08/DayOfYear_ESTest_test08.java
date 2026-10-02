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
     * Day 366 is only valid in a leap year, so isValidYear returns true
     * exactly when the given year is a leap year. The year -488 is a leap
     * year (divisible by 4, not by 100), so isValidYear must return true.
     */
    @Test(timeout = 4000)
    public void isValidYearReturnsTrueForLeapYearOnDay366() throws Throwable {
        DayOfYear day366 = DayOfYear.of(366);

        boolean validForLeapYear = day366.isValidYear(-488);

        assertEquals(366, day366.getValue());
        assertTrue(validForLeapYear);
    }
}
