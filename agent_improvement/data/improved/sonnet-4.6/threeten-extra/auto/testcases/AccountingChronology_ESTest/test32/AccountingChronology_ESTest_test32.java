package org.threeten.extra.chrono;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.time.DayOfWeek;
import java.time.Month;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class AccountingChronology_ESTest_test32 extends AccountingChronology_ESTest_scaffolding {

    /**
     * Verifies that an AccountingDate for year 1, month 1, day 1 can be successfully created
     * using a chronology configured with Wednesday year-end, April fiscal year-end in the last
     * week, 4-5-4 quarterly division, leap week in month 2, and year offset 2.
     */
    @Test(timeout = 4000)
    public void test_createDate_yearOneMonthOneDay_withWednesdayAprilFiscalYearEnd() throws Throwable {
        AccountingChronology chronology = AccountingChronology.create(
                DayOfWeek.WEDNESDAY,
                Month.APRIL,
                true,  // year ends in last week of April
                AccountingYearDivision.QUARTERS_OF_PATTERN_4_5_4_WEEKS,
                2,     // leap week falls in month 2
                2      // year offset
        );

        AccountingDate firstDayOfFirstYear = chronology.date(1, 1, 1);

        assertNotNull(firstDayOfFirstYear);
    }
}
