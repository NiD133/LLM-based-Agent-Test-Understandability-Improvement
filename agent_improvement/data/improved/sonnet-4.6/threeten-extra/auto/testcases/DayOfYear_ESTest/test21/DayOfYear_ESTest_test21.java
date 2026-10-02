package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import java.time.LocalDate;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class DayOfYear_ESTest_test21 extends DayOfYear_ESTest_scaffolding {

    /**
     * Verifies that converting the current day-of-year to a LocalDate and back
     * produces the same day-of-year value (day 45 under the mocked clock).
     */
    @Test(timeout = 4000)
    public void test_roundTripThroughLocalDate_preservesDayOfYear() throws Throwable {
        // Obtain the current day-of-year (mocked to day 45)
        DayOfYear currentDay = DayOfYear.now();

        // Attach it to year 1 to form a concrete date, then extract the day-of-year back
        LocalDate date = currentDay.atYear(1);
        DayOfYear roundTripped = DayOfYear.from(date);

        assertEquals(45, roundTripped.getValue());
    }
}
