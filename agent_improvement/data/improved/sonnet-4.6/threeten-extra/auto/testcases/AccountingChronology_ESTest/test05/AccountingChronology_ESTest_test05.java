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
public class AccountingChronology_ESTest_test05 extends AccountingChronology_ESTest_scaffolding {

    /**
     * Two AccountingChronology instances that differ only in their fiscal year-end month
     * (October vs November) must not be considered equal.
     */
    @Test(timeout = 4000)
    public void test05_chronologiesWithDifferentYearEndMonthsAreNotEqual() throws Throwable {
        DayOfWeek yearEndDay = DayOfWeek.SATURDAY;
        AccountingYearDivision division = AccountingYearDivision.QUARTERS_OF_PATTERN_5_4_4_WEEKS;
        int leapWeekInMonth = 4;
        int yearOffset = 4;

        // Both chronologies share all settings except the fiscal year-end month
        AccountingChronology chronologyEndingInOctober = AccountingChronology.create(
                yearEndDay, Month.OCTOBER, true, division, leapWeekInMonth, yearOffset);
        AccountingChronology chronologyEndingInNovember = AccountingChronology.create(
                yearEndDay, Month.NOVEMBER, true, division, leapWeekInMonth, yearOffset);

        boolean areEqual = chronologyEndingInNovember.equals(chronologyEndingInOctober);

        assertFalse("Chronologies with different year-end months should not be equal", areEqual);
    }
}
