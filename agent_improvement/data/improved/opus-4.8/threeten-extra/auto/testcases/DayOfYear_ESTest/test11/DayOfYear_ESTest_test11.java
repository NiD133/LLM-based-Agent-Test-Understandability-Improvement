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
     * getLong(DAY_OF_YEAR) should return the day-of-year value as a long.
     * Under the EvoSuite mocked clock the current date resolves to the 45th
     * day of the year, so DayOfYear.now() represents day 45.
     */
    @Test(timeout = 4000)
    public void getLong_withDayOfYearField_returnsDayOfYearValue() throws Throwable {
        DayOfYear currentDayOfYear = DayOfYear.now();

        long dayOfYearValue = currentDayOfYear.getLong(ChronoField.DAY_OF_YEAR);

        assertEquals(45L, dayOfYearValue);
    }
}
