package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import java.time.OffsetDateTime;
import java.time.YearMonth;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.time.MockOffsetDateTime;
import org.evosuite.runtime.mock.java.time.MockYearMonth;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class DayOfMonth_ESTest_test09 extends DayOfMonth_ESTest_scaffolding {

    /**
     * Under the EvoSuite mock clock, the current date is fixed to the 14th of the month.
     * A DayOfMonth extracted from "now" should therefore hold the value 14, and that day
     * should be valid for the current (mocked) year-month.
     */
    @Test(timeout = 4000)
    public void test09() throws Throwable {
        // Build a DayOfMonth from the current date-time (mocked, day-of-month = 14).
        OffsetDateTime nowDateTime = MockOffsetDateTime.now();
        DayOfMonth dayOfMonth = DayOfMonth.from(nowDateTime);

        // The 14th is a valid day for the current (mocked) year-month.
        YearMonth currentYearMonth = MockYearMonth.now();
        boolean validForYearMonth = dayOfMonth.isValidYearMonth(currentYearMonth);

        assertTrue(validForYearMonth);
        assertEquals(14, dayOfMonth.getValue());
    }
}
