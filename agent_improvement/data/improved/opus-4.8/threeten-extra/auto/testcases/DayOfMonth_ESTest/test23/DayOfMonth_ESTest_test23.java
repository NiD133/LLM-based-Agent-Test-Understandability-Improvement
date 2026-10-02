package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.time.MockClock;
import org.evosuite.runtime.mock.java.time.MockLocalDateTime;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class DayOfMonth_ESTest_test23 extends DayOfMonth_ESTest_scaffolding {

    /**
     * Verifies that DayOfMonth.from(...) extracts the day-of-month from a
     * date-time taken from the (mocked) system clock, and that calling
     * hashCode() does not disturb the stored value.
     *
     * Under EvoSuite's mocked clock the current date is fixed, so the
     * day-of-month is deterministically 14.
     */
    @Test(timeout = 4000)
    public void from_currentDateTime_extractsDayOfMonthAndKeepsValueAfterHashCode() throws Throwable {
        int expectedDayOfMonth = 14;

        Clock utcSystemClock = MockClock.system(ZoneOffset.UTC);
        LocalDateTime currentDateTime = MockLocalDateTime.now(utcSystemClock);

        DayOfMonth dayOfMonth = DayOfMonth.from(currentDateTime);
        dayOfMonth.hashCode();

        assertEquals(expectedDayOfMonth, dayOfMonth.getValue());
    }
}
