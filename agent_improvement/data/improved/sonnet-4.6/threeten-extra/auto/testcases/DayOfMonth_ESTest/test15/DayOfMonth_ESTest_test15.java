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
public class DayOfMonth_ESTest_test15 extends DayOfMonth_ESTest_scaffolding {

    /**
     * Verifies that isSupported returns false when passed a null TemporalField,
     * and that DayOfMonth.now() reflects the mocked current day (14).
     * The isSupported contract specifies null returns false without throwing.
     */
    @Test(timeout = 4000)
    public void test_isSupported_withNullField_returnsFalse() throws Throwable {
        DayOfMonth currentDay = DayOfMonth.now();

        boolean nullFieldSupported = currentDay.isSupported((TemporalField) null);

        assertEquals(14, currentDay.getValue());
        assertFalse(nullFieldSupported);
    }
}
