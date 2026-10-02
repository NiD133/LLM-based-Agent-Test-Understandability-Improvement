package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class DayOfYear_ESTest_test07 extends DayOfYear_ESTest_scaffolding {

    /**
     * Day 366 only exists in a leap year. The year 366 is not a leap year
     * (it is not divisible by 4), so it cannot accommodate the 366th day.
     * Therefore {@code isValidYear} must report the pairing as invalid.
     */
    @Test(timeout = 4000)
    public void dayOfYear366_isNotValidInNonLeapYear() throws Throwable {
        DayOfYear lastDayOfLeapYear = DayOfYear.of(366);

        boolean validInYear366 = lastDayOfLeapYear.isValidYear(366);

        assertFalse(
                "Day 366 should not be valid in year 366, which is not a leap year",
                validInYear366);
    }
}
