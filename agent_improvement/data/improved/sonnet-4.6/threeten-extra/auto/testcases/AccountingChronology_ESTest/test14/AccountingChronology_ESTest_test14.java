package org.threeten.extra.chrono;

import org.junit.Test;
import static org.junit.Assert.*;
import java.time.DayOfWeek;
import java.time.Month;
import java.time.temporal.ChronoField;
import java.time.temporal.ValueRange;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class AccountingChronology_ESTest_test14 extends AccountingChronology_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test_range_dayOfMonth_returnsNonNull_forQuarterDivisionChronology() throws Throwable {
        // Build a chronology ending on Wednesday in the last week of June,
        // divided into 4-4-5 quarters with the leap week in month 10.
        DayOfWeek yearEndDay = DayOfWeek.WEDNESDAY;
        Month yearEndMonth = Month.JUNE;
        boolean endsInLastWeek = true;
        AccountingYearDivision quarterDivision = AccountingYearDivision.QUARTERS_OF_PATTERN_4_4_5_WEEKS;
        int leapWeekInMonth = 10;
        int yearOffset = -1;

        AccountingChronology chronology = AccountingChronology.create(
                yearEndDay, yearEndMonth, endsInLastWeek, quarterDivision, leapWeekInMonth, yearOffset);

        ValueRange dayOfMonthRange = chronology.range(ChronoField.DAY_OF_MONTH);

        assertNotNull(dayOfMonthRange);
    }
}
