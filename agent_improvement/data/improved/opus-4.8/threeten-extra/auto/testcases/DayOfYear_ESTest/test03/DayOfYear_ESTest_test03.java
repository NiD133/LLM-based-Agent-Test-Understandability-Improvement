package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import java.time.Clock;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.time.MockClock;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class DayOfYear_ESTest_test03 extends DayOfYear_ESTest_scaffolding {

    /**
     * A DayOfYear is never equal to an object of a different type (here a Clock),
     * and DayOfYear.now() reflects the day-of-year of the (mocked) current date.
     */
    @Test(timeout = 4000)
    public void equalsReturnsFalseForNonDayOfYearArgument() throws Throwable {
        DayOfYear today = DayOfYear.now();
        Clock someClock = MockClock.systemUTC();

        boolean equalsClock = today.equals(someClock);

        assertFalse("DayOfYear should not equal a Clock instance", equalsClock);
        assertEquals("Mocked system date is the 45th day of the year", 45, today.getValue());
    }
}
