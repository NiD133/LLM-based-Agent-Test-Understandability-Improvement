package org.threeten.extra;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.time.YearMonth;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class DayOfMonth_ESTest_test10 extends DayOfMonth_ESTest_scaffolding {

    /**
     * A null year-month is never valid for a day-of-month, so isValidYearMonth
     * should return false. Under the mocked clock the current day-of-month is 14.
     */
    @Test(timeout = 4000)
    public void isValidYearMonth_returnsFalse_whenYearMonthIsNull() throws Throwable {
        DayOfMonth currentDay = DayOfMonth.now();

        boolean validForNullYearMonth = currentDay.isValidYearMonth((YearMonth) null);

        assertEquals(14, currentDay.getValue());
        assertFalse(validForNullYearMonth);
    }
}
