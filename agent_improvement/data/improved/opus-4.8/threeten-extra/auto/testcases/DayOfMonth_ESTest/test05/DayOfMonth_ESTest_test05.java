package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.fail;
import static org.evosuite.runtime.EvoAssertions.verifyException;
import java.time.Clock;
import java.time.DateTimeException;
import java.time.chrono.JapaneseDate;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.time.MockClock;
import org.evosuite.runtime.mock.java.time.chrono.MockJapaneseDate;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class DayOfMonth_ESTest_test05 extends DayOfMonth_ESTest_scaffolding {

    /**
     * adjustInto requires the target temporal to use the ISO calendar system.
     * Adjusting a non-ISO temporal (here a JapaneseDate) must fail with a
     * DateTimeException stating that adjustment is only supported on ISO date-time.
     */
    @Test(timeout = 4000)
    public void adjustIntoNonIsoTemporalThrowsDateTimeException() throws Throwable {
        DayOfMonth dayOfMonth = DayOfMonth.now();
        Clock utcClock = MockClock.systemUTC();
        JapaneseDate nonIsoDate = MockJapaneseDate.now(utcClock);

        try {
            dayOfMonth.adjustInto(nonIsoDate);
            fail("Expecting exception: DateTimeException");
        } catch (DateTimeException e) {
            // Message: "Adjustment only supported on ISO date-time"
            verifyException("org.threeten.extra.DayOfMonth", e);
        }
    }
}
