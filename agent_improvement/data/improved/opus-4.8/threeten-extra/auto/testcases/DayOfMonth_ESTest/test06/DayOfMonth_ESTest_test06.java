package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.Temporal;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.time.MockClock;
import org.evosuite.runtime.mock.java.time.MockLocalDateTime;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class DayOfMonth_ESTest_test06 extends DayOfMonth_ESTest_scaffolding {

    /**
     * Adjusting a date-time with the DayOfMonth taken from that same date-time
     * is a no-op: the day-of-month already matches, so adjustInto returns the
     * very same Temporal instance.
     */
    @Test(timeout = 4000)
    public void adjustIntoReturnsSameTemporalWhenDayAlreadyMatches() throws Throwable {
        Clock utcClock = MockClock.system(ZoneOffset.UTC);
        LocalDateTime dateTime = MockLocalDateTime.now(utcClock);
        DayOfMonth dayOfMonth = DayOfMonth.from(dateTime);

        Temporal adjusted = dayOfMonth.adjustInto(dateTime);

        assertSame(dateTime, adjusted);
    }
}
