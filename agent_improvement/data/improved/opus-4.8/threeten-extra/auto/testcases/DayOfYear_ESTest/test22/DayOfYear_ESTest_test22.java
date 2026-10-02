package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import java.time.Year;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.time.MockYear;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class DayOfYear_ESTest_test22 extends DayOfYear_ESTest_scaffolding {

    /**
     * Combining a day-of-year with a year produces a date but must not mutate
     * the original DayOfYear instance, since DayOfYear is immutable.
     *
     * <p>Under the mocked clock the current day-of-year is 45, so its value
     * stays 45 before and after calling {@link DayOfYear#atYear(Year)}.
     */
    @Test(timeout = 4000)
    public void atYearLeavesDayOfYearValueUnchanged() throws Throwable {
        DayOfYear currentDayOfYear = DayOfYear.now();
        Year currentYear = MockYear.now();

        currentDayOfYear.atYear(currentYear);

        assertEquals(45, currentDayOfYear.getValue());
    }
}
