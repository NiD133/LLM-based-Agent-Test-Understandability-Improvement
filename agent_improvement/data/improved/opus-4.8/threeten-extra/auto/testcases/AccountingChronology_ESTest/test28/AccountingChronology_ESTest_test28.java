package org.threeten.extra.chrono;

import org.junit.Test;
import static org.junit.Assert.*;
import java.time.DayOfWeek;
import java.time.Month;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class AccountingChronology_ESTest_test28 extends AccountingChronology_ESTest_scaffolding {

    /**
     * Verifies that an AccountingChronology can produce a date from a
     * proleptic-year and day-of-year via {@link AccountingChronology#dateYearDay(int, int)}.
     */
    @Test(timeout = 4000)
    public void dateYearDay_returnsDateForFirstDayOfYear() throws Throwable {
        // Build a chronology whose accounting year ends on the Thursday in the
        // last week of March, with quarters following the 5-4-4 week pattern.
        AccountingChronology chronology = AccountingChronology.create(
                DayOfWeek.THURSDAY,
                Month.MARCH,
                /* inLastWeek */ true,
                AccountingYearDivision.QUARTERS_OF_PATTERN_5_4_4_WEEKS,
                /* leapWeekInMonth */ 1,
                /* yearOffset */ 1);

        // Request the first day of proleptic-year 1.
        AccountingDate firstDayOfYearOne = chronology.dateYearDay(1, 1);

        assertNotNull(firstDayOfYearOne);
    }
}
