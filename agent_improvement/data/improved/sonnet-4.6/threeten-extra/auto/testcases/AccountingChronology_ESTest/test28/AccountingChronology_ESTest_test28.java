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
public class AccountingChronology_ESTest_test28 extends AccountingChronology_ESTest_scaffolding {

    /**
     * Verifies that {@code dateYearDay} returns a non-null {@code AccountingDate}
     * for proleptic year 1, day-of-year 1 using a 5-4-4 quarterly calendar
     * that ends on Thursday in the last week of March with a year offset of 1.
     */
    @Test(timeout = 4000)
    public void test28() throws Throwable {
        // Set up a 5-4-4 quarterly accounting calendar ending on Thursday in the last week of March
        AccountingYearDivision quarterPattern = AccountingYearDivision.QUARTERS_OF_PATTERN_5_4_4_WEEKS;
        DayOfWeek yearEndDay = DayOfWeek.THURSDAY;
        Month yearEndMonth = Month.MARCH;
        AccountingChronology chronology = AccountingChronology.create(
                yearEndDay, yearEndMonth, /*inLastWeek=*/ true, quarterPattern, /*leapWeekInMonth=*/ 1, /*yearOffset=*/ 1);

        // Obtain the first day of accounting year 1 and confirm a valid date is returned
        AccountingDate firstDayOfYear1 = chronology.dateYearDay(1, 1);
        assertNotNull("dateYearDay(1, 1) should return a valid AccountingDate", firstDayOfYear1);
    }
}
