package org.threeten.extra.chrono;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.time.Clock;
import java.time.DateTimeException;
import java.time.DayOfWeek;
import java.time.Instant;
import java.time.Month;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.chrono.ChronoZonedDateTime;
import java.time.chrono.Era;
import java.time.chrono.ThaiBuddhistEra;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.ValueRange;
import java.util.List;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.time.MockClock;
import org.evosuite.runtime.mock.java.time.MockInstant;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class AccountingChronology_ESTest_test27 extends AccountingChronology_ESTest_scaffolding {

    /**
     * Verifies that dateNow(Clock) returns a non-null AccountingDate when called with
     * a system-default-zone clock on a 4-5-4 quarterly accounting chronology ending on
     * Tuesday in the last week of January (year offset 1, leap week in month 1).
     */
    @Test(timeout = 4000)
    public void test27_dateNow_withSystemClockReturnsNonNullAccountingDate() throws Throwable {
        // Configure a quarterly 4-5-4 accounting year that ends on the Tuesday
        // falling within the last week of January, with the leap week in month 1.
        DayOfWeek yearEndsOnTuesday = DayOfWeek.TUESDAY;
        Month yearEndMonth = Month.JANUARY;
        boolean endsInLastWeekOfMonth = true;
        AccountingYearDivision quarterlyDivision = AccountingYearDivision.QUARTERS_OF_PATTERN_4_5_4_WEEKS;
        int leapWeekInMonth = 1;
        int yearOffset = 1;

        AccountingChronology chronology = AccountingChronology.create(
                yearEndsOnTuesday, yearEndMonth, endsInLastWeekOfMonth,
                quarterlyDivision, leapWeekInMonth, yearOffset);

        // Obtain today's date using a mocked system-default-zone clock
        Clock systemDefaultZoneClock = MockClock.systemDefaultZone();
        AccountingDate today = chronology.dateNow(systemDefaultZoneClock);

        assertNotNull(today);
    }
}
