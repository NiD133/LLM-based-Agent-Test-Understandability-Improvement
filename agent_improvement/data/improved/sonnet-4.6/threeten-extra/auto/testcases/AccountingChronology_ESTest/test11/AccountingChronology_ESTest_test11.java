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
public class AccountingChronology_ESTest_test11 extends AccountingChronology_ESTest_scaffolding {

    /**
     * Verifies that querying the PROLEPTIC_MONTH field range on a 13-month
     * AccountingChronology returns a non-null ValueRange.
     *
     * The chronology is configured to:
     *   - end each year on a Saturday
     *   - anchor to the last week of January
     *   - divide the year into 13 equal months of 4 weeks
     *   - place the leap week in month 4
     *   - use a year offset of 4
     *
     * Because the division is THIRTEEN_EVEN_MONTHS_OF_4_WEEKS, the chronology
     * should return the 13-month proleptic-month range (PROLEPTIC_MONTH_RANGE_13).
     */
    @Test(timeout = 4000)
    public void test11() throws Throwable {
        DayOfWeek yearEndsOnSaturday = DayOfWeek.SATURDAY;
        Month anchorMonth = Month.JANUARY;
        // true = year ends in the last week of the anchor month
        boolean endsInLastWeek = true;
        AccountingYearDivision thirteenMonthDivision = AccountingYearDivision.THIRTEEN_EVEN_MONTHS_OF_4_WEEKS;
        int leapWeekInMonth = 4;
        int yearOffset = 4;

        AccountingChronology chronology = AccountingChronology.create(
                yearEndsOnSaturday, anchorMonth, endsInLastWeek,
                thirteenMonthDivision, leapWeekInMonth, yearOffset);

        ValueRange prolepticMonthRange = chronology.range(ChronoField.PROLEPTIC_MONTH);

        assertNotNull(prolepticMonthRange);
    }
}
