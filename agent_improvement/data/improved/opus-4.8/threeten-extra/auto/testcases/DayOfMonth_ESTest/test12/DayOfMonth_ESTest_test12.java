package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import java.time.temporal.ChronoField;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class DayOfMonth_ESTest_test12 extends DayOfMonth_ESTest_scaffolding {

    /**
     * getLong(DAY_OF_MONTH) returns the day-of-month value as a long.
     * Under the mocked clock, DayOfMonth.now() resolves to the 14th of the month.
     */
    @Test(timeout = 4000)
    public void getLong_withDayOfMonthField_returnsCurrentDay() throws Throwable {
        DayOfMonth currentDay = DayOfMonth.now();

        long dayValue = currentDay.getLong(ChronoField.DAY_OF_MONTH);

        assertEquals(14L, dayValue);
    }
}
