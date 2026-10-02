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
public class AccountingChronology_ESTest_test12 extends AccountingChronology_ESTest_scaffolding {

    /**
     * Verifies that querying the PROLEPTIC_MONTH range on a valid AccountingChronology
     * returns a non-null ValueRange.
     *
     * The chronology is configured as:
     *   - Year ends on TUESDAY
     *   - Year end is based on JANUARY
     *   - inLastWeek = true  (year ends in the last week of January, not nearest end-of-month)
     *   - Year divided into QUARTERS_OF_PATTERN_4_5_4_WEEKS (12-period year)
     *   - Leap week placed in period 1
     *   - Year offset = 1
     *
     * Because QUARTERS_OF_PATTERN_4_5_4_WEEKS is a 12-month division,
     * the PROLEPTIC_MONTH range should be the 12-month variant (not null).
     */
    @Test(timeout = 4000)
    public void test_range_prolepticMonth_returnsNonNull_for12PeriodQuarterlyChronology() throws Throwable {
        DayOfWeek yearEndsOnDay = DayOfWeek.TUESDAY;
        Month yearEndMonth = Month.JANUARY;
        boolean endsInLastWeek = true;
        AccountingYearDivision quarterlyDivision = AccountingYearDivision.QUARTERS_OF_PATTERN_4_5_4_WEEKS;
        int leapWeekInPeriod1 = 1;
        int yearOffset = 1;

        AccountingChronology chronology = AccountingChronology.create(
                yearEndsOnDay, yearEndMonth, endsInLastWeek, quarterlyDivision, leapWeekInPeriod1, yearOffset);

        ValueRange prolepticMonthRange = chronology.range(ChronoField.PROLEPTIC_MONTH);

        assertNotNull(prolepticMonthRange);
    }
}
