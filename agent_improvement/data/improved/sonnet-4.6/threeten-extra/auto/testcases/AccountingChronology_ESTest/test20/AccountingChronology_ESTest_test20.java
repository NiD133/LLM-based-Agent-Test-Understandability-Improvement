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
public class AccountingChronology_ESTest_test20 extends AccountingChronology_ESTest_scaffolding {

    /**
     * Verifies that creating an AccountingChronology with a leap-week month number
     * that falls outside the valid month range (1-12) throws IllegalStateException.
     * Month 1426 is far beyond the 12-month range of QUARTERS_OF_PATTERN_4_4_5_WEEKS.
     */
    @Test(timeout = 4000)
    public void test20() throws Throwable {
        DayOfWeek yearEndDay = DayOfWeek.WEDNESDAY;
        Month nearestEndMonth = Month.JUNE;
        AccountingYearDivision yearDivision = AccountingYearDivision.QUARTERS_OF_PATTERN_4_4_5_WEEKS;
        int outOfRangeLeapWeekMonth = 1426;
        int yearOffset = -1;

        try {
            AccountingChronology.create(yearEndDay, nearestEndMonth, true, yearDivision, outOfRangeLeapWeekMonth, yearOffset);
            fail("Expecting exception: IllegalStateException");
        } catch (IllegalStateException e) {
            //
            // Leap week cannot not be placed in non-existent month 1426, range is [1 - 12].
            //
            verifyException("org.threeten.extra.chrono.AccountingChronology", e);
        }
    }
}
