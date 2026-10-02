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
public class DayOfMonth_ESTest_test20 extends DayOfMonth_ESTest_scaffolding {

    /**
     * Combining the current day-of-month with the current year-month does not
     * change the day-of-month value held by the original instance, since
     * {@link DayOfMonth} is immutable.
     */
    @Test(timeout = 4000)
    public void atYearMonthLeavesOriginalDayOfMonthUnchanged() throws Throwable {
        DayOfMonth currentDayOfMonth = DayOfMonth.now();
        YearMonth currentYearMonth = MockYearMonth.now();

        currentDayOfMonth.atYearMonth(currentYearMonth);

        assertEquals(14, currentDayOfMonth.getValue());
    }
}
