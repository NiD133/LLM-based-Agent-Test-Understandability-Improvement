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
public class AccountingChronology_ESTest_test18 extends AccountingChronology_ESTest_scaffolding {

    /**
     * Verifies that proleptic year 0 (i.e., the year before year 1, a BCE year) is not a leap year
     * in an accounting chronology configured to end on TUESDAY in the last week of JANUARY,
     * divided into 4-5-4 quarters with the leap week placed in month 9.
     */
    @Test(timeout = 4000)
    public void test_isLeapYear_returnsFalse_forYearZero() throws Throwable {
        DayOfWeek yearEndDay = DayOfWeek.TUESDAY;
        Month yearEndMonth = Month.JANUARY;
        AccountingYearDivision quarterDivision = AccountingYearDivision.QUARTERS_OF_PATTERN_4_5_4_WEEKS;
        int leapWeekMonth = 9;
        int yearOffset = 0;

        AccountingChronology chronology = AccountingChronology.create(
                yearEndDay, yearEndMonth, /* inLastWeek= */ true, quarterDivision, leapWeekMonth, yearOffset);

        boolean isLeap = chronology.isLeapYear(0);

        assertFalse(isLeap);
    }
}
