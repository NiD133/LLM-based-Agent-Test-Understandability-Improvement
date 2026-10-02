package org.threeten.extra.chrono;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.time.DayOfWeek;
import java.time.Month;
import java.time.temporal.ChronoField;
import java.time.temporal.ValueRange;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class AccountingChronology_ESTest_test15 extends AccountingChronology_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test15() throws Throwable {
        // Build a 4-5-4-quarter Accounting chronology ending on Wednesday nearest end of April,
        // with the leap week in month 2 and a year offset of 2.
        DayOfWeek endsOnWednesday = DayOfWeek.WEDNESDAY;
        Month endMonth = Month.APRIL;
        AccountingYearDivision quarterDivision = AccountingYearDivision.QUARTERS_OF_PATTERN_4_5_4_WEEKS;
        AccountingChronology chronology = AccountingChronology.create(
                endsOnWednesday, endMonth, /*inLastWeek=*/ true, quarterDivision, /*leapWeekInMonth=*/ 2, /*yearOffset=*/ 2);

        // ALIGNED_WEEK_OF_YEAR range is always defined for any valid AccountingChronology.
        ValueRange alignedWeekOfYearRange = chronology.range(ChronoField.ALIGNED_WEEK_OF_YEAR);
        assertNotNull(alignedWeekOfYearRange);
    }
}
