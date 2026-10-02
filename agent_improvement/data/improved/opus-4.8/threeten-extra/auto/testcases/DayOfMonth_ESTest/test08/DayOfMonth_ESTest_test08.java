package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import java.time.YearMonth;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.time.MockYearMonth;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class DayOfMonth_ESTest_test08 extends DayOfMonth_ESTest_scaffolding {

    /**
     * The 31st day is not valid for the current (mocked) year-month, because that
     * month has fewer than 31 days. {@code isValidYearMonth} should therefore
     * report the combination as invalid, while leaving the day value unchanged.
     */
    @Test(timeout = 4000)
    public void isValidYearMonth_returnsFalseWhenDayExceedsMonthLength() throws Throwable {
        DayOfMonth dayThirtyFirst = DayOfMonth.of(31);
        YearMonth currentYearMonth = MockYearMonth.now();

        boolean validForCurrentMonth = dayThirtyFirst.isValidYearMonth(currentYearMonth);

        assertFalse(validForCurrentMonth);
        assertEquals(31, dayThirtyFirst.getValue());
    }
}
