package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.time.temporal.Temporal;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.time.MockLocalDate;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class DayOfYear_ESTest_test05 extends DayOfYear_ESTest_scaffolding {

    /**
     * Adjusting an ISO-based LocalDate to today's day-of-year is a no-op,
     * so adjustInto returns the very same LocalDate instance it was given.
     */
    @Test(timeout = 4000)
    public void adjustIntoTodaysDateReturnsSameInstance() throws Throwable {
        DayOfYear today = DayOfYear.now();
        LocalDate todaysDate = MockLocalDate.now();

        Temporal adjustedDate = today.adjustInto(todaysDate);

        assertSame(todaysDate, adjustedDate);
    }
}
