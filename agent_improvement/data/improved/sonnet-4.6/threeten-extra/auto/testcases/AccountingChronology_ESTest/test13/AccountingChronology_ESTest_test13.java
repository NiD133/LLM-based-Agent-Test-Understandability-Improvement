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
public class AccountingChronology_ESTest_test13 extends AccountingChronology_ESTest_scaffolding {

    /**
     * Verifies that querying the DAY_OF_YEAR field range on a 13-month
     * accounting chronology (ending on Fridays in the last week of July)
     * returns a non-null ValueRange.
     */
    @Test(timeout = 4000)
    public void test_dayOfYearRange_isNotNull_forThirteenMonthChronology() throws Throwable {
        // Build a 13-even-month accounting chronology whose fiscal year ends
        // on the last Friday of July, with the leap week placed in month 4,
        // and a year offset of 4.
        DayOfWeek yearEndsOnFriday = DayOfWeek.FRIDAY;
        Month yearEndsInJuly = Month.JULY;
        boolean endInLastWeekOfMonth = true;
        AccountingYearDivision thirteenEvenMonths = AccountingYearDivision.THIRTEEN_EVEN_MONTHS_OF_4_WEEKS;
        int leapWeekInMonth4 = 4;
        int yearOffset = 4;

        AccountingChronology thirteenMonthChronology = AccountingChronology.create(
                yearEndsOnFriday,
                yearEndsInJuly,
                endInLastWeekOfMonth,
                thirteenEvenMonths,
                leapWeekInMonth4,
                yearOffset);

        // DAY_OF_YEAR range for an accounting year is 1–364 (standard) or 1–371 (leap).
        ValueRange dayOfYearRange = thirteenMonthChronology.range(ChronoField.DAY_OF_YEAR);
        assertNotNull(dayOfYearRange);
    }
}
