package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import java.time.temporal.ChronoField;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class DayOfYear_ESTest_test11 extends DayOfYear_ESTest_scaffolding {

    /**
     * Verifies that getLong(DAY_OF_YEAR) returns the day-of-year value
     * matching the mocked current date (day 45).
     */
    @Test(timeout = 4000)
    public void test_getLong_withDayOfYearField_returnsCurrentDayValue() throws Throwable {
        DayOfYear currentDay = DayOfYear.now();
        long dayValue = currentDay.getLong(ChronoField.DAY_OF_YEAR);
        assertEquals(45L, dayValue);
    }
}
