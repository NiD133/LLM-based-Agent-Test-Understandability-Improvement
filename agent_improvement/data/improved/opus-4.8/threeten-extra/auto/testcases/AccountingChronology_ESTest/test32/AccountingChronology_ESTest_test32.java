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
public class AccountingChronology_ESTest_test32 extends AccountingChronology_ESTest_scaffolding {

    /**
     * Verifies that an AccountingChronology can build a valid date from
     * proleptic-year, month and day-of-month values.
     */
    @Test(timeout = 4000)
    public void date_fromYearMonthDay_returnsNonNullDate() throws Throwable {
        // Build a chronology whose accounting year ends on a Wednesday in the
        // last week of April, divided into quarters using the 4-5-4 week pattern.
        int leapWeekInMonth = 2;
        int yearOffset = 2;
        AccountingChronology chronology = AccountingChronology.create(
                DayOfWeek.WEDNESDAY,
                Month.APRIL,
                true,
                AccountingYearDivision.QUARTERS_OF_PATTERN_4_5_4_WEEKS,
                leapWeekInMonth,
                yearOffset);

        // Create the first day of the first month of year 1.
        AccountingDate firstDate = chronology.date(1, 1, 1);

        assertNotNull(firstDate);
    }
}
