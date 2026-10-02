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
public class AccountingChronology_ESTest_test06 extends AccountingChronology_ESTest_scaffolding {

    /**
     * Two AccountingChronology instances that differ only in the inLastWeek flag
     * must not be considered equal.
     *
     * inLastWeek=false means the year ends nearest to the last day of the month
     * (may spill into the next month), while inLastWeek=true means it ends
     * within the last week of the month. This is a meaningful semantic difference
     * and must be reflected in equals().
     */
    @Test(timeout = 4000)
    public void test06() throws Throwable {
        DayOfWeek yearEndDay = DayOfWeek.FRIDAY;
        Month yearEndMonth = Month.FEBRUARY;
        AccountingYearDivision division = AccountingYearDivision.QUARTERS_OF_PATTERN_4_5_4_WEEKS;
        int leapWeekInMonth = 4;
        int yearOffset = 4;

        AccountingChronology chronologyNearestEnd =
                AccountingChronology.create(yearEndDay, yearEndMonth, false, division, leapWeekInMonth, yearOffset);
        AccountingChronology chronologyLastWeek =
                AccountingChronology.create(yearEndDay, yearEndMonth, true, division, leapWeekInMonth, yearOffset);

        boolean areEqual = chronologyNearestEnd.equals(chronologyLastWeek);

        assertFalse("Chronologies with different inLastWeek settings must not be equal", areEqual);
    }
}
