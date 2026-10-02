package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.time.Clock;
import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.OffsetDateTime;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.chrono.JapaneseDate;
import java.time.chrono.ThaiBuddhistDate;
import java.time.temporal.ChronoField;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalField;
import java.time.temporal.UnsupportedTemporalTypeException;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.time.MockClock;
import org.evosuite.runtime.mock.java.time.MockLocalDateTime;
import org.evosuite.runtime.mock.java.time.MockOffsetDateTime;
import org.evosuite.runtime.mock.java.time.MockYearMonth;
import org.evosuite.runtime.mock.java.time.chrono.MockHijrahDate;
import org.evosuite.runtime.mock.java.time.chrono.MockJapaneseDate;
import org.evosuite.runtime.mock.java.time.chrono.MockMinguoDate;
import org.evosuite.runtime.mock.java.time.chrono.MockThaiBuddhistDate;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class DayOfMonth_ESTest_test02 extends DayOfMonth_ESTest_scaffolding {

    // The mocked system clock fixes the current date to the 14th of the month
    private static final int MOCKED_CURRENT_DAY = 14;

    @Test(timeout = 4000)
    public void test02() throws Throwable {
        // currentDay reflects the mocked system clock (day 14)
        DayOfMonth currentDay = DayOfMonth.now();
        // firstDay represents the 1st of any month
        DayOfMonth firstDay = DayOfMonth.of(1);

        // Two distinct day-of-month values should not be equal
        boolean daysAreEqual = currentDay.equals(firstDay);
        assertFalse(daysAreEqual);

        // Confirm that the mocked clock indeed produced day 14
        assertEquals(MOCKED_CURRENT_DAY, currentDay.getValue());

        // Equality must be symmetric: firstDay (1) != currentDay (14) as well
        assertFalse(firstDay.equals((Object) currentDay));
    }
}
